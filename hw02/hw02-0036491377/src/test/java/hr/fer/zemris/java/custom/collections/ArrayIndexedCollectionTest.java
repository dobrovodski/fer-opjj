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
	public void Add_AddedOneValue_ValueAtIndexZero() {
		int value = 5;
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(value);
		Assert.assertEquals(value, collection.get(0));
	}

	@Test
	public void Add_AddedMultipleValues_ValuesAtCorrectIndices() {
		String[] values = { "A", "quick", "brown", "fox" };
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		for (int i = 0; i < values.length; i++) {
			Assert.assertEquals(values[i], collection.get(i));
		}
	}

	@Test
	public void Size_NothingAdded_Zero() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		Assert.assertEquals(0, collection.size());
	}

	@Test
	public void Size_SevenValuesAdded_Seven() {
		double[] values = { 1.0, 2.354, 1.0, 3.0, 9999.0, -28.8, -1000.999 };
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		for (double value : values) {
			collection.add(value);
		}

		Assert.assertEquals(values.length, collection.size());
	}

	@Test
	public void Contains_ExistentValue_True() {
		char[] values = { 'a', 'b', 'z' };
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		for (char value : values) {
			collection.add(value);
		}

		Assert.assertEquals(true, collection.contains(values[1]));
	}

	@Test
	public void Contains_NonExistentValue_False() {
		char[] values = { 'a', 'b', 'z' };
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		for (char value : values) {
			collection.add(value);
		}

		Assert.assertEquals(false, collection.contains('n'));
	}

	@Test
	public void Remove_ExistentValue_True() {
		String[] values = { "A", "quick", "brown", "fox" };
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		Assert.assertEquals(true, collection.remove(values[1]));
	}

	@Test
	public void Remove_NonExistentValue_False() {
		String[] values = { "A", "quick", "brown", "fox" };
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		Assert.assertEquals(false, collection.remove("jumps"));
	}

	@Test
	public void Remove_ExistentValue_SizeReducedByOne() {
		String[] values = { "A", "quick", "brown", "fox" };
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

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
		String[] values = { "A", "quick", "brown", "fox" };
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

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
		String[] values = { "A", "quick", "brown", "fox" };
		int index = 1;
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		collection.remove(index);

		Assert.assertEquals(false, collection.contains(values[index]));
	}

	@Test
	public void Remove_ExistentValue_ValuesShiftedByOne() {
		double[] values = { 1.0, 2.354, 1.0, 3.0, 9999.0, -28.8, -1000.999 };
		int index = 1;
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		for (double value : values) {
			collection.add(value);
		}

		collection.remove(index);

		for (int i = index; i < values.length - 1; i++) {
			Assert.assertEquals(values[i + 1], collection.get(i));
		}
	}

	@Test
	public void ToArray_ArrayOfIntegers_ArraysEqual() {
		int[] values = { 1, 2, 3, 8, -1, 8, 99, 0 };
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

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
		String[] values = { "A", "quick", "brown", "fox" };
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		collection.clear();

		Assert.assertEquals(0, collection.size());
	}

	@Test
	public void Insert_InsertValueAtIndex_ValueAtIndex() {
		String[] values = { "A", "quick", "brown", "fox" };
		String insertValue = "swift";
		int index = 1;
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}

		collection.insert(insertValue, index);

		Assert.assertEquals(insertValue, collection.get(index));
	}

	@Test
	public void Insert_InsertValueAtIndex_ValuesShifted() {
		String[] values = { "A", "quick", "brown", "fox" };
		String insertValue = "swift";
		int index = 1;
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

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
		String[] startValues = { "A", "quick", "brown", "fox" };
		String[] insertValues = { "jumps", "over", "the" };
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
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
	
	@Test
	public void IndexOf_ExistentValue_CorrectIndex() {
		String[] values = { "A", "quick", "brown", "fox" };
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		int index = 2;

		for (String value : values) {
			collection.add(value);
		}
		
		Assert.assertEquals(index, collection.indexOf(values[index]));
	}
	
	@Test
	public void IndexOf_NonExistentValue_NegativeOne() {
		String[] values = { "A", "quick", "brown", "fox" };
		String valueNotAdded = "jumps";
		ArrayIndexedCollection collection = new ArrayIndexedCollection();

		for (String value : values) {
			collection.add(value);
		}
		
		Assert.assertEquals(-1, collection.indexOf(valueNotAdded));
	}
	
	//TODO exception checks
	//TODO get

	@Test
	public void MethodName_StateUnderTest_ExpectedBehavior() {

	}
}
