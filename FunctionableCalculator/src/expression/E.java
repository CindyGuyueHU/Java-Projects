package expression;
/**
 * E - this is the class on Euler’s number expression which can show and evaluate.
 * 
 * @author Guyue Hu, u5608260
 * @author Hongyu Gao, u5274725
 */

public class E extends Constant {

	@Override
	public String show() {
		//show the e
		return "e";
	}

	@Override
	public double evaluate(int depth) {
		//return the value of the e
		return 2.71828;
	}

}
