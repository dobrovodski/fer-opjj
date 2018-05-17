package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.buttons.CalcButton;

/**
 * Single method interface which every {@link CalcButton} needs to implement in order to perform its actions.
 *
 * @author matej
 */
public interface CalcAction {
    /**
     * This method is called when the button is pressed.
     *
     * @param model reference to the model
     */
    void action(CalcModel model);
}
