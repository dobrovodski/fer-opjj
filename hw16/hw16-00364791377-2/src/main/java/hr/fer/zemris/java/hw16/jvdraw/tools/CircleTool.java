package hr.fer.zemris.java.hw16.jvdraw.tools;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class CircleTool extends AbstractTool implements Tool {
    private DrawingModel model;
    private IColorProvider colorProvider;

    public CircleTool(DrawingModel model, IColorProvider colorProvider) {
        this.model = model;
        this.colorProvider = colorProvider;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        drawing = !drawing;

        if (!drawing) {
            Circle c = new Circle(startX, startY, endX, endY);
            c.setColor(colorProvider.getCurrentColor());
            model.add(c);
        }

        super.mouseReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(colorProvider.getCurrentColor());
        int r = Math.min(Math.abs(endX - startX), Math.abs(endY - startY));
        int x = startX < endX ? startX : endX;
        if (x < startX - r)
            x = startX - r;

        int y = startY < endY ? startY : endY;
        if (y < startY - r)
            y = startY - r;

        g2d.drawOval(x, y, r, r);
    }
}
