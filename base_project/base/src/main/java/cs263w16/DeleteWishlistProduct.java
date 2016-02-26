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
			response.getWriter().println(productID + " has been deleted.");
		}
		response.flushBuffer();
	}
}
