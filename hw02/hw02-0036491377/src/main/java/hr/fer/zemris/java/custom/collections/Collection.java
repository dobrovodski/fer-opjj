package hr.fer.zemris.java.custom.collections;

/**
 * The {@code Collection} class represents a general collection of objects.
 * 
 * @author matej
 *
 */
public class Collection {

	/**
	 * Default empty constructor.
	 */
	protected Collection() {

	}

	/**
	 * Returns {@code true} if collection contains no objects and {@code false}
	 * otherwise.
	 * 
	 * @return {@code true} if collection is empty, otherwise {@code false}
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the number of currently stored objects in this collection.
	 * 
	 * @return number of elements in collection
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object into this collection.
	 * 
	 * @param value object to add into collection
	 */
	public void add(Object value) {

	}

	/**
	 * Returns {@code true} if the collection contains the specified value, as
	 * determined by the {@code equals} method.
	 * 
	 * @param value object to find in collection
	 * @return {@code true} if collection contains {@code value}, otherwise
	 *         {@code false}
	 */
	boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns {@code true} if the collection contains given value as determined by
	 * the {@code equals} method and removes one occurrence of it.
	 * 
	 * @param value object to find in collection
	 * @return {@code true} if {@code value} has been found and removed, otherwise
	 *         {@code false}
	 */
	boolean remove(Object value) {
		return false;
	}

	/**
	 * Creates an array, fills it with the elements from the collection and returns
	 * it.
	 * 
	 * @return array with elements from collection
	 */
	Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls {@code processor.process(element)} for each element of this
	 * collection.
	 * 
	 * @param processor object whose {@code process} method is called on every element
	 *        of collection
	 */
	void forEach(Processor processor) {

	}

	/**
	 * Adds all elements from {@code other} collection into current collection
	 * without modifying {@code other}.
	 * 
	 * @param other collection from which the elements are added
	 */
	void addAll(Collection other) {
		class LocalProcessor extends Processor {
			public void process(Object value) {
				add(value);
			}
		}

		LocalProcessor processor = new LocalProcessor();
		other.forEach(processor);
	}

	/**
	 * Removes all elements from this collection.
	 */
	void clear() {

	}

}
