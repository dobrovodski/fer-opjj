package hr.fer.zemris.java.hw16.jvdraw.geometrical.objects;

import hr.fer.zemris.java.hw16.jvdraw.geometrical.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.visitors.GeometricalObjectVisitor;

import java.awt.*;

public class FilledCircle extends GeometricalObject {
    private Color fillColor;

    public FilledCircle(int x0, int y0, int x1, int y1) {
        super(x0, y0, x1, y1);
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new FilledCircleEditor(this);
    }

    @Override
    public String saveFormat() {
        return String.format("FCIRCLE %d %d %d %d %d %d %d %d %d",
                Math.abs(x1 + x2) / 2, (y1 + y2) / 2, Math.abs(x1 - x2) / 2,
                getColor().getRed(), getColor().getGreen(), getColor().getBlue(),
                getFillColor().getRed(), getFillColor().getBlue(), getFillColor().getGreen());
    }

    @Override
    public String toString() {
        String color = String.format("#%02x%02x%02x", fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
        return String.format("Circle (%d,%d), %d, %s",
                (x1 + x2) / 2, (y1 + y2) / 2, Math.abs(x1 - x2) / 2, color.toUpperCase());
    }
}
