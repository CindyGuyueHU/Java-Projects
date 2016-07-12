package function;

import expression.Expression;
import expression.ParseException;

/**
 * AssignStatement - this is the class for assign statement
 * 					 which can return the value of a expression
 * 
 * 
 * @author Guyue HU
 */

public class ReturnStatement extends Statement {

	Expression value;
	
	public ReturnStatement(Expression v) {
		// a new return statement return the value of v
		value = v;
	}

	@Override
	public String show() {
		// don't show anything for a return statement
		return null;
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		// evaluate the value to return
		return value.evaluate(0);
	}
	
	public boolean isReturnStatement() {
		// return true indicating this is a return statement
		return true;
	}
}
