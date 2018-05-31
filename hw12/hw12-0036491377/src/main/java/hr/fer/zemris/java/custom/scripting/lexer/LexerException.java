package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Exception that is thrown if something goes wrong while attempting to lex the given text.
 *
 * @author matej
 */
public class LexerException extends RuntimeException {
	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

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
