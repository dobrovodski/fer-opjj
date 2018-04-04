package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * The {@code Dictionary} class represents a general map-like colection of objects.
 * @author matej
 */
public class Dictionary {
	/**
	 * Private class for modelling a single dictionary entry.
	 *  @author matej
	 */
	private static class Entry {
		// Key to store the value under
		Object key;
		// Value to store in the dictionary
		Object value;
	}

	// Array to store key-value entries
	private ArrayIndexedCollection entries;

	public Dictionary() {
		entries = new ArrayIndexedCollection();
	}

	/**
	 * Returns {@code true} if dictionary is empty, {@code false} otherwise.
	 * @return {@code true} if empty, {@code false} otherwise
	 */
	public boolean isEmpty() {
		return entries.isEmpty();
	}

	/**
	 * Returns size of dictionary.
	 * @return size of dictionary
	 */
	public int size() {
		return entries.size();
	}

	/**
	 * Clears the dictionary.
	 */
	public void clear() {
		entries.clear();
	}

	/**
	 * Puts given value under the provided key into the dictionary. Overwrites already existing value under {@code key}.
	 * @param key key to store the value under
	 * @param value value to be stored
	 */
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

	/**
	 * Retrieves value found under {@code key}. Returns {@code null} if the value is null or not added to dictionary.
	 * @param key key to find the value under
	 * @return value found in dictionary
	 * @throws NullPointerException if the key is null.
	 */
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
