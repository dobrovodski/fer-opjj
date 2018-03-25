package hr.fer.zemris.java.hw03.prob1;

public class Lexer {
	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;

	public Lexer(String text) {
		this.data = text.toCharArray();
		this.state = LexerState.BASIC;
	}

	public Token nextToken() {
		return token;
	}

	public Token getToken() {
		return token;
	}

	public void setState(LexerState state) {
		this.state = state;
	}
}
