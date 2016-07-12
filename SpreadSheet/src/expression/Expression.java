package expression;

import java.util.ArrayList;

import spreadsheet.CellIndex;
import spreadsheet.WorkSheet;
import function.AssignStatement;
import function.CellForStatement;
import function.CellVariable;
import function.FunVariable;
import function.Function;
import function.IfStatement;
import function.NumForStatement;
import function.NumVariable;
import function.ReturnStatement;
import function.Statement;

/**
 * Expression - this is the abstract class for all expressions contains the parsing methods
 * 				for expressions and functions
 * 
 * 
 * @author Guyue HU
 */


public abstract class Expression {
	
	Statement sta = null;
	Function fun = null;
	WorkSheet w = null;
	FunVariable funVariable = null;
	MySimpleTokenizer tok = null;
	MyFunctionTokenizer fun_tok = null;
	
	final static String Capitalletter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public abstract String show(); // this should show the expression as a
									// string (which can be parsed back into a
									// expression).

	public abstract double evaluate(int depth) throws ParseException; // this should evaluate the expression
	
    // Production rule:
	// inequality ::= expression ">" expression | expression "<" expression | expression "=" expression
    // expression ::= term | expression "+" term  | expression "-" term
    // term ::= power | term "*" power | term "/" power | brackets
    // power ::= factor | factor `^` factor | brackets
	// factor ::= "-" factor | number | constant |variable | brackets | function
    // brackets ::= `(` expression `)`
	
	public static Expression parseParentheses(Tokenizer tok, WorkSheet w, ArrayList<FunVariable> fv) throws ParseException {
		// parse brackets in the Production rule
		//`(` expression `)`
		tok.next();
		Expression exp = parseExpression(tok, w, fv);
		tok.parse(")");
		return exp;
	}
	
	public static Expression parseInequality(Tokenizer tok, WorkSheet w, ArrayList<FunVariable> fv) throws ParseException {
		// parse the inequality
		Object c = tok.current();
		Expression exp = parseExpression(tok, w, fv);
		while (tok.hasNext()) {		
			c = tok.current();
			if (c.equals(">")) {		//expression ">" term
				tok.next();
				exp = new Larger(exp, parseExpression(tok, w, fv));
			} else if (c.equals("<")) {		//expression "<" term
				tok.next();
				exp = new Smaller(exp, parseExpression(tok, w, fv));
			} else if (c.equals("=")) {		//expression "<" term
				tok.next();
				exp = new Equal(exp, parseExpression(tok, w, fv));
			} else { 	//term
				return exp;
			}
		}
		return exp;

	}
	
	public static Expression parseExpression(Tokenizer tok, WorkSheet w, ArrayList<FunVariable> fv) throws ParseException {				
		// parse expression in the Production rule
				
		Object c = tok.current();
		Expression exp = parseTerm(tok, w, fv);
		while (tok.hasNext()) {		
			c = tok.current();
			if (c.equals("+")) {		//expression "+" term
				tok.next();
				exp = new Addition(exp, parseTerm(tok, w, fv));
			} else if (c.equals("-")) {		//expression "-" term
				tok.next();
				exp = new Subtraction(exp, parseTerm(tok, w, fv));
			} else { 	//term
				return exp;
			}
		}
		return exp;
	}
	
	public static Expression parseTerm(Tokenizer tok, WorkSheet w, ArrayList<FunVariable> fv) throws ParseException {
		// parse term in the Production rule
		
		Object c = tok.current();
		Expression exp = parsePower(tok, w, fv);
		while (tok.hasNext()) {
			c = tok.current();
			if (c.equals("*")) {	//term "*" power
				tok.next();
				exp = new Multiplication(exp, parsePower(tok, w, fv));
			} else if (c.equals("/")) {		//term "/" power
				tok.next();
				exp = new Division(exp, parsePower(tok, w, fv));
			} else {	//power
				return exp;
			}
		}
		return exp;
	}
	
	public static Expression parsePower(Tokenizer tok, WorkSheet w, ArrayList<FunVariable> fv) throws ParseException {
		// parse power in the Production rule
		Object c = tok.current();
		Expression exp = parseFactor(tok, w, fv);
		
		while (tok.hasNext()) {
			c = tok.current();
			if (c.equals("^")) {	//factor `^` factor
				tok.next();
				exp = new Power(exp, parseFactor(tok, w, fv));
			}  else {	//factor
				return exp;
			}
		}
		return exp;
	}
	
