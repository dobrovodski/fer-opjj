package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.GeometricalObject;

public interface DrawingModel {
    int getSize();

    GeometricalObject getObject(int index);

    void add(GeometricalObject object);

    void addDrawingModelListener(DrawingModelListener l);

    void removeDrawingModelListener(DrawingModelListener l);

    void remove(GeometricalObject object);

    void changeOrder(GeometricalObject object, int offset);
}
