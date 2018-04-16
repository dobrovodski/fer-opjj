package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;

public class ValueWrapper {
    private Object value;

    public ValueWrapper(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void add(Object incValue) {

    }

    public void subtract(Object decValue) {

    }

    public void multiply(Object mulValue) {

    }

    public void divide(Object divValue) {

    }

    public int numCompare(Object withValue) {
        return 0;
    }
}
