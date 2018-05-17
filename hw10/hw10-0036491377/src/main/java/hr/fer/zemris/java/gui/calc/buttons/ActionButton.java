package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.CalcModel;

import javax.swing.*;
import java.util.function.Consumer;

/**
 * The {@link ActionButton} represents a calculator button which performs "abstract" actions such as pushing, popping or
 * clearing. Their action is a {@link Consumer} which takes in the reference to the {@link CalcModel} of the
 * calculator.
 *
 * @author matej
 */
public class ActionButton extends CalcButton {
    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Consumer action to be performed.
     */
    private Consumer<CalcModel> action;

    /**
     * Constructor.
     *
     * @param text text which will be inside of the {@link JButton}.
     * @param action action to be performed
     */
    public ActionButton(String text, Consumer<CalcModel> action) {
        super(text);
        this.action = action;
    }

    @Override
    public void action(CalcModel model) {
        action.accept(model);
    }
}
