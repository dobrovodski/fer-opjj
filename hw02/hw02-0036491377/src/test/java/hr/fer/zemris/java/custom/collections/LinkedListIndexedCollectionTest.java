package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for LinkedListIndexedCollection.
 *
 * @author matej
 * @see <a href=
 * "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html">
 * Naming standards for unit tests </a>
 */
public class LinkedListIndexedCollectionTest {

	@Test
	public void Constructor_OtherCollectionProvided_CollectionAdded() {
		String[] values = {"A", "quick", "brown", "fox"};
		ArrayIndexedCollection other = new ArrayIndexedCollection();

		for (String value : values) {
			other.add(value);
		}

		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(other);

		for (String value : values) {
			assertEquals(true, collection.contains(value));
		}

		for (int i = 0; i < values.length; i++) {
			assertEquals(i, collection.indexOf(values[i]));
		}
	}

	@Test
	public void Add_AddedOneValue_ValueAtIndexZero() {
		int value = 5;
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(value);
		Assert.assertEquals(value, collection.get(0));
	}

	@Test
	public void Add_AddedMultipleValues_ValuesAtCorrectIndices() {
		String[] values = {"A", "quick", "brown", "fox"};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		for (int i = 0; i < values.length; i++) {
			Assert.assertEquals(values[i], collection.get(i));
		}
	}

