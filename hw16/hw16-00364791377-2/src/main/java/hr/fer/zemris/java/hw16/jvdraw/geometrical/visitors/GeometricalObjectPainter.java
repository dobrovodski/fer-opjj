package hr.fer.zemris.java.hw16.jvdraw.geometrical.visitors;

import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.Line;

import java.awt.*;

public class GeometricalObjectPainter implements GeometricalObjectVisitor {
    private Graphics2D g2d;

    public GeometricalObjectPainter(Graphics2D g2d) {
        this.g2d = g2d;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    public void visit(Line line) {
        g2d.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    @Override
    public void visit(Circle circle) {
        int r = (circle.getX2() - circle.getX1()) / 2;
        //TODO: this is probably broken so come back to fix it when you notice
        g2d.drawOval(circle.getX1(), circle.getY1(), r, r);
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        int r = (filledCircle.getX2() - filledCircle.getX1()) / 2;
        g2d.fillOval(filledCircle.getX1(), filledCircle.getY1(), r, r);
    }
}
