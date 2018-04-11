package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.fieldgetters.IFieldValueGetters;
import hr.fer.zemris.java.hw05.db.operators.IComparisonOperator;

/**
 * This class models a single conditional expression within a database query. The format of the expression is
 * (fieldName) (operator) "(literal)".
 *
 * @author matej
 */
public class ConditionalExpression {
    // First part of the expression, the field selector
    private IFieldValueGetters fieldGetter;
    // The string literal to be compared with
    private String stringLiteral;
    // The operator used for the comparison of StudentRecords with the literal
    private IComparisonOperator comparisonOperator;

    /**
     * Constructor for the class
     *
     * @param getter field getter
     * @param literal string literal
     * @param operator operator used for comparison
     */
    public ConditionalExpression(IFieldValueGetters getter, String literal, IComparisonOperator operator) {
        this.fieldGetter = getter;
        this.stringLiteral = literal;
        this.comparisonOperator = operator;
    }

    /**
     * Returns the field getter.
     *
     * @return field getter
     */
    public IFieldValueGetters getFieldGetter() {
        return fieldGetter;
    }

    /**
     * Returns the string literal
     *
     * @return string literal
     */
    public String getStringLiteral() {
        return stringLiteral;
    }

    /**
     * Returns the comparison operator
     *
     * @return comparison operator
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }
}
