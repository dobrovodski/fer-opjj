package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for DictionaryTest.
 *
 * @author matej
 * @see <a href=
 * "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html">
 * Naming standards for unit tests </a>
 */
public class DictionaryTest {
	@Test
	public void Put_SimpleValue_GetReturnsIt() {
		double x = 485.33;
		Assert.assertEquals(x, new Vector2D(x, 0).getX(), EPS);
	}

	@Test
	public void Put_NullKey_ExceptionThrown() {
		double x = 485.33;
		Assert.assertEquals(x, new Vector2D(x, 0).getX(), EPS);
	}

	@Test
	public void Put_ExistentKey_ValueOverwritten() {
		double x = 485.33;
		Assert.assertEquals(x, new Vector2D(x, 0).getX(), EPS);
	}

	@Test
	public void Put_SimpleValue_GetReturnsIt() {
		double x = 485.33;
		Assert.assertEquals(x, new Vector2D(x, 0).getX(), EPS);
	}

	@Test
	public void Put_NullValue_NoException() {
		double x = 485.33;
		Assert.assertEquals(x, new Vector2D(x, 0).getX(), EPS);
	}
}
