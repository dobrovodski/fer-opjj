package hr.fer.zemris.java.hw06.observer1;

public class ChangeCounter implements IntegerStorageObserver {
    private int counter;
    @Override
    public void valueChanged(IntegerStorage istorage) {
        counter++;
        System.out.format("Number of value changes since tracking: %d\n", counter);
    }
}
