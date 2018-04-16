package hr.fer.zemris.java.hw06.observer1;

public class DoubleValue implements IntegerStorageObserver {
    private int n;
    private int counter;

    public DoubleValue(int n) {
        this.n = n;
    }

    @Override
    public void valueChanged(IntegerStorage istorage) {
        counter++;
        if (counter <= this.n) {
            System.out.printf("Double value: %d\n", istorage.getValue() * 2);
        } else {
            istorage.removeObserver(this);
        }
    }
}
