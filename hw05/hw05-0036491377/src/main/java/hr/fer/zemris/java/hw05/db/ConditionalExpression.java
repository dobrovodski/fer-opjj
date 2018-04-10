package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.fieldgetters.IFieldValueGetters;
import hr.fer.zemris.java.hw05.db.operators.IComparisonOperator;

/**
 *
 */
public class ConditionalExpression {
    //
    private IFieldValueGetters fieldGetter;
    //
    private String stringLiteral;
    //
    private IComparisonOperator comparisonOperator;

    /**
     *
     * @param getter
     * @param literal
     * @param operator
     */
    public ConditionalExpression(IFieldValueGetters getter, String literal, IComparisonOperator operator) {
        this.fieldGetter = getter;
        this.stringLiteral = literal;
        this.comparisonOperator = operator;
    }

    /**
     *
     * @return
     */
    public IFieldValueGetters getFieldGetter() {
        return fieldGetter;
    }

    /**
     *
     * @return
     */
    public String getStringLiteral() {
        return stringLiteral;
    }

    /**
     *
     * @return
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }
}
