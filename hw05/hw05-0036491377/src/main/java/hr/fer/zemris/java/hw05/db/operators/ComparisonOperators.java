package hr.fer.zemris.java.hw05.db.operators;

/**
 * Implements concrete strategies for all the different operators that can be found in database queries.
 * @author matej
 */
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
            //String[] z = value2.split("\\*", -1);
            if (value2.split("\\*", -1).length > 2) {
                throw new IllegalArgumentException("LIKE operator supports up to one wildcard *.");
            }
            value2 = value2.replaceAll("\\*", ".*");

            return value1.matches(value2);
        };
    }

    /**
     * Attempts to turn the given string into a certain operator.
     * @param operator string to be converted
     * @return operator method
     */
    public static IComparisonOperator from(String operator) {
        ComparisonOperatorType type = ComparisonOperatorType.getType(operator);

        switch (type) {
            case EQ:
                return EQUALS;
            case LT:
                return LESS;
            case LTE:
                return LESS_OR_EQUALS;
            case GT:
                return GREATER;
            case GTE:
                return GREATER_OR_EQUALS;
            case NE:
                return NOT_EQUALS;
            case LIKE:
                return LIKE;
            default:
                throw new IllegalArgumentException("Could not turn given type into operator.");
        }
    }
}
