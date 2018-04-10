package hr.fer.zemris.java.hw05.db.operators;

/**
 * Enumerates all the different operators which can be found in a database query.
 * @author matej
 */
public enum ComparisonOperatorType {
    /**
     * Equals operator
     */
    EQ("="),
    /**
     * Less than operator
     */
    LT("<"),
    /**
     * Less than or equal operator
     */
    LTE("<="),
    /**
     * Greater than operator
     */
    GT(">"),
    /**
     * Greater than or equal operator
     */
    GTE(">="),
    /**
     * Not equal operator
     */
    NE("!="),
    /**
     * Like operator
     */
    LIKE("LIKE");

    // string representation of the operator
    private String operator;

    ComparisonOperatorType(String operator) {
        this.operator = operator;
    }

    /**
     * Returns enum based on given string.
     * @param operator string representation of the operator to convert to
     * @return enum representing the selected {@link ComparisonOperators} strategy
     * @throws IllegalArgumentException if the string cannot be converted to an existing operator
     */
    public static ComparisonOperatorType getType(String operator) {
        for (ComparisonOperatorType t : ComparisonOperatorType.values()) {
            if (t.operator.equals(operator)) {
                return t;
            }
        }

        throw new IllegalArgumentException("Could not interpret as operator: " + operator);
    }
}

