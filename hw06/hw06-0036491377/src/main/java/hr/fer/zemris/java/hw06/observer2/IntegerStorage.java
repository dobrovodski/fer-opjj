package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;

public class IntegerStorage {
    private int value;
    private List<IntegerStorageObserver> observers;
    private List<IntegerStorageObserver> observersToRemove;

    public IntegerStorage(int initialValue) {
        this.value = initialValue;
        this.observers = new ArrayList<>();
        this.observersToRemove = new ArrayList<>();
    }

    public void addObserver(IntegerStorageObserver observer) {
        if (!this.observers.contains(observer)) {
            this.observers.add(observer);
        }
    }

    public void removeObserver(IntegerStorageObserver observer) {
        if (this.observers.contains(observer)) {
            this.observersToRemove.add(observer);
        }
    }

    public void clearObservers() {
        this.observers.clear();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if(this.value!=value) {

            if(observers!=null) {
                checkToRemove();

                IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
                for(IntegerStorageObserver observer : observers) {
                    observer.valueChanged(change);
                }
            }

            this.value = value;
        }
    }

    private void checkToRemove() {
        for (IntegerStorageObserver observer : observersToRemove) {
            this.observers.remove(observer);
        }
        observersToRemove.clear();
    }
}
