/*
 * comp6442 Assignment 1 - Personal Organiser
 * Author: Guyue HU, u5608260
 * ANU
 * 
 */

import javax.mail.*;

public class SMTPAuthenticator extends Authenticator {
	/*
	 * This class authenticates the user name and password
	 * reference: http://blog.sina.com.cn/s/blog_5b9b4abe010170qh.html
	 */
	String username = null;
	String password = null;
	public SMTPAuthenticator() {}
	public SMTPAuthenticator(String username, String password) {
		this.username = username;
		this.password = password; 
	}
	
	// get a authenticator with proviede username and password
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}