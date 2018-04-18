package hr.fer.zemris.java.hw06.observer2;

/**
 * Observer class which increments its counter and prints it on every change.
 *
 * @author matej
 */
public class ChangeCounter implements IntegerStorageObserver {
    /**
     * Keeps track of number of times that the observed subject changed.
     */
    private int counter;

    @Override
    public void valueChanged(IntegerStorageChange istorage) {
        counter++;
        System.out.format("Number of value changes since tracking: %d\n", counter);
    }
}
