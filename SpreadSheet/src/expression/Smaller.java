package expression;

/**
 * Smaller expression
 * 
 * @author Guyue Hu, u5608260
 */

public class Smaller extends Binop {

   public Smaller(Expression left_expression, Expression right_expression) {
	   // the left sub-expression less than the right sub-expression
			le = left_expression;
			re = right_expression;
		}

	@Override
	public String show() {
		// show the less than expression
		return "(" + le.show() + " < " + re.show() + ")";
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		// Returns 1.0 if left hand side expression is smaller than right hand side expression
		// Returns 0.0 otherwise
		if (le.evaluate(depth) < re.evaluate(depth)) {
			return 1.0;
		} else return 0.0;
	}
}

