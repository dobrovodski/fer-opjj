package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.fieldgetters.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.operators.ComparisonOperators;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * JUnit tests for QueryParserT.
 *
 * @author matej
 * @see <a href= "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html"> Naming standards for unit
 *         tests </a>
 */
public class QueryParserTest {
    //TODO: check multiple wildcards
    @Test
    public void IsDirectQuery_QueryIsDirect_True() {
        String query = " jmbag=\"0000000003\"";
        QueryParser qp = new QueryParser(query);
        Assert.assertEquals(true, qp.isDirectQuery());
    }

    @Test
    public void IsDirectQuery_QueryNotDirect_False() {
        String query = " jmbag!=\"0000000003\"";
        QueryParser qp = new QueryParser(query);
        Assert.assertEquals(false, qp.isDirectQuery());
    }

    @Test
    public void GetQueriedJMBAG_QueryIsDirect_JMBAGRetrieved() {
        String query = " jmbag=\"0000000003\"";
        QueryParser qp = new QueryParser(query);
        Assert.assertEquals("0000000003", qp.getQueriedJMBAG());
    }

    @Test(expected = IllegalStateException.class)
    public void GetQueriedJMBAG_QueryNotDirect_ExceptionThrown() {
        String query = " jmbag<\"0000000003\"";
        QueryParser qp = new QueryParser(query);
        qp.getQueriedJMBAG();
    }

    @Test(expected = IllegalArgumentException.class)
    public void Parse_BadOperator_ExceptionThrown() {
        String query = " jmbag<<\"0000000003\"";
        QueryParser qp = new QueryParser(query);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Parse_BadFieldGetter_ExceptionThrown() {
        String query = " jbmag=\"0000000003\"";
        QueryParser qp = new QueryParser(query);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Parse_BadLiteral_ExceptionThrown() {
        String query = " jmbag=\"0000000003";
        QueryParser qp = new QueryParser(query);
    }

    @Test
    public void Parse_SingleExpression_QueryCorrect() {
        String query = " firstName =\"Marko\"";
        QueryParser qp = new QueryParser(query);
        List<ConditionalExpression> q = qp.getQuery();
        ConditionalExpression e = q.get(0);

        Assert.assertEquals(1, q.size());
        Assert.assertEquals(e.getComparisonOperator(), ComparisonOperators.EQUALS);
        Assert.assertEquals(e.getFieldGetter(), FieldValueGetters.FIRST_NAME);
        Assert.assertEquals(e.getStringLiteral(), "Marko");
    }

    @Test
    public void Parse_MultipleExpresions_QueryCorrect() {
        String query = " lastName>=\"Mijić\" and firstName=\"Marko\"";
        QueryParser qp = new QueryParser(query);

        List<ConditionalExpression> q = qp.getQuery();

        Assert.assertEquals(2, q.size());

        ConditionalExpression e1 = q.get(0);
        Assert.assertEquals(e1.getComparisonOperator(), ComparisonOperators.GREATER_OR_EQUALS);
        Assert.assertEquals(e1.getFieldGetter(), FieldValueGetters.LAST_NAME);
        Assert.assertEquals(e1.getStringLiteral(), "Mijić");

        ConditionalExpression e2 = q.get(1);
        Assert.assertEquals(e2.getComparisonOperator(), ComparisonOperators.EQUALS);
        Assert.assertEquals(e2.getFieldGetter(), FieldValueGetters.FIRST_NAME);
        Assert.assertEquals(e2.getStringLiteral(), "Marko");
    }
}
