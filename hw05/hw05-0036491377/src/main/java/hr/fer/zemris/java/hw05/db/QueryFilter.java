package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.conditionalexpressions.ConditionalExpression;

import java.util.List;

public class QueryFilter implements IFilter {
	List<ConditionalExpression> query;

	public QueryFilter(List<ConditionalExpression> query) {
		this.query = query;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		return false;
	}
}
