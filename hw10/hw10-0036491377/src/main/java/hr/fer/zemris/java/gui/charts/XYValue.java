package hr.fer.zemris.java.gui.charts;

import java.util.Objects;

public class XYValue {
    private int x;
    private int y;

    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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
