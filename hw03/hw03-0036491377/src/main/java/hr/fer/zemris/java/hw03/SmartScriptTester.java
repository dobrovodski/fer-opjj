package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Demo class for the {@code SmartScriptParser} class
 */
public class SmartScriptTester {
	/**
	 * Main method
	 * @param args command line arguments
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("This test only accepts one argument (filepath)");
			return;
		}

		String filepath = args[0];
		String docBody;

		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Something went wrong trying to read the file.");
			return;
		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptParser.createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original

		SmartScriptParser parser2 = null;
		try {
			parser2 = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		DocumentNode document2 = parser2.getDocumentNode();
		String docBody2 = SmartScriptParser.createOriginalDocumentBody(document2); // Nodes should be same

		//System.out.println("Are the 2 document bodies the same?: " + originalDocumentBody.equals(docBody2));

	}
}
