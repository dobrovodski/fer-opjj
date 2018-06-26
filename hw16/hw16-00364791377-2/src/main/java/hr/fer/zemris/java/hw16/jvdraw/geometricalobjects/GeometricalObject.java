package hr.fer.zemris.java.hw16.jvdraw.geometricalobjects;


import java.util.List;

abstract class GeometricalObject {
    private List<GeometricalObjectListener> listeners;

    public abstract void accept(GeometricalObjectVisitor v);

    public abstract GeometricalObjectEditor createGeometricalObjectEditor();

    public void addGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.add(l);
    }

    public void removeGeometricalObjectListener(GeometricalObjectListener l) {
        //TODO: concurrent modification
        listeners.remove(l);
    }
}