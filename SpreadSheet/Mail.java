/*
 * comp6442 Assignment 1 - Personal Organiser
 * Author: Guyue HU, u5608260
 * ANU
 */

import java.util.Properties;

public class Mail {
	/*
	 * This class stores the data of an e-mail
	 * reference: http://blog.sina.com.cn/s/blog_5b9b4abe010170qh.html
	 */
	
	String fromAddress = "comp6442testing@gmail.com";
	String toAddress;
	String subject;
	String content;
	String mailHost; 
	String username = "comp6442testing@gmail.com";
	String password = "sccomp6442";
	boolean validate;
	
	// set the host server, port for the email address where the email is initialed
	public Properties getProperties() {
			
			Properties p = new Properties();
			
			p.put("mail.smtp.auth", "true");
			p.put("mail.smtp.starttls.enable", "true");
			p.put("mail.smtp.host", "smtp.gmail.com");
			p.put("mail.smtp.port", "587");
			return p;
		}
	
	public String getFromAddress() {
		return fromAddress;
		}
	
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	
	public String getToAddress() {
		return toAddress;
		}
	
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getMailHost() {
		return mailHost;
	}
	
	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValidate() {
		return validate;
	}
	
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	
}