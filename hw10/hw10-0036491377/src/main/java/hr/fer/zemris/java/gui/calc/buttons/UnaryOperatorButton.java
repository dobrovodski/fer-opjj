package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.CalcModel;

import javax.swing.*;
import java.util.function.DoubleUnaryOperator;

/**
 * The {@link UnaryOperatorButton} represents buttons which act as unary operators, eg. sin, cos, tan. The button takes
 * a {@link DoubleUnaryOperator} to call when pressed.
 *
 * @author matej
 */
public class UnaryOperatorButton extends CalcButton {
    /**
     * Action to be performed.
     */
    private DoubleUnaryOperator op;

    /**
     * Constructor.
     *
     * @param text text which will be inside of the {@link JButton}.
     * @param op action to be performed
     */
    public UnaryOperatorButton(String text, DoubleUnaryOperator op) {
        super(text);
        this.op = op;
    }

    @Override
    public void action(CalcModel model) {
        model.setValue(op.applyAsDouble(model.getValue()));
    }
}
