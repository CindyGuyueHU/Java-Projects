package expression;

/**
 * Constant expression - e | pi
 * 
 * @author Guyue Hu, u5608260
 */

public class Constant extends Expression {
	
	
	@Override
	public String show() {
		// show constant
		return null;
	};

	@Override
	public double evaluate(int depth) {
		// evaluate constant
		return 0;
	};

}
