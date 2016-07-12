package function;

import java.util.ArrayList;

import spreadsheet.CellIndex;
import spreadsheet.WorkSheet;
import expression.Expression;
import expression.ParseException;

/**
 * AssignStatement - this is the class for assign statement
 * 					 which can assign values to a (number/cell) functional variable;
 * 
 * 
 * @author Guyue HU
 */

public class AssignStatement extends Statement {
	
	FunVariable var;
	Expression exp;
	WorkSheet w;
	Function f;
	
	public AssignStatement(FunVariable v, Expression e, WorkSheet ws, Function fun) {
		// a new assign statement
		// assigning the value of the e to the v
		var = v;
		exp = e;
		w = ws;
		f = fun;
	}

	@Override
	public String show() {
		// don't show anything for assigning statement
		return null;
	}

	@Override
	public double evaluate(int depth) throws ParseException {
		if (var.IsNumVariable()) {
			// return the value of the num variable
			var.setValue(exp.evaluate(0));
		} else if (var.IsCellVariable()) {
			// calculate the expression in the cell variable and return the value
			String s = exp.show();
			ArrayList<FunVariable> vl = f.getVarList();
			FunVariable v = Expression.matchFunVariable(s, vl);
			if (v != null) {
				var.setValue(v.getValue());
			} else {
				CellIndex idx = new CellIndex(exp.show());
				var.setValue(idx);
			}
		}
		return 0.0;
	}


}
