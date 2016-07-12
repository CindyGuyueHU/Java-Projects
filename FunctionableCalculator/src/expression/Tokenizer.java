package expression;

import javax.swing.JOptionPane;
/**
 * Tokenizer - this uses the StreamTokenizer class to make a simpler tokenizer
 * which provides a stream of tokens which are either Integer, Double, or
 * String.
 * 
 * @author Guyue Hu, u5608260
 * @author Hongyu Gao, u5274725
 * 
 */
public abstract class Tokenizer {

	abstract boolean hasNext();
	// if the string has a next token

	public abstract Object current();
	// return the current token

	public abstract void next();
	// move to the next token

	public void parse(Object o) throws ParseException {
		if (current() == null || !current().equals(o))
			//throw new ParseException();
			JOptionPane.showMessageDialog(null, "Wrong format: please check the bracket to keep calculating");
		next();
	}
}
