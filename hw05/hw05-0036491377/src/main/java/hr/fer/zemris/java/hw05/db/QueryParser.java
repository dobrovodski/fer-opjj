package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.fieldgetters.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.fieldgetters.IFieldValueGetters;
import hr.fer.zemris.java.hw05.db.operators.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.operators.IComparisonOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class QueryParser {
    //
    private String queryString;
    //
    private List<ConditionalExpression> query;

    /**
     *
     * @param queryString
     */
    public QueryParser(String queryString) {
        this.queryString = queryString.trim();
        parse();
    }

    /**
     *
     * @return
     */
    public boolean isDirectQuery() {
        return query.size() == 1 &&
               query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS) &&
               query.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG);
    }

    /**
     *
     * @return
     */
    public String getQueriedJMBAG() {
        if (!isDirectQuery()) {
            throw new IllegalStateException("Query was not direct.");
        }

        return query.get(0).getStringLiteral();
    }

    /**
     *
     * @return
     */
    public List<ConditionalExpression> getQuery() {
        return query;
    }

    /**
     *
     */
    private void parse() {
        List<ConditionalExpression> expressionsList = new ArrayList<>();

        // Splits query into expressions on logical operator AND
        String[] expressions = this.queryString.split("(?i)\\s+and\\s+");

        // id - matches field
        // op - matches operators
        // lit - matches string literal (asterisk, numbers, unicode letters)
        //TODO: check more if regex ever breaks
        String regex = "^(?<id>([a-zA-z]+))\\s*(?<op>[!=<>]+|(LIKE))\\s*\"(?<lit>[*0-9\\p{L}]+)\"$";
        Pattern p = Pattern.compile(regex);

        for (String expression : expressions) {
            Matcher m = p.matcher(expression);

            if (!m.find()) {
                throw new IllegalArgumentException("Could not parse expression. Format has to be "
                                                   + "(field) (operator) \"(literal)\": " + expression);
            }

            String field = m.group("id");
            String operator = m.group("op");
            String literal = m.group("lit");

            IFieldValueGetters fieldGetter = FieldValueGetters.from(field);
            IComparisonOperator comparisonOperator = ComparisonOperators.from(operator);

            expressionsList.add(new ConditionalExpression(fieldGetter, literal, comparisonOperator));
        }

        this.query = expressionsList;
    }
}
