package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception that is thrown if something goes wrong while attempting to parse the given document.
 *
 * @author matej
 */
public class SmartScriptParserException extends RuntimeException {
    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public SmartScriptParserException() {
        super();
    }

    /**
     * Constructor which accepts message to display in output.
     *
     * @param message message to be shown
     */
    public SmartScriptParserException(String message) {
        super(message);
    }
}
