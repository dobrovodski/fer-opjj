package hr.fer.zemris.java.custom.collections;

/**
 * Thrown to indicate that the stack was empty when a method that retrieves an
 * element from the stack was called.
 *
 * @author matej
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyStackException() {
		super();
	}

	public EmptyStackException(String message) {
		super(message);
	}

	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyStackException(Throwable cause) {
		super(cause);
	}

}
