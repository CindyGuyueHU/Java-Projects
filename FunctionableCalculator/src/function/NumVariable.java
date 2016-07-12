package function;

import expression.ParseException;

/**
 * CellVariable - this is the class for functional variable of "Cell" type
 * 
 * 
 * @author Guyue HU
 */

public class NumVariable extends FunVariable {

	String name;
	Double value;
	
	public NumVariable(String s) {
		// a number variable
		name = s;
		value = 0.0;
	}
	
	@Override
	public String show() {
		// show the name of the variable
		return name;
	}
	
	@Override
	public void setValue(double d) {
		// set the value of the variable to d
		value = d;
	}
	
	public double getNumValue() {
		// get the value of the variable
		return value;
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		//return the calculated value of a variable
		return value;
	}
	
	public boolean IsNumVariable() {
		// return true indicating this is a num variable
		return true;
	}
}
