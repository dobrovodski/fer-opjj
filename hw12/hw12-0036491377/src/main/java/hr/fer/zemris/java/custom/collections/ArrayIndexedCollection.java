package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * The {@code ArrayIndexedCollection} class represents a resizable array-backed collection of objects.
 *
 * @author matej
 */
public class ArrayIndexedCollection extends Collection {

    private final static int DEFAULT_INITIAL_CAPACITY = 16;

    private int size;
    private int capacity;
    private Object[] elements;

    /**
     * Default constructor for the {@code ArrayIndexedCollection} class. Initializes the capacity to 16.
     */
    public ArrayIndexedCollection() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Creates an instance of {@code ArrayIndexedCollection} and initializes the capacity to the given value.
     *
     * @param initialCapacity amount that the capacity of the collection should be set to
     *
     * @throws IllegalArgumentException if initial capacity is less than 1
     */
    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Initial capacity cannot be less than 1.");
        }
        capacity = initialCapacity;
        elements = new Object[initialCapacity];
    }

    /**
     * Creates an instance of {@code ArrayIndexedCollection} using elements of {@code other} collection and sets the
     * initial capacity to 16.
     *
     * @param other other collection whose elements to put into this collection
     *
     * @throws NullPointerException if the other collection is null
     */
    public ArrayIndexedCollection(Collection other) {
        this(other, DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Creates an instance of {@code ArrayIndexedCollection} using elements of {@code other} collection and sets the
     * initial capacity either to {@code initialCapacity} or {@code other}'s capacity if it is higher.
     *
     * @param other other collection whose elements to put into this collection
     * @param initialCapacity amount that the capacity should be initialized to
     *
     * @throws NullPointerException if the other collection is null
     * @throws IllegalArgumentException if initial capacity is less than 1
     */
    public ArrayIndexedCollection(Collection other, int initialCapacity) {
        if (other == null) {
            throw new NullPointerException("Cannot pass null as other collection.");
        }

        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Initial capacity cannot be less than 1.");
        }

        // Set capacity to larger
        capacity = other.size() > initialCapacity ? other.size() : initialCapacity;
        elements = new Object[capacity];
        addAll(other);
    }

    /**
     * {@inheritDoc}
     *
     * @throws NullPointerException if the value to add is null
     */
    @Override
    public void add(Object value) {
        if (value == null) {
            throw new NullPointerException("Cannot add null to collection.");
        }

        // Average complexity is O(1), unless reallocation happens, in which case the
        // complexity is O(n).
        if (size + 1 > capacity) {
            capacity *= 2;
            elements = Arrays.copyOf(elements, capacity);
        }

        elements[size] = value;
        size++;
    }

    /**
     * Inserts given object at specified position in the collection.
     *
     * @param value element to insert into collection
     * @param position index at which it should be inserted
     *
     * @throws NullPointerException if the value is null
     * @throws IndexOutOfBoundsException if the position is either negative or more than {@code size}
     */
    public void insert(Object value, int position) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException(
                    "Cannot insert element if position is negative or more than size. Given position: " + position);
        }

        if (value == null) {
            throw new NullPointerException("Cannot insert null into collection.");
        }

        // Average complexity is O(n) because n elements have to be shifted on average
        if (size + 1 > capacity) {
            capacity *= 2;
            elements = Arrays.copyOf(elements, capacity);
        }

        System.arraycopy(elements, position, elements, position + 1, size - position);

        elements[position] = value;
        size++;

    }

    /**
     * Returns the object at given index in the collection.
     *
     * @param index position of the object in the collection
     *
     * @return object found at specified position
     *
     * @throws IndexOutOfBoundsException if the position is either negative or more than {@code size - 1}
     */
    public Object get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException(
                    "Cannot get element if index is negative or more than size-1. Given index: " + index);
        }

        return elements[index];
    }

    /**
     * Removes element at {@code index} in the collection. Shifts all elements to the right of {@code index} by one
     * position to the left.
     *
     * @param index position from which to remove element
     *
     * @throws IndexOutOfBoundsException if the index is negative or more than size-1.
     */
    public void remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException(
                    "Cannot remove element if index is negative or more than size-1. Index: " + index);
        }

        // Shift elements into the empty slot
        System.arraycopy(elements, index + 1, elements, index, size - index);

        elements[size] = null;
        size--;
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

    @Override
    public void clear() {
        // Make eligible for GC
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }

        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) > -1;
    }

    /**
     * Finds the given element in the collection and returns its position if found, -1 otherwise.
     *
     * @param value element to be found
     *
     * @return position of element in collection if found, -1 otherwise
     */
    public int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] toArray() {
        Object[] newElements = new Object[size];

        System.arraycopy(elements, 0, newElements, 0, size);

        return newElements;
    }

    @Override
    public void forEach(Processor processor) {
        for (int i = 0; i < size; i++) {
            processor.process(elements[i]);
        }
    }

}
