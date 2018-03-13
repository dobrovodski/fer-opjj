package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for Factorial.
 * 
 * @see <a href=
 *      "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html">
 *      Naming standards for unit tests </a>
 * @author matej
 *
 */
public class FactorialTest {

	@Test
	public void Factorial_ZeroFactorial_Calculated() {
		Assert.assertEquals(1, Factorial.factorial(0));
	}

	@Test
	public void Factorial_OneFactorial_Calculated() {
		Assert.assertEquals(1, Factorial.factorial(1));
	}

	@Test
	public void Factorial_TwentyFactorial_Calculated() {
		long twentyFact = 2432902008176640000L;
		Assert.assertEquals(twentyFact, Factorial.factorial(20));
	}

	@Test
	public void Factorial_FiveFactorial_Calculated() {
		Assert.assertEquals(120, Factorial.factorial(5));
	}

	@Test(expected = IllegalArgumentException.class)
	public void Factorial_NegativeFactorial_ExceptionThrown() {
		Factorial.factorial(-1);
	}
}
