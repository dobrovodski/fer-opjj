package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.CalcModel;

import javax.swing.*;

/**
 * The digit button represents a calculator "number button". The action for this button inserts a digit into the
 * calculator.
 *
 * @author matej
 */
public class DigitButton extends CalcButton {
    /**
     * Digit to be inserted.
     */
    private int digit;

    /**
     * Constructor.
     *
     * @param text text which will be inside of the {@link JButton}.
     */
    public DigitButton(String text) {
        super(text);
        this.digit = Integer.valueOf(text);
    }

    @Override
    public void action(CalcModel model) {
        model.insertDigit(digit);
    }
}
