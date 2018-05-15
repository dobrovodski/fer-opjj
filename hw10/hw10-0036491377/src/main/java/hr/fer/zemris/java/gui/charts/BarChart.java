package hr.fer.zemris.java.gui.charts;

public class BarChart {
    private XYValue values;
    String xDescription;
    String yDescription;
    int minY;
    int maxY;
    int gapY;

    public BarChart(XYValue values, String xDescription, String yDescription, int minY, int maxY, int gapY) {
        this.values = values;
        this.xDescription = xDescription;
        this.yDescription = yDescription;
        this.minY = minY;
        this.maxY = maxY;
        this.gapY = gapY;
    }
}
