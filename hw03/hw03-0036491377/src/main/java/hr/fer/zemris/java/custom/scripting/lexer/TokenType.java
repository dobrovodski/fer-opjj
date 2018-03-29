package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum which represents all the types a token can be.
 */
public enum TokenType {

	/**
	 * End of file
	 */
	EOF,
	/**
	 * Starts with a letter, has any sequence of letters, digits or underscores
	 */
	IDENTIFIER,
	/**
	 * Operators: +, -, /, *, %, ^
	 */
	OPERATOR,
	/**
	 * Pure text (not a string)
	 */
	TEXT,
	/**
	 * String literal
	 */
	LITERAL_STRING,
	/**
	 * Double literal
	 */
	LITERAL_DOUBLE,
	/**
	 * Integer literal
	 */
	LITERAL_INT,
	/**
	 * Type of tag (i.e. for, echo, =)
	 */
	TAG_TYPE,
	/**
	 * Start of tag (i.e. {$)
	 */
	TAG_START,
	/**
	 * End of tag (i.e. $})
	 */
	TAG_END,
	/**
	 * Symbol (i.e. @)
	 */
	SYMBOL
}