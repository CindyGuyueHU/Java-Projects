package function;

import java.util.ArrayList;

import expression.Expression;
import expression.ParseException;

/**
 * AssignStatement - this is the class for "if" statement
 * 					 which can implement the if control flow
 * 
 * 
 * @author Guyue HU
 */

public class IfStatement extends Statement {

	Expression condExp;
	ArrayList<Statement> statement1, statement2;
	
	
	public IfStatement(Expression cp) {
		// a new if statement controlled by the condition cp
		condExp = cp;
		statement1 = new ArrayList<Statement>();
		statement2 = new ArrayList<Statement>();
	}

	@Override
	public String show() {
		// don't show anything for a if statement
		return null;
	}
	
	@Override
	public void addSubStatement1(Statement sta1) {
		// statement 1 record the sub statements if the condExp is true
		statement1.add(sta1);
	}
	
	@Override
	public void addSubStatement2(Statement sta2) {
		// statement 1 record the sub statements if the condExp is false
		statement2.add(sta2);
	}
	
	@Override
	public double evaluate(int depth) throws ParseException {
		double cond = condExp.evaluate(0);
		if (cond == 0.0) { // false
			// run the statements recorded in statemens2
			for (int k = 0; k < statement2.size(); k++) {
				statement2.get(k).evaluate(0);
			}
		} else { //true
			// run the statements recorded in statemens1
			for (int k = 0; k < statement1.size(); k++) {
				statement1.get(k).evaluate(0);
			}
		}
		return 0.0;
	}


}
