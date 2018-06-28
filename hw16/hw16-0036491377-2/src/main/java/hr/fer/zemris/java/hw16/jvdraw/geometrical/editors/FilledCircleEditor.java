package hr.fer.zemris.java.hw16.jvdraw.geometrical.editors;

import hr.fer.zemris.java.hw16.jvdraw.Util;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geometrical.objects.FilledCircle;

import javax.swing.*;
import java.awt.*;

public class FilledCircleEditor extends GeometricalObjectEditor {
    private FilledCircle filledCircle;
    private JTextField fieldx, fieldy, fieldr;
    private JColorArea bg;
    private JColorArea fg;
    private int[] newValues;

    public FilledCircleEditor(FilledCircle filledCircle) {
        this.filledCircle = filledCircle;

        setLayout(new GridLayout(5, 2));

        add(new JLabel("Center x:"));
        fieldx = new JTextField(String.valueOf((filledCircle.getX1() + filledCircle.getX2()) / 2));
        add(fieldx);

        add(new JLabel("Center y:"));
        fieldy = new JTextField(String.valueOf((filledCircle.getY1() + filledCircle.getY2()) / 2));
        add(fieldy);

        add(new JLabel("Radius:"));
        fieldr = new JTextField(String.valueOf(Math.abs(filledCircle.getX1() - filledCircle.getX2()) / 2));
        add(fieldr);

        add(new JLabel("Color: "));
        bg = new JColorArea(filledCircle.getColor());
        add(bg);

        add(new JLabel("Color: "));
        fg = new JColorArea(filledCircle.getFillColor());
        add(fg);
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
        filledCircle.setX1(newValues[0] - newValues[2]);
        filledCircle.setY1(newValues[1] - newValues[2]);
        filledCircle.setX2(newValues[0] + newValues[2]);
        filledCircle.setY2(newValues[1] + newValues[2]);

        if (bg.getCurrentColor() != null) {
            filledCircle.setColor(bg.getCurrentColor());
        }

        if (fg.getCurrentColor() != null) {
            filledCircle.setFillColor(fg.getCurrentColor());
        }
    }
}
