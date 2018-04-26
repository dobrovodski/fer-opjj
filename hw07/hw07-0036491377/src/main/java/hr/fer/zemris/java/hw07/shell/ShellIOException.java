package hr.fer.zemris.java.hw07.shell;

/**
 * This exception is thrown when there is an error while trying to read or write from the standard input.
 *
 * @author matej
 */
public class ShellIOException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     *
     * @param message the detail message
     */
    public ShellIOException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with null as its detail message.
     */
    public ShellIOException() {
        super();
    }
}
