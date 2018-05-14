package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

public interface CalcModel {
    void addCalcValueListener(CalcValueListener l);

    void removeCalcValueListener(CalcValueListener l);

    String toString();

    double getValue();

    void setValue(double value);

    void clear();

    void clearAll();

    void swapSign();

    void insertDecimalPoint();

    void insertDigit(int digit);

    boolean isActiveOperandSet();

    double getActiveOperand();

    void setActiveOperand(double activeOperand);

    void clearActiveOperand();

    DoubleBinaryOperator getPendingBinaryOperation();

    void setPendingBinaryOperation(DoubleBinaryOperator op);
}