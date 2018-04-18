package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import org.junit.Assert;
import org.junit.Test;

public class ValueWrapperTest {
    @Test
    public void Add_TwoIntegers_CorrectResult() {
        int a = 5;
        int b = 10;
        ValueWrapper w = new ValueWrapper(a);
        w.add(b);
        Assert.assertEquals(a + b, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Integer);
    }

    @Test
    public void Add_TwoDoubles_CorrectResult() {
        double a = 5.3;
        double b = 10.1;
        ValueWrapper w = new ValueWrapper(a);
        w.add(b);
        Assert.assertEquals(a + b, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test
    public void Add_NullToInteger_Unchanged() {
        int a = 5;
        ValueWrapper w = new ValueWrapper(a);
        w.add(null);
        Assert.assertEquals(a, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Integer);
    }

    @Test
    public void Add_NullToDouble_Unchanged() {
        double a = 5.3;
        ValueWrapper w = new ValueWrapper(a);
        w.add(null);
        Assert.assertEquals(a, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test
    public void Add_StringScientific_CorrectResult() {
        double a = 5.3;
        String b = "120E-1";
        ValueWrapper w = new ValueWrapper(a);
        w.add(b);
        Assert.assertEquals(a + Double.parseDouble(b), w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test
    public void Add_StringStandard_CorrectResult() {
        double a = 5.3;
        String b = "12.4900";
        ValueWrapper w = new ValueWrapper(a);
        w.add(b);
        Assert.assertEquals(a + Double.parseDouble(b), w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Add_StringIncorrect_ExceptionThrown() {
        double a = 5.3;
        String b = "z12.49";
        ValueWrapper w = new ValueWrapper(a);
        w.add(b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Add_StringIntegerIncorrect_ExceptionThrown() {
        double a = 5.3;
        String b = "z12";
        ValueWrapper w = new ValueWrapper(a);
        w.add(b);
    }

    @Test
    public void Add_StringInteger_CorrectResult() {
        double a = 5.3;
        String b = "12";
        ValueWrapper w = new ValueWrapper(a);
        w.add(b);
        Assert.assertEquals(a + Integer.parseInt(b), w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test(expected = ClassCastException.class)
    public void Add_NotANumber_ExceptionThrown() {
        double a = 5.3;
        boolean b = true;
        ValueWrapper w = new ValueWrapper(a);
        w.add(b);
    }

    @Test
    public void Add_IntegerToNull_CorrectResult() {
        int b = 10;
        ValueWrapper w = new ValueWrapper(null);
        w.add(b);
        Assert.assertEquals(b, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Integer);
    }

    // Subtract

    @Test
    public void Subtract_TwoIntegers_CorrectResult() {
        int a = 5;
        int b = 10;
        ValueWrapper w = new ValueWrapper(a);
        w.subtract(b);
        Assert.assertEquals(a - b, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Integer);
    }

    @Test
    public void Subtract_TwoDoubles_CorrectResult() {
        double a = 5.3;
        double b = 10.1;
        ValueWrapper w = new ValueWrapper(a);
        w.subtract(b);
        Assert.assertEquals(a - b, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test
    public void Subtract_NullToInteger_Unchanged() {
        int a = 5;
        ValueWrapper w = new ValueWrapper(a);
        w.subtract(null);
        Assert.assertEquals(a, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Integer);
    }

    @Test
    public void Subtract_NullToDouble_Unchanged() {
        double a = 5.3;
        ValueWrapper w = new ValueWrapper(a);
        w.subtract(null);
        Assert.assertEquals(a, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test
    public void Subtract_StringScientific_CorrectResult() {
        double a = 5.3;
        String b = "120E-1";
        ValueWrapper w = new ValueWrapper(a);
        w.subtract(b);
        Assert.assertEquals(a - Double.parseDouble(b), w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test
    public void Subtract_StringStandard_CorrectResult() {
        double a = 5.3;
        String b = "12.4900";
        ValueWrapper w = new ValueWrapper(a);
        w.subtract(b);
        Assert.assertEquals(a - Double.parseDouble(b), w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Subtract_StringIncorrect_ExceptionThrown() {
        double a = 5.3;
        String b = "z12.49";
        ValueWrapper w = new ValueWrapper(a);
        w.subtract(b);
    }

    @Test(expected = ClassCastException.class)
    public void Subtract_NotANumber_ExceptionThrown() {
        double a = 5.3;
        boolean b = true;
        ValueWrapper w = new ValueWrapper(a);
        w.subtract(b);
    }

    @Test
    public void Subtract_IntegerToNull_CorrectResult() {
        int b = 10;
        ValueWrapper w = new ValueWrapper(null);
        w.subtract(b);
        Assert.assertEquals(-b, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Integer);
    }

    // Multiply

    @Test
    public void Multiply_TwoIntegers_CorrectResult() {
        int a = 5;
        int b = 10;
        ValueWrapper w = new ValueWrapper(a);
        w.multiply(b);
        Assert.assertEquals(a * b, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Integer);
    }

    @Test
    public void Multiply_TwoDoubles_CorrectResult() {
        double a = 5.3;
        double b = 10.1;
        ValueWrapper w = new ValueWrapper(a);
        w.multiply(b);
        Assert.assertEquals(a * b, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test
    public void Multiply_NullToInteger_Zero() {
        int a = 5;
        ValueWrapper w = new ValueWrapper(a);
        w.multiply(null);
        Assert.assertEquals(0, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Integer);
    }

    @Test
    public void Multiply_NullToDouble_Zero() {
        double a = 5.3;
        ValueWrapper w = new ValueWrapper(a);
        w.multiply(null);
        Assert.assertEquals(0.0, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test
    public void Multiply_StringScientific_CorrectResult() {
        double a = 5.3;
        String b = "120E-1";
        ValueWrapper w = new ValueWrapper(a);
        w.multiply(b);
        Assert.assertEquals(a * Double.parseDouble(b), w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test
    public void Multiply_StringStandard_CorrectResult() {
        double a = 5.3;
        String b = "12.4900";
        ValueWrapper w = new ValueWrapper(a);
        w.multiply(b);
        Assert.assertEquals(a * Double.parseDouble(b), w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Multiply_StringIncorrect_ExceptionThrown() {
        double a = 5.3;
        String b = "z12.49";
        ValueWrapper w = new ValueWrapper(a);
        w.multiply(b);
    }

    @Test(expected = ClassCastException.class)
    public void Multiply_NotANumber_ExceptionThrown() {
        double a = 5.3;
        boolean b = true;
        ValueWrapper w = new ValueWrapper(a);
        w.multiply(b);
    }

    @Test
    public void Multiply_IntegerToNull_CorrectResult() {
        int b = 10;
        ValueWrapper w = new ValueWrapper(null);
        w.multiply(b);
        Assert.assertEquals(0, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Integer);
    }

    // divide

    @Test
    public void Divide_TwoIntegers_CorrectResult() {
        int a = 5;
        int b = 10;
        ValueWrapper w = new ValueWrapper(a);
        w.divide(b);
        Assert.assertEquals(a / b, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Integer);
    }

    @Test
    public void Divide_TwoDoubles_CorrectResult() {
        double a = 5.3;
        double b = 10.1;
        ValueWrapper w = new ValueWrapper(a);
        w.divide(b);
        Assert.assertEquals(a / b, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test(expected = ArithmeticException.class)
    public void Divide_NullToInteger_ExceptionThrown() {
        int a = 5;
        ValueWrapper w = new ValueWrapper(a);
        w.divide(null);
    }

    @Test
    public void Divide_NullToDouble_Infinity() {
        double a = 5.3;
        ValueWrapper w = new ValueWrapper(a);
        w.divide(null);
        Assert.assertEquals(Double.POSITIVE_INFINITY, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test
    public void Divide_StringScientific_CorrectResult() {
        double a = 5.3;
        String b = "120E-1";
        ValueWrapper w = new ValueWrapper(a);
        w.divide(b);
        Assert.assertEquals(a / Double.parseDouble(b), w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test
    public void Divide_StringStandard_CorrectResult() {
        double a = 5.3;
        String b = "12.4900";
        ValueWrapper w = new ValueWrapper(a);
        w.divide(b);
        Assert.assertEquals(a / Double.parseDouble(b), w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Double);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Divide_StringIncorrect_ExceptionThrown() {
        double a = 5.3;
        String b = "z12.49";
        ValueWrapper w = new ValueWrapper(a);
        w.divide(b);
    }

    @Test(expected = ClassCastException.class)
    public void Divide_NotANumber_ExceptionThrown() {
        double a = 5.3;
        boolean b = true;
        ValueWrapper w = new ValueWrapper(a);
        w.divide(b);
    }

    @Test
    public void Divide_IntegerToNull_CorrectResult() {
        int b = 10;
        ValueWrapper w = new ValueWrapper(null);
        w.divide(b);
        Assert.assertEquals(0, w.getValue());
        Assert.assertEquals(true, w.getValue() instanceof Integer);
    }

    // numCompare

    @Test
    public void NumCompare_BiggerToSmaller_Positive() {
        int a = 10;
        int b = 5;
        ValueWrapper w = new ValueWrapper(a);
        Assert.assertEquals(true, w.numCompare(b) > 0);
    }

    @Test
    public void NumCompare_SmallerToBigger_Negative() {
        int a = 5;
        int b = 10;
        ValueWrapper w = new ValueWrapper(a);
        Assert.assertEquals(true, w.numCompare(b) < 0);
    }

    @Test
    public void NumCompare_Equal_Zero() {
        int a = 5;
        int b = 10;
        ValueWrapper w = new ValueWrapper(a);
        Assert.assertEquals(true, w.numCompare(b) < 0);
    }

    @Test
    public void NumCompare_DoubleToInteger_Correct() {
        int a = 5;
        double b = 10.34;
        ValueWrapper w = new ValueWrapper(a);
        Assert.assertEquals(true, w.numCompare(b) < 0);
    }

    @Test
    public void NumCompare_UsingNull_Correct() {
        int a = 5;
        ValueWrapper w = new ValueWrapper(a);
        Assert.assertEquals(true, w.numCompare(null) > 0);
    }
}
