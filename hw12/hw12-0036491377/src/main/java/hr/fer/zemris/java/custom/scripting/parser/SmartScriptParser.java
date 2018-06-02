package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

import java.util.Arrays;

/**
 * Parser class for a custom scripting language.
 *
 * @author matej
 */
public class SmartScriptParser {
    /**
     * Lexer object used to tokenize input.
     */
    private Lexer lexer;
    /**
     * Initial tree node for parsing tree.
     */
    private DocumentNode documentNode;
    /**
     * Stack used for turning tokens into tree.
     */
    private ObjectStack stack;

    /**
     * Constructor for the parser. Takes in script in the form of a {@code String} and throws an error if parsing was
     * unsuccessful.
     *
     * @param document script to parse
     *
     * @throws SmartScriptParserException thrown if script couldn't be parsed or if the {@code document} was
     *         null
     */
    public SmartScriptParser(String document) {
        if (document == null) {
            throw new SmartScriptParserException("Cannot pass null as parser document.");
        }

        this.lexer = new Lexer(document);
        this.stack = new ObjectStack();
        this.documentNode = new DocumentNode();

        try {
            parse();
        } catch (SmartScriptParserException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SmartScriptParserException("Something went wrong while parsing.");
        }
    }

    /**
     * Returns the top node of the document model.
     *
     * @return node representing an entire document
     */
    public DocumentNode getDocumentNode() {
        return documentNode;
    }

    /**
     * Recreates the original document such that it is parsable again in the same way.
     *
     * @param node node representing the entire document
     *
     * @return {@code string} representing the original script
     */
    public static String createOriginalDocumentBody(Node node) {
        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append(node.asText());

        int children = node.numberOfChildren();
        for (int i = 0; i < children; i++) {
            textBuilder.append(createOriginalDocumentBody(node.getChild(i)));
        }

        if (node.hasEndTag()) {
            textBuilder.append("{$END$}");
        }

        return textBuilder.toString();
    }

    /**
     * Used for actual parsing of the script.
     */
    private void parse() {
        this.stack.push(this.documentNode);
        Token token = lexer.nextToken();

        while (token.getType() != TokenType.EOF) {

            if (token.getType() == TokenType.TEXT) {
                addNode(new TextNode((String) token.getValue()), false);
            } else if (token.getType() == TokenType.TAG_START) {
                try {
                    token = lexer.nextToken();
                } catch (LexerException ex) {
                    throw new SmartScriptParserException("Something went wrong while lexing. Check tags (i.e. names, "
                                                         + "symbols).");
                }

                if (token.getType() != TokenType.TAG_TYPE) {
                    throw new SmartScriptParserException("Tag starts with invalid token: " + token.getValue());
                }

                switch (token.getValue().toString().toUpperCase()) {
                    case "FOR":
                        ArrayIndexedCollection forParams = new ArrayIndexedCollection();
                        final int MAX_ARG = 4;
                        boolean endedEarly = false;
                        for (int i = 0; i < MAX_ARG; i++) {
                            token = lexer.nextToken();
                            if (token.getType() == TokenType.TAG_END) {
                                endedEarly = true;
                                break;
                            }
                            Element element = tokenToElement(token);
                            forParams.add(element);
                        }
                        if (!endedEarly) {
                            token = lexer.nextToken();
                        }
                        ElementVariable variable = (ElementVariable) forParams.get(0);
                        Element startExpression = (Element) forParams.get(1);
                        Element endExpression = (Element) forParams.get(2);
                        Element stepExpression = forParams.size() == 4 ? (Element) forParams.get(3) : null;

                        if (!(
                                isValidForLoopParam(startExpression) && isValidForLoopParam(endExpression) &&
                                isValidForLoopParam(stepExpression)
                        )) {
                            throw new SmartScriptParserException("For loop parameters aren't the correct type.");
                        }

                        addNode(new ForLoopNode(variable, startExpression, endExpression, stepExpression), true);
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
                    default:
                        throw new SmartScriptParserException("Unexpected tag: " + token.getValue());
                }

                if (token.getType() != TokenType.TAG_END) {
                    throw new SmartScriptParserException("Could not parse tag properly. Expected $}, got: " + token
                            .getValue());
                }
            }

            token = lexer.nextToken();
        }
    }

    /**
     * Adds given node to the node at the top of the stack and optionally pushes new node on stack.
     *
     * @param node node to add
     * @param toPush {@code true} if the node is to be pushed on top of the stack, {@code false} otherwise
     */
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

    /**
     * Checks validity of the parameter for the FOR tag.
     *
     * @param e element to check
     *
     * @return {@code true} if valid
     */
    private boolean isValidForLoopParam(Element e) {
        return (
                e instanceof ElementVariable
                || e instanceof ElementString
                || e instanceof ElementConstantDouble
                || e instanceof ElementConstantInteger
                || e == null
        );
    }

    /**
     * Converts given token into the appropriate {@code element}.
     *
     * @param token token to be converted
     *
     * @return element that represents the given token
     */
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
                    throw new SmartScriptParserException("Could not parse this value as integer: " + token.getValue());
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

    /**
     * Returns the stack in which the document model is stored.
     *
     * @return stack with document model
     */
    public ObjectStack getStack() {
        return stack;
    }
}
