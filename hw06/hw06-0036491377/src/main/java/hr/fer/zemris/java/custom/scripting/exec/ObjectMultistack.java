package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 *
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
     *
     */
    public void push(String name, ValueWrapper valueWrapper) {
        MultiStackEntry entry = new MultiStackEntry(valueWrapper);

        if (!entries.containsKey(name)) {
            entries.put(name, entry);
            return;
        }

        entry.next = entries.get(name);
        entries.replace(name, entry);
    }

    /**
     *
     */
    public ValueWrapper pop(String name) {
        MultiStackEntry current = entries.get(name);
        entries.replace(name, current.next);

        return current.value;
    }

    /**
     *
     */
    public ValueWrapper peek(String name) {
        return entries.get(name).value;
    }

    /**
     *
     */
    public boolean isEmpty(String name) {
        return entries.size() == 0;
    }

    /**
     *
     */
    private static class MultiStackEntry {
        private ValueWrapper value;
        private MultiStackEntry next;

        private MultiStackEntry(ValueWrapper value) {
            this.value = value;
        }
    }
}
