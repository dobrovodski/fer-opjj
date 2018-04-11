package hr.fer.zemris.java.hw05.db.operators;

/**
 * Single method interface used for determining if a comparison operator is satisfied given two values.
 *
 * @author matej
 */
public interface IComparisonOperator {
    /**
     * Determines if the selected operator is satisfied by comparing the two values in a certain way.
     *
     * @param value1 first value
     * @param value2 second value
     *
     * @return {@code true} if it is satisfied, {@code false} otherwise
     */
    boolean satisfied(String value1, String value2);
}
