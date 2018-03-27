package hr.fer.zemris.java.custom.scripting.lexer;

import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

public class LexerTest {

	@Test
	public void testLexer() {
		Lexer lexer = new Lexer("{$ FoR i 1 10 1 $}\n" +
				" This is {$= i $}-th time this message is generated.\n" +
				"{$END$}\n" +
				"{$FOR i 0 10 2 $}\n" +
				" sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" +
				"{$END$}");

		checkToken(lexer.nextToken(), new Token(TokenType.TAG_START, "{$"));
		lexer.setState(LexerState.TAG);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_TYPE, "FOR"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.LITERAL, "1"));
		checkToken(lexer.nextToken(), new Token(TokenType.LITERAL, "10"));
		checkToken(lexer.nextToken(), new Token(TokenType.LITERAL, "1"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_END, "$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\n This is "));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_START, "{$"));
		lexer.setState(LexerState.TAG);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_TYPE, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_END, "$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "-th time this message is generated.\n"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_START, "{$"));
		lexer.setState(LexerState.TAG);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_TYPE, "END"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_END, "$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\n"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_START, "{$"));
		lexer.setState(LexerState.TAG);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_TYPE, "FOR"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.LITERAL, "0"));
		checkToken(lexer.nextToken(), new Token(TokenType.LITERAL, "10"));
		checkToken(lexer.nextToken(), new Token(TokenType.LITERAL, "2"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_END, "$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\n sin("));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_START, "{$"));
		lexer.setState(LexerState.TAG);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_TYPE, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_END, "$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "^2) = "));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_START, "{$"));
		lexer.setState(LexerState.TAG);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_TYPE, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, '*'));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '@'));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "sin"));
		checkToken(lexer.nextToken(), new Token(TokenType.LITERAL, "\"0.000\""));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '@'));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "decfmt"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_END, "$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\n"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_START, "{$"));
		lexer.setState(LexerState.TAG);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_TYPE, "END"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_END, "$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}

	@Test
	public void testLexer2() {
		Lexer lexer = new Lexer("Example \\{$=1$}. Now actually write one {$=1$}");
		Token[] t = new Token[30];
		int i = 0;
		Token n;
		while (true) {
			n = lexer.nextToken();
			if (n.getType() == TokenType.TAG_START) {
				lexer.setState(LexerState.TAG);
			}
			if (n.getType() == TokenType.TAG_END){
				lexer.setState(LexerState.BASIC);
			}
			t[i++] = n;
			if (n.getType() == TokenType.EOF) {
				break;
			}
		}
		int z = 1;
	}

	private void checkToken(Token actual, Token expected) {
		String msg = "Token are not equal.";
		assertEquals(msg, expected.getType(), actual.getType());
		assertEquals(msg, expected.getValue(), actual.getValue());
	}
}
