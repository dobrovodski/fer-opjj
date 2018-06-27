package hr.fer.zemris.java.hw16.jvdraw.geometrical;

import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.GeometricalObject;

public interface GeometricalObjectListener {
    void geometricalObjectChanged(GeometricalObject o);
}