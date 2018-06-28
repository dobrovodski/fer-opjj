package hr.fer.zemris.java.hw16.jvdraw.geometrical.objects;

import hr.fer.zemris.java.hw16.jvdraw.geometrical.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.editors.LineEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.visitors.GeometricalObjectVisitor;

public class Line extends GeometricalObject {
    public Line(int x0, int y0, int x1, int y1) {
        super(x0, y0, x1, y1);
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new LineEditor(this);
    }

    @Override
    public String saveFormat() {
        return String.format("LINE %d %d %d %d %d %d %d",
                x1, y1, x2, y2,
                getColor().getRed(), getColor().getGreen(), getColor().getBlue());
    }

    @Override
    public String toString() {
        return String.format("Line (%d,%d)-(%d,%d)", x1, y1, x2, y2);
    }
}
