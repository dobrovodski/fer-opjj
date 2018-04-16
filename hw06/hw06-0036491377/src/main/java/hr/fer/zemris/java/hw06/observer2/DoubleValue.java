package hr.fer.zemris.java.hw06.observer2;

public class DoubleValue implements IntegerStorageObserver {
    private int n;
    private int counter;

    public DoubleValue(int n) {
        this.n = n;
    }

    @Override
    public void valueChanged(IntegerStorageChange istorage) {
        counter++;
        if (counter <= this.n) {
            System.out.printf("Double value: %d\n", istorage.getNewValue() * 2);
        } else {
            istorage.getStorage().removeObserver(this);
        }
    }
}
