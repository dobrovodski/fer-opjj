package hr.fer.zemris.java.custom.collections;

public class Collection {

	protected Collection() {

	}

	public boolean isEmpty() {
		return size() > 0;
	}

	public int size() {
		return 0;
	}

	public void add(Object value) {

	}

	boolean contains(Object value) {
		return false;
	}

	boolean remove(Object value) {
		return false;
	}

	Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	void forEach(Processor processor) {

	}

	void addAll(Collection other) {
		class LocalProcessor extends Processor {
			public void process(Object value) {
				add(value);
			}
		}

		LocalProcessor processor = new LocalProcessor();
		other.forEach(processor);
	}

	void clear() {

	}

}
