package hr.fer.zemris.java.hw03.prob1;

/**
 * Exception that is thrown if something goes wrong while attempting to lex the given text.
 *
 * @author matej
 */
public class LexerException extends RuntimeException {
	/**
	 * Default constructor.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Constructor which accepts message to display in output.
	 *
	 * @param message message to be shown
	 */
	public LexerException(String message) {
		super(message);
	}
}
