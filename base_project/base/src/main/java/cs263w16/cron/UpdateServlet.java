/*
 * @Author Wei-Tsung Lin
 * @Date 02/04/2016
 * @Description Update all prices in datastore
 */
package cs263w16.cron;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import cs263w16.MailService;
import cs263w16.WishlistProduct;
import cs263w16.amazon.JavaCodeSnippet;

@SuppressWarnings("serial")
public class UpdateServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("Cron job triggered. " + (new Date()).toString());

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		JavaCodeSnippet jcs = new JavaCodeSnippet();

		Query query = new Query();
		// query.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.ASCENDING);
		List<Entity> results = datastore.prepare(query).asList(
				FetchOptions.Builder.withDefaults());
		// List all entities
		for (Entity entity : results) {
			if (entity.getKind().equals("SharedList")) {
				continue;
			}
			String email = entity.getKind();
			String productID = entity.getKey().getName();
			String productName = (String) entity.getProperty("productName");
			System.out.println("Checking " + email + ": " + productID + ": "
					+ productName);
			// Update the price and check if the current price is lower
			try {
				Object priceObj = syncCache.get(productID);
				double price;
				if (priceObj == null) {
					WishlistProduct wishlistProduct = jcs.search(productID);
					if (wishlistProduct == null) {
						continue;
					}
					price = wishlistProduct.getCurrentPrice();
					System.out.println("Cache miss");
				} else {
					price = (double) priceObj;
					System.out.println("Cache hit");
				}
				System.out.println("Last price: "
						+ (double) entity.getProperty("currentPrice"));
				if (price != (double) entity.getProperty("currentPrice")) {
					System.out.println("Current price: " + price);
					if (price > 0) {
						if (price < (double) entity.getProperty("currentPrice")) {
							entity.setProperty("lowestPrice", price);
							entity.setProperty("lowestDate", new Date());
							Queue queue = QueueFactory.getDefaultQueue();
							queue.add(TaskOptions.Builder.withUrl("/mail")
									.param("recipient", email)
									.param("subject", productName)
									.param("body", productID));
							resp.getWriter().println(
									"Notify " + email + " about " + productName
											+ "(" + productID + ")");
						}
						entity.setProperty("currentPrice", price);
						datastore.put(entity);
						syncCache.put(productID, price);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		resp.getWriter().flush();
	}
}
