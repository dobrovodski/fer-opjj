package hr.fer.zemris.java.hw16.jvdraw.geometrical.objects;


import hr.fer.zemris.java.hw16.jvdraw.Util;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.visitors.GeometricalObjectVisitor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GeometricalObject {
    int x1;
    int y1;
    int x2;
    int y2;
    private Color color = Color.BLACK;
    private List<GeometricalObjectListener> listeners = new ArrayList<>();

    public GeometricalObject(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public static GeometricalObject fromString(String s) {
        String[] d = s.split(" ");
        int[] values = Util.checkParsable(Arrays.copyOfRange(d, 1, d.length));
        if (s.startsWith("LINE")) {
            Line l = new Line(values[0], values[1], values[2], values[3]);
            Color c = new Color(values[4], values[5], values[6]);
            l.setColor(c);

            return l;

        }
        if (s.startsWith("CIRCLE")) {
            int cx = values[0];
            int cy = values[1];
            int r = values[2];
            Circle circle = new Circle(cx - r, cy - r, cx + r, cy + r);
            Color c = new Color(values[3], values[4], values[5]);
            circle.setColor(c);

            return circle;
        }
        if (s.startsWith("FCIRCLE")) {
            int cx = values[0];
            int cy = values[1];
            int r = values[2];
            FilledCircle filledCircle = new FilledCircle(cx - r, cy - r, cx + r, cy + r);
            Color c = new Color(values[3], values[4], values[5]);
            Color f = new Color(values[6], values[7], values[8]);
            filledCircle.setColor(c);
            filledCircle.setFillColor(f);

            return filledCircle;
        }
        throw new IllegalArgumentException("Could not parse as valid object: " + s);
    }

    public abstract void accept(GeometricalObjectVisitor v);

    public abstract GeometricalObjectEditor createGeometricalObjectEditor();

    public void addGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.add(l);
    }

    public void removeGeometricalObjectListener(GeometricalObjectListener l) {
        //TODO: concurrent modification
        listeners.remove(l);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public abstract String saveFormat();
}