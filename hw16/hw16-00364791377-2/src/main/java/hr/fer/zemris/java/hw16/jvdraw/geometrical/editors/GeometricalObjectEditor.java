package hr.fer.zemris.java.hw16.jvdraw.geometrical.editors;

import javax.swing.*;

public abstract class GeometricalObjectEditor extends JPanel {
    public GeometricalObjectEditor() {

    }

    public abstract void checkEditing();

    public abstract void acceptEditing();
}
