package hr.fer.zemris.java.hw16.jvdraw.color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class JColorArea extends JComponent implements IColorProvider {
    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;
    private Color selectedColor;
    private List<ColorChangeListener> listeners = new ArrayList<>();

    public JColorArea(Color selectedColor) {
        this.selectedColor = selectedColor;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color newColor = JColorChooser.showDialog(JColorArea.this, "Select a color", selectedColor);
                Color oldColor = JColorArea.this.selectedColor;
                JColorArea.this.selectedColor = newColor;

                for (ColorChangeListener l : listeners) {
                    l.newColorSelected(JColorArea.this, oldColor, newColor);
                }

                //TODO: repaint(); ?
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    public Color getCurrentColor() {
        return selectedColor;
    }

    @Override
    public void addColorChangeListener(ColorChangeListener l) {
        listeners.add(l);
    }

    @Override
    public void removeColorChangeListener(ColorChangeListener l) {
        //TODO: concurrent modification exception?
        listeners.remove(l);
    }
}
