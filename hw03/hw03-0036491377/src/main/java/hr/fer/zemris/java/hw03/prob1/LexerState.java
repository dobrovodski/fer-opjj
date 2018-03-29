package hr.fer.zemris.java.hw03.prob1;

/**
 * Enum which represents the state in which the lexer is currently.
 *
 * @author matej
 */
public enum LexerState {
	/**
	 * Regular state of lexer
	 */
	BASIC,
	/**
	 * Extended state of lexer (state of lexing a comment block)
	 */
	EXTENDED
}
