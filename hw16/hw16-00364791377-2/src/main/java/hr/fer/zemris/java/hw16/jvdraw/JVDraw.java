package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
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

        JToolBar tb = new JToolBar();

        JColorArea fg = new JColorArea(Color.CYAN);
        JColorArea bg = new JColorArea(Color.CYAN);
        tb.add(fg);
        tb.add(bg);
        tb.addSeparator();

        ButtonGroup buttonGroup = new ButtonGroup();
        JToggleButton lineButton = new JToggleButton("Line");
        JToggleButton circleButton = new JToggleButton("Circle");
        JToggleButton fillCircleButton = new JToggleButton("Fill Circle");

        buttonGroup.add(lineButton);
        buttonGroup.add(circleButton);
        buttonGroup.add(fillCircleButton);
        tb.add(lineButton);
        tb.add(circleButton);
        tb.add(fillCircleButton);

        cp.add(tb, BorderLayout.NORTH);

        DrawingModel model = new DrawingModelImpl();
        JDrawingCanvas canvas = new JDrawingCanvas(model);
        canvas.setCurrentState(new LineTool(model, fg));
        model.addDrawingModelListener(canvas);
        cp.add(canvas, BorderLayout.CENTER);
        canvas.repaint();
    }
}
