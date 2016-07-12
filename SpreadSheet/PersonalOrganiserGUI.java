/*
 * comp6442 Assignment 1 - Personal Organiser
 * Author: Guyue HU, u5608260
 * ANU
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

public class PersonalOrganiserGUI implements Runnable {
	
	/*
	 * This class designs the main frame of a personal organiser application
	 */
	
	AddNewContactDialog dialog = null;
	EditCurrentContactDialog edialog = null;
	SendEMailDialog sdialog = null;
	Contact c = null;
	Display dis = null;
	MyCalendar cal = null;
	

	JFrame frame = new JFrame(
			"comp6442 Assignment 1 Personal Organiser - Guyue HU, u5608260");

	JPanel headerPanel = new JPanel();
	JPanel footerPanel = new JPanel();
	JPanel leftPanel = new JPanel(new BorderLayout());
	JPanel rightPanel = new JPanel(); 
	ImagePane centerPanel = new ImagePane("Image/Default/background.png");
	JPanel centerDisplayPanel = new JPanel();

	JTextField addTodo = new JTextField(13);
	DefaultListModel todoModel = new DefaultListModel();
	JList todoList = new JList(todoModel);

	ImageIcon contactIcon = new ImageIcon("Image/Label/title1.png");
	ImageIcon calendarIcon = new ImageIcon("Image/Label/title2.png");
	ImageIcon todoIcon = new ImageIcon("Image/Label/title3.png");
	JLabel MY_CONTACT = new JLabel(contactIcon);
	JLabel CALENDAR = new JLabel(calendarIcon);
	JLabel TODO_LIST = new JLabel(todoIcon);
	static Font myContactFont = new Font("Dialog", Font.BOLD, 25);

	static final String HEADERFILENAME = "Image/Default/header00.jpg";
	static final String FOOTERFILENAME = "Image/Default/footer00.jpg";
	
	ImageIcon addIcon = new ImageIcon("Image/Label/add.png");
	ImageIcon deleteIcon = new ImageIcon("Image/Label/delete.png");
	ImageIcon editIcon = new ImageIcon("Image/Label/edit.png");

	final static String S_NAME = "NAME";
	final static String S_SEARCH = "SEARCH";

	String currentPhoneNumber = "";
	String currentEMailAddress = "";
	String currentHomeAddress = "";
	String currentDateOfBirth = "";
	String currentPhotoPath = "";

	DefaultListModel model1 = new DefaultListModel();
	DefaultListModel model2 = new DefaultListModel();

	JList jlist1 = new JList(model1);
	JList jlist2 = new JList(model2);

	String cardFlag = "NAME";
	String searchString = "";

	ArrayList<String> nameList = new ArrayList<String>();

	public PersonalOrganiserGUI() {
		SwingUtilities.invokeLater(this);
	}

	// Add a image label to the header
	public static void addComponentsToHeaderPane(Container pane) {
		JLabel headerImage = new JLabel();

		ImageIcon icon = new ImageIcon(HEADERFILENAME);
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth(),
				icon.getIconHeight(), Image.SCALE_DEFAULT));

		headerImage.setIcon(icon);
		pane.add(headerImage);
	}

	// Add a image label to the footer
	public static void addComponentsToFooterPane(Container pane) {
		JLabel footerImage = new JLabel();

		ImageIcon icon = new ImageIcon(FOOTERFILENAME);
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth(),
				icon.getIconHeight(), Image.SCALE_DEFAULT));

		footerImage.setIcon(icon);
		pane.add(footerImage);
	}

	// Add components to the right Panel: calendar, to-do list
	public void addComponentsToRightPane(Container pane) {
		pane.setLayout(new BorderLayout());

		JPanel calendarPanel = new JPanel();

		MyCalendar myCalendar = new MyCalendar();
		calendarPanel = myCalendar.createCalendar();

		CALENDAR.setFont(myContactFont);
		pane.add(CALENDAR, BorderLayout.PAGE_START);
		pane.add(calendarPanel, BorderLayout.CENTER);

		JPanel LowerPanel = new JPanel(new BorderLayout());
		JPanel addPanel = new JPanel(new BorderLayout());
		JScrollPane todoListPanel = new JScrollPane(todoList);
		ImageIcon tickIcon = new ImageIcon("Image/Label/tick.png");
		JButton addButton = new JButton(tickIcon);
		todoListPanel.setPreferredSize(new Dimension(220, 150));
		TODO_LIST.setFont(myContactFont);

		addPanel.add(addTodo, BorderLayout.WEST);
		addPanel.add(addButton, BorderLayout.EAST);
		LowerPanel.add(addPanel, BorderLayout.PAGE_END);
		LowerPanel.add(todoListPanel, BorderLayout.CENTER);
		LowerPanel.add(TODO_LIST, BorderLayout.PAGE_START);
		pane.add(LowerPanel, BorderLayout.PAGE_END);

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String todoContent = addTodo.getText();
				System.out.println(todoContent);
				todoModel.addElement(todoContent);
				addTodo.setText(null);
			}
		});
	}

	// Add a list of contactnames and a aphabetic index to the left panel
	public void addComponentsToLeftPane(Container pane) {

		final JTextField searchField = new JTextField(10);
		ImageIcon searchIcon = new ImageIcon("Image/Label/search.png");
		JButton searchButton = new JButton(searchIcon);

		ImageIcon sendMailIcon = new ImageIcon("Image/Label/mail.png");
		final JButton sendEMailButton = new JButton(sendMailIcon);
		JPanel lButtonPanel = new JPanel();
		lButtonPanel.add(sendEMailButton);

		String toAddress;

		// mouselistener when a contact in the list is selected
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {

				JList theList = (JList) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 1) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);
						String currentName = (String) o;
						Contact contact = null;
						try {
							contact = new Contact();
							JSONObject jsonContact = contact.getJsonContact();
							currentName = (String) jlist1.getSelectedValue();
							JSONObject currentContact = jsonContact
									.getJSONObject(currentName);
							currentPhoneNumber = currentContact
									.getString("PhoneNumber");
							currentEMailAddress = currentContact
									.getString("EMailAddress");
							currentHomeAddress = currentContact
									.getString("HomeAddress");
							currentDateOfBirth = currentContact
									.getString("DateOfBirth");
							currentPhotoPath = currentContact
									.getString("PhotoPath");
							// System.out.println("Name:  " + currentName +
							// "Phone: " + currentPhoneNumber);

							// JLabel lab = new JLabel();
							centerPanel.remove(centerDisplayPanel);
							Display display = new Display(currentName,
									currentPhoneNumber, currentEMailAddress,
									currentHomeAddress, currentDateOfBirth,
									currentPhotoPath);
							centerDisplayPanel = new JPanel();
							centerDisplayPanel = display.getDisplayPanel();
							centerPanel.add(centerDisplayPanel,
									BorderLayout.CENTER);
							// centerPanel.add(lab, BorderLayout.PAGE_START);
							centerPanel.validate();
							centerPanel.repaint();

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}

				// actionlistener when the send-email button is clicked
				sendEMailButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						// System.out.println("button clicekd");
						sdialog = new SendEMailDialog(frame,
								currentEMailAddress);
						sdialog.setLocationRelativeTo(null);

					}
				});
			}
		};

		// actionlistener when the search button is clicked
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Contact contact = null;

				try {
					contact = new Contact();
					model1.clear();
					searchString = searchField.getText();
					nameList = contact.getSearchNamelist(searchString);
					for (int i = 0; i < nameList.size(); i++) {
						model1.addElement(nameList.get(i));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		JPanel card2 = new JPanel();
		card2.setLayout(new BorderLayout());
		JPanel searchPanel = new JPanel(new BorderLayout());
		searchPanel.add(searchField, BorderLayout.CENTER);
		searchPanel.add(searchButton, BorderLayout.LINE_END);
		JScrollPane searchResult = new JScrollPane(jlist1);
		jlist1.addMouseListener(mouseListener);
		jlist1.setOpaque(false);
		jlist1.setFont(new Font("Dialog", Font.BOLD, 20));
		jlist1.setCellRenderer(new ListCellRenderer<String>(){
			@Override
			public Component getListCellRendererComponent(
					JList<? extends String> list, String value, int index,
					boolean selected, boolean hasfocus) {
				// TODO Auto-generated method stub
				JLabel jl = new JLabel(value);
				Color dark_green = new Color(143, 188, 143);
				Color light_green = new Color(245, 245, 245);
				jl.setBackground(selected?dark_green:light_green);
				jl.setOpaque(true);
				return jl;
			}});
		jlist1.setBorder(new EmptyBorder(10, 10, 10, 10));
		searchResult.setOpaque(false);
		searchResult.getViewport().setOpaque(false);
		card2.add(searchPanel, BorderLayout.PAGE_START);
		card2.add(searchResult, BorderLayout.CENTER);

		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		MY_CONTACT.setFont(myContactFont);
		pane.add(MY_CONTACT, BorderLayout.PAGE_START);
		// pane.add(comboBoxPane, BorderLayout.PAGE_START);
		pane.add(card2, BorderLayout.CENTER);
		pane.add(lButtonPanel, BorderLayout.PAGE_END);
	}

	// Add widgets to the center panel
	public void addComponentsToCenterPane(Container pane) {

		JButton addNewContactButton = new JButton(addIcon);
		JButton deleteCurrentContactButton = new JButton(deleteIcon);
		JButton editCurrentContactButton = new JButton(editIcon);

		JPanel buttonPanel = new JPanel();
		//buttonPanel.setOpaque(false);

		buttonPanel.add(addNewContactButton);
		buttonPanel.add(deleteCurrentContactButton);
		buttonPanel.add(editCurrentContactButton);

		// Listener to addNewContactButton
		addNewContactButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				dialog = new AddNewContactDialog(frame);
				dialog.setLocationRelativeTo(null);

				String currentFirstName = dialog.getFNameText();
				String currentLastName = dialog.getLNameText();
				String currentPhoneNumber = dialog.getPhoneNumberText();
				String currentEMailAddress = dialog.getEMailAddressText();
				String currentHomeAddress = dialog.getHomeAddressText();
				String currentDateOfBirth = dialog.getDateOfBirthText();
				String currentPhotoPath = dialog.getPhotoPath();
				String currentName = currentLastName + ", " + currentFirstName;

				Contact contact = null;
				try {
					contact = new Contact();
					model1.clear();
					nameList = contact.getNameList();
					for (int i = 0; i < nameList.size(); i++) {
						model1.addElement(nameList.get(i));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// System.out.print(currentFirstName);
				if (currentFirstName.length() > 0
						|| currentLastName.length() > 0) {
					HashMap<String, String> currentPerson = new HashMap<String, String>();
					currentPerson.put("PhoneNumber", currentPhoneNumber);
					currentPerson.put("EMailAddress", currentEMailAddress);
					currentPerson.put("HomeAddress", currentHomeAddress);
					currentPerson.put("DateOfBirth", currentDateOfBirth);
					currentPerson.put("PhotoPath", currentPhotoPath);
					try {
						contact.add(currentName, currentPerson);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						contact = new Contact();
						model1.clear();
						nameList = contact.getNameList();
						for (int i = 0; i < nameList.size(); i++) {
							model1.addElement(nameList.get(i));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		// Listener to editCurrentContactButton, method: delete current contact and add a new one
		editCurrentContactButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (cardFlag.equals("NAME")) {

					JSONObject jsonContact = new JSONObject();
					JSONObject currentContact = new JSONObject();

					String currentName = "";
					String currentFirstName = "";
					String currentLastName = "";
					String currentPhoneNumber = "";
					String currentEMailAddress = "";
					String currentHomeAddress = "";
					String currentDateOfBirth = "";
					String currentPhotoPath = "";

					Contact contact = null;
					try {
						contact = new Contact();
						jsonContact = contact.getJsonContact();
						currentName = (String) jlist1.getSelectedValue();
						currentContact = jsonContact.getJSONObject(currentName);
						currentPhoneNumber = currentContact
								.getString("PhoneNumber");
						currentEMailAddress = currentContact
								.getString("EMailAddress");
						currentHomeAddress = currentContact
								.getString("HomeAddress");
						currentDateOfBirth = currentContact
								.getString("DateOfBirth");
						currentPhotoPath = currentContact
								.getString("PhotoPath");
						if (currentName.indexOf(", ") != -1) {
							String[] nameStr = currentName.split(", ");
							currentLastName = nameStr[0];
							currentFirstName = nameStr[1];
						} else {
							currentLastName = currentName;
							currentFirstName = "";
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Delete
					contact = null;
					try {
						contact = new Contact();
						contact.delete((String) jlist1.getSelectedValue());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						contact = new Contact();
						model1.clear();
						nameList = contact.getNameList();
						for (int i = 0; i < nameList.size(); i++) {
							model1.addElement(nameList.get(i));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Add
					edialog = new EditCurrentContactDialog(
							frame, currentFirstName, currentLastName,
							currentPhoneNumber, currentEMailAddress,
							currentHomeAddress, currentDateOfBirth);
					edialog.setLocationRelativeTo(null);

					currentFirstName = edialog.getFNameText();
					currentLastName = edialog.getLNameText();
					currentPhoneNumber = edialog.getPhoneNumberText();
					currentEMailAddress = edialog.getEMailAddressText();
					currentHomeAddress = edialog.getHomeAddressText();
					currentDateOfBirth = edialog.getDateOfBirthText();
					currentPhotoPath = edialog.getPhotoPath();
					currentName = currentLastName + ", " + currentFirstName;

					contact = null;
					try {
						contact = new Contact();
						model1.clear();
						nameList = contact.getNameList();
						for (int i = 0; i < nameList.size(); i++) {
							model1.addElement(nameList.get(i));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					HashMap<String, String> currentPerson = new HashMap<String, String>();
					currentPerson.put("PhoneNumber", currentPhoneNumber);
					currentPerson.put("EMailAddress", currentEMailAddress);
					currentPerson.put("HomeAddress", currentHomeAddress);
					currentPerson.put("DateOfBirth", currentDateOfBirth);
					currentPerson.put("PhotoPath", currentPhotoPath);
					try {
						contact.add(currentName, currentPerson);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						contact = new Contact();
						model1.clear();
						nameList = contact.getNameList();
						for (int i = 0; i < nameList.size(); i++) {
							model1.addElement(nameList.get(i));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		// Listener to deleteCurrentContactButton
		deleteCurrentContactButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (cardFlag.equals("NAME")) {
					Contact contact = null;
					try {
						contact = new Contact();
						contact.delete((String) jlist1.getSelectedValue());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						contact = new Contact();
						model1.clear();
						nameList = contact.getNameList();
						for (int i = 0; i < nameList.size(); i++) {
							model1.addElement(nameList.get(i));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		pane.add(buttonPanel, BorderLayout.PAGE_END);
		// pane.add(centerDisplayPanel, BorderLayout.PAGE_END);
	}

	public void run() {

		Contact contact = null;
		try {
			contact = new Contact();
			nameList = contact.getNameList();
			model1.clear();
			for (int i = 0; i < nameList.size(); i++) {
				model1.addElement(nameList.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setLocationRelativeTo(null);

		rightPanel.setPreferredSize(new Dimension(220, 800));
		leftPanel.setPreferredSize(new Dimension(220, 800));
		// rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		// leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		centerPanel.setLayout(new BorderLayout());
		// Add components to panels
		addComponentsToHeaderPane(headerPanel);
		addComponentsToFooterPane(footerPanel);
		addComponentsToLeftPane(leftPanel);
		addComponentsToRightPane(rightPanel);
		addComponentsToCenterPane(centerPanel);

		// Add panels to frame
		frame.add(headerPanel, BorderLayout.PAGE_START);
		frame.add(footerPanel, BorderLayout.PAGE_END);
		frame.add(leftPanel, BorderLayout.LINE_START);
		frame.add(rightPanel, BorderLayout.LINE_END);
		frame.add(centerPanel, BorderLayout.CENTER);

		// Display frame
		frame.setSize(1000, 800);
		frame.setResizable(false);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		new PersonalOrganiserGUI();
	}
}
