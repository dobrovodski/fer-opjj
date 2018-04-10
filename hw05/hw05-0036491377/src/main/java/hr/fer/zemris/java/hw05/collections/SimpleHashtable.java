package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double THRESHOLD = 0.75;
    private static final boolean REHASH = true;

    public static class TableEntry<K, V> {
        private final K key;
        private V value;
        private TableEntry<K, V> next;

        public TableEntry(K key, V value) {
            this.key = key;
            this.value = value;
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

    private int capacity;
    private TableEntry<K, V>[] table;
    private int size;
    private int modificationCount;

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
            throw new NullPointerException("Cannot insert item with the key null into hashtable.");
        }

        if (size / capacity > THRESHOLD && REHASH) {
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

    public boolean isEmpty() {
        return size == 0;
    }

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

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }

        modificationCount++;
        size = 0;
    }

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

    private class IteratorImpl implements Iterator<TableEntry<K, V>> {
        private int currentSlotIndex;
        private TableEntry<K, V> currentEntry;
        private boolean canRemove;
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

    private int getHash(Object key) {
        return (key.hashCode() & 0x7FFFFFFF) % capacity;
    }
}
