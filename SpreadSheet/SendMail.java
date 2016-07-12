/*
 * comp6442 Assignment 1 - Personal Organiser
 * Author: Guyue HU, u5608260
 * ANU
 * 
 */

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {
	/*
	 * This class tries to send email
	 * reference: http://blog.sina.com.cn/s/blog_5b9b4abe010170qh.html
	 */
	
	SMTPAuthenticator au = null;
	
	private static boolean flag = false;
	public static boolean send(Mail mail) throws Exception {
		SMTPAuthenticator authenticator = null;
		Properties props = mail.getProperties();
		
		// check authenticator with the mail server
		if (mail.isValidate()) {
			authenticator = new SMTPAuthenticator(mail.getUsername(), mail.getPassword());
		}
		
		// create a new session to send the mail
		Session session = Session.getDefaultInstance(props, authenticator);
		try { 
			Message message = new MimeMessage(session);
			Address from = new InternetAddress(mail.getFromAddress());
			message.setFrom(from);
			Address to = new InternetAddress(mail.getToAddress());
			message.setRecipient(Message.RecipientType.TO, to);
			message.setSubject(mail.getSubject());
			Multipart mainPart = new MimeMultipart();
			BodyPart body = new MimeBodyPart();
			body.setContent(mail.getContent(), "text/html;charset=utf-8");
			mainPart.addBodyPart(body);
			message.setContent(mainPart);
			System.out.println("tried");
			Transport.send(message);
			flag = true;
		} catch (Exception e) {
			flag = false;
			throw e;
		}
		
		return flag;
	}
	
}