	public static Expression parseFactor(Tokenizer tok, WorkSheet w, ArrayList<FunVariable> fv) throws ParseException {
		// parse factor in the Production rule
		
		Object c = tok.current();
		
		if (c.equals("(")) {	//brackets
			return parseParentheses(tok, w, fv);
		} else if (c.equals("-")) {		//"-" factor 
			tok.next();
			return new Inverse(parseFactor(tok, w, fv));
		} else if (c instanceof Double) {	//number
			tok.next();
			return new Number((double) c);
		} else if (c.equals("e")) {		//constant
			tok.next();
			return new E();
		} else if (c.equals("p")) {		//constant
			tok.next();
			return new Pi();
		} else if (Capitalletter.contains((String)c)) { // Cell variable
			tok.next();
			Object n = tok.current();
			if (n instanceof Double) {
				int i = (int) ((double) n);
				tok.next();
				String str = ((String)c)+(""+i);
				return new Variable(str, w);
			} else throw new ParseException("Illegal symbols");
		} else  { 		// if the token starts with lower case letter,
						// it could be a function or a functional variable		
			
			String s = (String) c;
			
			tok.next();
			if (tok.current() != null) {
				c = tok.current();
			}
			
			if (c.equals("(")) { // Function
			
				ArrayList<Expression> functions = w.getFuctionList();
			
				Function f = null;
				for (int i = 0; i < functions.size(); i++) {
					if (functions.get(i).show().equals(s)) {
						f = (Function)functions.get(i);
					}
				}
			
				if (f != null) {
				
					fv = f.getVarList();
					
					tok.next();
					c = tok.current();
					int count = 0;
					while (!(c.equals(")"))) {
						//handle parameters
						
						FunVariable v = fv.get(count);
						
						if (v.IsCellVariable()) {
							if (Capitalletter.contains((String)c)) { // Cell variable
								tok.next();
								Object n = tok.current();
								if (n instanceof Double) {
									int i = (int) ((double) n);
									String str = ((String)c)+(""+i);
									fv.get(count).setValue(new CellIndex(str));
								} else throw new ParseException("Illegal symbols");
							} else throw new ParseException("Illegal syntax");
						} else {
							if (c instanceof Double) {
								fv.get(count).setValue((double) c);
							} else throw new ParseException("Illegal syntax");
						}
						
						count++;
						tok.next();
						if (tok.current().equals(",")) tok.parse(",");
						c = tok.current();
					}
					tok.parse(")");
					return f;
		        } 
			
     		} else { // function variable
		    	 FunVariable v = null; 
		    	 if (fv != null) {
		    		 v = matchFunVariable(s, fv);
		    	 }
		    	 
		    	 if (v != null) {
		    		 return v;
		    	 } else throw new ParseException("Variable not defined!");
		     } 
			
		} 
		return null; 
	}

	public static FunVariable matchFunVariable(String s, ArrayList<FunVariable> funVariable) {
		// find if s represent a functional variable, return the variable if yes
		// return null if no
		FunVariable v = null;
		for (int i=0; i < funVariable.size(); i++) {
			if (funVariable.get(i).show().equals(s)) {
				v = funVariable.get(i);
			}
		}
		return v;
	}
	
	private static String getSubStatement(Tokenizer tok) {
		// Get a string contains all the contents in substatement of for/if statement
		String s = "";
		Object c = tok.current();
		int count = 0;
		while ((!(c.equals(";"))) || (! (count == 0))) {
				s = s + ("" + c) + " ";
				tok.next();
				c = tok.current();
				if (c.equals("{")) count++;
				if (c.equals("}")) count--;
		}
		
		s = s + ";";
		tok.next();
		return s;
	}
	
	private static CellVariable parseCellVariable(Tokenizer tok, WorkSheet w) {
		// parse a new cell variable
		tok.next();
		Object c = tok.current();
		String s = (String) c;
		CellVariable var = new CellVariable(s, w);
		tok.next();
		return var;
	}
	
	private static NumVariable parseNumVariable(Tokenizer tok) {
		// parse a new num variable
		tok.next();
		Object c = tok.current();
		String s = (String) c;
		NumVariable var = new NumVariable(s);
		tok.next();
		return var;
	}
	
