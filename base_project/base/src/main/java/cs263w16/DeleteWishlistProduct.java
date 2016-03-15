package cs263w16;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class DeleteWishlistProduct extends HttpServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String productID = request.getParameter("productID");

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			response.getWriter().println("Login first.");
		} else {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			// MemcacheService syncCache =
			// MemcacheServiceFactory.getMemcacheService();
			Key entKey = KeyFactory.createKey(user.getEmail(), productID);
			try {
				datastore.delete(entKey);
				// syncCache.delete(productID);
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.getWriter().println("<html><head>");
			response.getWriter().println(
					"<meta http-equiv=\"refresh\" content=\"3;url=/wishlist.jsp\" />");
			response.getWriter().println("</head><body>");
			response.getWriter().println(
					"<h1>" + productID + " has been deleted.</h1><br>");
			response.getWriter()
					.println(
							"<h2>Redirecting in 3 seconds...</h2> <a href=\"/\">Go back now</a>");
			response.getWriter().println("</body></html>");
			response.flushBuffer();
		}
		response.flushBuffer();
	}
}
