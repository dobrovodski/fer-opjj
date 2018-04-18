package hr.fer.zemris.java.hw06.observer2;

/**
 * Interface for modelling an observer that listens to changes in {@link IntegerStorage}.
 *
 * @author matej
 */
public interface IntegerStorageObserver {
    /**
     * Method called every time a change occurs.
     *
     * @param istorage instance which holds information about the change (i.e. reference to the instance being
     *         observer, previous value, current value)
     */
    public void valueChanged(IntegerStorageChange istorage);
}