	@Test(expected = NullPointerException.class)
	public void Add_AddNull_ExceptionThrown() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.add(null);
	}

	@Test
	public void Get_AddedValue_ValueRetrieved() {
		String[] values = {"A", "quick", "brown", "fox"};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		int index = 2;

		for (String value : values) {
			collection.add(value);
		}

		Assert.assertEquals(values[index], collection.get(index));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void Get_IndexGreaterThanSize_ExceptionThrown() {
		String[] values = {"A", "quick", "brown", "fox"};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		int index = values.length + 1;

		for (String value : values) {
			collection.add(value);
		}

		collection.get(index);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void Get_IndexLessThanZero_ExceptionThrown() {
		String[] values = {"A", "quick", "brown", "fox"};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		int index = -1;

		for (String value : values) {
			collection.add(value);
		}

		collection.get(index);
	}

	@Test
	public void Size_NothingAdded_Zero() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Assert.assertEquals(0, collection.size());
	}

	@Test
	public void Size_SevenValuesAdded_Seven() {
		double[] values = {1.0, 2.354, 1.0, 3.0, 9999.0, -28.8, -1000.999};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (double value : values) {
			collection.add(value);
		}

		Assert.assertEquals(values.length, collection.size());
	}

	@Test
	public void Contains_ExistentValue_True() {
		char[] values = {'a', 'b', 'z'};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (char value : values) {
			collection.add(value);
		}

		Assert.assertEquals(true, collection.contains(values[1]));
	}

	@Test
	public void Contains_NonExistentValue_False() {
		char[] values = {'a', 'b', 'z'};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (char value : values) {
			collection.add(value);
		}

		Assert.assertEquals(false, collection.contains('n'));
	}

	@Test
	public void Contains_ContainsNull_False() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		assertEquals(false, collection.contains(null));
	}

	@Test
	public void Remove_ExistentValue_True() {
		String[] values = {"A", "quick", "brown", "fox"};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		Assert.assertEquals(true, collection.remove(values[1]));
	}

	@Test
	public void Remove_NonExistentValue_False() {
		String[] values = {"A", "quick", "brown", "fox"};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		Assert.assertEquals(false, collection.remove("jumps"));
	}

	@Test
	public void Remove_ExistentValue_SizeReducedByOne() {
		String[] values = {"A", "quick", "brown", "fox"};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		int startSize = collection.size();
		collection.remove(values[1]);
		int endSize = collection.size();

		Assert.assertEquals(1, startSize - endSize);
	}

	@Test
	public void Remove_NonExistentValue_SizeStaysSame() {
		String[] values = {"A", "quick", "brown", "fox"};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		int startSize = collection.size();
		collection.remove("jumps");
		int endSize = collection.size();

		Assert.assertEquals(0, startSize - endSize);
	}

	@Test
	public void Remove_OneCopyOfValueInCollection_ContainsReturnsFalse() {
		String[] values = {"A", "quick", "brown", "fox"};
		int index = 1;
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		collection.remove(index);

		Assert.assertEquals(false, collection.contains(values[index]));
	}

	@Test
	public void Remove_ExistentValue_ValuesShiftedByOne() {
		double[] values = {1.0, 2.354, 1.0, 3.0, 9999.0, -28.8, -1000.999};
		int index = 1;
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (double value : values) {
			collection.add(value);
		}

		collection.remove(index);

		for (int i = index; i < values.length - 1; i++) {
			Assert.assertEquals(values[i + 1], collection.get(i));
		}
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void Remove_IndexGreaterThanSize_ExceptionThrown() {
		double[] values = {1.0, 2.354, 1.0, 3.0, 9999.0, -28.8, -1000.999};
		int index = values.length + 1;
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (double value : values) {
			collection.add(value);
		}

		collection.remove(index);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void Remove_IndexLessThanZero_ExceptionThrown() {
		double[] values = {1.0, 2.354, 1.0, 3.0, 9999.0, -28.8, -1000.999};
		int index = -1;
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (double value : values) {
			collection.add(value);
		}

		collection.remove(index);
	}

	@Test
	public void ToArray_ArrayOfIntegers_ArraysEqual() {
		int[] values = {1, 2, 3, 8, -1, 8, 99, 0};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (int value : values) {
			collection.add(value);
		}

		Object[] toArrayValues = collection.toArray();

		for (int i = 0; i < values.length; i++) {
			Assert.assertEquals(values[i], toArrayValues[i]);
		}
	}

	@Test
	public void Clear_ArrayOfValues_SizeZero() {
		String[] values = {"A", "quick", "brown", "fox"};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		collection.clear();

		Assert.assertEquals(0, collection.size());
	}

	@Test
	public void Insert_InsertValueAtIndex_ValueAtIndex() {
		String[] values = {"A", "quick", "brown", "fox"};
		String insertValue = "swift";
		int index = 1;
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		collection.insert(insertValue, index);

		Assert.assertEquals(insertValue, collection.get(index));
	}

	@Test
	public void Insert_InsertValueAtIndex_ValuesShifted() {
		String[] values = {"A", "quick", "brown", "fox"};
		String insertValue = "swift";
		int index = 1;
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		collection.insert(insertValue, index);

		for (int i = index; i < values.length; i++) {
			Assert.assertEquals(values[i], collection.get(i + 1));
		}
	}

	@Test
	public void Insert_InsertMultipleValues_SizeIncreased() {
		String[] startValues = {"A", "quick", "brown", "fox"};
		String[] insertValues = {"jumps", "over", "the"};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		int index = 2;

		for (String value : startValues) {
			collection.add(value);
		}
		int sizeBeforeInsert = collection.size();

		for (String value : insertValues) {
			collection.insert(value, index);
		}
		int sizeAfterInsert = collection.size();

		Assert.assertEquals(insertValues.length, sizeAfterInsert - sizeBeforeInsert);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void Insert_IndexGreaterThanSize_ExceptionThrown() {
		String[] values = {"A", "quick", "brown", "fox"};
		String insertValue = "swift";
		int index = values.length + 1;
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		collection.insert(insertValue, index);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void Insert_IndexLessThanZero_ExceptionThrown() {
		String[] values = {"A", "quick", "brown", "fox"};
		String insertValue = "swift";
		int index = -1;
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		collection.insert(insertValue, index);
	}

	@Test
	public void IndexOf_ExistentValue_CorrectIndex() {
		String[] values = {"A", "quick", "brown", "fox"};
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		int index = 2;

		for (String value : values) {
			collection.add(value);
		}

		Assert.assertEquals(index, collection.indexOf(values[index]));
	}

	@Test
	public void IndexOf_NonExistentValue_NegativeOne() {
		String[] values = {"A", "quick", "brown", "fox"};
		String valueNotAdded = "jumps";
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		Assert.assertEquals(-1, collection.indexOf(valueNotAdded));
	}

	@Test
	public void IsEmpty_NothingAdded_True() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		Assert.assertEquals(true, c.isEmpty());
	}

	@Test
	public void IsEmpty_SomethingAdded_False() {
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		c.add(5);
		Assert.assertEquals(false, c.isEmpty());
	}

	@Test
	public void ForEach_CreateStringFromCollection_CorrectString() {
		// Dodgy and contrived test but hey, it works. 
		// Gladly looking for a better way to test forEach.

		String[] values = {"A", "quick", "brown", "fox"};
		String str = String.join(" ", values);
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		class P extends Processor {
			public String valuesString = "";

			public void process(Object o) {
				valuesString += o.toString() + " ";
			}
		}

		P p = new P();
		collection.forEach(p);

		Assert.assertEquals(str, p.valuesString.trim());
	}

}
