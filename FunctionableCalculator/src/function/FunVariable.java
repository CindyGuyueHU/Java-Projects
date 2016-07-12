package function;

import spreadsheet.CellIndex;
import expression.Expression;
import expression.ParseException;

/**
 * FunVariable - this is the class for functional variables, there can be two types of variables:
 * 				 cell variable and
 * 				 number variable
 * 
 * @author Guyue HU
 */

public abstract class FunVariable extends Expression {
	public String show() {
		// show the name of the functional variable
		return "";
	}

	public double evaluate(int depth) throws ParseException {
		// evaluate the functional variable
		return 0;
	}
	
	public boolean IsNumVariable() {
		// return if the variable is a number variable
		return false;
	}
	
	public boolean IsCellVariable() {
		// return if the variable is a cell variable
		return false;
	}
	
	public void setValue(double d) {
		// set the value of a num variable
	}
	
	public void setValue(CellIndex c) {
		// set the value of a cell variable
	}
	
	public CellIndex getValue() {
		// get the value of a cell variable
		return null;
	}
	
	public double getNumValue() {
		// get the value of a num variable
		return 0.0;
	}
}
