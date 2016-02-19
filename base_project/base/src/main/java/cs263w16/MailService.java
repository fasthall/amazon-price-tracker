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

	public MailService(String recipient, String subject, String body) {
		this.recipient = recipient;
		this.subject = subject;
		this.body = "The product " + subject
				+ " in your wishlist is on sale!<br>"
				+ "http://www.amazon.com/dp/" + body;
	}

	public void send() {
		if (recipient == null || subject == null || body == null) {
			return;
		}

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(
					"cs263w16-1190@appspot.gserviceaccount.com",
					"Amazon Price Tracker"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					recipient));
			msg.setSubject(subject);
			msg.setText(body);
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
