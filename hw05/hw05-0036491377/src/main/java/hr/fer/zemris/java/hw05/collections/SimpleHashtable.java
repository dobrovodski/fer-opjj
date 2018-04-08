package hr.fer.zemris.java.hw05.collections;

public class SimpleHashtable<K, V> {
	private static final int DEFAULT_CAPACITY = 16;

	public static class TableEntry<K, V> {
		private final K key;
		private V value;
		private TableEntry<K, V> next;

		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public String toString() {
			return this.key.toString() + "=" + this.value.toString();
		}
	}

	private int capacity;
	private TableEntry<K, V>[] table;
	private int size;

	public SimpleHashtable() {
		this.capacity = DEFAULT_CAPACITY;
		this.table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}

	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Cannot set initial capacity to less than 1.");
		}

		int capacity = 1;
		while (capacity < initialCapacity) {
			capacity *= 2;
		}

		this.capacity = capacity;
		this.table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}

	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Cannot insert item with the key null into hashtable.");
		}

		int hash = getHash(key);

		if (table[hash] == null) {
			table[hash] = new TableEntry<>(key, value, null);
			size++;
			return;
		}

		if (!this.containsKey(key)) {
			TableEntry<K, V> currentEntry = table[hash];
			while (currentEntry.next != null) {
				currentEntry = currentEntry.next;
			}

			currentEntry.next = new TableEntry<>(key, value, null);
			size++;
			return;
		}

		TableEntry<K, V> currentEntry = table[hash];
		while (currentEntry != null) {
			if (currentEntry.getKey().equals(key)) {
				currentEntry.setValue(value);
			}

			currentEntry = currentEntry.next;
		}
	}

	public V get(Object key) {
		if (key == null) {
			return null;
		}

		int hash = getHash(key);

		if (table[hash] == null) {
			return null;
		}

		TableEntry<K, V> currentEntry = table[hash];
		while (currentEntry != null) {
			if (currentEntry.getKey().equals(key)) {
				return currentEntry.getValue();
			}

			currentEntry = currentEntry.next;
		}

		return null;
	}

	public int size() {
		return size;
	}

	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}

		int hash = getHash(key);
		if (table[hash] == null) {
			return false;
		}

		TableEntry<K, V> currentEntry = table[hash];
		while (currentEntry != null) {
			if (currentEntry.getKey().equals(key)) {
				return true;
			}

			currentEntry = currentEntry.next;
		}

		return false;
	}

	public void remove(Object key) {
		if (key == null) {
			return;
		}

		int hash = getHash(key);
		if (table[hash] == null) {
			return;
		}

		TableEntry<K, V> currentEntry = table[hash];
		if (currentEntry.getKey().equals(key)) {
			table[hash] = currentEntry.next;
			size--;
			return;
		}

		TableEntry<K, V> nextEntry = table[hash];
		while (nextEntry != null) {
			if (nextEntry.getKey().equals(key)) {
				currentEntry.next = nextEntry.next;
				size--;
				return;
			}
			currentEntry = nextEntry;
			nextEntry = nextEntry.next;
		}
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean containsValue(Object value) {
		for (TableEntry e : table) {
			while (e != null) {
				if (e.getValue().equals(value)) {
					return true;
				}
				e = e.next;
			}
		}

		return false;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int i = 0; i < capacity; i++) {
			TableEntry current = table[i];
			while (current != null) {
				sb.append(current.toString());

				// Dont Append ', ' if you are in the final slot and its the last entry in the slot
				if (!(current.next == null && i == capacity - 1)) {
					sb.append(", ");
				}

				current = current.next;
			}
		}
		sb.append(']');

		return sb.toString();
	}

	private int getHash(Object key) {
		return (key.hashCode() & 0x7FFFFFFF) % capacity;
	}
}