	private static Statement parseReturnStatement(Tokenizer tok, WorkSheet w,
			ArrayList<FunVariable> funVariable) throws ParseException {
		// parse a new return statement
		tok.next();
		Object c = tok.current();
		String s = "";
		while (!(c.equals(";"))) {
			s = s + (""+c);
			tok.next();
			c = tok.current();
		}
		Expression exp = parseInequality(new MySimpleTokenizer(s), w, funVariable);
		Statement sta = new ReturnStatement(exp);
		tok.parse(";");
		return sta;
	}
	
	private static Statement parseNumForStatement(Tokenizer tok, WorkSheet w,
			ArrayList<FunVariable> funVariable, Function fun, FunVariable v) throws ParseException {
		// new For statement on number variable
		tok.next();
		tok.parse("in");
		tok.parse("range");
		tok.parse("(");
		Object c = tok.current();
		String s = (String) c;
		FunVariable v1 = matchFunVariable(s, funVariable);
		if (!(v1.IsNumVariable())) {
			throw new ParseException("Invalid variable:" + s);
		}
		tok.next();
		tok.parse(",");
		c = tok.current(); 
		s = (String) c;
		FunVariable v2 = matchFunVariable(s, funVariable);
		if (!(v2.IsNumVariable())) {
			throw new ParseException("Invalid variable:" + s);
		}
		tok.next();
		tok.parse(")");
		
		Statement sta = new NumForStatement((NumVariable)v, (NumVariable)v1, (NumVariable)v2);
		
		tok.parse("{");
		c = tok.current();
		while (!(c.equals("}"))) {
			
			s = getSubStatement(tok);
			c = tok.current();
			MyFunctionTokenizer st_tok = new MyFunctionTokenizer(s);
			Statement for_sta = parseStatement(st_tok, w, funVariable, fun);
			sta.addSubStatement(for_sta);

		}
		
		tok.parse("}");
		return sta;
	}
	
	private static Statement parseCellForStatement(Tokenizer tok, WorkSheet w,
			ArrayList<FunVariable> funVariable, Function fun, FunVariable v) throws ParseException {
		// new For statement on cell variable
		tok.next();
		tok.parse("in");
		tok.parse("range");
		tok.parse("(");
		Object c = tok.current();
		String s = (String) c;
		FunVariable v1 = matchFunVariable(s, funVariable);
		if (!(v1.IsCellVariable())) {
			throw new ParseException("Invalid variable:" + s);
		}
		tok.next();
		tok.parse(",");
		c = tok.current(); 
		s = (String) c;
		FunVariable v2 = matchFunVariable(s, funVariable);
		if (!(v2.IsCellVariable())) {
			throw new ParseException("Invalid variable:" + s);
		}
		tok.next();
		tok.parse(")");
		
		Statement sta = new CellForStatement((CellVariable)v, (CellVariable)v1, (CellVariable)v2);
		
		tok.parse("{");
		c = tok.current();
		while (!(c.equals("}"))) {
			s = getSubStatement(tok);
			c = tok.current();
			MyFunctionTokenizer st_tok = new MyFunctionTokenizer(s);
			Statement for_sta = parseStatement(st_tok, w, funVariable, fun);
			sta.addSubStatement(for_sta);
		}
		
		tok.parse("}");
		return sta;
	}
	
	private static Statement parseIfStatement(Tokenizer tok, WorkSheet w,
			ArrayList<FunVariable> funVariable, Function fun) throws ParseException {
		// new if statement
		tok.next();
		tok.parse("(");
		Object c = tok.current();
		String s = "";
		while (!(c.equals(")"))) {
			s = s + c;
			tok.next();
			c = tok.current();
		}
		tok.parse(")");
		Tokenizer tok1 = new MySimpleTokenizer(s);
		Expression condExp = parseInequality(tok1, w, funVariable);
		
		Statement sta = new IfStatement(condExp);
		
		tok.parse("{");
		
		c = tok.current();
		while (!(c.equals("}"))) {
			s = getSubStatement(tok);
			c = tok.current();
			MyFunctionTokenizer st_tok = new MyFunctionTokenizer(s);
			Statement sta1 = parseStatement(st_tok, w, funVariable, fun);
			sta.addSubStatement1(sta1);
		}
		tok.parse("}");
		tok.parse("else");
		tok.parse("{");
		c = tok.current();
		
		while (!(c.equals("}"))) {
			s = getSubStatement(tok);
			c = tok.current();
			MyFunctionTokenizer st_tok = new MyFunctionTokenizer(s);
			Statement sta2 = parseStatement(st_tok, w, funVariable, fun);
			sta.addSubStatement2(sta2);
		}
		tok.parse("}");
		tok.parse(";");
		return sta;
	}
	
