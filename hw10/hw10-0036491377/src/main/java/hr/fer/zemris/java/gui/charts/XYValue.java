package hr.fer.zemris.java.gui.charts;

import java.util.Objects;

/**
 * Holds information about a data point in a bar chart. It 2 pieces of information: an x and a y value.
 *
 * @author matej
 */
public class XYValue {
    /**
     * X value.
     */
    private int x;
    /**
     * Y value.
     */
    private int y;

    /**
     * Constructor.
     *
     * @param x x value
     * @param y y value
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the stored x value.
     *
     * @return x value
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the stored y value.
     *
     * @return y value
     */
    public int getY() {
        return y;
    }

    /**
     * Converts string to {@link XYValue} if it is possible. The string has to be formatted like "x,y" where x is the x
     * value and y is the y value of the newly created {@link XYValue}.
     *
     * @param str string to convert
     *
     * @return created XYValue
     *
     * @throws IllegalArgumentException if the string could not be parsed to an {@link XYValue}
     */
    public static XYValue fromString(String str) {
        Objects.requireNonNull(str, "Cannot parse null.");

        str = str.replace("\\s", "");
        String[] parts = str.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Could not parse as XYValue: " + str);
        }
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        return new XYValue(x, y);
    }
}
