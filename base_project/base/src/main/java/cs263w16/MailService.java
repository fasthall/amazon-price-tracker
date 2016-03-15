/*
 * @Author Wei-Tsung Lin
 * @Date 02/08/2016
 * @Description Send a mail
 */
package cs263w16;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
	private String recipient;
	private String subject;
	private String body;

	public MailService(String recipient, String productName, String productID,
			String price) {
		this.recipient = recipient;
		this.subject = productName + " is on sale!";
		this.body = "<h1>The product "
				+ "<a href=\"http://www.amazon.com/dp/"
				+ productID
				+ "\">"
				+ productName
				+ "</a>"
				+ " on your wishlist is on sale!</h1><br><h2>The current price is $"
				+ price + "</h2>";
	}

	public void send() {
		if (recipient == null || subject == null || body == null) {
			return;
		}

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			// send the mail
			Message msg = new MimeMessage(session);
			// gae service sender mail address is temporarily broken
			// use developer's address instead
			msg.setFrom(new InternetAddress("fasthall@gmail.com",
					"Amazon Price Tracker"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					recipient));
			msg.setSubject(subject);
			msg.setContent(body, "text/html");
			Transport.send(msg);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
