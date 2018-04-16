package hr.fer.zemris.java.hw06.observer2;

public class SquareValue implements IntegerStorageObserver {
    @Override
    public void valueChanged(IntegerStorageChange istorage) {
        int value = istorage.getNewValue();
        System.out.format("Provided new value: %d, square is %d\n", value, value * value);
    }
}
