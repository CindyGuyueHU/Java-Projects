package expression;
/**
 * Inverse expression
 * 
 * @author Guyue Hu, u5608260
 * @author Hongyu Gao, u5274725
 */
public class Inverse extends Uniop {

	public Inverse(Expression uni_expression) {
		// inverse the sub-expression
		ue = uni_expression;
	}

	@Override
	public String show() {
		// show the inverse expression
		return "- " + "( " + ue.show() + " )";
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		// returns the inverse of the expression
		return 0 - ue.evaluate(depth);
	}

}
