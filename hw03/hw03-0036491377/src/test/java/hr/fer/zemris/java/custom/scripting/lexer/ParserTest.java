package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import org.junit.Assert;
import org.junit.Test;

public class ParserTest {

	@Test
	public void Parse_Example_NoException() {
		String doc = loader("parserTests\\doc1.txt");
		SmartScriptParser p = new SmartScriptParser(doc);
	}

	@Test
	public void Parse_UTF8_NoException() {
		String doc = loader("parserTests\\test_UTF8.txt");
		SmartScriptParser p = new SmartScriptParser(doc);
	}

	@Test(expected = SmartScriptParserException.class)
	public void Parse_IllegalForTag_ExceptionThrown() {
		String doc = loader("parserTests\\test_IllegalForTag.txt");
		SmartScriptParser p = new SmartScriptParser(doc);
	}

	@Test(expected = SmartScriptParserException.class)
	public void Parse_IllegalEndTag_ExceptionThrown() {
		String doc = loader("parserTests\\test_IllegalEndTag.txt");
		SmartScriptParser p = new SmartScriptParser(doc);
	}

	@Test(expected = SmartScriptParserException.class)
	public void Parse_IllegalEchoTag_ExceptionThrown() {
		String doc = loader("parserTests\\test_IllegalEchoTag.txt");
		SmartScriptParser p = new SmartScriptParser(doc);
	}

	@Test(expected = SmartScriptParserException.class)
	public void Parse_MissingEndCurlyBrace_ExceptionThrown() {
		String doc = loader("parserTests\\test_MissingEndCurlyBrace.txt");
		SmartScriptParser p = new SmartScriptParser(doc);
	}

	@Test(expected = SmartScriptParserException.class)
	public void Parse_ForTooManyArguments_ExceptionThrown() {
		String doc = loader("parserTests\\test_ForTooManyArguments.txt");
		SmartScriptParser p = new SmartScriptParser(doc);
	}

	@Test(expected = SmartScriptParserException.class)
	public void Parse_ForTooFewArguments_ExceptionThrown() {
		String doc = loader("parserTests\\test_ForTooFewArguments.txt");
		SmartScriptParser p = new SmartScriptParser(doc);
	}

	@Test
	public void Parse_ForThreeArguments_NoException() {
		String doc = loader("parserTests\\test_ForThreeArguments.txt");
		SmartScriptParser p = new SmartScriptParser(doc);
	}

	@Test
	public void Parse_ReParseEqualResults_True() {
		String doc = loader("parserTests\\test_ReParseEqualResults.txt");
		SmartScriptParser p = new SmartScriptParser(doc);
		Node docNode = p.getDocumentNode();
		String docbody1 = SmartScriptParser.createOriginalDocumentBody(docNode);

		SmartScriptParser p2 = new SmartScriptParser(docbody1);
		Node docNode2 = p2.getDocumentNode();
		String docbody2 = SmartScriptParser.createOriginalDocumentBody(docNode2);
		Assert.assertEquals(docbody1, docbody2);
	}

	@Test(expected = SmartScriptParserException.class)
	public void Parse_ForLoopFunctionAsArgument_ExceptionThrown() {
		String doc = loader("parserTests\\test_ForLoopFunctionAsArgument.txt");
		SmartScriptParser p = new SmartScriptParser(doc);
	}

	@Test
	public void Parse_CompareDocumentModelStructure_Same() {
		String doc = loader("parserTests\\doc1.txt");
		SmartScriptParser p = new SmartScriptParser(doc);
		Node docNode = p.getDocumentNode();
		ObjectStack stack = p.getStack();

		Assert.assertEquals(1, stack.size());
		Assert.assertEquals(4, docNode.numberOfChildren());
		Assert.assertEquals(3, docNode.getChild(1).numberOfChildren());
		Assert.assertEquals(5, docNode.getChild(3).numberOfChildren());
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

}
