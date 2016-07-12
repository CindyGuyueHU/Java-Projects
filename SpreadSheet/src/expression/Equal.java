package expression;

/**
 * Equal expression
 * 
 * @author Guyue Hu, u5608260
 */

public class Equal extends Binop {
	
	public Equal(Expression left_expression, Expression right_expression) {
		// equality of the left sub-expression and right sub-expression
		le = left_expression;
		re = right_expression;
	}

	@Override
	public String show() {
		// show the quality expression
		return "(" + le.show() + " = " + re.show() + ")";
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		// Returns 1.0 if two expressions equals
		// Returns 0.0 if two expressions not equals
		if (le.evaluate(depth) == re.evaluate(depth)) {
			return 1.0;
		} else return 0.0;
	}

}
