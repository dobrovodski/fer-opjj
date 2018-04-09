package hr.fer.zemris.java.hw05.db.operators;

public enum ComparisonOperatorType {
	EQ("="),
	LT("<"),
	LTE("<="),
	GT(">"),
	GTE(">="),
	NE("!="),
	LIKE("like");

	private String name;

	ComparisonOperatorType(String name) {
		this.name = name;
	}

	public static ComparisonOperatorType getType(String name) {
		for (ComparisonOperatorType t : ComparisonOperatorType.values()) {
			if (t.name.equals(name.toLowerCase())) {
				return t;
			}
		}

		throw new IllegalArgumentException("Could not interpret as command: " + name);
	}
}

