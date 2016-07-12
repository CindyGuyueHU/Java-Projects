package expression;
/**
 * Subtraction expression
 * 
 * @author Guyue Hu, u5608260
 */
public class Subtraction extends Binop {
	
	public Subtraction(Expression left_expression, Expression right_expression) {
		// Subtract the left sub-expression by the right sub-expression
		le = left_expression;
		re = right_expression;
	}

	@Override
	public String show() {
		// show the substraction expression
		return  "(" + le.show()  + " - "  + re.show() + ")";
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		// return the subtraction of the left hand side expression and the right hand side expression
		return le.evaluate(depth) - re.evaluate(depth);
	}
}
