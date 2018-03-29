package hr.fer.zemris.java.hw03.prob1;

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
		// Throw error if calling nextToken after end
		if (end) {
			throw new LexerException("Reached end of input, no more tokens to be generated.");
		}

		// Move to the next non-empty character
		while (index < data.length && isEmpty(data[index])) {
			index++;
		}

		// Check EOF
		if (index == data.length) {
			end = true;
			token = new Token(TokenType.EOF, null);
			return token;
		}

		// BASIC mode
		if (this.state == LexerState.BASIC) {
			if (Character.isLetter(data[index]) || isEscape(data[index])) {
				StringBuilder wordBuilder = new StringBuilder();

				while (index < data.length && !isEmpty(data[index])) {
					// Stop if number reached before escape symbol
					if (Character.isDigit(data[index])) {
						break;
					}

					// If escape-symbol reached, move 1 spot to the right, check what is being escaped
					if (isEscape(data[index])) {
						index++;

						if (index == data.length) {
							throw new LexerException("Cannot escape the end.");
						}

						if (!Character.isDigit(data[index]) && !isEscape(data[index])) {
							throw new LexerException("Cannot escape non-digit.");
						}
					}

					wordBuilder.append(data[index]);
					index++;
				}

				token = new Token(TokenType.WORD, wordBuilder.toString());
			} else if (Character.isDigit(data[index])) {
				StringBuilder numBuilder = new StringBuilder();

				// Glue together digits
				while (index < data.length && Character.isDigit(data[index])) {
					numBuilder.append(data[index]);
					index++;
				}

				long num = parseLong(numBuilder.toString());
				token = new Token(TokenType.NUMBER, num);
			} else if (!isEmpty(data[index])) {
				char sym = data[index];
				index++;
				token = new Token(TokenType.SYMBOL, sym);
			}

		} else if (this.state == LexerState.EXTENDED) {
			StringBuilder wordBuilder = new StringBuilder();

			if (data[index] == '#') {
				index++;
				token = new Token(TokenType.SYMBOL, '#');
			} else {
				while (index < data.length && !isEmpty(data[index])) {
					// Glue together everything up to #
					if (data[index] == '#') {
						break;
					}
					wordBuilder.append(data[index]);
					index++;
				}

				token = new Token(TokenType.WORD, wordBuilder.toString());
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
	 * Wrapper for {@code Character.isWhitespace}.
	 *
	 * @param character character to be checked
	 * @return {@code true} if character is whitespace, {@code false} otherwise
	 */
	private boolean isEmpty(char character) {
		return Character.isWhitespace(character);
	}

	/**
	 * Attempts to parse given string as a long number.
	 *
	 * @param str string to be parsed
	 * @return number that the string was parsed to
	 * @throws LexerException if the string could not be parsed
	 */
	private long parseLong(String str) {
		long num;

		try {
			num = Long.parseLong(str);
		} catch (NumberFormatException ex) {
			throw new LexerException("Invalid input: could not pass number as long. Number was: " + str);
		}

		return num;
	}

	/**
	 * Check if character is escape symbol.
	 *
	 * @param character character to be checked
	 * @return {@code true} if character is escape symbol, {@code false} otherwise
	 */
	private boolean isEscape(char character) {
		return character == '\\';
	}
}
