package hr.fer.zemris.java.hw06.observer1;

/**
 * Interface for modelling an observer that listens to changes in {@link IntegerStorage}.
 *
 * @author matej
 */
public interface IntegerStorageObserver {
    /**
     * Method called every time a change occurs.
     *
     * @param istorage the reference to the instance that the listener is listening to
     */
    public void valueChanged(IntegerStorage istorage);
}
