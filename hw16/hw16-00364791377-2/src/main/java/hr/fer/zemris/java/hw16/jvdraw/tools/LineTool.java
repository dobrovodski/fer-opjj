package hr.fer.zemris.java.hw16.jvdraw.tools;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class LineTool extends AbstractTool implements Tool {
    private DrawingModel model;
    private IColorProvider colorProvider;

    public LineTool(DrawingModel model, IColorProvider colorProvider) {
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
            Line l = new Line(startX, startY, endX, endY);
            l.setColor(colorProvider.getCurrentColor());
            model.add(l);
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
        g2d.drawLine(startX, startY, endX, endY);
    }
}
