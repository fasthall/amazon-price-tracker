/*
 * @Author Wei-Tsung Lin
 * @Date 01/30/2016
 */
package cs263w16;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import cs263w16.amazon.JavaCodeSnippet;

@Path("/wishlist")
public class Wishlist {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	/*
	 * List all the entities in XML
	 */
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<WishlistProduct> getEntitiesBrowser() {
		List<WishlistProduct> list = new ArrayList<WishlistProduct>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("WishlistProduct");
		query.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.ASCENDING);
		List<Entity> results = datastore.prepare(query).asList(
				FetchOptions.Builder.withDefaults());
		for (Entity entity : results) {
			String productID = (String) entity.getKey().getName();
			String productName = (String) entity.getProperty("productName");
			double currentPrice = (double) entity.getProperty("currentPrice");
			double lowestPrice = (double) entity.getProperty("lowestPrice");
			Date lowestDate = (Date) entity.getProperty("lowestDate");
			list.add(new WishlistProduct(productID, productName, currentPrice,
					lowestPrice, lowestDate));
		}

		return list;
	}

	/*
	 * List all the entities in XML
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<WishlistProduct> getEntities() {
		List<WishlistProduct> list = new ArrayList<WishlistProduct>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("WishlistProduct");
		query.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.ASCENDING);
		List<Entity> results = datastore.prepare(query).asList(
				FetchOptions.Builder.withDefaults());
		for (Entity entity : results) {
			String productID = (String) entity.getKey().getName();
			String productName = (String) entity.getProperty("productName");
			double currentPrice = (double) entity.getProperty("currentPrice");
			double lowestPrice = (double) entity.getProperty("lowestPrice");
			Date lowestDate = (Date) entity.getProperty("lowestDate");
			list.add(new WishlistProduct(productID, productName, currentPrice,
					lowestPrice, lowestDate));
		}

		return list;
	}

	/*
	 * Post a new product with given product ID
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newWishlistProduct(@FormParam("productID") String productID,
			@Context HttpServletResponse servletResponse) throws Exception {
		System.out.println("Getting current price of " + productID);
		JavaCodeSnippet jcs = new JavaCodeSnippet();
		WishlistProduct product = jcs.search(productID);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		Entity entity = new Entity("WishlistProduct", productID);
		entity.setProperty("productName", product.getProductName());
		entity.setProperty("currentPrice", product.getCurrentPrice());
		entity.setProperty("lowestPrice", product.getLowestPrice());
		entity.setProperty("lowestDate", new Date());
		datastore.put(entity);
		syncCache.put(productID, entity);
		servletResponse.getWriter().println(productID + " has been added.");
		servletResponse.flushBuffer();


	}
    // delete
	
	
	/*
	 * Get a certain product by ID
	 */
	@Path("{productID}")
	public WishlistProductResource getEntity(
			@PathParam("productID") String productID) {
		return new WishlistProductResource(uriInfo, request, productID);
	}
}