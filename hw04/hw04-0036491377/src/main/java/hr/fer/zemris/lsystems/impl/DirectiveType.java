package hr.fer.zemris.lsystems.impl;

public enum DirectiveType {
	ORIGIN("origin"),
	ANGLE("angle"),
	UNIT_LENGTH("unitlength"),
	SCALAR("unitlengthdegreescaler"),
	COMMAND("command"),
	AXIOM("axiom"),
	PRODUCTION("production");

	private String name;
	DirectiveType(String name) {
		this.name = name;
	}

	public static DirectiveType getType(String name) {
		for (DirectiveType t : DirectiveType.values()) {
			if (t.name.equals(name.toLowerCase())) {
				return t;
			}
		}

		throw new IllegalArgumentException("Could not interpret as directive: " + name);
	}
}
