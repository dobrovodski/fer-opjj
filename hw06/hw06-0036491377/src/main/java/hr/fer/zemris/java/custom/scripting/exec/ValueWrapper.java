package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;

/**
 * Stores a single value of any kind. Provides methods for numerical calculations under the assumption that the stored
 * value can be converted into a valid number.
 *
 * @author matej
 */
public class ValueWrapper {
    /**
     * Stores the value of the wrapper.
     */
    private Object value;

    /**
     * Constructor for {@link ValueWrapper}.
     *
     * @param value value to store
     */
    public ValueWrapper(Object value) {
        this.value = value;
    }

    /**
     * Returns the value stored in the wrapper.
     *
     * @return value stored in wrapper.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value of the wrapper. Any value is permitted, even {@code null}.
     *
     * @param value value to store
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Adds stored value and {@code incValue} and stores the result.
     *
     * @param incValue value to be added
     */
    public void add(Object incValue) {
        this.value = calculate(this.value, incValue, (a, b) -> a + b, (a, b) -> a + b);
    }

    /**
     * Subtracts stored value and {@code decValue} and stores the result.
     *
     * @param decValue value to be subtracted
     */
    public void subtract(Object decValue) {
        this.value = calculate(this.value, decValue, (a, b) -> a - b, (a, b) -> a - b);
    }

    /**
     * Multiplies stored value and {@code mulValue} and stores the result.
     *
     * @param mulValue value to multiplied with
     */
    public void multiply(Object mulValue) {
        this.value = calculate(this.value, mulValue, (a, b) -> a * b, (a, b) -> a * b);
    }

    /**
     * Divides stored value and {@code divValue} and stores the result.
     *
     * @param divValue value to divided with
     */
    public void divide(Object divValue) {
        this.value = calculate(this.value, divValue, (a, b) -> a / b, (a, b) -> a / b);
    }

    /**
     * Compares the stored value and the provided values numerically.
     *
     * @param withValue object to compare with
     *
     * @return the value 0 if x == y; a value less than 0 if x < y; and a value greater than 0 if x > y
     */
    public int numCompare(Object withValue) {
        return (int) calculate(this.value, withValue, Integer::compare, Double::compare);
    }

    /**
     * Converts given object into a numerical value by the following rules: <br>
     *     - {@code null} is converted to an {@link Integer} with value of 0 <br>
     *     - {@link String} is converted into either an {@link Integer} or a {@link Double} <br>
     *     - instances of {@link Double} and {@link Integer} stay the same <br>
     *     - everything else throws an exception
     *
     * @param value value to be converted
     *
     * @return converted value
     *
     * @throws IllegalArgumentException if the {@code value} couldn't be converted by any rules
     */
    private Number convert(Object value) {
        if (!(value instanceof String || value instanceof Integer || value instanceof Double || value == null)) {
            throw new ClassCastException("Couldn't cast to a valid numerical value: " + value);
        }

        if (value == null) {
            return 0;
        }

        if (value instanceof Double) {
            return (Double) value;
        }

        if (value instanceof Integer) {
            return (Integer) value;
        }

        String valueString = (String) value;
        if (valueString.contains(".") || valueString.contains("E")) {
            try {
                return Double.parseDouble(valueString);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Could not convert to double: " + valueString);
            }
        } else {
            try {
                return Integer.parseInt(valueString);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Could not convert to integer: " + valueString);
            }
        }
    }

    /**
     * Calculates the result of the mathematical operator provided. If either of the arguments to use are {@link
     * Double}, it uses {@code doubleOp}, if both are {@link Integer} it uses {@code intOp}, otherwise an exception is
     * thrown.
     *
     * @param first first value
     * @param second second value
     * @param intOp integer math operation
     * @param doubleOp double math operation
     *
     * @return result of mathematical operation
     */
    private Number calculate(Object first, Object second, IntBinaryOperator intOp, DoubleBinaryOperator doubleOp) {
        Number firstConverted = convert(first);
        Number secondConverted = convert(second);

        if (firstConverted instanceof Double || secondConverted instanceof Double) {
            return doubleOp.applyAsDouble(firstConverted.doubleValue(), secondConverted.byteValue());
        }

        if (firstConverted instanceof Integer && secondConverted instanceof Integer) {
            return intOp.applyAsInt(firstConverted.intValue(), secondConverted.intValue());
        }

        // This line should never fire
        throw new IllegalArgumentException("Couldn't perform calculation: " + first + " " + second);
    }
}
