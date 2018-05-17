package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.CalcAction;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract class which represents calculator buttons. Every button needs to implement {@link CalcAction}'s method to
 * perform an action.
 *
 * @author matej
 */
public abstract class CalcButton extends JButton implements CalcAction {
    /**
     * Constructor.
     *
     * @param text text which will be inside of the {@link JButton}.
     */
    public CalcButton(String text) {
        super(text);
        setFont(new Font("Arial", Font.PLAIN, 16));
    }
}
