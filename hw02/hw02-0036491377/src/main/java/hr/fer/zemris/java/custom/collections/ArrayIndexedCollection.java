package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

//TODO: doc
//TODO: exception messages

public class ArrayIndexedCollection extends Collection {
	private int size;
	private int capacity;
	private Object[] elements;

	public ArrayIndexedCollection() {
		this(16);
	}

	public ArrayIndexedCollection(int initialCapacity) {
		capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}

	public ArrayIndexedCollection(Collection other) {
		this(other, 16);
	}

	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (other == null) {
			throw new NullPointerException();
		}

		capacity = other.size() > initialCapacity ? other.size() : initialCapacity;
		elements = new Object[capacity];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}

		if (size + 1 >= capacity) {
			capacity *= 2;
			elements = Arrays.copyOf(elements, capacity);
		}

		elements[size + 1] = value;
		size++;
	}

	@Override
	public boolean contains(Object value) {
		for (Object element : elements) {
			if (value.equals(element)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean remove(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				remove(i);
				return true;
			}
		}
		return false;
	}

	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		for (int i = index; i < size; i++) {
			elements[i] = elements[i + 1];
		}
		
		elements[size] = null;
		size--;
	}

	@Override
	public Object[] toArray() {
		Object[] newElements = new Object[size];

		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}

		return newElements;
	}

	@Override
	public void forEach(Processor processor) {
		for (Object element : elements) {
			processor.process(element);
		}
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}

		size = 0;
	}

	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}

		return elements[index];
	}

	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}

		if (value == null) {
			throw new NullPointerException();
		}

		if (size + 1 >= capacity) {
			capacity *= 2;
			elements = Arrays.copyOf(elements, capacity);
		}

		for (int i = size - 1; i > position; i--) {
			elements[i + 1] = elements[i];
		}
		
		elements[position] = value;

	}

	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}

		return -1;
	}

}
