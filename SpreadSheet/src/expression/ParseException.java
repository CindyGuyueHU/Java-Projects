package expression;

import javax.swing.JOptionPane;

/**
 * ParseException
 * 
 * @author Guyue Hu, u5608260
 */
public class ParseException extends Exception {
	public ParseException(String str) {
		// show the error message
		JOptionPane.showMessageDialog(null, str);
	}
}
