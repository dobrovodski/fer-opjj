package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.geometrical.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class JDrawingCanvas extends JComponent implements DrawingModelListener {
    private DrawingModel source;
    private GeometricalObjectPainter painter = new GeometricalObjectPainter();
    private Tool currentState;

    public JDrawingCanvas(DrawingModel source) {
        this.source = source;

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentState == null) {
                    return;
                }

                currentState.mouseReleased(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (currentState == null) {
                    return;
                }

                currentState.mouseMoved(e);
                repaint();
            }
        };

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                adapter.mouseMoved(e);
            }
        });
        addMouseListener(adapter);
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        this.source = source;
        repaint();
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        this.source = source;
        repaint();
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        this.source = source;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        if (currentState == null) {
            return;
        }

        g.clearRect(0, 0, getWidth(), getHeight());
        painter.setG2d((Graphics2D) g);

        for (int i = 0; i < source.getSize(); i++) {
            source.getObject(i).accept(painter);
        }

        currentState.paint((Graphics2D) g);
    }

    public void setCurrentState(Tool currentState) {
        this.currentState = currentState;
    }
}
