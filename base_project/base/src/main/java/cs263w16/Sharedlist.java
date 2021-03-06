/*
 * @Author Wei-Tsung Lin
 * @Date 03/04/2016
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
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Path("/sharedlist")
public class Sharedlist {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	/*
	 * List all the entities in XML
	 */
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<SharedProduct> getEntitiesBrowser() {
		List<SharedProduct> list = new ArrayList<SharedProduct>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("SharedList");
		query.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.ASCENDING);
		List<Entity> results = datastore.prepare(query).asList(
				FetchOptions.Builder.withDefaults());
		for (Entity entity : results) {
			String email = (String) entity.getProperty("email");
			String productID = (String) entity.getKey().getName();
			String productName = (String) entity.getProperty("productName");
			Date sharedDate = (Date) entity.getProperty("sharedDate");

			list.add(new SharedProduct(email, productID, productName,
					sharedDate));
		}

		return list;
	}

	/*
	 * List all the entities in XML/JSON
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<SharedProduct> getEntities() {
		List<SharedProduct> list = new ArrayList<SharedProduct>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("SharedList");
		query.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.ASCENDING);
		List<Entity> results = datastore.prepare(query).asList(
				FetchOptions.Builder.withDefaults());
		for (Entity entity : results) {
			String email = (String) entity.getProperty("email");
			String productID = (String) entity.getKey().getName();
			String productName = (String) entity.getProperty("productName");
			Date sharedDate = (Date) entity.getProperty("sharedDate");

			list.add(new SharedProduct(email, productID, productName,
					sharedDate));
		}

		return list;
	}

	/*
	 * Post a new product with given product ID
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newSharedProduct(@FormParam("productID") String productID,
			@FormParam("productName") String productName,
			@Context HttpServletResponse servletResponse) throws Exception {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			System.out.println("Login first");
			return;
		}

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity entity = new Entity("SharedList", productID);
		entity.setProperty("email", user.getEmail());
		entity.setProperty("productName", productName);
		entity.setProperty("sharedDate", new Date());
		datastore.put(entity);
		servletResponse.getWriter().println(productName + " has been added.");
		servletResponse.flushBuffer();
	}

	/*
	 * Post a new product with given JSON string
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_JSON)
	public void newSharedProduct(SharedProduct product,
			@Context HttpServletResponse servletResponse) throws Exception {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			System.out.println("Login first");
			return;
		}

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity entity = new Entity("SharedList", product.getProductID());
		entity.setProperty("email", user.getEmail());
		entity.setProperty("productName", product.getProductName());
		entity.setProperty("sharedDate", product.getSharedDate());
		datastore.put(entity);
		servletResponse.getWriter().println(
				product.getProductName() + " has been added.");
		servletResponse.flushBuffer();
	}

}