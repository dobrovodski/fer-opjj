package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum which represents the state in which the lexer is currently.
 *
 * @author matej
 */
public enum LexerState {
    /**
     * Default state while parsing TEXT
     */
    BASIC,
    /**
     * State while entering a tag (when {$ is read)
     */
    TAG_START,
    /**
     * State while parsing a tag
     */
    TAG
}
