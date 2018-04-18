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
}
