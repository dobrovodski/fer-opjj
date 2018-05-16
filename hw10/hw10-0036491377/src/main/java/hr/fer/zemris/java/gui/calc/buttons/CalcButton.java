package hr.fer.zemris.java.gui.calc.buttons;

import hr.fer.zemris.java.gui.calc.CalcAction;

import javax.swing.*;
import java.awt.*;

public abstract class CalcButton extends JButton implements CalcAction {
    public CalcButton(String text) {
        super(text);
        setFont(new Font("Arial", Font.PLAIN, 16));
    }
}
