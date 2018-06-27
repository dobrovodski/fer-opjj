package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.GeometricalObject;

import java.util.ArrayList;
import java.util.List;

public class DrawingModelImpl implements DrawingModel {
    private List<GeometricalObject> objects = new ArrayList<>();
    private List<DrawingModelListener> listeners = new ArrayList<>();

    @Override
    public int getSize() {
        return objects.size();
    }

    @Override
    public GeometricalObject getObject(int index) {
        return objects.get(index);
    }

    @Override
    public void add(GeometricalObject object) {
        objects.add(object);
        int index = objects.indexOf(object);

        object.addGeometricalObjectListener(o -> {
            for (DrawingModelListener l : listeners) {
                l.objectsChanged(this, index, index);
            }
        });

        for (DrawingModelListener l : listeners) {
            l.objectsAdded(this, index, index);
        }
    }

    @Override
    public void addDrawingModelListener(DrawingModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener l) {
        listeners.remove(l);
    }

    @Override
    public void remove(GeometricalObject object) {
        objects.remove(object);

        int index = objects.indexOf(object);
        for (DrawingModelListener l : listeners) {
            l.objectsRemoved(this, index, index);
        }
    }

    @Override
    public void changeOrder(GeometricalObject object, int offset) {
        int index = objects.indexOf(object);
        if (index < 0) {
            //TODO: what do here?
            return;
        }
        objects.remove(object);
        //TODO: might be wrong
        objects.add(index + offset, object);

        for (DrawingModelListener l : listeners) {
            l.objectsChanged(this, index, index + offset);
        }
    }
}
