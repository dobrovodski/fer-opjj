package hr.fer.zemris.lsystems.impl;

/**
 *
 */
public enum CommandType {
	//
	DRAW("draw"),
	//
	SKIP("skip"),
	//
	SCALE("scale"),
	//
	ROTATE("rotate"),
	//
	PUSH("push"),
	//
	POP("pop"),
	//
	COLOR("color");

	private String name;
	CommandType(String name) {
		this.name = name;
	}

	/**
	 *
	 * @param name
	 * @return
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
