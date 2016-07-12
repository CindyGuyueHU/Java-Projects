package expression;
/**
 * Number expression
 * 
 * @author Guyue Hu, u5608260
 */
public class Number extends Expression {

	double ne;
	
	public Number(double number) {
		// a number expression
		ne = number;
	}
	
	@Override
	public String show() {
		// show the number
		return Double.toString(ne);
	}

	@Override
	public double evaluate(int depth) {
		// return the value of the number
		return ne;
	}

}
