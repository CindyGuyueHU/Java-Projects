package spreadsheet;
/**
 * Cell - an object of this class holds the data of a single cell. 
 * 
 * @author Eric McCreath
 */

import expression.Expression;
import expression.MySimpleTokenizer;
import expression.ParseException;
import expression.Tokenizer;

public class Cell {

	private String text; // this is the text the person typed into the cell
	private Double calculatedValue; // this is the current calculated value for
									// the cell

	public Cell(String text) {
		this.text = text;
		calculatedValue = null;
	}

	public Cell() {
		text = "";
		calculatedValue = null;
	}

	public Double value() {
		return calculatedValue;
	}
	

	public double calculate(WorkSheet worksheet, int depth) throws ParseException {
		calculatedValue = null;
		Tokenizer tok = new MySimpleTokenizer(text);
		if (tok.current().equals("=")) {
			tok.next();
			Expression exp = Expression.parseInequality(tok, worksheet, null);
			calculatedValue = exp.evaluate(depth);	
		} else if (tok.current() instanceof Double) {
			Expression exp = Expression.parseInequality(tok, worksheet, null);
			calculatedValue = exp.evaluate(depth);
		}
		return calculatedValue;
	}

	public String show() { // this is what is viewed in each Cell
		//System.out.println(text);
		//System.out.println(calculatedValue);
		return calculatedValue == null ? text : calculatedValue.toString();
	}

	@Override
	public String toString() {
		return text + "," + calculatedValue;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public boolean isEmpty() {
		return text.equals("");
	}
}
