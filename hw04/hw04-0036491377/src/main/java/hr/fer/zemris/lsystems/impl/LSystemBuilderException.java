package hr.fer.zemris.lsystems.impl;

/**
 * Exception which is thrown if something goes wrong while attempting to create an L-System with the given parameters.
 *
 * @author matej
 */
public class LSystemBuilderException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LSystemBuilderException(String message) {
		super(message);
	}
}
