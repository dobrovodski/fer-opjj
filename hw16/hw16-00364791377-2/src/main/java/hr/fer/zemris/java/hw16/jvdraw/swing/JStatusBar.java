package hr.fer.zemris.java.hw16.jvdraw.swing;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class JStatusBar extends JPanel {
    private IColorProvider currentFg;
    private IColorProvider currentBg;

    public JStatusBar(IColorProvider fg, IColorProvider bg) {
        this.currentFg = fg;
        this.currentBg = bg;

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(new BevelBorder(BevelBorder.LOWERED));

        JLabel label = new JLabel();
        setLabelText(label);
        fg.addColorChangeListener((source, oldColor, newColor) -> {
            this.currentFg = source;
            setLabelText(label);
        });

        bg.addColorChangeListener((source, oldColor, newColor) -> {
            this.currentBg = source;
            setLabelText(label);
        });

        add(label);
    }

    private void setLabelText(JLabel label) {
        Color fg = currentFg.getCurrentColor();
        Color bg = currentBg.getCurrentColor();
        String s = String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d)", fg.getRed(), fg
                .getGreen(), fg.getBlue(), bg.getRed(), bg.getGreen(), bg.getBlue());
        label.setText(s);
    }
}
