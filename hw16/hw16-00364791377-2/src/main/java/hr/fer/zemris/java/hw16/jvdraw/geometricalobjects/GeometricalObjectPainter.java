package hr.fer.zemris.java.hw16.jvdraw.geometricalobjects;

import java.awt.*;

public class GeometricalObjectPainter implements GeometricalObjectVisitor {
    private Graphics2D g2d;

    public GeometricalObjectPainter(Graphics2D g2d) {
        this.g2d = g2d;
    }

    @Override
    public void visit(Line line) {

    }

    @Override
    public void visit(Circle circle) {

    }

    @Override
    public void visit(FilledCircle filledCircle) {

    }
}