	private static Statement parseAssignStatement(Tokenizer tok, WorkSheet w,
			ArrayList<FunVariable> funVariable, Function fun) throws ParseException {
		// parse new assign statement
		Object c = tok.current();
		String s = (String) c;
		FunVariable v = matchFunVariable(s, funVariable);
		tok.next();
		tok.parse("=");
		c = tok.current();
		s = "";
		while (! (c.equals(";"))) {
			s = s + ("" + c);
			tok.next();
			c = tok.current();
		}
		
		MySimpleTokenizer tok1 = new MySimpleTokenizer(s);
		Expression exp = parseInequality(tok1, w, funVariable);
		
		Statement sta = new AssignStatement(v, exp, w, fun);
		
		tok.parse(";");
		
		return sta;
	}
	
	public static Statement parseStatement(Tokenizer tok, WorkSheet w, ArrayList<FunVariable> funVariable, Function fun) throws ParseException {
		// parse a statement in a function
		// possible statement: assignment statement, for statement, if statement, return statement
		
		Object c = tok.current();
		String s;
		Statement sta = null;
		
		if (c.equals("return")) {
			// new return statement (the return statement can only return value of number variable)
			sta = parseReturnStatement(tok, w, funVariable);
			c = tok.current();
		} else if (c.equals("for")) {
			// new for statement (there are two different kinds of for statement: for loop on Cell 
																				//for loop on number)
			tok.next();
			c = tok.current();
			s = (String) c;
			FunVariable v = matchFunVariable(s, funVariable);
			if (v.IsNumVariable()) {
				sta = parseNumForStatement(tok, w, funVariable, fun, v);
				c = tok.current();
			} else if (v.IsCellVariable()) {
				sta = parseCellForStatement(tok, w, funVariable, fun, v);
				c = tok.current();
			} else {
				throw new ParseException("Invalid variable:" + s);
			}
			tok.parse(";");
			c = tok.current();
		} else if (c.equals("if")) { 	// if statement
			sta = parseIfStatement(tok, w, funVariable, fun);
			c = tok.current();
			
		} else {
			sta = parseAssignStatement(tok, w, funVariable, fun);
			c = tok.current();
		}
		
		return sta;

	}
	
	public static Expression parseFunction(Tokenizer tok, WorkSheet w) throws ParseException {
		// parse a function
		
		String functionName;
		
		ArrayList<Statement> statement = new ArrayList<Statement>();
		ArrayList<FunVariable> funVariable = new ArrayList<FunVariable>();
		
		Object c = tok.current();
		String s = (String) c;
		
		
		// parse the function name
		functionName = s;
		tok.next();tok.parse("(");
		
		Function fun = new Function(functionName);
		
		// parse the parameters
		c = tok.current();
		while (! (c.equals(")"))) {
			if (c.equals(",")) {
				tok.parse(",");
				c = tok.current();
			} else if (c.equals("cell")) {
				CellVariable var = parseCellVariable(tok, w);
				funVariable.add(var);
				c = tok.current();
			} else if (c.equals("double")) {
				NumVariable var = parseNumVariable(tok);
				funVariable.add(var);
				c = tok.current();
			} 
		}
		tok.parse(")");
		
		tok.parse("{");
		c = tok.current();
		
		while (!(c.equals("}"))) {
			
			// parse the variables
			if  (c.equals("cell")) {
				CellVariable var = parseCellVariable(tok, w);
				funVariable.add(var);
				tok.parse(";");
				c = tok.current();
			} else if (c.equals("double")) {
				NumVariable var = parseNumVariable(tok);
				funVariable.add(var);
				tok.parse(";");
				c = tok.current();
			} else {
				// parse the statements
				s = "";
				int count = 0;
				while ((!(c.equals(";"))) || (! (count == 0))) {
						s = s + ("" + c) + " ";
						tok.next();
						c = tok.current();
						if (c.equals("{")) count++;
						if (c.equals("}")) count--;
				}
				s = s + ";";
				MyFunctionTokenizer st_tok = new MyFunctionTokenizer(s);
				Statement sta = parseStatement(st_tok, w, funVariable, fun);
				statement.add(sta);
				tok.next();
				c = tok.current();
			}
			
		}
		
		tok.next();
		
		fun.setVariable(funVariable);
		fun.setStatement(statement);
		
		return fun;
	}

}
