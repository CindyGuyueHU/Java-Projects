package expression;

/**
 * Binop expression - * | / | + | - | ** | > | < | =
 * 
 * @author Guyue Hu, u5608260
 */

public class Binop extends Expression {
	
	Expression le;
	Expression re;
	
	@Override
	public String show() {
		// show binop expression
		return null;
	};

	@Override
	public double evaluate(int depth) throws ParseException {
		// evaluate binop expression
		return 0;
	};

}
