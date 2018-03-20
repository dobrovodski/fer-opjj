package hr.fer.zemris.java.custom.collections;


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
public class ArrayIndexedCollectionTest {

	@Test
	public void Size_NothingAdded_Zero() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		Assert.assertEquals(0, collection.size());
	}
}
