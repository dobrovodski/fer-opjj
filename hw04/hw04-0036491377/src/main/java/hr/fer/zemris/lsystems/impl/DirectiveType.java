package hr.fer.zemris.lsystems.impl;

import java.text.ParseException;

/**
 * Enumerates the different directives.
 * @author matej
 */
public enum DirectiveType {
	// Origin directive
	ORIGIN("origin"),
	// Angle directive
	ANGLE("angle"),
	// Unit length directive
	UNIT_LENGTH("unitLength"),
	// Unit length degree scalar directive
	SCALAR("unitLengthDegreeScaler"),
	// Command directive
	COMMAND("command"),
	// Axiom directive
	AXIOM("axiom"),
	// Production directive
	PRODUCTION("production");

	// Name of the directive
	private String name;

	DirectiveType(String name) {
		this.name = name;
	}

	/**
	 * Maps given string to a DirectiveType.
	 * @param name string to be mapped
	 * @return directive type
	 * @throws LSystemBuilderException if a type with that name cannot be found.
	 */
	public static DirectiveType getType(String name) {
		for (DirectiveType t : DirectiveType.values()) {
			if (t.name.equals(name)) {
				return t;
			}
		}

		throw new LSystemBuilderException("Could not interpret as directive: " + name);
	}
}
