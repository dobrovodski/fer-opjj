package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * The interface for a model of a calculator which provides methods used to change the state of the calculator.
 *
 * @author matej
 */
public interface CalcModel {
    /**
     * Adds a listener which listens for changes in the model.
     *
     * @param l listener to add
     */
    void addCalcValueListener(CalcValueListener l);

    /**
     * Removes given listener from list of listeners.
     *
     * @param l listener to remove
     */
    void removeCalcValueListener(CalcValueListener l);

    /**
     * Returns a string representation of the current value stored in the calculator.
     *
     * @return current value stored in the model
     */
    String toString();

    /**
     * Returns the current value stored in the model.
     *
     * @return current value stored in the model
     */
    double getValue();

    /**
     * Sets the current value stored in the model.
     *
     * @param value value to store
     */
    void setValue(double value);

    /**
     * Clears the current value stored in the model.
     */
    void clear();

    /**
     * Clears the model's state.
     */
    void clearAll();

    /**
     * Swaps the sign of the current value.
     */
    void swapSign();

    /**
     * Inserts a decimal point at the end of the current value. If the value is already a decimal number, it does
     * nothing.
     */
    void insertDecimalPoint();

    /**
     * Appends the given digit to the current value.
     *
     * @param digit digit to append
     */
    void insertDigit(int digit);

    /**
     * Returns true if the active operand is set.
     *
     * @return true if the active operand is set, false otherwise.
     */
    boolean isActiveOperandSet();

    /**
     * Returns the current active operand.
     *
     * @return current active operand
     *
     * @throws IllegalStateException if there is no active operand set
     */
    double getActiveOperand();

    /**
     * Sets the given operand to the given one.
     *
     * @param activeOperand active operand
     */
    void setActiveOperand(double activeOperand);

    /**
     * Clears the current active operand.
     */
    void clearActiveOperand();

    /**
     * Returns the pending binary operation.
     *
     * @return pending binary operation
     */
    DoubleBinaryOperator getPendingBinaryOperation();

    /**
     * Sets the pending binary operation.
     *
     * @param op pending operation to set
     */
    void setPendingBinaryOperation(DoubleBinaryOperator op);
}