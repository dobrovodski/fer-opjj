package hr.fer.zemris.lsystems.impl;

import java.text.ParseException;

/**
 *
 */
public enum DirectiveType {
	//
	ORIGIN("origin"),
	//
	ANGLE("angle"),
	//
	UNIT_LENGTH("unitLength"),
	//
	SCALAR("unitLengthDegreeScaler"),
	//
	COMMAND("command"),
	//
	AXIOM("axiom"),
	//
	PRODUCTION("production");

	private String name;
	DirectiveType(String name) {
		this.name = name;
	}

	/**
	 *
	 * @param name
	 * @return
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
