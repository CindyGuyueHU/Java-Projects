import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/*
 * comp6442 Assignment 1 - Personal Organiser
 * Author: Guyue HU, u5608260
 * ANU
 * 
 */

public class Display {
	
	/*
	 * This class creates a panel display the personal information of selected contact
	 * 
	 */
	String name;
	String eMailAddress;
	String homeAddress;
	String dateOfBirth;
	String photoPath;
	String phoneNumber;
	
	ScaleIcon si = null;
	
	ImageIcon rawIcon = null;
	
	//JPanel displayPanel = null;
	JPanel contentPanel = null;
	
	Font cfont = new Font("Serif", Font.BOLD, 20);
	
	FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
	
	public Display(String currentName, String currentPhoneNumber, String currentEMailAddress, String currentHomeAddress,
			String currentDateOfBirth, String currentPhotoPath){
		name = currentName;
		eMailAddress = currentEMailAddress;
		homeAddress = currentHomeAddress;
		dateOfBirth = currentDateOfBirth;
		photoPath = currentPhotoPath;
		phoneNumber = currentPhoneNumber;
		
	} 
	
	// Put the personal information passed in into a well designed display panel
	JPanel getDisplayPanel() {
		JPanel displayPanel = new JPanel();
		displayPanel.setOpaque(false); 
		displayPanel.setLayout(new BorderLayout());
		displayPanel.setBackground(Color.black);
		displayPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		ImageIcon rawicon = new ImageIcon(photoPath);
		Image img = rawicon.getImage();
		Image newimg = img.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
		ImageIcon proicon = new ImageIcon(newimg);  
		ScaleIcon icon = new ScaleIcon(proicon);
		

		ImagePane photoPanel = new ImagePane("Image/Default/photoback.png");
		//photoPanel.setOpaque(false);
		JLabel photoLabel = new JLabel(icon);
		
		//JPanel photoPanel = new JPanel();
		photoPanel.add(photoLabel);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setOpaque(false);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		//JLabel lab = new JLabel("Name:  " + name + "PhotoPath" + photoPath + " Phone: " + phoneNumber + " E-mail: "
			//	+ eMailAddress + " Home: " + homeAddress + " DOB: " + dateOfBirth);
		//contentPanel.add(lab);
		
		ImageIcon nameIcon = new ImageIcon("Image/Label/nameLabel.png");
		ImageIcon phoneIcon = new ImageIcon("Image/Label/phoneLabel.png");
		ImageIcon addressIcon = new ImageIcon("Image/Label/addressLabel.png");
		ImageIcon emailIcon = new ImageIcon("Image/Label/emailLabel.png");
		ImageIcon dobIcon = new ImageIcon("Image/Label/birthdayLabel.png");
	
		JPanel namePanel = new JPanel();
		namePanel.setOpaque(false);
		JPanel phonePanel = new JPanel();
		phonePanel.setOpaque(false);
		JPanel addressPanel = new JPanel();
		addressPanel.setOpaque(false);
		JPanel emailPanel = new JPanel();
		emailPanel.setOpaque(false);
		JPanel dobPanel = new JPanel(); 
		dobPanel.setOpaque(false);
		
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
	    phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.Y_AXIS));
		addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.Y_AXIS));
		emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
		dobPanel.setLayout(new BoxLayout(dobPanel, BoxLayout.Y_AXIS));
		
		JLabel nameLabel = new JLabel(nameIcon);
		JLabel phoneLabel = new JLabel(phoneIcon);
		JLabel addressLabel = new JLabel(addressIcon);
		JLabel emailLabel = new JLabel(emailIcon);
		JLabel dobLabel = new JLabel(dobIcon);
		
		JLabel nameText = new JLabel("  " + name);
		nameText.setFont(cfont);
		JLabel phoneText = new JLabel("  " + phoneNumber);
		phoneText.setFont(cfont);
		JLabel emailText = new JLabel("  " + eMailAddress);
		emailText.setFont(cfont);
		JLabel addressText = new JLabel("  " + homeAddress);
		addressText.setFont(cfont);
		JLabel dobText = new JLabel("  " + dateOfBirth);
		dobText.setFont(cfont);
		
		namePanel.add(nameLabel);
		namePanel.add(nameText);
		phonePanel.add(phoneLabel);
		phonePanel.add(phoneText);
		addressPanel.add(addressLabel);
		addressPanel.add(addressText);
		emailPanel.add(emailLabel);
		emailPanel.add(emailText);
		dobPanel.add(dobLabel);
		dobPanel.add(dobText);
		
		contentPanel.add(namePanel);
		contentPanel.add(phonePanel);
		contentPanel.add(addressPanel);
		contentPanel.add(emailPanel);
		contentPanel.add(dobPanel);
		
		//System.out.println("displayed Name:"+ name);
		
		displayPanel.add(photoPanel, BorderLayout.PAGE_START);
		displayPanel.add(contentPanel, BorderLayout.CENTER);
		
		return displayPanel;
	}
}