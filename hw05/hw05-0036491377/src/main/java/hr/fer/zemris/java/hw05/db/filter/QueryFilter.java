package hr.fer.zemris.java.hw05.db.filter;

import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.StudentRecord;

import java.util.List;

/**
 * This class is used to decide if the provided {@link StudentRecord} passes the given query.
 * @author matej
 */
public class QueryFilter implements IFilter {
    // List of conditional expressions represents a single user query
    private List<ConditionalExpression> query;

    public QueryFilter(List<ConditionalExpression> query) {
        this.query = query;
    }

    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression expression : query) {
            String left = expression.getFieldGetter().get(record);
            String right = expression.getStringLiteral();
            boolean satisfied = expression.getComparisonOperator().satisfied(left, right);

            if (!satisfied) {
                return false;
            }
        }

        return true;
    }
}
