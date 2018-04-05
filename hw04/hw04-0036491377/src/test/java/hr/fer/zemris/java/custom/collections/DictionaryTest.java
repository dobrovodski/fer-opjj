package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for Dictionary.
 *
 * @author matej
 * @see <a href=
 * "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html">
 * Naming standards for unit tests </a>
 */
public class DictionaryTest {
	@Test
	public void Put_SimpleValue_GetReturnsIt() {
		String key = "a";
		String value = "b";
		Dictionary d = new Dictionary();
		d.put(key, value);
		Assert.assertEquals(value, d.get(key));
	}

	@Test(expected = NullPointerException.class)
	public void Put_NullKey_ExceptionThrown() {
		Dictionary d = new Dictionary();
		d.put(null, 3);
	}

	@Test
	public void Put_ExistentKey_ValueOverwritten() {
		Dictionary d = new Dictionary();
		String key = "key";
		int value1 = 10;
		int value2 = 30;
		d.put(key, value1);
		Assert.assertEquals(value1, d.get(key));
		d.put(key, value2);
		Assert.assertEquals(value2, d.get(key));
	}

	@Test
	public void Put_NullValue_NoException() {
		Dictionary d = new Dictionary();
		int key = 0;
		d.put(key, null);
	}

	@Test
	public void Get_SimpleValue_Returned() {
		Dictionary d = new Dictionary();
		double key = 3.3;
		double value = -10;
		d.put(key, value);
		Assert.assertEquals(value, (double) d.get(key), 1E-6);
	}

	@Test
	public void Get_NonInsertedKey_ReturnedNull() {
		Dictionary d = new Dictionary();
		Assert.assertEquals(null, d.get("a"));
	}

	@Test(expected = NullPointerException.class)
	public void Get_KeyNull_ExceptionThrown() {
		Dictionary d = new Dictionary();
		d.get(null);
	}

	@Test
	public void IsEmpty_NothingPut_True() {
		Dictionary d = new Dictionary();
		Assert.assertEquals(true, d.isEmpty());
	}

	@Test
	public void IsEmpty_OnePut_False() {
		Dictionary d = new Dictionary();
		d.put("a", "b");
		Assert.assertEquals(false, d.isEmpty());
	}

	@Test
	public void Size_NothingPut_Zero() {
		Dictionary d = new Dictionary();
		Assert.assertEquals(0, d.size());
	}

	@Test
	public void Size_OnePut_One() {
		Dictionary d = new Dictionary();
		d.put("a", "b");
		Assert.assertEquals(1, d.size());
	}

	@Test
	public void Clear_ValuesPut_SizeZero() {
		Dictionary d = new Dictionary();
		d.put("a", "b");
		d.put("c", "b");
		d.put("d", "b");
		d.clear();
		Assert.assertEquals(0, d.size());
	}

	@Test
	public void Clear_ValuesInserted_ValuesNull() {
		Dictionary d = new Dictionary();
		d.put("a", "b");
		d.put("c", "b");
		Assert.assertEquals("b", d.get("a"));
		d.clear();
		Assert.assertEquals(null, d.get("a"));
	}
}
