package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;


import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JStatusBar extends JPanel {
    private LJLabel lengthNameLabel;
    private JLabel lengthLabel;
    private JLabel lineLabel;
    private JLabel colLabel;
    private JLabel selLabel;
    private JLabel clockLabel;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    private ILocalizationProvider lp;
    private boolean stopClock = false;

    private CaretListener caretListener;

    public JStatusBar(ILocalizationProvider lp) {
        this.lp = lp;
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(new BevelBorder(BevelBorder.LOWERED));

        caretListener = e -> {
            JTextArea area = (JTextArea) e.getSource();
            setLengthLabel(area.getText().length());

            int pos = area.getCaretPosition();
            int line;
            try {
                line = area.getLineOfOffset(pos);
                setLineLabel(line + 1);
                setColLabel(pos - area.getLineStartOffset(line) + 1);
                setSelLabel(Math.abs(e.getDot() - e.getMark()));
            } catch (BadLocationException ignored) {
            }
        };

        lengthNameLabel = new LJLabel("length", lp);
        lengthLabel = new JLabel();
        setLengthLabel(0);
        lineLabel = new JLabel();
        setLineLabel(0);
        colLabel = new JLabel();
        setColLabel(0);
        selLabel = new JLabel();
        setSelLabel(0);
        clockLabel = new JLabel();
        setTimeLabel();

        add(Box.createRigidArea(new Dimension(10, 0)));
        add(lengthNameLabel);
        add(lengthLabel);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(new JSeparator(SwingConstants.VERTICAL));
        add(lineLabel);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(colLabel);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(selLabel);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(clockLabel);
        add(Box.createRigidArea(new Dimension(5, 0)));

        startClock();
    }

    private void startClock() {
        Thread t = new Thread(() -> {
            while (!stopClock) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
                SwingUtilities.invokeLater(this::setTimeLabel);
            }
        });

        t.setDaemon(true);
        t.start();
    }

    public void setLengthLabel(int length) {
        lengthLabel.setText(": " + length);
    }

    public void setLineLabel(int length) {
        lineLabel.setText("Ln: " + length);
    }

    public void setColLabel(int length) {
        colLabel.setText("Col: " + length);
    }

    public void setSelLabel(int length) {
        selLabel.setText("Sel: " + length);
    }

    public void stopClock() {
        stopClock = true;
    }

    private void setTimeLabel() {
        clockLabel.setText(formatter.format(LocalDateTime.now()));
    }

    public CaretListener getCaretListener() {
        return caretListener;
    }
}