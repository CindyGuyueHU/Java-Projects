package expression;

/**
 * Addition expression
 * 
 * @author Guyue HU, u5608260
 */

public class Addition extends Binop {

	public Addition(Expression left_expression, Expression right_expression) {
		// add the left and right subexpression
		le = left_expression;
		re = right_expression;
	}

	@Override
	public String show() {
		//show the addition expression
		return "(" + le.show() + " + " + re.show() + ")";
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		// return the sum of the left hand side expression and the right hand side expression
		return le.evaluate(depth) + re.evaluate(depth);
	}
}
