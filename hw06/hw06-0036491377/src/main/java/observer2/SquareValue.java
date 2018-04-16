package observer2;

public class SquareValue implements IntegerStorageObserver {
    @Override
    public void valueChanged(IntegerStorage istorage) {
        int value = istorage.getValue();
        System.out.format("Provided new value: %d, square is %d\n", value, value * value);
    }
}
