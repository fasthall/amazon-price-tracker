/*
 * @Author Wei-Tsung Lin
 * @Date 01/30/2016
 */
package cs263w16;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.logging.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.memcache.*;

import cs263w16.amazon.JavaCodeSnippet;

@Path("/wishlist")
public class Wishlist {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

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
	}

	@Path("{productID}")
	public WishlistProductResource getEntity(
			@PathParam("productID") String productID) {
		System.out.println("GET " + productID);
		JavaCodeSnippet jcs = new JavaCodeSnippet();
		try {
			jcs.search(productID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new WishlistProductResource(uriInfo, request, productID);
	}
}