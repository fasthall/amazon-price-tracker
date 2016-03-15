/*
 * @Author Wei-Tsung Lin
 * @Date 03/11/2016
 * @Description Show all products in the wishlist
 */
package cs263w16;

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
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class WishlistServlet extends HttpServlet {

	/*
	 * List all the products on the wishlist. Not used now.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");

		resp.getWriter().println("<html><body>");

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.getWriter().println("Please login first!");
			resp.getWriter().println("</body></html>");
			return;
		}

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query(user.getEmail());
		query.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.ASCENDING);
		List<Entity> results = datastore.prepare(query).asList(
				FetchOptions.Builder.withDefaults());
		resp.getWriter().println("<table border=\"0\">");
		resp.getWriter()
				.println(
						"<tr><b><td>Product name</td><td>Current price</td><td>History lowest</td></b></tr>");
		int i = 0;
		for (Entity entity : results) {
			String productID = (String) entity.getKey().getName();
			String productName = (String) entity.getProperty("productName");
			double currentPrice = (double) entity.getProperty("currentPrice");
			double lowestPrice = (double) entity.getProperty("lowestPrice");
			Date lowestDate = (Date) entity.getProperty("lowestDate");
			if (i % 2 == 0) {
				resp.getWriter().println(
						"<tr style=\"background-color: #f5f5f5\">");
			} else {
				resp.getWriter().println("<tr>");
			}
			++i;
			resp.getWriter().println("<td>");
			resp.getWriter().println(
					"<a href=\"http://www.amazon.com/dp/" + productID
							+ "\" target=\"_blank\">" + "<b>" + productName
							+ "</b></a>");
			resp.getWriter().println("</td>");
			resp.getWriter().println("<td>");
			resp.getWriter().println("$" + currentPrice);
			resp.getWriter().println("</td>");
			resp.getWriter().println("<td>");
			resp.getWriter().println("$" + lowestPrice);
			resp.getWriter().println("</td>");
			resp.getWriter().println("</tr>");
		}
		resp.getWriter().println("</table>");
		resp.getWriter().println("</body></html>");
	}

}