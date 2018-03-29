package hr.fer.zemris.java.hw03.prob1;

/**
 * Enum which represents the different types a token can be.
 *
 * @author matej
 */
public enum TokenType {
	/**
	 * End of file
	 */
	EOF,
	/**
	 * String of letters
	 */
	WORD,
	/**
	 * Only numbers
	 */
	NUMBER,
	/**
	 * Anything else that isn't a WORD, NUMBER or whitespace
	 */
	SYMBOL
}
