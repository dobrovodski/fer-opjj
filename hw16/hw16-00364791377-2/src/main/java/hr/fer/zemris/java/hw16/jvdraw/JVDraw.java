package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.swing.JObjectList;
import hr.fer.zemris.java.hw16.jvdraw.swing.JStatusBar;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;

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

        DrawingModel model = new DrawingModelImpl();
        JDrawingCanvas canvas = new JDrawingCanvas(model);
        model.addDrawingModelListener(canvas);
        cp.add(canvas, BorderLayout.CENTER);

        JToolBar tb = new JToolBar();
        tb.setFloatable(false);

        JColorArea bg = new JColorArea(Color.WHITE);
        JColorArea fg = new JColorArea(Color.BLACK);
        tb.add(bg);
        tb.addSeparator(new Dimension(5, 0));
        tb.add(fg);

        tb.addSeparator();

        ButtonGroup buttonGroup = new ButtonGroup();
        JToggleButton lineButton = new JToggleButton("Line");
        JToggleButton circleButton = new JToggleButton("Circle");
        JToggleButton fillCircleButton = new JToggleButton("Fill Circle");

        lineButton.addActionListener(e -> {
            JToggleButton b = (JToggleButton) e.getSource();
            if (b.isSelected()) {
                canvas.setCurrentState(new LineTool(model, fg));
            }
        });
        circleButton.addActionListener(e -> {
            JToggleButton b = (JToggleButton) e.getSource();
            if (b.isSelected()) {
                canvas.setCurrentState(new CircleTool(model, fg));
            }
        });
        fillCircleButton.addActionListener(e -> {
            JToggleButton b = (JToggleButton) e.getSource();
            if (b.isSelected()) {
                canvas.setCurrentState(new FilledCircleTool(model, fg, bg));
            }
        });

        buttonGroup.add(lineButton);
        buttonGroup.add(circleButton);
        buttonGroup.add(fillCircleButton);
        tb.add(lineButton);
        tb.add(circleButton);
        tb.add(fillCircleButton);

        cp.add(tb, BorderLayout.NORTH);

        cp.add(new JScrollPane(new JObjectList(model)), BorderLayout.EAST);

        cp.add(new JStatusBar(fg, bg), BorderLayout.SOUTH);
        canvas.repaint();
    }
}
