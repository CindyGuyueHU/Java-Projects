package expression;

/**
 * MySimpleTokenizer - this tokenizer uses some simple check to break up the
 * tokens the text.  The Integer and Double parsers are used to parse numbers. 
 * 
 * 
 * reference: Eric McCreath - GPLv2
 * @author Guyue HU, u5608260
 */

public class MySimpleTokenizer extends Tokenizer {
	private String text;
	private int pos;
	private Object current;

	static final char whitespace[] = { ' ', '\n', '\t' };
	static final char symbol[] = { '=', '(', ')', '+', '-', '*', '/', '^', 'e', 'p', 'A', 'B', 'C', 'D', 'E',
		'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
		'>', '<', ':', ','};

	public MySimpleTokenizer(String text) {
		// tokenize a expression string
		this.text = text;
		this.pos = 0;
		next();
	}

	boolean hasNext() {
		// return if the string has a next token
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
		// consume any enter/ space in the string
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
