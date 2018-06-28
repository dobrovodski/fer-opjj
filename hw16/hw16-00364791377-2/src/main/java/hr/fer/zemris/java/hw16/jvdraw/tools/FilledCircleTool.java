package hr.fer.zemris.java.hw16.jvdraw.tools;

import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class FilledCircleTool extends AbstractTool implements Tool {
    private DrawingModel model;
    private IColorProvider fgColorProvider;
    private IColorProvider bgColorProvider;

    public FilledCircleTool(DrawingModel model, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
        this.model = model;
        this.fgColorProvider = fgColorProvider;
        this.bgColorProvider = bgColorProvider;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        drawing = !drawing;

        if (!drawing) {
            FilledCircle fc = new FilledCircle(startX, startY, endX, endY);
            fc.setColor(fgColorProvider.getCurrentColor());
            fc.setFillColor(bgColorProvider.getCurrentColor());
            model.add(fc);
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
        int r = Math.min(Math.abs(endX - startX), Math.abs(endY - startY));
        int x = startX < endX ? startX : endX;
        if (x < startX - r)
            x = startX - r;

        int y = startY < endY ? startY : endY;
        if (y < startY - r)
            y = startY - r;
        g2d.setColor(fgColorProvider.getCurrentColor());
        g2d.drawOval(x, y, r, r);

        g2d.setColor(bgColorProvider.getCurrentColor());
        g2d.fillOval(x + 1, y + 1, r - 1, r - 1);
    }
}
