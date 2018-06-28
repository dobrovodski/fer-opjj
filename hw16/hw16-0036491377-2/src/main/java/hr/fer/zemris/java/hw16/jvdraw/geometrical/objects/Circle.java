package hr.fer.zemris.java.hw16.jvdraw.geometrical.objects;

import hr.fer.zemris.java.hw16.jvdraw.geometrical.editors.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.visitors.GeometricalObjectVisitor;

public class Circle extends GeometricalObject {
    public Circle(int x0, int y0, int x1, int y1) {
        super(x0, y0, x1, y1);
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new CircleEditor(this);
    }

    @Override
    public String saveFormat() {
        return String.format("CIRCLE %d %d %d %d %d %d",
                Math.abs(x1 + x2) / 2, (y1 + y2) / 2, Math.abs(x1 - x2) / 2,
                getColor().getRed(), getColor().getGreen(), getColor().getBlue());
    }

    @Override
    public String toString() {
        return String.format("Circle (%d,%d), %d", Math.abs(x1 + x2) / 2, (y1 + y2) / 2, Math.abs(x1 - x2) / 2);
    }
}
