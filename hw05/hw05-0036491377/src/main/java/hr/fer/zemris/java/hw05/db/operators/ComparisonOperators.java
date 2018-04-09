package hr.fer.zemris.java.hw05.db.operators;

public class ComparisonOperators {
	public static final IComparisonOperator LESS;
	public static final IComparisonOperator LESS_OR_EQUALS;
	public static final IComparisonOperator GREATER;
	public static final IComparisonOperator GREATER_OR_EQUALS;
	public static final IComparisonOperator EQUALS;
	public static final IComparisonOperator NOT_EQUALS;
	public static final IComparisonOperator LIKE;

	static {
		LESS = (value1, value2) -> value1.compareTo(value2) < 0;

		LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0;

		GREATER = (value1, value2) -> value1.compareTo(value2) > 0;

		GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0;

		EQUALS = (value1, value2) -> value1.compareTo(value2) == 0;

		NOT_EQUALS = (value1, value2) -> value1.compareTo(value2) != 0;

		LIKE = (value1, value2) -> {
			if (value2.split("\\*").length > 2) {
				throw new IllegalArgumentException("LIKE operator supports up to one wildcard *.");
			}
			value2 = value2.replaceAll("\\*", ".*");

			return value1.matches(value2);
		};
	}
}
