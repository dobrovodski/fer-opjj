package hr.fer.zemris.lsystems.impl;

/**
 * Enumerates the different commands.
 * @author matej
 */
public enum CommandType {
	// Draw command
	DRAW("draw"),
	// Skip command
	SKIP("skip"),
	// Scale command
	SCALE("scale"),
	// Rotate command
	ROTATE("rotate"),
	// Push command
	PUSH("push"),
	// Pop command
	POP("pop"),
	// Color command
	COLOR("color");

	private String name;
	CommandType(String name) {
		this.name = name;
	}

	/**
	 * Maps given string to a CommandType.
	 * @param name string to be mapped
	 * @return command type
	 * @throws LSystemBuilderException if a type with that name cannot be found.
	 */
	public static CommandType getType(String name) {
		for (CommandType t : CommandType.values()) {
			if (t.name.equals(name)) {
				return t;
			}
		}

		throw new LSystemBuilderException("Could not interpret as command: " + name);
	}
}
