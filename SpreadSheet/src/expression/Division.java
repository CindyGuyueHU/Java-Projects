package expression;

/**
 * Division expression
 * 
 * @author Guyue HU, u5608260
 */

public class Division extends Binop {
	public Division(Expression left_expression, Expression right_expression) {
		// divide the left sub-expression by the right sub-expression
		le = left_expression;
		re = right_expression;
	}

	@Override
	public String show() {
		return "( " + le.show() + " / "  + re.show() + " )";
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		// returns the division of the left hand side expression and the right hand side expression
		if (re.evaluate(depth) != 0) return le.evaluate(depth) / re.evaluate(depth);
		else throw new ParseException("Divided by 0");
	}
}
