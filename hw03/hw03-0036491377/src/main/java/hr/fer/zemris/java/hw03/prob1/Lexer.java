package hr.fer.zemris.java.hw03.prob1;

import java.util.Arrays;
import java.util.Objects;

public class Lexer {
	private char[] data;
	private int index;
	private Token token;
	private LexerState state;
	private boolean end;

	/**
	 * Constructor for the Lexer class.
	 *
	 * @param text text that will be checked for lexical errors
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Lexer text cannot be null.");
		}

		this.data = text.toCharArray();
		this.state = LexerState.BASIC;
	}

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

	public Token getToken() {
		return token;
	}

	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("Cannot set state to null.");
		}

		this.state = state;
	}

	private boolean isEmpty(char character) {
		return Character.isWhitespace(character);
	}

	private long parseLong(String str) {
		long num;

		try {
			num = Long.parseLong(str);
		} catch (NumberFormatException ex) {
			throw new LexerException("Invalid input: could not pass number as long. Number was: " + str);
		}

		return num;
	}

	private boolean isEscape(char character) {
		return character == '\\';
	}
}
