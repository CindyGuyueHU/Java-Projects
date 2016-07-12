package function;

import java.util.ArrayList;

import expression.Expression;
import expression.ParseException;

/**
 * Function - this is the class for functions
 * 			  contains a list of functional variable
 * 			  and a list of statements
 * 
 * 
 * @author Guyue HU
 */

public class Function extends Expression {

	String name;
	ArrayList<FunVariable> funVariable = null;
	ArrayList<Statement> statement = null;
	
	public Function(String n) {
		// a new function named n
		name = n;
	}
	
	public void setVariable(ArrayList<FunVariable> fv) {
		// set the functional variables
		funVariable = fv;
	}
	
	public void setStatement(ArrayList<Statement> st) {
		// set the statements
		statement = st;
	}

	@Override
	public String show() {
		// show the name of the function
		return name;
	}
	
	public ArrayList<FunVariable> getVarList() {
		// get the list of functional variables
		return funVariable;
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		// run the statements in a procedural function one by one
		Object d = null;
		for (int i = 0; i < statement.size(); i++) {
			d = statement.get(i).evaluate(0);
		}
		return (double)d;
	}

}
