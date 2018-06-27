package hr.fer.zemris.java.hw16.jvdraw.geometrical.editors;

import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.Line;

import javax.swing.*;
import java.awt.*;

public class LineEditor extends GeometricalObjectEditor {
    private Line line;

    public LineEditor(Line line) {
        this.line = line;

        setLayout(new GridLayout(4, 1));

        add(new JTextField(String.valueOf(line.getX1())));
        add(new JTextField(String.valueOf(line.getY1())));
        add(new JTextField(String.valueOf(line.getX2())));
        add(new JTextField(String.valueOf(line.getY2())));
    }

    @Override
    public void checkEditing() {

    }

    @Override
    public void acceptEditing() {

    }
}
