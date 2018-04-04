package hr.fer.zemris.lsystems.impl;

public enum CommandType {
	DRAW("draw"),
	SKIP("skip"),
	SCALE("scale"),
	ROTATE("rotate"),
	PUSH("push"),
	POP("pop"),
	COLOR("color");

	private String name;
	CommandType(String name) {
		this.name = name;
	}

	public static CommandType getType(String name) {
		for (CommandType t : CommandType.values()) {
			if (t.name.equals(name.toLowerCase())) {
				return t;
			}
		}

		throw new IllegalArgumentException("Could not interpret as command: " + name);
	}
}
