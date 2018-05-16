package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import java.awt.*;

public class PrimDemo extends JFrame {

    public PrimDemo() {
        setLocation(20, 50);
        setSize(500, 300);
        setTitle("Prim Demo");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new PrimDemo();
            frame.setVisible(true);
        });
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        PrimListModel model = new PrimListModel();

        JList<Integer> list1 = new JList<>(model);
        JList<Integer> list2 = new JList<>(model);

        JButton next = new JButton("Sljedeći");
        next.addActionListener(e -> {
            model.next();
        });

        JPanel panel = new JPanel(new GridLayout(1, 0));
        panel.add(new JScrollPane(list1));
        panel.add(new JScrollPane(list2));
        cp.add(panel, BorderLayout.CENTER);
        cp.add(next, BorderLayout.SOUTH);
    }
}
