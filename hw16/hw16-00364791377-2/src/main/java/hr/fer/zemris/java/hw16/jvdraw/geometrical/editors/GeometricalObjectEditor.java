package hr.fer.zemris.java.hw16.jvdraw.geometrical.editors;

import javax.swing.*;

public abstract class GeometricalObjectEditor extends JPanel {

    public abstract void checkEditing();

    public abstract void acceptEditing();

    int[] checkParsable(String[] toParse) {
        int[] parsed = new int[toParse.length];
        for (int i = 0; i < toParse.length; i++) {
            try {
                parsed[i] = Integer.parseInt(toParse[i]);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Not an integer: " + toParse[i]);
            }
        }
        return parsed;
    }
}
