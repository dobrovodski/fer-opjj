package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Lexer class used to turn given text into tokens.
 *
 * @author matej
 */
public class Lexer {
	// Data given to the lexer to tokenize
	private char[] data;
	// Current position in data
	private int index;
	// Latest generated token
	private Token token;
	// Current state of the lexer
	private LexerState state;
	// Keeps track whether the tokenization process has finished
	private boolean end;

	/**
	 * Constructor for the Lexer class.
	 *
	 * @param text text that will be checked for lexical errors
	 * @throws IllegalAccessException if the text is null
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Lexer text cannot be null.");
		}

		this.data = text.toCharArray();
		this.state = LexerState.BASIC;
	}

	/**
	 * Retrieves the next token from data.
	 *
	 * @return next valid token that has been found
	 */
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
					token = new Token(TokenType.OPERATOR, Character.toString(data[index]));
					index++;
				}
			} else if (data[index] == '@') {
				token = new Token(TokenType.SYMBOL, Character.toString(data[index]));
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

	/**
	 * Returns the last found token.
	 *
	 * @return last found token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets the lexer's state to the given one.
	 *
	 * @param state state for the lexer to be in
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("Cannot set state to null.");
		}

		this.state = state;
	}

	/**
	 * Skips to the first non-whitespace character in the data from the current index.
	 */
	private void skipWhitespace() {
		while (index < data.length && Character.isWhitespace(data[index])) {
			index++;
		}
	}

	/**
	 * Turns text following the current index into a TEXT token.
	 *
	 * @return token representation of the text in the script
	 */
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
					textBuilder.append(data[index]);
					index++;
				}
			}

			textBuilder.append(data[index]);
			index++;
		}

		return new Token(TokenType.TEXT, textBuilder.toString());
	}

	/**
	 * Turns number following the current index into a LITERAL token.
	 *
	 * @return token representation of the number being lexed
	 */
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
		if (dot) {
			return new Token(TokenType.LITERAL_DOUBLE, numBuilder.toString());
		} else {
			return new Token(TokenType.LITERAL_INT, numBuilder.toString());
		}
	}

	/**
	 * Turns string following the current index into a LITERAL token.
	 *
	 * @return token representation of the string being lexed
	 */
	private Token lexStringLiteral() {
		StringBuilder strBuilder = new StringBuilder();
		//skip "
		strBuilder.append(data[index]);
		index++;

		while (true) {
			if (data[index] == '\\') {
				boolean escaped = false;
				if (data[index + 1] == '\\' || data[index + 1] == '"') {
					strBuilder.append(data[index]);
					strBuilder.append(data[index + 1]);
					escaped = true;
				} else if (data[index + 1] == 'n') {
					strBuilder.append('\n');
					escaped = true;
				} else if (data[index + 1] == 'r') {
					strBuilder.append('\r');
					escaped = true;
				} else if (data[index + 1] == 't') {
					strBuilder.append('\t');
					escaped = true;
				}

				if (escaped) {
					index += 2;
					continue;
				}
			} else if (data[index] == '"') {
				strBuilder.append(data[index]);
				index++;
				break;
			}

			strBuilder.append(data[index]);
			index++;
		}

		return new Token(TokenType.LITERAL_STRING, strBuilder.toString());
	}

	/**
	 * Gets the next valid identifier from the current index in the data as a string.
	 *
	 * @return next identifier in the data
	 */
	private String getIdentifier() {
		if (Character.isLetter(data[index])) {
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

	/**
	 * Gets the next valid tag from the current index in the data as a string.
	 *
	 * @return next tag in the data
	 */
	private String getTag() {
		if (data[index] == '=') {
			index++;
			return "=";
		} else {
			return getIdentifier();
		}
	}

	/**
	 * Checks if given character is a valid operator or not.
	 *
	 * @param c character to be checked
	 * @return {@code} true if it is, {@code false} otherwise
	 */
	private boolean isOperator(char c) {
		return (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^');
	}

}
