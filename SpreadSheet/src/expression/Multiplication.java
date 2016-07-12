package expression;
/**
 * Multiplication expression
 * 
 * @author Guyue Hu, u5608260
 */
public class Multiplication extends Binop {
	
	public Multiplication(Expression left_expression,
			Expression right_expression) {
		// multiply the left sub-expression by the right sub-expression
		le = left_expression;
		re = right_expression;
	}

	@Override
	public String show() {
		// show the multiply expression
		return "( " + le.show()  + " * " +  re.show() + " )";
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		// return the product of the left hand side expression and the right hand side expression
		return le.evaluate(depth) * re.evaluate(depth);
	}
}
