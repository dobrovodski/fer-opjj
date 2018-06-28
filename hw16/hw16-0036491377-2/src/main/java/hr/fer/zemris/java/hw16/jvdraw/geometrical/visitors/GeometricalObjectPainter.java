package hr.fer.zemris.java.hw16.jvdraw.geometrical.visitors;

import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.Line;

import java.awt.*;

public class GeometricalObjectPainter implements GeometricalObjectVisitor {
    private Graphics2D g2d;

    public void setG2d(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.g2d = g2d;
    }

    @Override
    public void visit(Line line) {
        g2d.setColor(line.getColor());
        g2d.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    @Override
    public void visit(Circle circle) {
        g2d.setColor(circle.getColor());
        int startX = circle.getX1(), endX = circle.getX2();
        int startY = circle.getY1(), endY = circle.getY2();
        int r = Math.min(Math.abs(startX - endX), Math.abs(startY - endY));

        int x = startX < endX ? startX : endX;
        if (x < startX - r)
            x = startX - r;

        int y = startY < endY ? startY : endY;
        if (y < startY - r)
            y = startY - r;
        g2d.drawOval(x, y, r, r);
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        int startX = filledCircle.getX1(), endX = filledCircle.getX2();
        int startY = filledCircle.getY1(), endY = filledCircle.getY2();
        int r = Math.min(Math.abs(startX - endX), Math.abs(startY - endY));

        int x = startX < endX ? startX : endX;
        if (x < startX - r)
            x = startX - r;

        int y = startY < endY ? startY : endY;
        if (y < startY - r)
            y = startY - r;

        g2d.setColor(filledCircle.getColor());
        g2d.drawOval(x, y, r, r);

        g2d.setColor(filledCircle.getFillColor());
        g2d.fillOval(x + 1, y + 1, r - 1, r - 1);
    }
}
