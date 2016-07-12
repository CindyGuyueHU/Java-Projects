package spreadsheet;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import expression.Expression;
import expression.ParseException;

public class Spreadsheet implements Runnable, ActionListener,
		SelectionObserver, DocumentListener, ItemListener {

	private static final Dimension PREFEREDDIM = new Dimension(1000, 600);
	/**
	 * Spreadsheet - a simple spreadsheet program. Eric McCreath 2015
	 */

	private static final String EXITCOMMAND = "exitcommand";
	private static final String CLEARCOMMAND = "clearcommand";
	private static final String SAVECOMMAND = "savecommand";
	private static final String OPENCOMMAND = "opencommand";
	private static final String EDITFUNCTIONCOMMAND = "editfunctioncommand";
	private static final String PICOMMAND = "picommand";
	private static final String ECOMMAND = "ecommand";
	private static final String ADDTABCOMMAND = "addtabcommand";
	private static final String COPYCOMMAND = "copycommand";
	private static final String PASTECOMMAND = "pastecommand";
	private static final String CALCULATECOMMAND = "calculatecommand";
	private static final String ABOUTCOMMAND = "aboutcommand";
	private static final String TIPSCOMMAND = "tipscommand";

	JFrame jframe;
	WorksheetView worksheetview, worksheetview1;
	FunctionEditor functioneditor;
	WorkSheet worksheet, worksheet1;
	ArrayList<WorkSheet> worksheet_list = new ArrayList<WorkSheet>();
	ArrayList<WorksheetView> worksheetview_list = new ArrayList<WorksheetView>();
	
	//use clipboard to copy and paste
	String clipboard;
	
	//multiple worksheet tabbed panel
	JTabbedPane multiPan = null;
	JButton addButton = null;
	int Count = 1;

	JButton calculateButton, piButton, eButton;
	JTextField cellEditTextField;
	JLabel selectedCellLabel;
	JFileChooser filechooser = new JFileChooser();

	Choice funList;

	public Spreadsheet() {
		SwingUtilities.invokeLater(this);
	}

	public static void main(String[] args) {
		new Spreadsheet();
	}

	public void run() {
		jframe = new JFrame("Spreadsheet");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set up the menu bar
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("File");
		bar.add(menu);
		makeMenuItem(menu, "New", CLEARCOMMAND);
		makeMenuItem(menu, "Open", OPENCOMMAND);
		makeMenuItem(menu, "Save", SAVECOMMAND);
		makeMenuItem(menu, "Exit", EXITCOMMAND);

		// add new menu items which are copy and paste
		menu = new JMenu("Edit");
		bar.add(menu);
		makeMenuItem(menu, "Copy", COPYCOMMAND);
		makeMenuItem(menu, "Paste", PASTECOMMAND);
		
		// add new menu item which can let user to custom function
		menu = new JMenu("Function");
		bar.add(menu);
		makeMenuItem(menu, "EditFunction", EDITFUNCTIONCOMMAND);

		menu = new JMenu("Help");
		bar.add(menu);
		makeMenuItem(menu, "About", ABOUTCOMMAND);
		makeMenuItem(menu, "Tips", TIPSCOMMAND);
		
		jframe.setJMenuBar(bar);
		worksheet1 = new WorkSheet(this);
		worksheetview1 = new WorksheetView(worksheet1);
		worksheetview1.addSelectionObserver(this);
		worksheet_list.add(worksheet1);
		worksheetview_list.add(worksheetview1);
		worksheet = worksheet1;
		worksheetview = worksheetview1;

		// set up the tool area
		JPanel toolarea = new JPanel();
		calculateButton = new JButton("Calculate");
		calculateButton.addActionListener(this);
		calculateButton.setActionCommand(CALCULATECOMMAND);
		toolarea.add(calculateButton);
		selectedCellLabel = new JLabel("--");
		selectedCellLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		toolarea.add(selectedCellLabel);
		cellEditTextField = new JTextField(20);
		cellEditTextField.getDocument().addDocumentListener(this);
		//when finished input, click enter to calculate
		cellEditTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					performCalculate();
				}
			}
		});
		toolarea.add(cellEditTextField);
		
		//add pi and e constant button to make operation more easier
		ImageIcon piicon = new ImageIcon("pi.png");
		piButton = new JButton(piicon);
		piButton.addActionListener(this);
		piButton.setActionCommand(PICOMMAND);
		toolarea.add(piButton);
		
		ImageIcon eicon = new ImageIcon("e.png");
		eButton = new JButton(eicon);
		eButton.addActionListener(this);
		eButton.setActionCommand(ECOMMAND);
		toolarea.add(eButton);

		funList = new Choice();
		funList.add("functions ...");
		funList.addItemListener(this);
		toolarea.add(funList);
		
		//complete muitiple worksheets
		multiPan = new JTabbedPane();
		addButton = new JButton("+");
		addButton.addActionListener(this);
		addButton.setActionCommand(ADDTABCOMMAND);
		multiPan.setTabLayoutPolicy(multiPan.SCROLL_TAB_LAYOUT);
		multiPan.addTab("worksheet1", new JScrollPane(worksheetview));
		multiPan.setTabPlacement(JTabbedPane.BOTTOM);
		multiPan.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (multiPan.getSelectedComponent() != null) {
					int selectedIndex = multiPan.getSelectedIndex();
					String title = multiPan.getTitleAt(selectedIndex);
					System.out.println(title);
					System.out.println(selectedIndex);
					worksheet1 = worksheet_list.get(selectedIndex);
					worksheetview1 = worksheetview_list.get(selectedIndex);
					worksheet = worksheet1;
					worksheetview = worksheetview1;
				}
			}
		});

		functioneditor = new FunctionEditor(worksheet);

		// JScrollPane(worksheetview),BorderLayout.CENTER);
		jframe.getContentPane().add(toolarea, BorderLayout.PAGE_START);
		jframe.getContentPane().add(multiPan, BorderLayout.CENTER);
		jframe.getContentPane().add(addButton, BorderLayout.PAGE_END);

		jframe.setVisible(true);
		jframe.setPreferredSize(PREFEREDDIM);
		jframe.pack();
	}

	private void makeMenuItem(JMenu menu, String name, String command) {
		JMenuItem menuitem = new JMenuItem(name);
		menu.add(menuitem);
		menuitem.addActionListener(this);
		menuitem.setActionCommand(command);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals(EXITCOMMAND)) {
			exit();
		} else if (ae.getActionCommand().equals(SAVECOMMAND)) {
			int res = filechooser.showSaveDialog(jframe);
			if (res == JFileChooser.APPROVE_OPTION) {
				worksheet.save(filechooser.getSelectedFile());
			}
		} else if (ae.getActionCommand().equals(OPENCOMMAND)) {
			int res = filechooser.showOpenDialog(jframe);
			if (res == JFileChooser.APPROVE_OPTION) {
				worksheet = WorkSheet.load(filechooser.getSelectedFile(), this);
				worksheetChange(worksheet, worksheetview);
			}
		} else if (ae.getActionCommand().equals(CLEARCOMMAND)) {
			worksheet = new WorkSheet(this);
			worksheetChange(worksheet, worksheetview);
		} else if (ae.getActionCommand().equals(EDITFUNCTIONCOMMAND)) {
			functioneditor.setVisible(true);

		} else if (ae.getActionCommand().equals(CALCULATECOMMAND)) {
			performCalculate();
		} else if (ae.getActionCommand().equals(PICOMMAND)) {

			CellIndex index = worksheetview.getSelectedIndex();
			Cell current = worksheet.lookup(index);
			if (current.getText().equals("")) {
				cellEditTextField.setText(worksheet.lookup(index).getText()
						+ "=p");
			} else {
				cellEditTextField.setText(worksheet.lookup(index).getText()
						+ "p");
			}
			worksheetview.repaint();
		} else if (ae.getActionCommand().equals(ECOMMAND)) {

			CellIndex index = worksheetview.getSelectedIndex();
			Cell current = worksheet.lookup(index);
			if (current.getText().equals("")) {
				cellEditTextField.setText(worksheet.lookup(index).getText()
						+ "=e");
			} else {
				cellEditTextField.setText(worksheet.lookup(index).getText()
						+ "e");
			}
			worksheetview.repaint();
		} else if (ae.getActionCommand().equals(ADDTABCOMMAND)) {
			Count++;
			multiPan.add("worksheet" + Count, addNewWorkSheet("worksheet"
					+ Count));
		} else if (ae.getActionCommand().equals(COPYCOMMAND)) {

			CellIndex index = worksheetview.getSelectedIndex();
			Cell current = worksheet.lookup(index);

			clipboard = current.getText();
		} else if (ae.getActionCommand().equals(PASTECOMMAND)) {

			CellIndex index = worksheetview.getSelectedIndex();
			Cell current = worksheet.lookup(index);

			current.setText(clipboard);
			cellEditTextField.setText(clipboard);
		} else if (ae.getActionCommand().equals(ABOUTCOMMAND)) {
			JDialog aboutjd = new JDialog();
			aboutjd.setSize(300, 200);
			Container c = aboutjd.getContentPane();
			aboutjd.setTitle("About");
			JLabel jl = new JLabel("<html><b><font size=5 color= black>"
					+ "<center>author: " 
					+ "<center> Guyue Hu, u5608260" 
					+ "<center> Hongyu Gao, u5274725"
					+ "<center>date:"
					+ "<center>22.05.2015", JLabel.CENTER);
			c.add(jl, BorderLayout.CENTER);
			aboutjd.setVisible(true);
		} 
		else if (ae.getActionCommand().equals(TIPSCOMMAND)) {
			JDialog helpjd = new JDialog();
			helpjd.setSize(500, 300);
			Container c = helpjd.getContentPane();
			helpjd.setTitle("Tips");
			JLabel jl = new JLabel("<html><b><font size=3 color = black>"
					+ "<br> Tips: " 
					+ "<br> "
					+ "<br> -  If you would like to input a text then select one cell and input the content in text field." 
					+ "<br> -  If you would like to calulate then select one cell and input '=' before you input the numbers or operators"
					+ "<br> -  You can calculate many equations like addition, division, multiplication, substraction and so on."
					+ "<br> -  You can calculate what you want and press the button 'calculate' or just use the key 'Enter'. "
					+ "<br> -  You can input constant pi and e by the button pi and e"
					+ "<br> -  You can custom the function you like(such as max, fun, sum, etc.) in editfunction"
					+ "<br> -  You can select what function you added before in the functionlist to keep calculating"
					+ "<br> -  You can copy and paste the cell"
					+ "<br> -  The cell can be recalculate when the relative cells changed automatically."
					,JLabel.CENTER);
			c.add(jl, BorderLayout.PAGE_START);
			helpjd.setVisible(true);
			
		}
	}

	private void performCalculate() {
		String index = worksheetview.getSelectedIndex().show();
		String text = cellEditTextField.getText();
		worksheet.put(index, text);
		try {
			worksheet.calculate();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		worksheetview.repaint();

	}
	//add worksheet
	private Component addNewWorkSheet(String s) {
		WorkSheet worksheet1 = new WorkSheet(this);
		WorksheetView worksheetview1 = new WorksheetView(worksheet1);
		worksheetview1.addSelectionObserver(this);
		worksheet_list.add(worksheet1);
		worksheetview_list.add(worksheetview1);
		worksheetChange(worksheet1, worksheetview1);
		return new JScrollPane(worksheetview1);
	}
	//change worksheet
	private void worksheetChange(WorkSheet ws, WorksheetView wsv) {
		worksheetview.setWorksheet(worksheet);
		functioneditor.setWorksheet(worksheet);
		worksheetview.repaint();
	}

	private void exit() {
		System.exit(0);
	}

	@Override
	public void update() {
		CellIndex index = worksheetview.getSelectedIndex();
		selectedCellLabel.setText(index.show());
		cellEditTextField.setText(worksheet.lookup(index).getText());
		jframe.repaint();
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		textChanged();
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		textChanged();
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		textChanged();
	}

	private void textChanged() {
		CellIndex index = worksheetview.getSelectedIndex();
		Cell current = worksheet.lookup(index);
		current.setText(cellEditTextField.getText());
		worksheetview.repaint();
	}
	//set function list to make operation more convinient
	public void setFunList(ArrayList<Expression> functions) {
		for (int i = 0; i < functions.size(); i++) {
			System.out.println(i);
			String s = functions.get(i).show();
			funList.add(s);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		String s = funList.getItem(funList.getSelectedIndex());
		String s1 = cellEditTextField.getText();
		if (s1.equals("")) {
			s1 = "=" + s + "(";
		} else {
			s1 = s1 + s + "(";
		}
		cellEditTextField.setText(s1);
	}
}