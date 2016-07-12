package function;

import java.util.ArrayList;

import expression.ParseException;

/**
 * AssignStatement - this is the class for "For" statement
 * 					 which can run for loop on Number variables
 * 
 * 
 * @author Guyue HU
 */

public class NumForStatement extends Statement {

	NumVariable variable, start_variable, end_variable;
	ArrayList<Statement> for_statement = null;
	
	public NumForStatement(NumVariable v, NumVariable v1, NumVariable v2) {
		// a for statement loop on number variable v
		// loop from v1 to v2
		variable = v;
		start_variable = v1;
		end_variable = v2;
		for_statement = new ArrayList<Statement>();
	}
	
	@Override
	public void addSubStatement(Statement s){
		// add substatement inside the for loop
		for_statement.add(s);
	}

	@Override
	public String show() {
		// don't show anything for a for statement
		return null;
	}
	
	@Override
	public double evaluate(int depth) throws ParseException {
		// for the integers between v1 and v2
		// run the inner statements of the for loop
		int val1 = (int) start_variable.getNumValue();
		int val2 = (int) end_variable.getNumValue();
		for (int i = val1; i < val2+1; i++){
			variable.setValue(i);
			for (int k = 0; k < for_statement.size(); k++) {
				for_statement.get(k).evaluate(0);
			}
		}
		return 0.0;
	}

}
