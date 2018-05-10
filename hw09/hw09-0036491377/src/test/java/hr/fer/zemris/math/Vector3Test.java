package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link Vector3}.
 *
 * @author matej
 * @see <a href= "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html"> Naming standards for unit
 *         tests </a>
 */
public class Vector3Test {
    private static final double EPS = 1E-6;
    private Vector3 v1;
    private Vector3 v2;

    @Before
    public void before() {
        v1 = new Vector3(2.3, -1.3, 2.8);
        v2 = new Vector3(-4.1, -7.3, 8.04);
    }

    @Test
    public void Norm_RegularVectors_Correct() {
        Assert.assertEquals(v1.norm(), 3.8496753, EPS);
        Assert.assertEquals(v2.norm(), 11.607824, EPS);
    }

    @Test
    public void Normalized_RegularVectors_Correct() {
        Assert.assertEquals(v1.normalized(), new Vector3(0.597453, -0.337691, 0.727334));
        Assert.assertEquals(v2.normalized(), new Vector3(-0.35321, -0.628886, 0.692636));
    }

    @Test
    public void Add_RegularVectors_Correct() {
        Assert.assertEquals(v1.add(v2), new Vector3(-1.8, -8.6, 10.84));
    }

    @Test(expected = NullPointerException.class)
    public void Add_Null_ExceptionThrown() {
        v1.add(null);
    }

    @Test
    public void Sub_RegularVectors_Correct() {
        Assert.assertEquals(v1.sub(v2), new Vector3(6.4, 6, -5.24));
    }

    @Test(expected = NullPointerException.class)
    public void Sub_Null_ExceptionThrown() {
        v1.sub(null);
    }

    @Test
    public void Dot_RegularVectors_Correct() {
        Assert.assertEquals(v1.dot(v2), 22.572, EPS);
    }

    @Test(expected = NullPointerException.class)
    public void Dot_Null_ExceptionThrown() {
        v1.dot(null);
    }

    @Test
    public void Cross_RegularVectors_Correct() {
        Assert.assertEquals(v1.cross(v2), new Vector3(9.988, -29.972, -22.12));
    }

    @Test(expected = NullPointerException.class)
    public void Cross_Null_ExceptionThrown() {
        v1.cross(null);
    }

    @Test
    public void Scale_RegularVectors_Correct() {
        Assert.assertEquals(v1.scale(2.1), new Vector3(4.83, -2.73, 5.88));
    }

    @Test
    public void CosAngle_RegularVectors_Correct() {
        Assert.assertEquals(v1.cosAngle(v2), 0.5051206, EPS);
    }

    @Test(expected = NullPointerException.class)
    public void CosAngle_Null_ExceptionThrown() {
        v1.cosAngle(null);
    }

    @Test
    public void ToArray_RegularVectors_Correct() {
        Assert.assertArrayEquals(v1.toArray(), new double[]{2.3, -1.3, 2.8}, EPS);
        Assert.assertArrayEquals(v2.toArray(), new double[]{-4.1, -7.3, 8.04}, EPS);
    }
}
