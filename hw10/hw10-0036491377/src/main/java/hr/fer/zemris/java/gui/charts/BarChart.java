package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {
    private List<XYValue> values;
    private String xDescription;
    private String yDescription;
    private int minY;
    private int maxY;
    private int gapY;

    public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int gapY) {
        this.values = values;
        this.xDescription = xDescription;
        this.yDescription = yDescription;
        this.minY = minY;
        this.maxY = maxY;
        this.gapY = gapY;
    }

    public List<XYValue> getValues() {
        return values;
    }

    public String getxDescription() {
        return xDescription;
    }

    public String getyDescription() {
        return yDescription;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getGapY() {
        return gapY;
    }
}
