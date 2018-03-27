package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmartScriptParser {
	private String document;

	public SmartScriptParser(String document) {
		this.document = document;
		parse();
	}

	public static void main(String[] args) {
		String filepath = args[0];
		String docBody = null;
		SmartScriptParser parser = null;

		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
		// content of docBody
	}

	private DocumentNode getDocumentNode() {
		return null;
	}

	private static String createOriginalDocumentBody(DocumentNode document) {
		return null;
	}

	private void parse() {

	}
}
