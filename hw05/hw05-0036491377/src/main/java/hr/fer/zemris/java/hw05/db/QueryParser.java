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
 * This class handles parsing for database queries. It turns the given String query into a list of conditional
 * expressions and provides methods for retrieving the expressions and checking if it was a direct query.
 *
 * @author matej
 */
public class QueryParser {
    // String to parse
    private String queryString;
    // List of conditional expressions after parsing
    private List<ConditionalExpression> query;

    /**
     * This constructor immediately parses the provided query.
     *
     * @param queryString query to parse
     */
    public QueryParser(String queryString) {
        this.queryString = queryString.trim();
        parse();
    }

    /**
     * Returns {@code true} if the query was direct (i.e. the format was jmbag = "(number)"), {@code false} otherwise.
     *
     * @return returns {@code true} if the query was direct, {@code false} otherwise.
     */
    public boolean isDirectQuery() {
        return query.size() == 1 &&
               query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS) &&
               query.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG);
    }

    /**
     * If the query was direct, returns the JMBAG associated with that query.
     *
     * @return the JMBAG of the direct query
     *
     * @throws IllegalStateException if the query was not direct
     */
    public String getQueriedJMBAG() {
        if (!isDirectQuery()) {
            throw new IllegalStateException("Query was not direct.");
        }

        return query.get(0).getStringLiteral();
    }

    /**
     * Returns the query as a list of conditional expressions.
     *
     * @return list of conditional expressions extracted from the query
     */
    public List<ConditionalExpression> getQuery() {
        return query;
    }

    /**
     * The main method used for parsing the provided query and turning it into conditional expressions. It splits the
     * query on the keyword "and" and then checks each individual expression on its own, turning them into {@link
     * ConditionalExpression} objects and putting them in a list.
     *
     * @throws IllegalArgumentException if the expressions within the query could not be parsed. This will also
     *         fire if the "and" operator was mangled because the query could not have been properly split into
     *         expressions
     */
    private void parse() {
        List<ConditionalExpression> expressionsList = new ArrayList<>();

        // Splits query into expressions on logical operator AND
        String[] expressions = this.queryString.split("(?i)\\s+and\\s+");

        // id - matches field (one or more letters)
        // op - matches operators (any combination of !=<> or just a single LIKE)
        // lit - matches string literal (any character that isn't ")
        String regex = "^(?<id>([a-zA-z]+))\\s*(?<op>[!=<>]+|(LIKE))\\s*\"(?<lit>[^\"]+)\"$";
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
