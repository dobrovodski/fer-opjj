package hr.fer.zemris.java.hw06.observer2;

public class IntegerStorageChange {
    private IntegerStorage storage;
    private int previousValue;
    private int newValue;

    public IntegerStorageChange(IntegerStorage storage, int previousValue, int newValue) {
        this.storage = storage;
        this.previousValue = previousValue;
        this.newValue = newValue;
    }

    public IntegerStorage getStorage() {
        return storage;
    }

    public int getPreviousValue() {
        return previousValue;
    }

    public int getNewValue() {
        return newValue;
    }
}
