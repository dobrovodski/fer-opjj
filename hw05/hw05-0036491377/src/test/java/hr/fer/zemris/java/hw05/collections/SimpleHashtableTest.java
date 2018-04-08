package hr.fer.zemris.java.hw05.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for SimpleHashtable.
 *
 * @author matej
 * @see <a href=
 * "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html">
 * Naming standards for unit tests </a>
 */
public class SimpleHashtableTest {
	@Test(expected = IllegalArgumentException.class)
	public void SimpleHashtable_InitialCapacityLessThanOne_ExceptionThrown() {
		int initialCapacity = 0;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);
	}

	@Test
	public void Put_SimpleValues_GetReturnsValue() {
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 4;
		int hashTableSize = 1;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(hashTableSize);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		for (int i = 0; i < numberOfEntries; i++) {
			Assert.assertEquals(valueTemplate + i, ht.get(keyTemplate + i));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void Put_KeyNull_ExceptionThrown() {
		String key = null;
		String value = "value";
		SimpleHashtable<String, String> ht = new SimpleHashtable<>();

		ht.put(key, value);
	}

	@Test
	public void Put_OverwriteValue_ValueOverwrittenSizeSame() {
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 4;
		int hashTableSize = 10;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(hashTableSize);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		String key = keyTemplate + (numberOfEntries - 1);
		String value = valueTemplate + (numberOfEntries - 1);
		String newValue = valueTemplate + (numberOfEntries + 1);
		int size = ht.size();

		Assert.assertEquals(value, ht.get(key));
		ht.put(key, newValue);
		Assert.assertEquals(newValue, ht.get(key));
		Assert.assertEquals(size, ht.size());
	}

	@Test
	public void Get_KeyNull_ReturnNull() {
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 4;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>();

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		Assert.assertEquals(null, ht.get(null));
	}

	@Test
	public void Get_NonExistentValueNonEmptyHashSlot_ReturnNull() {
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 4;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(1);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		Assert.assertEquals(null, ht.get(keyTemplate + numberOfEntries));
	}

	@Test
	public void Get_NonExistentValueEmptyHashSlot_ReturnNull() {
		String key = "key";
		SimpleHashtable<String, String> ht = new SimpleHashtable<>();

		Assert.assertEquals(null, ht.get(key));
	}

	@Test
	public void Get_DifferentTypeThanTable_ReturnNull() {
		int key = 10;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>();

		Assert.assertEquals(null, ht.get(key));
	}

	@Test
	public void Size_NothingAdded_Zero() {
		int initialCapacity = 20;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		Assert.assertEquals(0, ht.size());
	}

	@Test
	public void Size_NumValuesAdded_NumReturned() {
		int initialCapacity = 20;
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 10;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		Assert.assertEquals(numberOfEntries, ht.size());
	}

	@Test
	public void ContainsKey_NullKey_False() {
		int initialCapacity = 20;
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 10;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		Assert.assertEquals(false, ht.containsKey(null));
	}

	@Test
	public void ContainsKey_NonExistentKeyEmptyHashSlot_False() {
		String key = "key";
		int initialCapacity = 20;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		Assert.assertEquals(false, ht.containsKey(key));
	}

	@Test
	public void ContainsKey_NonExistentKeyNonEmptyHashSlot_False() {
		int initialCapacity = 1;
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 3;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		Assert.assertEquals(false, ht.containsKey(keyTemplate+numberOfEntries));
	}

	@Test
	public void ContainsKey_ExistentKey_True() {
		int initialCapacity = 10;
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 4;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		Assert.assertEquals(true, ht.containsKey(keyTemplate+(numberOfEntries - 1)));
	}

	@Test
	public void ContainsKey_DifferentTypeThanTable_False() {
		int key = 10;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>();

		Assert.assertEquals(false, ht.containsKey(key));
	}

	@Test
	public void IsEmpty_NothingAdded_True() {
		int initialCapacity = 10;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		Assert.assertEquals(true, ht.isEmpty());
	}

	@Test
	public void IsEmpty_NumValuesAdded_False() {
		int initialCapacity = 10;
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 5;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		Assert.assertEquals(false, ht.isEmpty());
	}

	@Test
	public void Remove_KeyNull_NoExceptionThrown() {
		int initialCapacity = 10;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);
		ht.remove(null);
	}

	@Test
	public void Remove_ExistentValue_SizeReducedByOne() {
		int initialCapacity = 10;
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 5;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		int originalSize = ht.size();
		ht.remove(keyTemplate + (numberOfEntries - 1));
		Assert.assertEquals(originalSize - 1, ht.size());
	}

	@Test
	public void Remove_ExistentValue_GetReturnsNull() {
		int initialCapacity = 10;
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 5;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		String key = keyTemplate + (numberOfEntries - 1);
		ht.remove(key);
		Assert.assertEquals(null, ht.get(key));
	}

	@Test
	public void Remove_ExistentValueFirstInSlot_ContainsKeyReturnsFalse() {
		int initialCapacity = 10;
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 5;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		String key = keyTemplate + (numberOfEntries - 1);
		ht.remove(key);
		Assert.assertEquals(false, ht.containsKey(key));
	}

	@Test
	public void Remove_ExistentValueNotFirstInSlot_ContainsKeyReturnsFalse() {
		int initialCapacity = 1;
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 5;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		String key = keyTemplate + (numberOfEntries - 1);
		ht.remove(key);
		Assert.assertEquals(false, ht.containsKey(key));
	}

	@Test
	public void Remove_NonExistentValue_NoExceptionThrown() {
		int initialCapacity = 10;
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 5;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		String key = keyTemplate + (numberOfEntries + 1);
		ht.remove(key);
	}

	@Test
	public void ContainsValue_ExistentValue_True() {
		int initialCapacity = 10;
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 5;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		String value = valueTemplate + (numberOfEntries - 1);
		Assert.assertEquals(true, ht.containsValue(value));
	}

	@Test
	public void ContainsValue_NonExistentValue_False() {
		int initialCapacity = 10;
		String keyTemplate = "key";
		String valueTemplate = "value";
		int numberOfEntries = 5;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>(initialCapacity);

		for (int i = 0; i < numberOfEntries; i++) {
			ht.put(keyTemplate + i, valueTemplate + i);
		}

		String value = valueTemplate + (numberOfEntries + 1);
		Assert.assertEquals(false, ht.containsValue(value));
	}

	@Test
	public void ContainsValue_DifferentTypeThanTable_False() {
		int key = 10;
		SimpleHashtable<String, String> ht = new SimpleHashtable<>();

		Assert.assertEquals(false, ht.containsValue(key));
	}

	@Test
	public void ToString_PreComputedString_CorrectReturn() {
		int initialCapacity = 2;
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>(initialCapacity);
		ht.put("Ivana", 5);
		ht.put("Jasna", 2);
		ht.put("Kristina", 5);
		ht.put("Ante", 2);
		String correct = "[Ante=2, Ivana=5, Jasna=2, Kristina=5]";

		Assert.assertEquals(correct, ht.toString());
	}
}
