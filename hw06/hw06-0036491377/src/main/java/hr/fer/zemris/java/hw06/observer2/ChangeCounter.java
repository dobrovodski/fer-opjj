package hr.fer.zemris.java.hw06.observer2;

public class ChangeCounter implements IntegerStorageObserver {
    private int counter;
    @Override
    public void valueChanged(IntegerStorageChange istorage) {
        counter++;
        System.out.format("Number of value changes since tracking: %d\n", counter);
    }
}
