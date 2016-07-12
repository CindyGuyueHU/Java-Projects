package expression;
/**
 * Uniop expression
 *
 * 
 * @author Guyue Hu, u5608260
 */

public class Uniop extends Expression {

	Expression ue;
	
	@Override
	public String show() {
		// show the uniop expression
		return null;
	};

	@Override
	public double evaluate(int depth) throws ParseException {
		// evaluate the value of a uniop expression
		return 0;
	};

}
