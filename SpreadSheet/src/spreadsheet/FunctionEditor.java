package spreadsheet;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import expression.ParseException;

public class FunctionEditor extends JDialog implements ActionListener {
/**
 * FunctionEditor - a simple modal dialog to edit the text of the functions. 
 * 
 * @author Eric McCreath
 * 
 */
	
	
	WorkSheet worksheet;
	JTextArea textarea;

	public FunctionEditor(WorkSheet worksheet) {
		this.worksheet = worksheet;
		textarea = new JTextArea(20, 50);
		JButton closebutton = new JButton("Close");
		closebutton.addActionListener(this);
		this.setModal(true);
		this.getContentPane().add(new JScrollPane(textarea), BorderLayout.CENTER);
		this.getContentPane().add(closebutton, BorderLayout.PAGE_END);
        this.pack();
	}

	public void setWorksheet(WorkSheet ws) {
		worksheet = ws;
		textarea.setText(worksheet.getFuctions());
	}
	public void actionPerformed(ActionEvent ae) { // it is always the close button
		try {
			updateWorksheet();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispose();
	}
	public void updateWorksheet() throws ParseException {
		worksheet.setFunctions(textarea.getText());
	}
}
