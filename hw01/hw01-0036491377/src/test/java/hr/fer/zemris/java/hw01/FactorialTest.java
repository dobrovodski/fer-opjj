package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author matej
 *
 */
public class FactorialTest {

	@Test
	public void zeroFactorial() {
		Assert.assertEquals(1, Factorial.factorial(0));
	}

	@Test
	public void oneFactorial() {
		Assert.assertEquals(1, Factorial.factorial(1));
	}

	@Test
	public void twentyFactorial() {
		long twentyFact = 2432902008176640000L;
		Assert.assertEquals(twentyFact, Factorial.factorial(20));
	}

	@Test
	public void fiveFactorial() {
		Assert.assertEquals(120, Factorial.factorial(5));
	}

	@Test
	public void sevenFactorial() {
		Assert.assertEquals(5040, Factorial.factorial(7));
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeFactorial() {
		Factorial.factorial(-1);
	}
}
