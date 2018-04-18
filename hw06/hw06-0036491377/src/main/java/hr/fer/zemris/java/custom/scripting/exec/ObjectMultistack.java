package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This data structure represents a map whose values act as stacks. It provides the usual
 *
 * @author matej
 */
public class ObjectMultistack {
    /**
     *
     */
    private Map<String, MultiStackEntry> entries;

    /**
     *
     */
    public ObjectMultistack() {
        this.entries = new HashMap<>();
    }

    /**
     * Pushes the {@link ValueWrapper} onto the stack {@code name}.
     * @param name name of the stack to push to
     * @param valueWrapper valueWrapper to store on stack
     * * @throws NullPointerException if name or valueWrapper were null
     */
    public void push(String name, ValueWrapper valueWrapper) {
        Objects.requireNonNull(name, "Cannot push to null key.");
        Objects.requireNonNull(valueWrapper, "Cannot push null onto stack.");

        MultiStackEntry entry = new MultiStackEntry(valueWrapper);

        if (!entries.containsKey(name)) {
            entries.put(name, entry);
            return;
        }

        entry.next = entries.get(name);
        entries.replace(name, entry);
    }

    /**
     * Pops value from the stack {@code name}.
     * @param name name of the stack to pop from
     * @return {@link ValueWrapper} stored on top of stack
     * @throws NullPointerException if name was null
     */
    public ValueWrapper pop(String name) {
        Objects.requireNonNull(name, "Cannot pop from null key.");

        MultiStackEntry current = entries.get(name);
        if (current == null) {
            throw new EmptyStackException();
        }

        entries.replace(name, current.next);

        return current.value;
    }

    /**
     *
     * @param name
     * @return
     */
    public ValueWrapper peek(String name) {
        Objects.requireNonNull(name, "Cannot peek from null key.");

        MultiStackEntry entry = entries.get(name);
        if (entry == null) {
            throw new EmptyStackException();
        }

        return entry.value;
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean isEmpty(String name) {
        Objects.requireNonNull(name, "Cannot check if null key is empty.");
        return entries.get(name) == null;
    }

    /**
     * Helper class for storing values and pointers to the next entry.
     *
     * @author matej
     */
    private static class MultiStackEntry {
        /**
         * Stores the value of the entry.
         */
        private ValueWrapper value;
        /**
         * Pointer to the next entry on the stack.
         */
        private MultiStackEntry next;

        /**
         * Constructor for {@link MultiStackEntry}.
         *
         * @param value value to store in entry
         */
        private MultiStackEntry(ValueWrapper value) {
            this.value = value;
        }
    }
}
