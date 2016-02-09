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
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String recipient = req.getParameter("recipient");
		String subject = req.getParameter("subject");
		String body = req.getParameter("body");
		if (recipient == null) {
			resp.getWriter().println("No recipient.");
			System.out.println("No recipient.");
			return;
		}
		if (subject == null) {
			subject = "No subject";
		}
		if (body == null) {
			body = "No content";
		}

		resp.setContentType("text/html");
		resp.getWriter().println("<html><body>");

		MailService mailService = new MailService();
		mailService.setRecipient(recipient);
		mailService.setSubject(subject);
		mailService.setBody(body);
		mailService.send();

		resp.getWriter().println("Sent a mail to: " + recipient + "<br>");
		resp.getWriter().println("Subject: " + subject + "<br>");
		resp.getWriter().println("Content:<br>" + body + "<br>");
		resp.getWriter().println("</body></html>");
	}
}