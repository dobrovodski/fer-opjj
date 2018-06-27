package hr.fer.zemris.java.hw16.jvdraw.tools;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class FilledCircleTool implements Tool {
    private DrawingModel model;
    private IColorProvider colorProvider;
    private boolean drawing = false;

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public FilledCircleTool(DrawingModel model, IColorProvider colorProvider) {
        this.model = model;
        this.colorProvider = colorProvider;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        drawing = !drawing;

        if (!drawing) {
            model.add(new FilledCircle(startX, startY, e.getX(), e.getY()));
            endX = e.getX();
            endY = e.getY();
        } else {
            startX = e.getX();
            startY = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (drawing) {
            endX = e.getX();
            endY = e.getY();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void paint(Graphics2D g2d) {
        int r = (endX - startX);
        g2d.setColor(colorProvider.getCurrentColor());
        g2d.fillOval(startX, startY, r, r);
    }
}
