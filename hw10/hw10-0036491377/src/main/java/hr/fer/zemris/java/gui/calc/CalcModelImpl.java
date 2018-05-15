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
        if (digit.indexOf('.') != -1 && value % 1 == 0) {
            //digit = digit.substring(0, digit.indexOf('.'));
        }
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
        pendingOperation = op;
    }

    @Override
    public String toString() {
        if (digit.isEmpty()) {
            return "0";
        }

        removeLeadingZeroes();
        return digit;
    }

    private void notifyListeners() {
        // TODO: watch for concurrent modification
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
