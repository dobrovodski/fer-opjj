package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;


import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Status bar used to display information about current document (length, line, column, selected area) and the current
 * time.
 *
 * @author matej
 */
public class LJStatusBar extends JPanel {
    /**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
    /**
     * Length of file label.
     */
    private JLabel lengthLabel;
    /**
     * Line number label.
     */
    private JLabel lineLabel;
    /**
     * Column label.
     */
    private JLabel colLabel;
    /**
     * Selected area label.
     */
    private JLabel selLabel;
    /**
     * Clock label.
     */
    private JLabel clockLabel;
    /**
     * Date time formatter.
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    /**
     * Flag used to stop the clock.
     */
    private boolean stopClock = false;
    /**
     * Caret listener.
     */
    private CaretListener caretListener;

    /**
     * Constructor.
     *
     * @param lp localization provider
     */
    public LJStatusBar(ILocalizationProvider lp) {
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

        LJLabel lengthNameLabel = new LJLabel("length", lp);
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

    /**
     * Starts the clock on a separate thread.
     */
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

    /**
     * Sets content of length label.
     *
     * @param length length
     */
    private void setLengthLabel(int length) {
        lengthLabel.setText(": " + length);
    }

    /**
     * Sets content of line label.
     *
     * @param line line
     */
    private void setLineLabel(int line) {
        lineLabel.setText("Ln: " + line);
    }

    /**
     * Sets content of column label.
     *
     * @param col column
     */
    private void setColLabel(int col) {
        colLabel.setText("Col: " + col);
    }

    /**
     * Sets content of selected label.
     *
     * @param sel selected
     */
    private void setSelLabel(int sel) {
        selLabel.setText("Sel: " + sel);
    }

    /**
     * Sets the flag for the clock to stop.
     */
    public void stopClock() {
        stopClock = true;
    }

    /**
     * Sets content of time label.
     */
    private void setTimeLabel() {
        clockLabel.setText(formatter.format(LocalDateTime.now()));
    }

    /**
     * Returns caret listener.
     *
     * @return caret listener
     */
    public CaretListener getCaretListener() {
        return caretListener;
    }
}