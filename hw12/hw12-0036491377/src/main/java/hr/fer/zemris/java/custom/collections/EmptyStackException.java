package hr.fer.zemris.java.custom.collections;

/**
 * Thrown to indicate that the stack was empty when a method that retrieves an element from the stack was called.
 *
 * @author matej
 */
public class EmptyStackException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public EmptyStackException() {
        super();
    }

    /**
     * Constructor which accepts message to display in output.
     *
     * @param message message to be shown
     */
    public EmptyStackException(String message) {
        super(message);
    }

}
