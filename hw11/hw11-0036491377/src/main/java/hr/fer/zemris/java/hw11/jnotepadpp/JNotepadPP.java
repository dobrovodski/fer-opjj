package hr.fer.zemris.java.hw11.jnotepadpp;


import javax.swing.*;
import java.awt.*;

public class JNotepadPP extends JFrame {
    public static void main(String[] args) {
        System.out.println("owo");
        SwingUtilities.invokeLater(() -> {
            new JNotepadPP().setVisible(true);
        });
    }

    public JNotepadPP() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(50, 50);
        setSize(600, 600);
        initGUI();
    }

    private void initGUI() {
        JTextArea editor = new JTextArea("owo");
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(new JScrollPane(editor), BorderLayout.CENTER);

    }

    private void createActions() {

    }

    private void createMenus() {

    }

    private void createToolbars() {

    }
}
