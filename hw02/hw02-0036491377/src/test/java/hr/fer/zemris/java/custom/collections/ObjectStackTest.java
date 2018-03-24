package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for ObjectStack.
 *
 * @author matej
 * @see <a href=
 * "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html">
 * Naming standards for unit tests </a>
 */
public class ObjectStackTest {

	@Test
	public void IsEmpty_NothingAdded_True() {
		ObjectStack stack = new ObjectStack();
		Assert.assertEquals(true, stack.isEmpty());
	}

	@Test
	public void IsEmpty_SomethingAdded_False() {
		ObjectStack stack = new ObjectStack();
		stack.push(5);
		Assert.assertEquals(false, stack.isEmpty());
	}

	@Test
	public void Size_NothingAdded_Zero() {
		ObjectStack stack = new ObjectStack();
		Assert.assertEquals(0, stack.size());
	}

	@Test
	public void Size_FourValuesAdded_Four() {
		String[] values = {"A", "quick", "brown", "fox"};
		ObjectStack stack = new ObjectStack();

		for (String value : values) {
			stack.push(value);
		}

		Assert.assertEquals(values.length, stack.size());
	}

	@Test
	public void Clear_StackContainsValues_SizeZero() {
		String[] values = {"A", "quick", "brown", "fox"};
		ObjectStack stack = new ObjectStack();

		for (String value : values) {
			stack.push(value);
		}

		stack.clear();

		Assert.assertEquals(0, stack.size());
	}

	@Test
	public void Pop_SeveralValuesAdded_EqualsLastOneAdded() {
		String[] values = {"A", "quick", "brown", "fox"};
		ObjectStack stack = new ObjectStack();

		for (String value : values) {
			stack.push(value);
		}

		Assert.assertEquals(values[values.length - 1], stack.pop());
	}

	@Test(expected = EmptyStackException.class)
	public void Pop_NothingAdded_ExceptionThrown() {
		ObjectStack stack = new ObjectStack();
		stack.pop();
	}

	@Test
	public void Pop_SeveralValuesAdded_SizeDecreasedByOne() {
		String[] values = {"A", "quick", "brown", "fox"};
		ObjectStack stack = new ObjectStack();

		for (String value : values) {
			stack.push(value);
		}

		stack.pop();
		Assert.assertEquals(values.length - 1, stack.size());
	}

	@Test
	public void Peek_SeveralValuesAdded_EqualsLastOneAdded() {
		String[] values = {"A", "quick", "brown", "fox"};
		ObjectStack stack = new ObjectStack();

		for (String value : values) {
			stack.push(value);
		}

		Assert.assertEquals(values[values.length - 1], stack.peek());
	}

	@Test
	public void Peek_SeveralValuesAdded_SizeUnchanged() {
		String[] values = {"A", "quick", "brown", "fox"};
		ObjectStack stack = new ObjectStack();

		for (String value : values) {
			stack.push(value);
		}

		stack.peek();
		Assert.assertEquals(values.length, stack.size());
	}

	@Test(expected = EmptyStackException.class)
	public void Peek_NothingAdded_ExceptionThrown() {
		ObjectStack stack = new ObjectStack();
		stack.peek();
	}

}
