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
				setState(LexerState.TAG_START);
				index += 2;
			} else {
				// lex until first unescaped {$
				token = lexText();
			}

		} else if (this.state == LexerState.TAG_START) {
			// get tag identifier (for, =, end or anything else thats a variable name)
			skipWhitespace();
			token = new Token(TokenType.TAG_TYPE, getTag());
			setState(LexerState.TAG);

		} else if (this.state == LexerState.TAG) {
			skipWhitespace();
			if (Character.isLetter(data[index])) {
				//identifier
				token = new Token(TokenType.IDENTIFIER, getIdentifier());
			} else if (isOperator(data[index])) {
				//negative number or operator
				if (data[index] == '-' && Character.isDigit(data[index + 1])) {
					token = lexNumber();
				} else {
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
				token = lexStringLiteral();
			} else if (data[index] == '$' && data[index + 1] == '}') {
				token = new Token(TokenType.TAG_END, "$}");
				index += 2;
				setState(LexerState.BASIC);
			} else {
				throw new LexerException("Could not parse symbol: " + data[index]);
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

			if (data[index] == '\\') {
				if (data[index + 1] != '{' && data[index + 1] != '\\') {
					throw new LexerException("Cannot escape this symbol: " + data[index + 1]);
				} else {
					index++;

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
		if (data[index] == '-') {
			numBuilder.append(data[index]);
			index++;
		}

		while (Character.isDigit(data[index]) || data[index] == '.') {
			numBuilder.append(data[index]);
			if (data[index] == '.') {
				if (dot) {
					throw new LexerException("Cannot parse number with more than one decimal point.");
				}
				dot = true;
			}
			index++;
		}
		return new Token(TokenType.LITERAL_NUM, numBuilder.toString());
	}

	private Token lexStringLiteral() {
		StringBuilder strBuilder = new StringBuilder();
		//skip "
		index++;

		while (true) {
			if (data[index] == '\\') {
				if (data[index + 1] == '\\' || data[index + 1] == '"') {
					strBuilder.append(data[index + 1]);
					index += 2;
					continue;
				}
			} else if (data[index] == '"') {
				index++;
				break;
			}

			strBuilder.append(data[index]);
			index++;
		}

		return new Token(TokenType.LITERAL_STRING, strBuilder.toString());
	}

	private String getIdentifier() {
		if (Character.isLetter(data[index])) {
			//identifier
			StringBuilder identifierBuilder = new StringBuilder();
			while (Character.isLetter(data[index]) || Character.isDigit(data[index]) || data[index] == '_') {
				identifierBuilder.append(data[index]);
				index++;
			}
			return identifierBuilder.toString();
		} else {
			throw new LexerException("Expected identifier but couldn't find one.");
		}
	}

	private String getTag() {
		if (data[index] == '=') {
			index++;
			return "=";
		} else {
			return getIdentifier();
		}
	}

	private boolean isOperator(char c) {
		return (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^');
	}

}
