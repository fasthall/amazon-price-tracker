package cs263w16;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class WishlistProductResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String productID;
	String email;

	public WishlistProductResource(UriInfo uriInfo, Request request,
			String productID, String email) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.productID = productID;
		this.email = email;
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public WishlistProduct getHTML() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		try {
			Key entKey = KeyFactory.createKey(email, productID);
			Entity entity = datastore.get(entKey);
			String productName = (String) entity.getProperty("productName");
			double currentPrice = (double) entity.getProperty("currentPrice");
			double lowestPrice = (double) entity.getProperty("lowestPrice");
			Date lowestDate = (Date) entity.getProperty("lowestDate");
			return new WishlistProduct(productID, productName, currentPrice,
					lowestPrice, lowestDate);
		} catch (EntityNotFoundException e) {
			throw new RuntimeException("GET: Wishlist with " + productID
					+ " not found.");
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WishlistProduct get() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		try {
			Key entKey = KeyFactory.createKey(email, productID);
			Entity entity = datastore.get(entKey);
			String productName = (String) entity.getProperty("productName");
			double currentPrice = (double) entity.getProperty("currentPrice");
			double lowestPrice = (double) entity.getProperty("lowestPrice");
			Date lowestDate = (Date) entity.getProperty("lowestDate");
			return new WishlistProduct(productID, productName, currentPrice,
					lowestPrice, lowestDate);
		} catch (EntityNotFoundException e) {
			throw new RuntimeException("GET: Wishlist with " + productID
					+ " not found.");
		}
	}

	@DELETE
	public void delete() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key entKey = KeyFactory.createKey(email, productID);
		try {
			datastore.delete(entKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}