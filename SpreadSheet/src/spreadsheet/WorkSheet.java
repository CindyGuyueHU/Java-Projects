package spreadsheet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import expression.Expression;
import expression.MyFunctionTokenizer;
import expression.ParseException;
import expression.Tokenizer;

/**
 * WorkSheet - this stores the information for the worksheet. This is made up of
 * all the cells. all the cells of the worksheet.
 * 
 * @author Eric McCreath
 * 
 */

public class WorkSheet {

	private static final String SPREAD_SHEET = "SpreadSheet";
	private static final String INDEXED_CELL = "IndexedCell";
	private static final String CELL = "Cell";
	private static final String CELL_INDEX = "CellIndex";
	private static final String FUNCTIONTEXT = "FunctionText";
	private static final String FUNCTIONS = "Functions";

	HashMap<CellIndex, Cell> tabledata;
	public ArrayList<String> indexlist;
	ArrayList<Expression> functions;
	String ff;
	Spreadsheet spreadsheet;
	
	// For simplicity the table data is just stored as a hashmap from a cell's
	// index to the
	// cells data. Cells that are not yet part of this mapping are assumed
	// empty.
	// Once a cell is constructed the object is not replaced in this mapping,
	// rather
	// the data of the cell can be modified.

	public WorkSheet(Spreadsheet ss) {
		tabledata = new HashMap<CellIndex, Cell>();
		indexlist = new ArrayList<String>();
		functions = new ArrayList<Expression>();
		ff = "";
		spreadsheet = ss;
	}

	public Cell lookup(CellIndex index) {
		Cell cell = tabledata.get(index);
		if (cell == null) { // if the cell is not there then create it and add
							// it to the mapping
			cell = new Cell();
			tabledata.put(index, cell);
		}
		return cell;
	}

	// calculate all the values for each cell that makes up the worksheet
	public void calculate() throws ParseException {
		
		for (int i = 0; i < indexlist.size(); i++){
			String s = indexlist.get(i);
			CellIndex idx = new CellIndex(s);
			tabledata.get(idx).calculate(this, 0);
		}
	}

	public void save(File file) {
		StoreFacade sf = new StoreFacade(file, SPREAD_SHEET);
		for (CellIndex i : tabledata.keySet()) {
			if (!tabledata.get(i).isEmpty()) {
				sf.start(INDEXED_CELL);
				sf.addText(CELL_INDEX, i.show());
				sf.addText(CELL, tabledata.get(i).getText());
			}
		}
		sf.start(FUNCTIONTEXT);
		sf.addText(FUNCTIONS, ff);
		sf.close();
	}

	public static WorkSheet load(File file, Spreadsheet ss) {
		LoadFacade lf = LoadFacade.load(file);
		WorkSheet worksheet = new WorkSheet(ss);
		String name;
		while ((name = lf.nextElement()) != null) {
			if (name.equals(INDEXED_CELL)) {
				String index = lf.getText(CELL_INDEX);
				String text = lf.getText(CELL);
				worksheet.put(index, text);
			} else if (name.equals(FUNCTIONTEXT)) {
				worksheet.ff = lf.getText(FUNCTIONS);
			}
		}
		return worksheet;
	}

	public String getFuctions() {
		return ff;
	}
	
	public ArrayList<Expression> getFuctionList() {
		return functions;
	}
	
	public void setFunctions(String fun) throws ParseException {
		Tokenizer tok = new MyFunctionTokenizer(fun);
		
		while (tok.current() != null) {
			Expression f = Expression.parseFunction(tok, this);
			functions.add(f);
		}
		
		ff = fun;
		
		spreadsheet.setFunList(functions);
	}

	public void put(String index, String text) {
		CellIndex idx = new CellIndex(index);
		Cell c = new Cell(text);
		indexlist.add(index);
		tabledata.put(idx, c);
	}

}