package hr.fer.zemris.java.hw16.jvdraw.geometrical.editors;

import hr.fer.zemris.java.hw16.jvdraw.Util;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.Circle;

import javax.swing.*;
import java.awt.*;

public class CircleEditor extends GeometricalObjectEditor {
    private Circle circle;
    private JTextField fieldx, fieldy, fieldr;
    private JColorArea colorArea;
    private int[] newValues;

    public CircleEditor(Circle circle) {
        this.circle = circle;

        setLayout(new GridLayout(4, 2));

        add(new JLabel("Center x:"));
        fieldx = new JTextField(String.valueOf((circle.getX1() + circle.getX2()) / 2));
        add(fieldx);

        add(new JLabel("Center y:"));
        fieldy = new JTextField(String.valueOf((circle.getY1() + circle.getY2()) / 2));
        add(fieldy);

        add(new JLabel("Radius:"));
        fieldr = new JTextField(String.valueOf(Math.abs(circle.getX1() - circle.getX2()) / 2));
        add(fieldr);

        add(new JLabel("Color: "));
        colorArea = new JColorArea(circle.getColor());
        add(colorArea);
    }

    @Override
    public void checkEditing() {
        newValues = Util.checkParsable(new String[]{
                fieldx.getText(),
                fieldy.getText(),
                fieldr.getText()});

        for (int v : newValues) {
            if (v < 0) {
                throw new IllegalArgumentException("Can't accept negative numbers.");
            }
        }
    }

    @Override
    public void acceptEditing() {
        circle.setX1(newValues[0] - newValues[2]);
        circle.setY1(newValues[1] - newValues[2]);
        circle.setX2(newValues[0] + newValues[2]);
        circle.setY2(newValues[1] + newValues[2]);

        if (colorArea.getCurrentColor() != null) {
            circle.setColor(colorArea.getCurrentColor());
        }
    }
}
