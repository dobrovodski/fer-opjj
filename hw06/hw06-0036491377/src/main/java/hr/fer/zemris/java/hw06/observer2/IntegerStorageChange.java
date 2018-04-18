package hr.fer.zemris.java.hw06.observer2;

/**
 * This class models an "information packet" which is sent to the observer with the data about the change.
 *
 * @author matej
 */
public class IntegerStorageChange {
    /**
     * Reference to the {@link IntegerStorage} being observed.
     */
    private IntegerStorage storage;
    /**
     * Previous value.
     */
    private int previousValue;
    /**
     * New value after the change.
     */
    private int newValue;

    public IntegerStorageChange(IntegerStorage storage, int previousValue, int newValue) {
        this.storage = storage;
        this.previousValue = previousValue;
        this.newValue = newValue;
    }

    /**
     * Returns the storage.
     *
     * @return storage
     */
    public IntegerStorage getStorage() {
        return storage;
    }

    /**
     * Returns the previous value stored.
     *
     * @return previous value
     */
    public int getPreviousValue() {
        return previousValue;
    }

    /**
     * Returns the new value after the change.
     *
     * @return new value
     */
    public int getNewValue() {
        return newValue;
    }
}
