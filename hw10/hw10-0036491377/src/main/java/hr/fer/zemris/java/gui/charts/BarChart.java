package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Holds necessary information to display a bar chart.
 *
 * @author matej
 */
public class BarChart {
    /**
     * Values of the bar chart.
     */
    private List<XYValue> values;
    /**
     * X-axis description.
     */
    private String xDescription;
    /**
     * Y-axis description.
     */
    private String yDescription;
    /**
     * Smallest value of y-axis.
     */
    private int minY;
    /**
     * Largest value of x-axis.
     */
    private int maxY;
    /**
     * Gap between values on y-axis.
     */
    private int gapY;

    /**
     * Constructor.
     *
     * @param values values to store in the bar chart
     * @param xDescription x-axis description
     * @param yDescription y-axis description
     * @param minY smallest value of y-axis
     * @param maxY largest value of x-axis
     * @param gapY gap between values on y-axis
     */
    public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int gapY) {
        this.values = values;
        this.xDescription = xDescription;
        this.yDescription = yDescription;
        this.minY = minY;
        this.maxY = maxY;
        this.gapY = gapY;
    }

    /**
     * Returns the values.
     *
     * @return values stored in bar chart
     */
    public List<XYValue> getValues() {
        return values;
    }

    /**
     * Returns the description of x-axis
     *
     * @return x-axis description
     */
    public String getxDescription() {
        return xDescription;
    }

    /**
     * Returns the description of y-axis
     *
     * @return y-axis description
     */
    public String getyDescription() {
        return yDescription;
    }

    /**
     * Returns smallest value of y-axis
     *
     * @return smallest value of y-axis
     */
    public int getMinY() {
        return minY;
    }

    /**
     * Returns largest value of y-axis
     *
     * @return slargest value of y-axis
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Returns gap between values on y-axis
     *
     * @return gap between values on y-axis
     */
    public int getGapY() {
        return gapY;
    }
}
