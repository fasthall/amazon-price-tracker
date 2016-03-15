/*
 * @Author Wei-Tsung Lin
 * @Date 02/08/2016
 * @Description Send a mail
 */
package cs263w16;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MailServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// parse the parameters
		String email = req.getParameter("email");
		String productName = req.getParameter("productName");
		String productID = req.getParameter("productID");
		String price = req.getParameter("price");
		if (email == null) {
			resp.getWriter().println("No recipient.");
			System.out.println("No recipient.");
			return;
		}
		if (productName == null) {
			productName = "No subject";
		}
		if (productID == null) {
			productID = "No content";
		}

		resp.setContentType("text/html");
		resp.getWriter().println("<html><body>");

		// Invoke mail sending service
		MailService mailService = new MailService(email, productName,
				productID, price);
		mailService.send();

		resp.getWriter().println("Sent a mail to: " + email + "<br>");
		resp.getWriter().println("Subject: " + productName + "<br>");
		resp.getWriter().println("Content:<br>" + productID + "<br>");
		resp.getWriter().println("</body></html>");
	}

}