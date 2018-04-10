package hr.fer.zemris.java.hw05.db.conditionalexpressions;

import hr.fer.zemris.java.hw05.db.fieldgetters.IFieldValueGetters;
import hr.fer.zemris.java.hw05.db.operators.IComparisonOperator;

public class ConditionalExpression {
	private IFieldValueGetters fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;

	public ConditionalExpression(IFieldValueGetters getter, String literal, IComparisonOperator operator) {
		this.fieldGetter = getter;
		this.stringLiteral = literal;
		this.comparisonOperator = operator;
	}

	public IFieldValueGetters getFieldGetter() {
		return fieldGetter;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}

	public IComparisonOperator getComparisonOperator() {
			return comparisonOperator;
	}
}
