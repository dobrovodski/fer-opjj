package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit tests for {@link Complex}.
 *
 * @author matej
 * @see <a href= "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html"> Naming standards for unit
 *         tests </a>
 */
public class ComplexTest {
    private final static double EPS = 1E-5;
    private Complex c1;
    private Complex c2;

    @Before
    public void before() {
        c1 = new Complex(2.3, -1.3);
        c2 = new Complex(-4.1, -7.3);
    }

    @Test
    public void Module_RegularComplexNumber_Correct() {
        Assert.assertEquals(c1.module(), 2.6419689, EPS);
    }

    @Test
    public void Module_ComplexZero_Zero() {
        Complex c = Complex.ZERO;
        Assert.assertEquals(c.module(), 0, EPS);
    }

    @Test
    public void Multiply_RegularComplexNumbers_Correct() {
        Assert.assertEquals(c1.multiply(c2), new Complex(-18.92, -11.46));
    }

    @Test(expected = NullPointerException.class)
    public void Multiply_Null_ExceptionThrown() {
        c1.multiply(null);
    }

    @Test
    public void Divide_RegularComplexNumbers_Correct() {
        Assert.assertEquals(c1.divide(c2), new Complex(0.00085592, 0.3155492));
    }

    @Test(expected = NullPointerException.class)
    public void Divide_Null_ExceptionThrown() {
        c1.divide(null);
    }

    @Test
    public void Add_RegularComplexNumbers_Correct() {
        Assert.assertEquals(c1.add(c2), new Complex(-1.8, -8.6));
    }

    @Test(expected = NullPointerException.class)
    public void Add_Null_ExceptionThrown() {
        c1.add(null);
    }

    @Test
    public void Sub_RegularComplexNumbers_Correct() {
        Assert.assertEquals(c1.sub(c2), new Complex(6.4, 6));
    }

    @Test(expected = NullPointerException.class)
    public void Sub_Null_ExceptionThrown() {
        c1.sub(null);
    }

    @Test
    public void Negate_RegularComplexNumber_Correct() {
        Assert.assertEquals(c1.negate(), new Complex(-2.3, 1.3));
    }

    @Test
    public void Power_RegularComplexNumbersPositivePower_Correct() {
        Assert.assertEquals(c1.power(3), new Complex(0.506, -18.434));
    }

    @Test
    public void Power_RegularComplexNumbersNegativePower_Correct() {
        Assert.assertEquals(c1.power(-3), new Complex(0.0014879, 0.054206));
    }

    @Test
    public void Root_RegularComplexNumbersPositiveRoot_Correct() {
        List<Complex> l = new ArrayList<>();
        l.add(new Complex(1.208044, -0.124736));
        l.add(new Complex(0.491937, 1.110372));
        l.add(new Complex(-0.90401, 0.810984));
        l.add(new Complex(-1.050646, -0.609156));
        l.add(new Complex(0.254675, -1.187464));
        Assert.assertEquals(c1.root(5), l);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Root_NegativeRoot_ExceptionThrown() {
        c1.root(-5);
    }

    @Test
    public void FromString_AllPossibleZeroes_Correct() {
        String z1 = "0";
        String z2 = "i0";
        String z3 = "0+i0";
        String z4 = "0-i0";
        Assert.assertEquals(Complex.ZERO, Complex.fromString(z1));
        Assert.assertEquals(Complex.ZERO, Complex.fromString(z2));
        Assert.assertEquals(Complex.ZERO, Complex.fromString(z3));
        Assert.assertEquals(Complex.ZERO, Complex.fromString(z4));
    }

    @Test
    public void FromString_MultipleCombinations_Correct() {
        String z1 = "1+i1";
        String z2 = "i1";
        String z3 = "1.0";
        String z4 = "-1.0";
        String z5 = "1 - i1";
        String z6 = "-1+i1.0";
        String z7 = " -1.0 -i1";
        String z8 = " i";
        String z9 = " -i";
        String z10 = " -1.0 -i";
        Assert.assertEquals(new Complex(1, 1), Complex.fromString(z1));
        Assert.assertEquals(Complex.IM, Complex.fromString(z2));
        Assert.assertEquals(Complex.ONE, Complex.fromString(z3));
        Assert.assertEquals(Complex.ONE_NEG, Complex.fromString(z4));
        Assert.assertEquals(new Complex(1, -1), Complex.fromString(z5));
        Assert.assertEquals(new Complex(-1, 1), Complex.fromString(z6));
        Assert.assertEquals(new Complex(-1, -1), Complex.fromString(z7));
        Assert.assertEquals(Complex.IM, Complex.fromString(z8));
        Assert.assertEquals(Complex.IM_NEG, Complex.fromString(z9));
        Assert.assertEquals(new Complex(-1, -1), Complex.fromString(z10));
    }
}
