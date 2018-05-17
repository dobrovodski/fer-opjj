package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.CalcModel;

import javax.swing.*;
import java.util.function.DoubleBinaryOperator;

/**
 * This button represents binary operations such as +, *, /, -. The button takes in a {@link DoubleBinaryOperator} as
 * its action to perform.
 *
 * @author matej
 */
public class BinaryOperatorButton extends CalcButton {
    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Action to perform.
     */
    private DoubleBinaryOperator op;

    /**
     * Constructor.
     *
     * @param text text which will be inside of the {@link JButton}.
     * @param op action to be performed.
     */
    public BinaryOperatorButton(String text, DoubleBinaryOperator op) {
        super(text);
        this.op = op;
    }

    @Override
    public void action(CalcModel model) {
        // Calculate the result of the previous binary operation if there is one
        DoubleBinaryOperator currentOperator = model.getPendingBinaryOperation();
        if (currentOperator != null && model.isActiveOperandSet()) {
            double operand = model.getActiveOperand();
            double result = currentOperator.applyAsDouble(operand, model.getValue());

            model.setValue(result);
        }

        // Set active operand to the current value and binary operation to the one of the button clicked
        model.setActiveOperand(model.getValue());
        model.setPendingBinaryOperation(op);
    }
}
