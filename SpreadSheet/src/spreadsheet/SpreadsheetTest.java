package spreadsheet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.junit.Test;

import expression.ParseException;

/**
 * 
 * SpreadsheetTest - This is a simple integration test.  
 * We basically set some text within cells of the spread sheet and check they evaluate correctly.
 * 
 * @author Eric McCreath
 * 
 */

public class SpreadsheetTest  {

	protected static final String sumandmaxfunctions = 
		"sum(cell c1 , cell c2) {"+
		"	double m;"+
		"	cell i;"+
		"	m = 0;"+
		"	for i in range (c1, c2) {"+
		"		m = m + i;"+
		"	};"+
		"	return m;"+
		"}"+

		"max(cell c1 , cell c2) {"+
		"	double m;"+
		"	cell i;"+
		"	m = c1;"+
		"	for i in range (c1, c2) {"+
		"		if (i > m) {"+
		"		m = i;} else {"+
		"		};"+
		"	};"+
		"	return m;"+
		"}"+
		
		"min(cell c1 , cell c2) {"+
		"	double m;"+
		"	cell i;"+
		"	m = c1;"+
		"	for i in range (c1, c2) {"+
		"		if (i < m) {"+
		"			m = i;} else {"+
		"		};"+
		"	};"+
		"	return m;"+
		"}"+

		
		"twntyfiv() {"+
		"	return 25;"+
		"}"+

		"count(cell c1 , cell c2) {"+
		"	double m;"+
		"	cell i;"+
		"	m = 0;"+
		"	for i in range (c1, c2) {"+
		"		m = m + 1;"+
		"	};"+
		"	return m;"+
		"}"+

		"add(double d1 , double d2) {"+
		"	double m;"+
		"	double i;"+
		"	m = 0;"+
		"	for i in range (d1, d2) {"+
		"		m = m + i;"+
		"	};"+
		"	return m;"+
		"}"+

		"qual (cell c1, cell c2) {"+
		"	double m;"+
		"	if (c1 = c2) {"+
		"		m=0;"+
		"	}else{"+
		"		m=1;"+
		"	};"+
		"	return m;"+
		"}"+

		"summax(cell c1, cell c2, cell c3, cell c4, cell c5, cell c6) {"+
		"	double m;"+
		"	double n;"+
		"	double q;"+
		"	double r;"+
		"	cell i;"+
		"	m = 0;"+
		"	n = c1;"+
		"	q = c3;"+
		"	r = c5;"+

		"	for i in range (c1, c2) {"+
		"		if (i > n) {"+
		"			n = i;} else {"+
		"		};"+
		"	};"+


		"	for i in range (c3, c4) {"+
		"		if (i > q) {"+
		"			q = i;} else {"+
		"		};"+
		"	};"+

		"	for i in range (c5, c6) {"+
		"		if (i > r) {"+
		"			r = i;} else {"+
		"		};"+
		"	};"+

		"	m = n + q + r;"+

		"	return m;"+
		"}";

	
	Spreadsheet gui;

