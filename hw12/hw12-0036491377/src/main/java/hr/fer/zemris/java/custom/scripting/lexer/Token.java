package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class which represents a single token created during lexical analysis. Keeps track of the type and value.
 *
 * @author matej
 */
public class Token {

	// Type of the token
	private TokenType type;
	// Value of the token
	private Object value;

	/**
	 * Constructor for the {@code Token} class.
	 *
	 * @param type  type of token
	 * @param value value to be stored
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns the value held inside the token.
	 *
	 * @return value stored
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Returns the type of the token.
	 *
	 * @return type of token
	 */
	public TokenType getType() {
		return type;
	}
}