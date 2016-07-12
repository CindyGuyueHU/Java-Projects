package expression;
/**
 * Pi expression
 * 
 * @author Guyue Hu, u5608260
 * @author Hongyu Gao, u5274725
 */
public class Pi extends Constant {

	@Override
	public String show() {
		// pi expression
		return "Pi";
	}

	@Override
	public double evaluate(int depth) {
		// return the value of pi
		return 3.14159;
	}

}
