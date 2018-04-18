package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores an integer value and a list of observers to notify on every change.
 *
 * @author matej
 */
public class IntegerStorage {
    /**
     * Value stored.
     */
    private int value;
    /**
     * List of observers.
     */
    private List<IntegerStorageObserver> observers;
    /**
     * List of observers to remove - used to prevent concurrent modification errors.
     */
    private List<IntegerStorageObserver> observersToRemove;

    public IntegerStorage(int initialValue) {
        this.value = initialValue;
        this.observers = new ArrayList<>();
        this.observersToRemove = new ArrayList<>();
    }

    /**
     * Adds observer to object.
     *
     * @param observer observer to be added
     */
    public void addObserver(IntegerStorageObserver observer) {
        if (!this.observers.contains(observer)) {
            this.observers.add(observer);
        }
    }

    /**
     * Removes observer from object.
     *
     * @param observer observer to be removed
     */
    public void removeObserver(IntegerStorageObserver observer) {
        if (this.observers.contains(observer)) {
            this.observersToRemove.add(observer);
        }
    }

    /**
     * Clears the list of observers and unsubscribes them.
     */
    public void clearObservers() {
        this.observers.clear();
    }

    /**
     * Returns value stored in object.
     *
     * @return value stored
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the object and notifies all the registered observers with the change.
     *
     * @param value value to set
     */
    public void setValue(int value) {
        if (this.value != value) {

            if (observers != null) {
                checkToRemove();

                IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
                for (IntegerStorageObserver observer : observers) {
                    observer.valueChanged(change);
                }
            }

            this.value = value;
        }
    }

    /**
     * Checks which observers should be removed before starting to notify all other observers.
     */
    private void checkToRemove() {
        for (IntegerStorageObserver observer : observersToRemove) {
            this.observers.remove(observer);
        }
        observersToRemove.clear();
    }
}
