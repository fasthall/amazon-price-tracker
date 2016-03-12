/*
 * @Author Wei-Tsung Lin
 * @Date 03/11/2016
 * @Description Show all products in the sharedlist
 */
package cs263w16;

import java.io.IOException;
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

@SuppressWarnings("serial")
public class SharedlistServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("<html><body>");

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("sharedlist");
		query.addSort(Entity.KEY_RESERVED_PROPERTY, SortDirection.ASCENDING);
		List<Entity> results = datastore.prepare(query).asList(
				FetchOptions.Builder.withDefaults());
		for (Entity entity : results) {
			String email = (String) entity.getProperty("email");
			String productID = (String) entity.getProperty("productID");
			String productName = (String) entity.getProperty("productName");

			resp.getWriter().println(
					email + " shared <a href=\"http://www.amazon.com/dp/"
							+ productID + "\">" + "<b>" + productID
							+ "</b></a>!<br>");
		}

		resp.getWriter().println("</body></html>");
	}
}