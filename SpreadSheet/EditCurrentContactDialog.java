import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/*
 * comp6442 Assignment 1 - Personal Organiser
 * Author: Guyue HU, u5608260
 * ANU
 * 
 */

public class EditCurrentContactDialog extends JDialog implements ActionListener {
	
	/*
	 * This class is to create a dialog to edit current contact to the personal organizer
	 */
	
	JTextField fNameField;
	JTextField lNameField;
	JTextField phoneNumberField;
	JTextField eMailAddressField; 
	JTextField homeAddressField;
	JTextField dateOfBirthField;
	
	JLabel FNAME = new JLabel("First Name: ");
	JLabel LNAME = new JLabel("Last Name: ");
	JLabel PHONE_NUMBER = new JLabel("Phone Number: ");
	JLabel E_MAIL_ADDRESS = new JLabel("E-mail Address: ");
	JLabel HOME_ADDRESS = new JLabel("Home Address: ");
	JLabel DATE_OF_BIRTH = new JLabel("Date of birth: ");
	
	String photoPath = "Image/Default/defaultPhoto.jpg";


	// Create a dialog for the user to edit selected contact
	public EditCurrentContactDialog(JFrame p, String currentFirstName, String currentLastName, String currentPhoneNumber,
			String currentEMailAddress, String currentHomeAddress, String currentDateOfBirth) {
		
		// Set the title
		super(p, "Add a New Contact", true);
		
		// Set the layout: Spring Layout
		//getContentPane().setLayout(new FlowLayout());
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		getContentPane().add(FNAME);
		fNameField = new JTextField(currentFirstName, 20);
		FNAME.setLabelFor(fNameField);
		getContentPane().add(fNameField);
		
		getContentPane().add(LNAME);
		lNameField = new JTextField(currentLastName, 20);
		getContentPane().add(lNameField);
		
		getContentPane().add(PHONE_NUMBER);
		phoneNumberField = new JTextField(currentPhoneNumber, 20);
		PHONE_NUMBER.setLabelFor(phoneNumberField);
		getContentPane().add(phoneNumberField);
		
		getContentPane().add(E_MAIL_ADDRESS);
		eMailAddressField = new JTextField(currentEMailAddress, 20);
		E_MAIL_ADDRESS.setLabelFor(eMailAddressField);
		getContentPane().add(eMailAddressField);
		
		getContentPane().add(HOME_ADDRESS);
		homeAddressField = new JTextField(currentHomeAddress, 20);
		HOME_ADDRESS.setLabelFor(homeAddressField);
		getContentPane().add(homeAddressField);
		
		getContentPane().add(DATE_OF_BIRTH);
		dateOfBirthField = new JTextField(currentDateOfBirth, 20);
		DATE_OF_BIRTH.setLabelFor(dateOfBirthField);
		getContentPane().add(dateOfBirthField);
		
		JPanel buttonPanel = new JPanel(new BorderLayout());
		JButton button = new JButton("Okay");
		JButton uploadButton = new JButton("Upload Photo");
		//button.addActionListener(this);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				setVisible(false);
			}
		});
		getContentPane().add(uploadButton);
		getContentPane().add(button);
		
		uploadButton.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();

	}

	// allow the user to upload a photo of any size
	@Override
	public void actionPerformed(ActionEvent e) {
		String path = null;
    	JFileChooser fc = new JFileChooser();
    	fc.setDialogTitle("Please choose the photo file...");
    	fc.setApproveButtonText("Okay");
    	if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(this)) {
    		path = fc.getSelectedFile().getPath();
    	    if (path.endsWith(".jpg")) {
    	    	System.out.println("endsWith .jpg");
    	    	photoPath = path.toString();
    	    	System.out.println(photoPath);
    		} else {
    			photoPath = "Image/Default/defaultPhoto.jpg";
    		}
    	}
	}

	public String getFNameText() {
		setVisible(true);
		return fNameField.getText();
	}
	
	public String getLNameText() {
		return lNameField.getText();
	}
	
	public String getPhoneNumberText() {
		//setVisible(true);
		return phoneNumberField.getText();
	}
	
	public String getEMailAddressText() {
		return eMailAddressField.getText();
	}
	
	public String getHomeAddressText() {
		return homeAddressField.getText();
	}
	
	public String getDateOfBirthText() {
		return dateOfBirthField.getText();
	}
	
	public String getPhotoPath() {
		return photoPath;
	}
}