package hr.fer.zemris.java.custom.scripting.lexer;

public class Token {

	private TokenType type;
	private Object value;

	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public TokenType getType() {
		return type;
	}
}