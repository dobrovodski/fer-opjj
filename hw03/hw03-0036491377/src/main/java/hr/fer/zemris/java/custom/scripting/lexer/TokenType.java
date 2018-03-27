package hr.fer.zemris.java.custom.scripting.lexer;

public enum TokenType {
	EOF,
	IDENTIFIER, //variables, functions
	OPERATOR, //+ - etc
	TEXT,
	LITERAL, //integer, double, string
	TAG_TYPE, //for, end, =
	TAG_START, //{$
	SYMBOL, //@
	TAG_END //$}
}