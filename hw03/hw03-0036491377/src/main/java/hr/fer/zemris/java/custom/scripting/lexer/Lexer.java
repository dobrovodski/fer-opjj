package hr.fer.zemris.java.custom.scripting.lexer;

public class Lexer {
	private char[] data;
	private LexerState state;
	private Token token;

	private boolean end;
	private int index;

	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Lexer text cannot be null.");
		}

		this.data = text.toCharArray();
		this.state = LexerState.BASIC;
	}

	public Token nextToken() {
		if (end) {
			throw new LexerException("Reached end of input, no more tokens to be generated.");
		}

		// check EOF
		if (index == data.length) {
			end = true;
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (this.state == LexerState.BASIC) {
			//special case when data starts with tag
			if (data[index] == '{' && (index == 0 || data[index - 1] != '\\') && data[index + 1] == '$') {
				token = new Token(TokenType.TAG_START, "{$");
				index += 2;
			} else {
				// lex until first unescaped {$
				token = lexText();
			}
		} else if (this.state == LexerState.TAG) {
			skipWhitespace();
			//special symbols
			if (follows("FOR")) {
				token = new Token(TokenType.TAG_TYPE, "FOR");
				index += 3;
			} else if (follows("=")) {
				token = new Token(TokenType.TAG_TYPE, "=");
				index += 1;
			} else if (follows("END")) {
				token = new Token(TokenType.TAG_TYPE, "END");
				index += 3;
			} else if (follows("$}") && data[index - 1] != '\\') {
				token = new Token(TokenType.TAG_END, "$}");
				index += 2;
			} else {
				if (Character.isLetter(data[index])) {
					//identifier
					StringBuilder identifierBuilder = new StringBuilder();
					while (!Character.isWhitespace(data[index]) && data[index] != '$') {
						identifierBuilder.append(data[index]);
						index++;
					}
					token = new Token(TokenType.IDENTIFIER, identifierBuilder.toString());
				} else if (isOperator(data[index])) {
					//operator
					if (data[index] == '-' && Character.isDigit(data[index + 1])) {
						token = lexNumber();
					}
					else {
						token = new Token(TokenType.OPERATOR, data[index]);
						index++;
					}
				} else if (data[index] == '@') {
					token = new Token(TokenType.SYMBOL, data[index]);
					index++;
				} else if (Character.isDigit(data[index])) {
					token = lexNumber();
				} else if (data[index] == '"') {
					//string literal
					StringBuilder strBuilder = new StringBuilder();
					while (true) {
						strBuilder.append(data[index]);
						index++;
						if (data[index] == '"' && data[index - 1] != '\\') {
							strBuilder.append(data[index]);
							index++;
							break;
						}
					}

					token = new Token(TokenType.LITERAL, strBuilder.toString());
				}
			}
		}

		return token;
	}

	public Token getToken() {
		return token;
	}

	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("Cannot set state to null.");
		}

		this.state = state;
	}

	private void skipWhitespace() {
		while (index < data.length && Character.isWhitespace(data[index])) {
			index++;
		}
	}

	private Token lexText() {
		StringBuilder textBuilder = new StringBuilder();

		while (index < data.length) {

			if (data[index] == '{') {
				if (data[index - 1] != '\\' && data[index + 1] == '$') {
					break;
				}
			}

			textBuilder.append(data[index]);
			index++;
		}

		return new Token(TokenType.TEXT, textBuilder.toString());
	}

	private Token lexNumber() {
		//number literal
		StringBuilder numBuilder = new StringBuilder();
		boolean dot = false;
		while (Character.isDigit(data[index]) || data[index] == '-' || data[index] == '.') {
			numBuilder.append(data[index]);
			if (data[index] == '.') {
				if (dot) {
					throw new LexerException("Cannot parse number with more than one decimal point.");
				}
				dot = true;
			}
			index++;
		}
		return new Token(TokenType.LITERAL, numBuilder.toString());
	}

	private boolean follows(String str) {
		char[] find = str.toCharArray();
		int i = index;

		for (char c : find) {
			if (c != Character.toUpperCase(data[i]) || i >= data.length) {
				return false;
			}
			i++;
		}

		return true;
	}

	private boolean isOperator(char c) {
		return (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^');
	}

}
