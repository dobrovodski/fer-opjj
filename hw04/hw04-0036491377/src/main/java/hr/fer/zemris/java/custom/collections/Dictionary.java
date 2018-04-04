package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

public class Dictionary {
	private static class Entry {
		public Object key;
		public Object value;
	}

	private ArrayIndexedCollection entries;


	public Dictionary() {
		entries = new ArrayIndexedCollection();
	}

	public boolean isEmpty() {
		return entries.isEmpty();
	}

	public int size() {
		return entries.size();
	}

	public void clear() {
		entries.clear();
	}

	public void put(Object key, Object value) {
		Objects.requireNonNull(key, "Key cannot be null.");

		Entry current;

		for (int i = 0, n = entries.size(); i < n; i++) {
			current = (Entry) entries.get(i);

			if (current.key.equals(key)) {
				current.value = value;
				return;
			}
		}

		Entry entry = new Entry();
		entry.key = key;
		entry.value = value;
		entries.add(entry);
	}

	public Object get(Object key) {
		Objects.requireNonNull(key, "Key cannot be null.");
		Entry current;

		for (int i = 0, n = entries.size(); i < n; i++) {
			current = (Entry) entries.get(i);

			if (current.key.equals(key)) {
				return current.value;
			}
		}

		return null;
	}
}
