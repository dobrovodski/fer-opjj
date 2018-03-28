package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class SmartScriptParser {
	private String document;
	private Lexer lexer;
	private DocumentNode documentNode;
	private ObjectStack stack;

	private Node[] nodes;

	public SmartScriptParser(String document) {
		if (document == null) {
			throw new SmartScriptParserException("Cannot pass null as parser document.");
		}

		this.document = document;
		this.lexer = new Lexer(document);
		this.stack = new ObjectStack();
		this.documentNode = new DocumentNode();

		try {
			parse();
		} catch (SmartScriptParserException ex) {
			throw ex;
		} catch (Exception ex) {
			// Prevention so I don't fail the class :^)
			throw new SmartScriptParserException("Something went terribly wrong while parsing.");
		}
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
		return documentNode;
	}

	private static String createOriginalDocumentBody(DocumentNode document) {
		return null;
	}

	private void parse() {
		this.stack.push(this.documentNode);
		Token token = lexer.nextToken();

		while (token.getType() != TokenType.EOF) {

			if (token.getType() == TokenType.TEXT) {
				addNode(new TextNode((String) token.getValue()), false);
			} else if (token.getType() == TokenType.TAG_START) {
				token = lexer.nextToken();

				if (token.getType() != TokenType.TAG_TYPE) {
					throw new SmartScriptParserException("Tag starts with invalid token: " + token.getValue());
				}

				switch (token.getValue().toString().toUpperCase()) {
					case "FOR":
						ArrayIndexedCollection forParams = new ArrayIndexedCollection();
						for (int i = 0; i < 4; i++) {
							token = lexer.nextToken();
							if (token.getType() == TokenType.TAG_END) {
								forParams.add(null);
								break;
							}
							Element element = tokenToElement(token);
							forParams.add(element);
						}
						token = lexer.nextToken();
						addNode(new ForLoopNode((ElementVariable) forParams.get(0), (Element) forParams.get(1), (Element) forParams.get(2), (Element) forParams.get(3)), true);
						break;
					case "=":
						token = lexer.nextToken();
						ArrayIndexedCollection elems = new ArrayIndexedCollection();
						while (true) {
							if (token.getType() == TokenType.TAG_END) {
								break;
							}

							elems.add(tokenToElement(token));
							token = lexer.nextToken();
						}
						addNode(new EchoNode(Arrays.copyOf(elems.toArray(), elems.size(), Element[].class)), false);
						break;
					case "END":
						stack.pop();
						if (stack.size() < 1) {
							throw new SmartScriptParserException("Script has more END tags than non-empty tags.");
						}
						token = lexer.nextToken();
						break;
				}

				if (token.getType() != TokenType.TAG_END) {
					throw new SmartScriptParserException("Could not parse tag properly. Expected $}, got: " + token.getValue());
				}
			}

			token = lexer.nextToken();
		}

		System.out.print("a");

	}

	private void pushNode(Node node) {
		this.stack.push(node);
	}

	private void addNode(Node node, boolean toPush) {
		try {
			Node top = (Node) stack.peek();
			top.addChildNode(node);
		} catch (EmptyStackException ex) {
			throw new SmartScriptParserException("Mismatch between opening and closing tags.");
		}

		if (toPush) {
			stack.push(node);
		}
	}

	private Element tokenToElement(Token token) {
		Element element;

		switch (token.getType()) {
			case IDENTIFIER:
				element = new ElementVariable((String) token.getValue());
				break;
			case OPERATOR:
				element = new ElementOperator((String) token.getValue());
				break;
			case LITERAL_STRING:
				element = new ElementString((String) token.getValue());
				break;
			case LITERAL_DOUBLE:
				double parsedDouble;
				try {
					parsedDouble = Double.parseDouble((String) token.getValue());
				} catch (NumberFormatException ex) {
					throw new SmartScriptParserException("Could not parse this value as double: " + token.getValue());
				}
				element = new ElementConstantDouble(parsedDouble);
				break;
			case LITERAL_INT:
				int parsedInt;
				try {
					parsedInt = Integer.parseInt((String) token.getValue());
				} catch (NumberFormatException ex) {
					throw new SmartScriptParserException("Could not parse this value as double: " + token.getValue());
				}
				element = new ElementConstantInteger(parsedInt);
				break;
			case SYMBOL:
				if (token.getValue().equals("@")) {
					Token function = lexer.nextToken();
					if (function.getType() != TokenType.IDENTIFIER) {
						throw new SmartScriptParserException("Incorrect function name: " + function.getValue());
					}

					element = new ElementFunction((String) function.getValue());
				} else {
					throw new SmartScriptParserException("Unrecognized symbol: " + token.getValue());
				}
				break;
			default:
				throw new SmartScriptParserException("Couldn't convert token to element: " + token.getValue());
		}

		return element;
	}
}
