package expression;
/**
 * Power expression
 * 
 * @author Guyue Hu, u5608260
 */
public class Power extends Binop {

	public Power(Expression left_expression, Expression right_expression) {
		// power the left sub-expression by the right sub-expression
		le = left_expression;
		re = right_expression;
	}

	@Override
	public String show() {
		// show the power expression
		return "(" + le.show()  + "^" +  re.show() + ")";
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		// return the left hand side expression to the power of the right hand side expression
		return Math.pow(le.evaluate(depth), re.evaluate(depth));
	}

}
