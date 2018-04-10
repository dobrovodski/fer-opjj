package hr.fer.zemris.java.hw05.db.parser;

import hr.fer.zemris.java.hw05.db.conditionalexpressions.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.operators.ComparisonOperators;

import java.util.List;

public class QueryParser {
	private String queryString;
	private List<ConditionalExpression> query;

	public QueryParser(String queryString) {
		this.queryString = queryString.trim();
		query = parseQuery();
	}

	public boolean isDirectQuery() {
		return query.size() == 1 && query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS);
	}

	public String getQueriedJMBAG() {
		return null;
	}

	public List<ConditionalExpression> getQuery() {
		return query;
	}

	private List<ConditionalExpression> parseQuery() {
		String[] expressions = this.queryString.split("i\\s+and\\s+");

		return null;
	}
}
