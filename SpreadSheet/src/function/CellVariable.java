package function;

/**
 * CellVariable - this is the class for functional variable of "Cell" type
 * 
 * 
 * @author Guyue HU
 */

import spreadsheet.CellIndex;
import spreadsheet.WorkSheet;
import expression.ParseException;

public class CellVariable extends FunVariable {
	String name;
	CellIndex value;
	WorkSheet w;
	
	public CellVariable(String s, WorkSheet worksheet) {
		// a cell variable named s
		name = s;
		value = null;
		w = worksheet;
	}
	
	@Override
	public String show() {
		// show the name of the cell variable
		return name;
	}
	
	@Override
	public void setValue(CellIndex c) {
		// set the value(index) of the cell variable
		value = c;
	}
	
	@Override
	public CellIndex getValue() {
		// get the index of the cell variable
		return value;
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		//return the calculated value of a variable
		return w.lookup(value).calculate(w, 0);
	}
	
	@Override
	public boolean IsCellVariable() {
		// return true if it is a cell variable
		return true;
	}
}
