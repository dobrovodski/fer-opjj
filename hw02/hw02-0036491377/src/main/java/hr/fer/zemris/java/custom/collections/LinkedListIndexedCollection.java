package hr.fer.zemris.java.custom.collections;

/**
 * The {@code LinkedListIndexedCollection} class represents a list-backed
 * collection of objects.
 * 
 * @author matej
 *
 */
public class LinkedListIndexedCollection extends Collection {

	private int size;
	private ListNode first;
	private ListNode last;

	/**
	 * Private class which represents a node in a link list with {@code previous}
	 * and {@code next} points to other nodes, and {@code value} which holds the
	 * value of the node.
	 * 
	 * @author matej
	 *
	 */
	private static class ListNode {
		public ListNode previous;
		public ListNode next;
		public Object value;

		@Override
		public String toString() {
			return value.toString();
		}
	}

	/**
	 * Default constructor for the {@code LinkedListIndexedCollection} class.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Creates an instance of {@code ArrayIndexedCollection} using elements of
	 * {@code other} collection and sets the initial capacity to 16.
	 * 
	 * @param other
	 *            other collection whose elements to put into this collection
	 * @throws NullPointerException
	 *             if the other collection is null
	 */
	public LinkedListIndexedCollection(Collection other) {
		if (other == null) {
			throw new NullPointerException("Cannot pass null as other collection.");
		}

		addAll(other);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Cannot insert null into collection.");
		}

		ListNode node = new ListNode();
		node.value = value;
		node.previous = last;

		if (first == null) {
			first = node;
			last = node;
		} else {
			last.next = node;
			last = node;
		}

		size++;
	}

	/**
	 * Returns the object at given index in the collection.
	 * 
	 * @param index
	 *            position of the object in the collection
	 * @return object found at specified position
	 * @throws IndexOutOfBoundsException
	 *             if the position is either negative or more than the size of the
	 *             collection
	 */
	Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException(
					"Cannot get element if index is negative or more than size-1. Given index: " + index);
		}

		ListNode current;

		if (index < size / 2) {
			current = first;
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
		} else {
			current = last;
			for (int i = size; i > index + 1; i--) {
				current = current.previous;
			}
		}

		return current.value;
	}

	/**
	 * Inserts given object at specified position in the collection.
	 * 
	 * @param value
	 *            element to insert into collection
	 * @param position
	 *            index at which it should be inserted
	 * @throws NullPointerException
	 *             if the value is null
	 * @throws IndexOutOfBoundsException
	 *             if the position is either negative or more than the size of the
	 *             collection
	 */
	void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException(
					"Cannot insert element if position is negative or more than size. Given position: " + position);
		}

		if (value == null) {
			throw new NullPointerException("Cannot insert null into collection.");
		}

		ListNode current = first;

		// Average complexity is O(n) because it has to move to the position via linked
		// elements
		for (int i = 0; i < position - 1; i++) {
			current = current.next;
		}
		ListNode next = current.next;

		ListNode newNode = new ListNode();
		newNode.value = value;

		newNode.previous = current;
		newNode.next = next;

		current.next = newNode;
		next.previous = newNode;

		size++;
	}

	/**
	 * Finds the given element in the collection and returns its position if found,
	 * -1 otherwise.
	 * 
	 * @param value
	 *            element to be found
	 * @return position of element in collection if found, -1 otherwise
	 */
	int indexOf(Object value) {
		int index = 0;
		ListNode current = first;

		while (current != null) {
			if (current.value.equals(value)) {
				return index;
			}

			current = current.next;
			index++;
		}

		return -1;
	}

	/**
	 * Removes element at {@code index} in the collection.
	 * 
	 * @param index
	 *            position from which to remove element
	 * @throws IndexOutOfBoundsException
	 *             if the index is negative or more than size-1.
	 */
	void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException(
					"Cannot remove element if index is negative or more than size-1. Index: " + index);
		}

		ListNode current = first;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		ListNode previousNode = current.previous;
		ListNode nextNode = current.next;
		previousNode.next = nextNode;
		nextNode.previous = previousNode;
		current = null;

		size--;
	}

	@Override
	boolean contains(Object value) {
		return indexOf(value) > -1;
	}

	@Override
	boolean remove(Object value) {
		ListNode current = first;
		for (int i = 0; i < size; i++) {
			if (current.value.equals(value)) {
				remove(i);
				return true;
			}
			current = current.next;
		}

		return false;
	}

	@Override
	Object[] toArray() {
		Object[] elements = new Object[size];
		ListNode current = first;
		for (int i = 0; i < size; i++) {
			elements[i] = current.value;
			current = current.next;
		}

		return elements;
	}

	@Override
	void forEach(Processor processor) {
		ListNode node = first;

		for (int i = 0; i < size; i++) {
			processor.process(node);
			node = node.next;
		}
	}

	@Override
	void clear() {
		first = null;
		last = null;
		size = 0;
	}
}
