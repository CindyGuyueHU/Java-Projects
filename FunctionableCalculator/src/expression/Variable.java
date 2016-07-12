package expression;

import spreadsheet.Cell;
import spreadsheet.CellIndex;
import spreadsheet.WorkSheet;
/**
 * Variable expression
 * 
 * @author Guyue Hu, u5608260
 */
public class Variable extends Expression {

	CellIndex idx;
	WorkSheet w;
	
	public Variable(String s, WorkSheet ws) {
		// cell variable like A1, C3,...etc.
		idx = new CellIndex(s);
		w = ws;
	}
	
	@Override
	public String show() {
		// show the index of the cell
		return idx.show();
	}

	public double evaluate(int depth) throws ParseException {
		//return the calculated value of a variable
			Cell current = w.lookup(idx);
			if (depth < 20) {
				return current.calculate(w, depth + 1);
			} else throw new ParseException("Cell references in the fomular refer to the fomular's result");
	}


}
