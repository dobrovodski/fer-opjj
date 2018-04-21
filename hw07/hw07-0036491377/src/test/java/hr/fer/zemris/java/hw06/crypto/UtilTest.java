package hr.fer.zemris.java.hw06.crypto;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class UtilTest {
    @Test
    public void Hextobyte_SimpleExample_CorrectArray() {
        String hex = "01aE22";
        byte[] correct = new byte[]{1, -82, 34};
        Assert.assertTrue(Arrays.equals(correct, Util.hextobyte(hex)));
    }

    @Test
    public void Hextobyte_ZeroLengthString_ZeroLengthArray() {
        String hex = "";
        byte[] correct = new byte[0];
        Assert.assertTrue(Arrays.equals(correct, Util.hextobyte(hex)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void Hextobyte_OddLengthString_ExceptionThrown() {
        String hex = "01aE2";
        Util.hextobyte(hex);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Hextobyte_InvalidString_ExceptionThrown() {
        String hex = "01aZ22";
        Util.hextobyte(hex);
    }

    @Test
    public void Bytetohex_SimpleExample_CorrectArray() {
        byte[] bytes = new byte[]{1, -82, 34};
        String correct = "01ae22";
        Assert.assertEquals(correct, Util.bytetohex(bytes));
    }

    @Test
    public void Bytetohex_EmptyArray_EmptyString() {
        byte[] bytes = new byte[]{};
        String correct = "";
        Assert.assertEquals(correct, Util.bytetohex(bytes));
    }
}
