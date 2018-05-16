package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {
    private List<CalcValueListener> listeners = new ArrayList<>();
    private Double activeOperand;
    private DoubleBinaryOperator pendingOperation;
    private String digit = "";

    @Override
    public void addCalcValueListener(CalcValueListener l) {
        listeners.add(l);
    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        listeners.remove(l);
    }

    @Override
    public double getValue() {
        if (digit.isEmpty()) {
            return 0.0;
        }

        return Double.parseDouble(digit);
    }

    @Override
    public void setValue(double value) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("Cannot set value to NaN.");
        }

        if (Double.isInfinite(value)) {
            throw new IllegalArgumentException("Cannot set value to infinite.");
        }

        digit = String.valueOf(value);
        notifyListeners();
    }

    @Override
    public void clear() {
        digit = "";
        notifyListeners();
    }

    @Override
    public void clearAll() {
        clear();
        clearActiveOperand();
        pendingOperation = null;
    }

    @Override
    public void swapSign() {
        if (digit.isEmpty()) {
            return;
        }

        if (digit.startsWith("-")) {
            digit = digit.substring(1);
        } else {
            digit = '-' + digit;
        }

        notifyListeners();
    }

    @Override
    public void insertDecimalPoint() {
        if (digit.isEmpty()) {
            insertDigit(0);
        }
        if (digit.indexOf('.') != -1) {
            return;
        }
        digit += '.';

        notifyListeners();
    }

    @Override
    public void insertDigit(int digit) {
        // TODO: error?
        if (!(digit >= 0 && digit <= 9)) {
            return;
        }

        if (this.digit.isEmpty()) {
            this.digit = String.valueOf(digit);
            notifyListeners();
            return;
        }

        String concatenated = this.digit + String.valueOf(digit);
        if (Double.isInfinite(Double.parseDouble(concatenated))) {
            return;
        }

        this.digit = concatenated;
        notifyListeners();
    }

    @Override
    public boolean isActiveOperandSet() {
        return activeOperand != null;
    }

    @Override
    public double getActiveOperand() {
        if (activeOperand == null) {
            throw new IllegalStateException("Operand has not yet been set.");
        }

        return activeOperand;
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
    }

    @Override
    public void clearActiveOperand() {
        activeOperand = null;
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        // Don't clear the digit if you're clearing the pending binary operation
        if (op != null) {
            digit = "";
        }

        pendingOperation = op;
    }

    @Override
    public String toString() {
        if (digit.isEmpty()) {
            return "0";
        }

        removeLeadingZeroes();

        /*if (getValue() % 1 == 0 && digit.indexOf('.') != -1 && digit.indexOf('.') != digit.length() - 1) {
            return digit.substring(0, digit.indexOf('.'));
        }*/

        return digit;
    }

    private void notifyListeners() {
        // Concurrent modification can cause an exception here, but for the domain of this problem, such operations
        // won't occur.
        for (CalcValueListener l : listeners) {
            l.valueChanged(this);
        }
    }

    private void removeLeadingZeroes() {
        while (digit.startsWith("0") && digit.length() > 1 && digit.charAt(1) != '.') {
            digit = digit.substring(1);
        }
    }
}
