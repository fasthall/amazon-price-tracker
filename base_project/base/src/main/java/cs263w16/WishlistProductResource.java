package cs263w16;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.logging.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.*;
import javax.xml.bind.JAXBElement;

public class WishlistProductResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String productID;

	public WishlistProductResource(UriInfo uriInfo, Request request, String productID) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.productID = productID;
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public WishlistProduct getHTML() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		Entity entity = (Entity) syncCache.get(productID);
		if (entity != null) {
			String productName = (String) entity.getProperty("productName");
			double currentPrice = (double) entity.getProperty("currentPrice");
			double lowestPrice = (double) entity.getProperty("lowestPrice");
			Date lowestDate = (Date) entity.getProperty("lowestDate");
			return new WishlistProduct(productID, productName, currentPrice, lowestPrice, lowestDate);
		}
		try {
			Key entKey = KeyFactory.createKey("WishlistProduct", productID);
			entity = datastore.get(entKey);
			String productName = (String) entity.getProperty("productName");
			double currentPrice = (double) entity.getProperty("currentPrice");
			double lowestPrice = (double) entity.getProperty("lowestPrice");
			Date lowestDate = (Date) entity.getProperty("lowestDate");
			return new WishlistProduct(productID, productName, currentPrice, lowestPrice, lowestDate);
		} catch (EntityNotFoundException e) {
			throw new RuntimeException("GET: Wishlist with " + productID +  " not found.");
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WishlistProduct get() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		Entity entity = (Entity) syncCache.get(productID);
		if (entity != null) {
			String productName = (String) entity.getProperty("productName");
			double currentPrice = (double) entity.getProperty("currentPrice");
			double lowestPrice = (double) entity.getProperty("lowestPrice");
			Date lowestDate = (Date) entity.getProperty("lowestDate");
			return new WishlistProduct(productID, productName, currentPrice, lowestPrice, lowestDate);
		}
		try {
			Key entKey = KeyFactory.createKey("WishlistProduct", productID);
			entity = datastore.get(entKey);
			String productName = (String) entity.getProperty("productName");
			double currentPrice = (double) entity.getProperty("currentPrice");
			double lowestPrice = (double) entity.getProperty("lowestPrice");
			Date lowestDate = (Date) entity.getProperty("lowestDate");
			return new WishlistProduct(productID, productName, currentPrice, lowestPrice, lowestDate);
		} catch (EntityNotFoundException e) {
			throw new RuntimeException("GET: Wishlist with " + productID +  " not found.");
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response put(WishlistProduct wishlistProduct) {
		Response res = null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		String productID = wishlistProduct.getProductID();
		Key entKey = KeyFactory.createKey("WishlistProduct", productID);
		Entity entity;
		try {
			entity = datastore.get(entKey);
			res = Response.noContent().build();
		} catch (EntityNotFoundException e) {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		entity  = new Entity("WishlistProduct", productID);
		entity.setProperty("productName", wishlistProduct.getProductName());
		entity.setProperty("currentPrice", wishlistProduct.getCurrentPrice());
		entity.setProperty("lowestPrice", wishlistProduct.getLowestPrice());
		entity.setProperty("lowestDate", wishlistProduct.getLowestDate());
		datastore.put(entity);
		syncCache.put(productID, entity);

		return res;
	}

	@DELETE
	public void delete() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		Key entKey = KeyFactory.createKey("WishlistProduct", productID);
		try {
			datastore.delete(entKey);
			syncCache.delete(productID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

} 