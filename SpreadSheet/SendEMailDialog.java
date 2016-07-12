/*
 * comp6442 Assignment 1 - Personal Organiser
 * Author: Guyue HU, u5608260
 * ANU
 * 
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SendEMailDialog extends JDialog implements ActionListener {
	
	/*
	 * This class is to create a dialog to send an email to current selected contact
	 * reference: http://blog.sina.com.cn/s/blog_5b9b4abe010170qh.html
	 */
	
	Mail mail = null;
	SendMail send = null;
	
	JPanel fromPanel = new JPanel(new BorderLayout());
	JPanel toPanel = new JPanel(new BorderLayout());
	JPanel subjectPanel = new JPanel(new BorderLayout());
	JPanel contentPanel = new JPanel(new BorderLayout());
	JPanel buttonPanel = new JPanel(new BorderLayout());
	
	JOptionPane pane = new JOptionPane();
	
	JLabel FROM = new JLabel("From   :");
	JLabel TO = new JLabel("To       :");
	JLabel SUBJECT = new JLabel("Subject: ");
	JLabel CONTENT = new JLabel("Content: ");
	
	JTextField fromField = new JTextField(30);
	JTextField toField = null;
	JTextField subjectField = new JTextField(30);
	JTextArea contentArea = new JTextArea(10, 30);
	
	JButton sendButton = new JButton("Send");
	JButton clearButton = new JButton("Clear");
	
	JScrollPane contentScrollPane = new JScrollPane(contentArea);
	
	public SendEMailDialog(JFrame p, String currentEMailAddress) {
		
		
		// Set the title
		super(p, "Send an e-mail", true);
		getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
		
		/*fromPanel.add(FROM, BorderLayout.WEST);
		fromPanel.add(fromField, BorderLayout.EAST);
		getContentPane().add(fromPanel);*/
		
		toField = new JTextField(currentEMailAddress, 30);
		
		toPanel.add(TO, BorderLayout.WEST);
		toPanel.add(toField, BorderLayout.EAST);
		getContentPane().add(toPanel);
		
		subjectPanel.add(SUBJECT, BorderLayout.WEST);
		subjectPanel.add(subjectField, BorderLayout.EAST);
		getContentPane().add(subjectPanel);
		
		contentPanel.add(CONTENT, BorderLayout.WEST);
		contentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentArea.setLineWrap(true);
		contentPanel.add(contentScrollPane, BorderLayout.EAST);
		getContentPane().add(contentPanel);
		
		buttonPanel.add(sendButton, BorderLayout.WEST);
		buttonPanel.add(clearButton, BorderLayout.EAST);
		getContentPane().add(buttonPanel);
		
		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				subjectField.setText(null);
				contentArea.setText(null);
			}
		});
		
		// actionlistener when the send email button is clicked
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) { 
				mail = new Mail();
				//mail.setFromAddress(fromField.getText());
				mail.setToAddress(toField.getText());
				mail.setSubject(subjectField.getText());
				mail.setContent(contentArea.getText());
				mail.setUsername("comp6442testing@gmail.com");
				mail.setPassword("sccomp6442");
				mail.setMailHost("smtp.gmail.com");
				mail.setValidate(true);
				
				try{
					SendMail.send(mail);
					pane.showMessageDialog(null, "E-mail sent successfully~ :)");
					subjectField.setText(null);
					contentArea.setText(null);
				} catch (Exception e) {
					pane.showMessageDialog(null, "E-mail failed :(");
				}
			}
		});
		
		setSize(450,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		//setVisible(false);
		setVisible(true);
	}

}