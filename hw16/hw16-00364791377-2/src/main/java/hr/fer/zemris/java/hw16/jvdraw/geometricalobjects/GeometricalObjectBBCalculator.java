package hr.fer.zemris.java.hw16.jvdraw.geometricalobjects;

import java.awt.*;

public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
    Rectangle boundingBox = new Rectangle();

    @Override
    public void visit(Line line) {
        updateBoundingBox(0, 0, 0, 0);
    }

    @Override
    public void visit(Circle circle) {
        updateBoundingBox(0, 0, 0, 0);
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        updateBoundingBox(0, 0, 0, 0);
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
