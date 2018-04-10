package hr.fer.zemris.java.hw05.db.operators;

/**
 *
 */
public enum ComparisonOperatorType {
    /**
     *
     */
    EQ("="),
    /**
     *
     */
    LT("<"),
    /**
     *
     */
    LTE("<="),
    /**
     *
     */
    GT(">"),
    /**
     *
     */
    GTE(">="),
    /**
     *
     */
    NE("!="),
    /**
     *
     */
    LIKE("LIKE");

    //
    private String name;

    /**
     *
     * @param name
     */
    ComparisonOperatorType(String name) {
        this.name = name;
    }

    /**
     *
     * @param name
     * @return
     */
    public static ComparisonOperatorType getType(String name) {
        for (ComparisonOperatorType t : ComparisonOperatorType.values()) {
            if (t.name.equals(name)) {
                return t;
            }
        }

        throw new IllegalArgumentException("Could not interpret as operator: " + name);
    }
}

