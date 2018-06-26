package hr.fer.zemris.java.hw16.jvdraw.geometricalobjects;

import javax.swing.*;

abstract class GeometricalObjectEditor extends JPanel {
    public abstract void checkEditing();

    public abstract void acceptEditing();
}
