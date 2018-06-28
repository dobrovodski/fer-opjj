package hr.fer.zemris.java.hw16.jvdraw.geometrical.visitors;

import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.Line;

import java.awt.*;

public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
    private Rectangle boundingBox = new Rectangle();

    @Override
    public void visit(Line line) {
        updateBoundingBox(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    @Override
    public void visit(Circle circle) {
        updateBoundingBox(circle.getX1(), circle.getY1(), circle.getX2(), circle.getY2());
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        updateBoundingBox(filledCircle.getX1(), filledCircle.getY1(), filledCircle.getX2(), filledCircle.getY2());
    }

    private void updateBoundingBox(int lowerX, int lowerY, int upperX, int upperY) {
        boundingBox.x = Math.min(boundingBox.x, lowerX);
        boundingBox.y = Math.min(boundingBox.y, lowerY);
        boundingBox.width = Math.max(boundingBox.width, upperX);
        boundingBox.height = Math.max(boundingBox.height, upperY);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }
}
