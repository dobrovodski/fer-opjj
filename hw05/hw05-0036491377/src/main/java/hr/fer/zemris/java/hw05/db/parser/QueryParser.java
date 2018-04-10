package hr.fer.zemris.java.hw05.db.parser;

import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.fieldgetters.FieldValueGetterType;
import hr.fer.zemris.java.hw05.db.fieldgetters.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.fieldgetters.IFieldValueGetters;
import hr.fer.zemris.java.hw05.db.operators.ComparisonOperatorType;
import hr.fer.zemris.java.hw05.db.operators.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.operators.IComparisonOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {
    private String queryString;
    private List<ConditionalExpression> query;

    public QueryParser(String queryString) {
        this.queryString = queryString.trim();
        parse();
    }

    public boolean isDirectQuery() {
        return query.size() == 1 &&
               query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS) &&
               query.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG);
    }

    public String getQueriedJMBAG() {
        return query.get(0).getStringLiteral();
    }

    public List<ConditionalExpression> getQuery() {
        return query;
    }

    private void parse() {
        List<ConditionalExpression> expressionsList = new ArrayList<>();

        String[] expressions = this.queryString.split("(?i)\\s+and\\s+");
        String regex = "^(?<id>([a-zA-z]+))\\s*(?<op>[!=<>]+|(LIKE))\\s*\"(?<lit>(.)+)\"$";
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

            FieldValueGetterType fieldType = FieldValueGetterType.getType(field);
            IFieldValueGetters fieldGetter = FieldValueGetters.from(fieldType);

            ComparisonOperatorType operatorType = ComparisonOperatorType.getType(operator);
            IComparisonOperator comparisonOperator = ComparisonOperators.from(operatorType);

            expressionsList.add(new ConditionalExpression(fieldGetter, literal, comparisonOperator));
        }

        this.query = expressionsList;
    }
}
