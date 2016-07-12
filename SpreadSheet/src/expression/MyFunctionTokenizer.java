package expression;

public class MyFunctionTokenizer extends Tokenizer {

	private String text;
	private int pos;
	private Object current;

	static final char whitespace[] = { ' ', '\n', '\t' };
	static final char symbol[] = { '=', '(', ')', '+', '-', '*', '/', '^', '>', '<', '{', '}', ';', ','};

	public MyFunctionTokenizer(String text) {
		// tokenize a function string
		this.text = text;
		this.pos = 0;
		next();
	}

	boolean hasNext() {
		// if the string has the next token
		return current != null;
	}

	public Object current() {
		// return the current token
		return current;
	}

	public void next() {
		// move to the next token in the string
		consumewhite();
		if (pos == text.length()) {
			current = null;

		} else if (isin(text.charAt(pos), symbol)) {
			current = "" + text.charAt(pos);
			pos++;

		} else if (Character.isDigit(text.charAt(pos))) {
			int start = pos;
			while (pos < text.length() && Character.isDigit(text.charAt(pos)) )
				pos++;
			if (pos+1 < text.length() && text.charAt(pos) == '.' &&
					Character.isDigit(text.charAt(pos+1))) {
				pos++;
				while (pos < text.length() && Character.isDigit(text.charAt(pos)))
					pos++;
				current = Double.parseDouble(text.substring(start, pos));
			} else {
			    current = Double.parseDouble(text.substring(start, pos));
			}

		} else {
			int start = pos;
			while (pos < text.length() && !isin(text.charAt(pos), symbol)
					&& !isin(text.charAt(pos), whitespace))
				pos++;
			current = text.substring(start, pos);
		}
	}

	

	private void consumewhite() {
		// consume any enters, spaces in the string
		while (pos < text.length() && isin(text.charAt(pos), whitespace))
			pos++;
	}

	private boolean isin(char c, char charlist[]) {
		// check if a char is in a string
		for (char w : charlist) {
			if (w == c)
				return true;
		}
		return false;
	}

}
