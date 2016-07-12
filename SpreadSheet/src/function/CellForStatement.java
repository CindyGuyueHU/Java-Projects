package function;

import java.util.ArrayList;

import spreadsheet.CellIndex;
import expression.ParseException;

/**
 * AssignStatement - this is the class for "For" statement
 * 					 which can run for loop on Cell variables
 * 
 * 
 * @author Guyue HU
 */

public class CellForStatement extends Statement {

	CellVariable variable, start_variable, end_variable;
	ArrayList<Statement> for_statement = null;
	
	public CellForStatement(CellVariable v, CellVariable v1, CellVariable v2) {
		// a new for statement on cell variable v
		// loop from v1 to v2
		variable = v;
		start_variable = v1;
		end_variable = v2;
		for_statement = new ArrayList<Statement>();
	}
	
	@Override
	public void addSubStatement(Statement s){
		// add substatements inside the loop
		for_statement.add(s);
	}

	@Override
	public String show() {
		// dont show anything for a for statement
		return null;
	}
	
	@Override
	public double evaluate(int depth) throws ParseException {
		// for the cells between v1 and v2
		// run the inner statements of the for loop
		int row1 = start_variable.getValue().get_row();
		int row2 = end_variable.getValue().get_row();
		int col1 = start_variable.getValue().get_col();
		int col2 = end_variable.getValue().get_col();
		for (int i = row1; i < (row2 +1); i++) {
			for (int j = col1; j < (col2 +1); j++) {
				variable.setValue(new CellIndex(j,i));
				for (int k = 0; k < for_statement.size(); k++) {
					for_statement.get(k).evaluate(0);
				}
			}
		}
		return 0.0;
	}

}
