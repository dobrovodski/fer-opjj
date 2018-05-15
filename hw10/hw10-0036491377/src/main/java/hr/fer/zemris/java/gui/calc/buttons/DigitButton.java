package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.CalcModel;

public class DigitButton extends CalcButton {
    private int digit;

    public DigitButton(String text) {
        super(text);
        this.digit = Integer.valueOf(text);
    }

    @Override
    public void action(CalcModel model) {
        model.insertDigit(digit);
    }
}
