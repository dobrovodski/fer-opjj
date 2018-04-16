package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

public class ObjectMultistack {
    private Map entries;

    public ObjectMultistack() {
        this.entries = new HashMap();
    }

    public void push(String name, ValueWrapper valueWrapper) {

    }

    public ValueWrapper pop(String name) {
        return null;
    }

    public ValueWrapper peek(String name) {
        return null;
    }

    public boolean isEmpty(String name) {
        return true;
    }

    private static class MultiStackEntry {
        private ValueWrapper value;
        private MultiStackEntry next;

        public MultiStackEntry(ValueWrapper value) {
            this.value = value;
        }
    }
}
