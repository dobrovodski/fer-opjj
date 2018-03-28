package hr.fer.zemris.java.custom.scripting.lexer;

public enum TokenType {
	EOF,
	IDENTIFIER, //variables, functions
	OPERATOR, // - + * % ^ /
	TEXT,
	//LITERAL, //integer, double, string
	LITERAL_STRING,
	LITERAL_NUM,
	TAG_TYPE, //for, end, =
	TAG_START, //{$
	TAG_END, //$}
	SYMBOL //@
}