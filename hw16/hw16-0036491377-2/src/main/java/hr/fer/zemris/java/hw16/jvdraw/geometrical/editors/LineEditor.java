package hr.fer.zemris.java.hw16.jvdraw.geometrical.editors;

import hr.fer.zemris.java.hw16.jvdraw.Util;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.Line;

import javax.swing.*;
import java.awt.*;

public class LineEditor extends GeometricalObjectEditor {
    private Line line;
    private JTextField fieldx1, fieldy1, fieldx2, fieldy2;
    private JColorArea colorArea;
    private int[] newValues;

    public LineEditor(Line line) {
        this.line = line;

        setLayout(new GridLayout(5, 2));

        add(new JLabel("Line start x:"));
        fieldx1 = new JTextField(String.valueOf(line.getX1()));
        add(fieldx1);

        add(new JLabel("Line start y:"));
        fieldy1 = new JTextField(String.valueOf(line.getY1()));
        add(fieldy1);

        add(new JLabel("Line end x:"));
        fieldx2 = new JTextField(String.valueOf(line.getX2()));
        add(fieldx2);

        fieldy2 = new JTextField(String.valueOf(line.getY2()));
        add(new JLabel("Line end y:"));
        add(fieldy2);

        add(new JLabel("Color: "));
        colorArea = new JColorArea(line.getColor());
        add(colorArea);

    }

    @Override
    public void checkEditing() {
        newValues = Util.checkParsable(new String[]{
                fieldx1.getText(),
                fieldy1.getText(),
                fieldx2.getText(),
                fieldy2.getText()});

        for (int v : newValues) {
            if (v < 0) {
                throw new IllegalArgumentException("Can't accept negative numbers.");
            }
        }

    }

    @Override
    public void acceptEditing() {
        line.setX1(newValues[0]);
        line.setY1(newValues[1]);
        line.setX2(newValues[2]);
        line.setY2(newValues[3]);

        if (colorArea.getCurrentColor() != null) {
            line.setColor(colorArea.getCurrentColor());
        }
    }
}
