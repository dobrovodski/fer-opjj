package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;

import javax.swing.*;
import java.awt.*;

public class JVDraw extends JFrame {
    public JVDraw() {
        setLocation(50, 50);
        setSize(600, 600);
        setTitle("JVDraw");
        initGUI();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException ignored) {
        }

        SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
    }

    private void initGUI() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JColorArea a = new JColorArea(Color.CYAN);
        JButton b = new JButton();

        b.add(a);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
        b.setBackground(a.getCurrentColor());
        a.addColorChangeListener((x, y, z) -> {
            b.setBackground(z);
        });

        cp.add(b, BorderLayout.CENTER);
    }
}
