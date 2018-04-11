package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.operators.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.operators.IComparisonOperator;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for ComparisonOperators.
 *
 * @author matej
 * @see <a href= "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html"> Naming standards for unit
 *         tests </a>
 */
public class ComparisonOperatorsTest {
    @Test
    public void LIKE_NotAlike_False() {
        IComparisonOperator oper = ComparisonOperators.LIKE;
        Assert.assertEquals(false, oper.satisfied("Zagreb", "Aba*"));
    }

    @Test
    public void LIKE_SameStrings_True() {
        IComparisonOperator oper = ComparisonOperators.LIKE;
        Assert.assertEquals(true, oper.satisfied("Zagreb", "Zagreb"));
    }

    @Test
    public void LIKE_WildcardAlike_True() {
        IComparisonOperator oper = ComparisonOperators.LIKE;
        Assert.assertEquals(true, oper.satisfied("Zagreb", "Z*b"));
        Assert.assertEquals(true, oper.satisfied("Zagreb", "*b"));
        Assert.assertEquals(true, oper.satisfied("Zagreb", "Z*"));
        Assert.assertEquals(true, oper.satisfied("Zagreb", "*"));
    }

    @Test
    public void LIKE_WildcardNotAlike_False() {
        IComparisonOperator oper = ComparisonOperators.LIKE;
        Assert.assertEquals(false, oper.satisfied("Zagreb", "A*b"));
        Assert.assertEquals(false, oper.satisfied("Zagreb", "A*"));
        Assert.assertEquals(false, oper.satisfied("Zagreb", "*t"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void LIKE_MoreThanOneWildcard_ExceptionThrown() {
        IComparisonOperator oper = ComparisonOperators.LIKE;
        oper.satisfied("Zagreb", "Z*g*b");
    }


    @Test(expected = IllegalArgumentException.class)
    public void LIKE_WildcardsNextToEachother_ExceptionThrown() {
        IComparisonOperator oper = ComparisonOperators.LIKE;
        oper.satisfied("Zagreb", "Z**b");
    }

    @Test
    public void LESS_NotLess_False() {
        IComparisonOperator oper = ComparisonOperators.LESS;
        Assert.assertEquals(false, oper.satisfied("Bar", "Aba"));
    }

    @Test
    public void LESS_Less_True() {
        IComparisonOperator oper = ComparisonOperators.LESS;
        Assert.assertEquals(true, oper.satisfied("Aba", "Bar"));
    }

    @Test
    public void LESS_OR_EQUALS_Equal_True() {
        IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
        Assert.assertEquals(true, oper.satisfied("Zagreb", "Zagreb"));
    }

    @Test
    public void GREATER_NotGreater_False() {
        IComparisonOperator oper = ComparisonOperators.GREATER;
        Assert.assertEquals(false, oper.satisfied("Aba", "Bar"));
    }

    @Test
    public void GREATER_Greater_True() {
        IComparisonOperator oper = ComparisonOperators.GREATER;
        Assert.assertEquals(true, oper.satisfied("Bar", "Aba"));
    }

    @Test
    public void GREATER_OR_EQUALS_Equal_True() {
        IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
        Assert.assertEquals(true, oper.satisfied("Zagreb", "Zagreb"));
    }

    @Test
    public void EQUALS_Equal_True() {
        IComparisonOperator oper = ComparisonOperators.EQUALS;
        Assert.assertEquals(true, oper.satisfied("Zagreb", "Zagreb"));
    }

    @Test
    public void EQUALS_NotEqual_False() {
        IComparisonOperator oper = ComparisonOperators.EQUALS;
        Assert.assertEquals(false, oper.satisfied("Zagreb", "Zagreb2"));
    }

    @Test
    public void NOT_EQUALS_Equal_False() {
        IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
        Assert.assertEquals(false, oper.satisfied("Zagreb", "Zagreb"));
    }

    @Test
    public void NOT_EQUALS_NotEqual_True() {
        IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
        Assert.assertEquals(true, oper.satisfied("Zagreb", "Zagreb2"));
    }
}
