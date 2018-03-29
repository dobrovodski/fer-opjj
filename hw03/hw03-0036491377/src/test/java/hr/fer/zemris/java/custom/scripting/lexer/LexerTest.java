package hr.fer.zemris.java.custom.scripting.lexer;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class LexerTest {

	@Test
	public void NextToken_BasicExample_Correct() {
		String text = loader("lexerTests\\test_BasicExample.txt");
		Lexer lexer = new Lexer(text);

		Token[] correctData = {
				new Token(TokenType.TEXT, "This is sample text.\r\n"),
				new Token(TokenType.TAG_START, "{$"),
				new Token(TokenType.TAG_TYPE, "FOR"),
				new Token(TokenType.IDENTIFIER, "i"),
				new Token(TokenType.LITERAL_INT, "1"),
				new Token(TokenType.LITERAL_INT, "10"),
				new Token(TokenType.LITERAL_INT, "1"),
				new Token(TokenType.TAG_END, "$}"),
				new Token(TokenType.TEXT, "\r\n"),
				new Token(TokenType.EOF, null)};

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void NextToken_LongForLoop_Correct() {
		String text = loader("lexerTests\\test_LongForLoop.txt");
		Lexer lexer = new Lexer(text);

		Token[] correctData = {
				new Token(TokenType.TEXT, "This is sample text.\r\n"),
				new Token(TokenType.TAG_START, "{$"),
				new Token(TokenType.TAG_TYPE, "FOR"),
				new Token(TokenType.IDENTIFIER, "i"),
				new Token(TokenType.LITERAL_INT, "1"),
				new Token(TokenType.LITERAL_INT, "10"),
				new Token(TokenType.LITERAL_INT, "1"),
				new Token(TokenType.LITERAL_INT, "1"),
				new Token(TokenType.LITERAL_INT, "1"),
				new Token(TokenType.LITERAL_INT, "1"),
				new Token(TokenType.LITERAL_INT, "1"),
				new Token(TokenType.LITERAL_INT, "-1"),
				new Token(TokenType.LITERAL_DOUBLE, "-1.2"),
				new Token(TokenType.LITERAL_STRING, "1"),
				new Token(TokenType.LITERAL_INT, "1"),
				new Token(TokenType.TAG_END, "$}"),
				new Token(TokenType.TEXT, "\r\n"),
				new Token(TokenType.EOF, null)};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void NextToken_EscapedTag_Correct() {
		String text = loader("lexerTests\\test_EscapedTag.txt");
		Lexer lexer = new Lexer(text);

		Token[] correctData = {
				new Token(TokenType.TEXT, "Example \\{$=1$}. Now actually write one "),
				new Token(TokenType.TAG_START, "{$"),
				new Token(TokenType.TAG_TYPE, "="),
				new Token(TokenType.LITERAL_INT, "1"),
				new Token(TokenType.TAG_END, "$}"),
				new Token(TokenType.EOF, null)};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void NextToken_EscapedInString_Correct() {
		String text = loader("lexerTests\\test_EscapedInString.txt");
		Lexer lexer = new Lexer(text);

		Token[] correctData = {
				new Token(TokenType.TEXT, "Example \\{$=1$}. Now actually write one "),
				new Token(TokenType.TAG_START, "{$"),
				new Token(TokenType.TAG_TYPE, "FOR"),
				new Token(TokenType.LITERAL_STRING, "a\\\"\\\""),
				new Token(TokenType.LITERAL_STRING, "\\\\"),
				new Token(TokenType.TAG_END, "$}"),
				new Token(TokenType.EOF, null)};

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void NextToken_IllegalTagName_Tokenized() {
		String text = loader("lexerTests\\test_IllegalTagName.txt");
		Lexer lexer = new Lexer(text);

		Token[] correctData = {
				new Token(TokenType.TEXT, "Example \\{$=1$}. Now actually write one "),
				new Token(TokenType.TAG_START, "{$"),
				new Token(TokenType.TAG_TYPE, "ZOOLOŠKI"),
				new Token(TokenType.LITERAL_STRING, "a\\\"\\\""),
				new Token(TokenType.LITERAL_STRING, "\\\\"),
				new Token(TokenType.TAG_END, "$}"),
				new Token(TokenType.EOF, null)};

		checkTokenStream(lexer, correctData);
	}

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is =
				     this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1) break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for (Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}
}
