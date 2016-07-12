package function;

/**
 * AssignStatement - this is the class for all statement
 * 					 possible statement type: "for" statement
 * 											  "if" statement
 * 											  "return" statement
 * 											   assignment statement
 * 					 
 * 
 * 
 * @author Guyue HU
 */

import expression.Expression;
import expression.ParseException;

public abstract class Statement extends Expression {

	public Statement() {
		// all kinds of statements
	}
	
	public double evaluate(int depth) throws ParseException {
		// execute the statement
		return 0;
	}
	
	public boolean isReturnStatement() {
		// return if the statement is a return statement
		return false;
	}
	
	public void addSubStatement(Statement s){
		// add substatements to a for statement
	}

	public void addSubStatement1(Statement sta1) {
		// add the true substatements to a if statement
	}

	public void addSubStatement2(Statement sta2) {
		// add the false substatements to a if statement
	}

}
