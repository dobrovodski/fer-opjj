package hr.fer.zemris.java.hw05.db.parser;

public class Token {
	private TokenType type;
	private Object value;

	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	public TokenType getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}
}