	@Test
	public void testSimple() {
		// test simple functions of the spread sheet
		gui = new Spreadsheet();
		try {
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					selectAndSet(1, 3, "Some Text");
					selectAndSet(4, 1, "5.12");
					gui.calculateButton.doClick();
				}
			});
			assertEquals(gui.worksheet.lookup(new CellIndex("C2")).show(),
					"Some Text");
			assertEquals(gui.worksheet.lookup(new CellIndex("A5")).show(),
					"5.12");
		} catch (InvocationTargetException e) {
			fail();
		} catch (InterruptedException e) {
			fail();
		}
	}

	@Test
	public void testExpressionCal() {
		// test the expression functions of the spread sheet
		gui = new Spreadsheet();
		try {
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					selectAndSet(2, 3, "Some Text");
					selectAndSet(3, 3, "23.4");
					selectAndSet(4, 3, "34.1");
					selectAndSet(5, 3, "=2.6+C4*C5");
					gui.calculateButton.doClick();
					selectAndSet(6, 3, "=2^8-3*7+5*25*(7+8)");
					gui.calculateButton.doClick();
					selectAndSet(7, 3, "=2^8+3*7-5*25*(7+8)+C4");
					gui.calculateButton.doClick();
				}
			});
			assertEquals(gui.worksheet.lookup(new CellIndex("C3")).show(),
					"Some Text");
			assertEquals(gui.worksheet.lookup(new CellIndex("C4")).show(),
					"23.4");
			assertEquals(gui.worksheet.lookup(new CellIndex("C5")).show(),
					"34.1");
			assertEquals(gui.worksheet.lookup(new CellIndex("C6")).show(),
					"800.54");
			assertEquals(gui.worksheet.lookup(new CellIndex("C7")).show(),
					"2110.0");
			assertEquals(gui.worksheet.lookup(new CellIndex("C8")).show(),
					"-1574.6");
		} catch (InvocationTargetException e) {
			fail();
		} catch (InterruptedException e) {
			fail();
		}
	}

	// Test user-defined functions
	@Test
	public void testFunctionCal1() {
		// test the max function
		gui = new Spreadsheet();
		try {
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					selectAndSet(2, 3, "1.1");
					selectAndSet(3, 3, "2.2");
					selectAndSet(4, 3, "3.3");
					selectAndSet(6, 3, "= max(C3,C5)");
					gui.functioneditor.textarea.setText(sumandmaxfunctions);
					try {
						gui.functioneditor.updateWorksheet();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gui.calculateButton.doClick();
				}
			});
			assertEquals(gui.worksheet.lookup(new CellIndex("C3")).show(),
					"1.1");
			assertEquals(gui.worksheet.lookup(new CellIndex("C4")).show(),
					"2.2");
			assertEquals(gui.worksheet.lookup(new CellIndex("C5")).show(),
					"3.3");
			assertEquals(gui.worksheet.lookup(new CellIndex("C7")).show(),
					"3.3");
		} catch (InvocationTargetException e) {
			fail();
		} catch (InterruptedException e) {
			fail();
		}
	}
	
	@Test
	public void testFunctionCal2() {
		// test the min function
		gui = new Spreadsheet();
		try {
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					selectAndSet(2, 3, "1.1");
					selectAndSet(3, 3, "2.2");
					selectAndSet(4, 3, "3.3");
					selectAndSet(5, 3, "= min(C3,C5)");
					gui.functioneditor.textarea.setText(sumandmaxfunctions);
					try {
						gui.functioneditor.updateWorksheet();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gui.calculateButton.doClick();
				}
			});
			assertEquals(gui.worksheet.lookup(new CellIndex("C3")).show(),
					"1.1");
			assertEquals(gui.worksheet.lookup(new CellIndex("C4")).show(),
					"2.2");
			assertEquals(gui.worksheet.lookup(new CellIndex("C5")).show(),
					"3.3");
			assertEquals(gui.worksheet.lookup(new CellIndex("C6")).show(),
					"1.1");
		} catch (InvocationTargetException e) {
			fail();
		} catch (InterruptedException e) {
			fail();
		}
	}
	
	@Test
	public void testFunctionCal3() {
		// test the sum function
		gui = new Spreadsheet();
		try {
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					selectAndSet(2, 3, "1.1");
					selectAndSet(3, 3, "2.2");
					selectAndSet(4, 3, "3.3");
					selectAndSet(5, 3, "= sum(C3,C5)");
					gui.functioneditor.textarea.setText(sumandmaxfunctions);
					try {
						gui.functioneditor.updateWorksheet();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gui.calculateButton.doClick();
				}
			});
			assertEquals(gui.worksheet.lookup(new CellIndex("C3")).show(),
					"1.1");
			assertEquals(gui.worksheet.lookup(new CellIndex("C4")).show(),
					"2.2");
			assertEquals(gui.worksheet.lookup(new CellIndex("C5")).show(),
					"3.3");
			assertEquals(gui.worksheet.lookup(new CellIndex("C6")).show(),
					"6.6");
		} catch (InvocationTargetException e) {
			fail();
		} catch (InterruptedException e) {
			fail();
		}
	}

	@Test
	public void testFunctionCal4() {
		// test the twntyfiv function
		gui = new Spreadsheet();
		try {
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					selectAndSet(5, 3, "= twntyfiv()");
					gui.functioneditor.textarea.setText(sumandmaxfunctions);
					try {
						gui.functioneditor.updateWorksheet();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gui.calculateButton.doClick();
				}
			});
			assertEquals(gui.worksheet.lookup(new CellIndex("C6")).show(),
					"25.0");
		} catch (InvocationTargetException e) {
			fail();
		} catch (InterruptedException e) {
			fail();
		}
	}
	
	
	@Test
	public void testFunctionCal5() {
		//test the count function
		gui = new Spreadsheet();
		try {
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					selectAndSet(2, 3, "1.1");
					selectAndSet(3, 3, "2.2");
					selectAndSet(4, 3, "3.3");
					selectAndSet(5, 3, "= count(C3,C5)");
					gui.functioneditor.textarea.setText(sumandmaxfunctions);
					try {
						gui.functioneditor.updateWorksheet();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gui.calculateButton.doClick();
				}
			});
			assertEquals(gui.worksheet.lookup(new CellIndex("C3")).show(),
					"1.1");
			assertEquals(gui.worksheet.lookup(new CellIndex("C4")).show(),
					"2.2");
			assertEquals(gui.worksheet.lookup(new CellIndex("C5")).show(),
					"3.3");
			assertEquals(gui.worksheet.lookup(new CellIndex("C6")).show(),
					"3.0");
		} catch (InvocationTargetException e) {
			fail();
		} catch (InterruptedException e) {
			fail();
		}
	}
	
	
	@Test
	public void testFunctionCal6() {
		// test the add function
		gui = new Spreadsheet();
		try {
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					selectAndSet(5, 3, "= add(1,10)");
					gui.functioneditor.textarea.setText(sumandmaxfunctions);
					try {
						gui.functioneditor.updateWorksheet();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gui.calculateButton.doClick();
				}
			});
			assertEquals(gui.worksheet.lookup(new CellIndex("C6")).show(),
					"55.0");
		} catch (InvocationTargetException e) {
			fail();
		} catch (InterruptedException e) {
			fail();
		}
	}
	
	
	@Test
	public void testFunctionCal7() {
		// test the equal function
		gui = new Spreadsheet();
		try {
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					selectAndSet(2, 3, "1.1");
					selectAndSet(3, 3, "2.2");
					selectAndSet(4, 3, "3.3");
					selectAndSet(5, 3, "= qual(C3,C5)");
					gui.functioneditor.textarea.setText(sumandmaxfunctions);
					try {
						gui.functioneditor.updateWorksheet();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gui.calculateButton.doClick();
				}
			});
			assertEquals(gui.worksheet.lookup(new CellIndex("C3")).show(),
					"1.1");
			assertEquals(gui.worksheet.lookup(new CellIndex("C4")).show(),
					"2.2");
			assertEquals(gui.worksheet.lookup(new CellIndex("C5")).show(),
					"3.3");
			assertEquals(gui.worksheet.lookup(new CellIndex("C6")).show(),
					"1.0");
		} catch (InvocationTargetException e) {
			fail();
		} catch (InterruptedException e) {
			fail();
		}
	}

	private void selectAndSet(int r, int c, String text) {
		gui.worksheetview.addRowSelectionInterval(r, r);
		gui.worksheetview.addColumnSelectionInterval(c, c);
		gui.cellEditTextField.setText(text);
	}
}
