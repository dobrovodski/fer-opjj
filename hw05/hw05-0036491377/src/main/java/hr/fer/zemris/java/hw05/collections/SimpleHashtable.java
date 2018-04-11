package hr.fer.zemris.java.hw05.collections;

import java.util.*;

/**
 * This class implements a hash table, which maps keys to values. The key cannot be null. In order to store items and
 * retrieve them, the keys must implement {@code hashCode} and {@code equals}. The constructor for the hashtable can use
 * a parameter to set the initial capacity of the table to. By default, the table resizes after reaching 75% capacity.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author matej
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
    // Default initial capacity of table
    private static final int DEFAULT_CAPACITY = 16;
    // Default threshold at which the table re-hashes
    private static final double THRESHOLD = 0.75;
    // If it should rehash
    private static final boolean REHASH = true;

    /**
     * This class represents a single entry in the hashtable.
     *
     * @param <K> the type of the key
     * @param <V> the type of the mapped value
     */
    public static class TableEntry<K, V> {
        // Key object that the entry will be stored under
        private final K key;
        // The value to store
        private V value;
        // Reference to the next entry in the linked list (in a single slot of the tasble)
        private TableEntry<K, V> next;

        /**
         * Constructor for the entry.
         *
         * @param key Key object that the entry will be stored under
         * @param value The value to store
         */
        public TableEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the key that the entry is stored under.
         *
         * @return TableEntry key
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the stored value.
         *
         * @return TableEntry value
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets the value of the entry.
         *
         * @param value value to set
         */
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key.toString() + "=" + this.value.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            TableEntry<?, ?> other = (TableEntry<?, ?>) o;
            return Objects.equals(key, other.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    // Current capacity of the table
    private int capacity;
    // Table of entries for the hashtable
    private TableEntry<K, V>[] table;
    // Keeps track of the number of elements in the table
    private int size;
    // Number of times the table has been directly modified, used for concurrent modification checking
    private int modificationCount;

    /**
     * Default constructor for the table which sets the initial capacity to 16.
     */
    public SimpleHashtable() {
        this.capacity = DEFAULT_CAPACITY;
        this.table = (TableEntry<K, V>[]) new TableEntry[capacity];
    }

    /**
     * Default constructor for the table which sets the initial capacity to the value provided.
     *
     * @param initialCapacity initial capacity to set the table to
     *
     * @throws IllegalArgumentException if capacity is less than 1
     */
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

    /**
     * Puts the provided value in the hashtable under the given key.
     *
     * @param key key to store value under
     * @param value value to be stored
     *
     * @throws NullPointerException if the key is null
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Cannot insert item with the key null into hashtable.");
        }

        if ((float) size / capacity > THRESHOLD && REHASH) {
            rehash();
        }

        int hash = getHash(key);

        if (table[hash] == null) {
            table[hash] = new TableEntry<>(key, value);
            size++;
            modificationCount++;
            return;
        }

        if (!this.containsKey(key)) {
            TableEntry<K, V> currentEntry = table[hash];
            while (currentEntry.next != null) {
                currentEntry = currentEntry.next;
            }

            currentEntry.next = new TableEntry<>(key, value);
            size++;
            modificationCount++;
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

    /**
     * Returns value stored under key if it is in the table. Returns null otherwise. Keep in mind that the value stored
     * under the key can also be null.
     *
     * @param key key that the value is stored under
     *
     * @return value
     */
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

    /**
     * Returns the number of entries in the table.
     *
     * @return number of entries in table
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if given key is in the table, {@code false} otherwise.
     *
     * @param key key to check
     *
     * @return {@code true} if given key is in the table, {@code false} otherwise
     */
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

    /**
     * Removes the value stored in the table under the given key. If the key is not in the table, it does nothing
     *
     * @param key key for which the value should be removed
     */
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
            modificationCount++;
            return;
        }

        TableEntry<K, V> nextEntry = table[hash];
        while (nextEntry != null) {
            if (nextEntry.getKey().equals(key)) {
                currentEntry.next = nextEntry.next;
                size--;
                modificationCount++;
                return;
            }
            currentEntry = nextEntry;
            nextEntry = nextEntry.next;
        }
    }

    /**
     * Returns {@code true} if the table is empty, {@code false} otherwise.
     *
     * @return {@code true} if the table is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns {@code true} if the given value is in the table, {@code false} otherwise. The complexity of this method
     * is O(n).
     *
     * @param value value to check
     *
     * @return {@code true} if the given value is in the table, {@code false} otherwise
     */
    public boolean containsValue(Object value) {
        for (TableEntry<K, V> e : table) {
            while (e != null) {
                if (e.getValue().equals(value)) {
                    return true;
                }
                e = e.next;
            }
        }

        return false;
    }

    /**
     * Clears all the values in the table and sets the size of the table to 0.
     */
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }

        modificationCount++;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean empty = true;
        sb.append('[');

        for (int i = 0; i < capacity; i++) {
            TableEntry<K, V> current = table[i];
            while (current != null) {
                sb.append(current.toString());
                sb.append(", ");
                empty = false;

                current = current.next;
            }
        }

        // Delete last ', '
        if (!empty) {
            sb.setLength(sb.toString().length() - 2);
        }
        sb.append(']');

        return sb.toString();
    }

    /**
     * Regularly resizes the hashtable to prevent collisions. By default, it triggers when there is 0.75 * capacity
     * entries in the table.
     */
    private void rehash() {
        int newCapacity = capacity * 2;
        TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[newCapacity];

        for (int i = 0; i < capacity; i++) {
            TableEntry<K, V> current = table[i];
            while (current != null) {
                int hash = (current.getKey().hashCode() & 0x7FFFFFFF) % newCapacity;

                // First entry in slot
                if (newTable[hash] == null) {
                    newTable[hash] = new TableEntry<>(current.getKey(), current.getValue());
                    current = current.next;
                    continue;
                }

                // If not first, add to the end
                TableEntry<K, V> existent = newTable[hash];
                while (existent.next != null) {
                    existent = existent.next;
                }
                existent.next = new TableEntry<>(current.getKey(), current.getValue());

                current = current.next;
            }
        }

        modificationCount++;
        capacity = newCapacity;
        table = newTable;
    }

    /**
     * An iterator over a collection.
     */
    private class IteratorImpl implements Iterator<TableEntry<K, V>> {
        // current index in the table
        private int currentSlotIndex;
        // current entry being iterated over
        private TableEntry<K, V> currentEntry;
        // makes sure remove isn't called twice on the same entry
        private boolean canRemove;
        // keeps track of expected modification count for concurrent modification purposes
        private int expectedModificationCount;

        public IteratorImpl() {
            expectedModificationCount = modificationCount;
        }

        @Override
        public boolean hasNext() {
            if (currentEntry == null) {
                for (int i = 0; i < capacity; i++) {
                    if (table[i] != null) {
                        return true;
                    }
                }

                return false;
            }

            if (currentEntry.next != null) {
                return true;
            }

            for (int i = currentSlotIndex + 1; i < capacity; i++) {
                if (table[i] != null) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public TableEntry<K, V> next() {
            if (SimpleHashtable.this.modificationCount != expectedModificationCount) {
                throw new ConcurrentModificationException();
            }

            if (currentEntry == null) {
                for (int i = 0; i < capacity; i++) {
                    if (table[i] != null) {
                        currentEntry = table[i];
                        currentSlotIndex = i;
                        canRemove = true;

                        return currentEntry;
                    }
                }

                throw new NoSuchElementException("The iteration has no elements");
            }

            if (currentEntry.next != null) {
                currentEntry = currentEntry.next;
                canRemove = true;

                return currentEntry;
            }

            for (int i = currentSlotIndex + 1; i < capacity; i++) {
                if (table[i] != null) {
                    currentEntry = table[i];
                    currentSlotIndex = i;
                    canRemove = true;

                    return currentEntry;
                }
            }

            throw new NoSuchElementException("The iteration has no more elements");
        }

        @Override
        public void remove() {
            if (SimpleHashtable.this.modificationCount != expectedModificationCount) {
                throw new ConcurrentModificationException();
            }

            if (!canRemove) {
                throw new IllegalStateException("Remove method has already been called.");
            }

            expectedModificationCount++;
            SimpleHashtable.this.remove(currentEntry.getKey());

            canRemove = false;
        }
    }

    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    /**
     * Calculates the hashcode of the given key for this table. This is also the index of the table slot that the entry
     * will be stored under
     *
     * @param key key to hash
     *
     * @return hashcode for given key
     */
    private int getHash(Object key) {
        return (key.hashCode() & 0x7FFFFFFF) % capacity;
    }
}
