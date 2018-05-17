package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

/**
 * Graphical component which displays the provided {@link BarChart}.
 *
 * @author matej
 */
public class BarChartComponent extends JComponent {
    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Bar chart to display.
     */
    private BarChart barChart;

    /**
     * Width of the component.
     */
    private int w;
    /**
     * Height of the component.
     */
    private int h;

    /**
     * Vertical padding.
     */
    private static final int VERTICAL_PAD = 30;
    /**
     * Horizontal padding.
     */
    private static final int HORIZONTAL_PAD = 30;
    /**
     * Size of the axis-arrow.
     */
    private static final int ARROW_SIZE = 10;
    /**
     * Margin between y-axis description and y-axis.
     */
    private static final int DESCRIPTION_MARGIN = 20;
    /**
     * Gap between bars.
     */
    private static final int BAR_DISTANCE = 2;

    /**
     * Current font size.
     */
    private int fontSize;
    /**
     * Font metrics.
     */
    private FontMetrics metrics;
    /**
     * Bar chart values.
     */
    private List<XYValue> values;
    /**
     * Current {@link GraphicsState}.
     */
    private GraphicsState graphicsState;

    /**
     * Color of bars.
     */
    private static final Color BAR_COLOR = new Color(244, 119, 72);
    /**
     * Color of grid lines.
     */
    private static final Color GRID_COLOR = new Color(238, 221, 190);
    /**
     * Color of axes lines.
     */
    private static final Color AXES_COLOR = new Color(151, 151, 151);
    /**
     * Color of labels.
     */
    private static final Color LABEL_COLOR = new Color(0, 0, 0);
    /**
     * Color of axes descriptions.
     */
    private static final Font DESCRIPTION_FONT = new Font("default", Font.PLAIN, 16);
    /**
     * Label font.
     */
    private static final Font LABEL_FONT = new Font("default", Font.BOLD, 16);

    /**
     * Constructor.
     *
     * @param barChart bar chart to display
     */
    public BarChartComponent(BarChart barChart) {
        this.barChart = barChart;
        values = barChart.getValues();
    }

    @Override
    protected void paintComponent(Graphics g) {
        metrics = g.getFontMetrics(g.getFont());
        fontSize = metrics.getHeight();
        w = getWidth();
        h = getHeight();

        paintComponent((Graphics2D) g);
    }

    /**
     * Paints component using given {@link Graphics2D} object.
     *
     * @param g2d graphics object used to draw
     */
    private void paintComponent(Graphics2D g2d) {
        drawChart(g2d);
        drawAxes(g2d);
    }

    /**
     * Draws the axes of the bar chart
     *
     * @param g2d graphics2d object
     */
    private void drawAxes(Graphics2D g2d) {
        final int padX = HORIZONTAL_PAD + maxYWidth() + fontSize +
                         DESCRIPTION_MARGIN;
        final int padY = VERTICAL_PAD + fontSize;

        saveGraphicsState(g2d);
        setGraphicsState(g2d, graphicsState.font, AXES_COLOR, new BasicStroke(2));
        // y-axis line
        g2d.drawLine(padX, h - padY + 5, padX, padY);
        // x-axis line
        g2d.drawLine(padX - 5, h - padY, w - padX, h - padY);
        loadGraphicsState(g2d);

        // y-axis arrow
        fillTriangle(
                g2d,
                padX - ARROW_SIZE / 2, // left of y-axis
                padY,
                padX + ARROW_SIZE / 2, // right of y-axis
                padY,
                padX,                 // center of y-axis
                padY - ARROW_SIZE
        );

        // x-axis arrow
        fillTriangle(
                g2d,
                w - padX,                   // above of x-axis
                h - padY - ARROW_SIZE / 2,
                w - padX,                   // below of x-axis
                h - padY + ARROW_SIZE / 2,
                w - padX + ARROW_SIZE,      // center of x-axis
                h - padY
        );

        saveGraphicsState(g2d);
        setGraphicsState(g2d, DESCRIPTION_FONT, LABEL_COLOR, new BasicStroke(1));

        // x-description
        String xDescription = barChart.getxDescription();
        g2d.drawString(xDescription, w / 2 - metrics.stringWidth(xDescription) / 2, h - VERTICAL_PAD + fontSize);

        // y-description
        String yDescription = barChart.getyDescription();
        AffineTransform at = AffineTransform.getQuadrantRotateInstance(-1);
        g2d.setTransform(at);
        g2d.drawString(yDescription, -(h / 2 + metrics.stringWidth(yDescription) / 2), HORIZONTAL_PAD);

        loadGraphicsState(g2d);
    }

    /**
     * Draws the actual bar chart, includuing the grid, bars and labels.
     *
     * @param g2d graphics2d object
     */
    private void drawChart(Graphics2D g2d) {
        final int padX = HORIZONTAL_PAD + maxYWidth() + fontSize + DESCRIPTION_MARGIN;
        final int countX = values.size();
        final int gapX = (int) ((w - 2.0 * padX) / countX);

        final int padY = VERTICAL_PAD + fontSize;
        int yMax = barChart.getMaxY(), yMin = barChart.getMinY(), spacing = barChart.getGapY();
        int countY = (int) Math.ceil((yMax - yMin) * 1.0 / spacing);
        final int gapY = (int) ((h - 2.0 * padY) / countY);

        saveGraphicsState(g2d);
        setGraphicsState(g2d, LABEL_FONT, g2d.getColor(), g2d.getStroke());

        for (int i = 0; i <= countY; i++) {
            String str = String.valueOf(barChart.getMinY() + i * barChart.getGapY());
            int x = HORIZONTAL_PAD + maxYWidth() - metrics.stringWidth(str) + DESCRIPTION_MARGIN;

            // y-label
            setGraphicsState(g2d, g2d.getFont(), LABEL_COLOR, g2d.getStroke());
            g2d.setColor(LABEL_COLOR);
            g2d.drawString(str, x, (h - i * gapY - VERTICAL_PAD) - (3 * fontSize / 4));

            // grid line
            setGraphicsState(g2d, g2d.getFont(), GRID_COLOR, new BasicStroke(2));
            g2d.drawLine(padX - 5, h - padY - i * gapY, w - padX, h - padY - i * gapY);
        }

        for (int i = 0; i <= countX; i++) {
            if (i != countX) {
                String str = String.valueOf(values.get(i).getX());
                int x = padX + i * gapX + gapX / 2 - metrics.stringWidth(str) / 2;

                // x-label
                setGraphicsState(g2d, LABEL_FONT, LABEL_COLOR, g2d.getStroke());
                g2d.drawString(str, x, h - VERTICAL_PAD);

                // rectangle
                int rectX = padX + i * gapX + BAR_DISTANCE / 2;
                int rectH = (int) ((values.get(i).getY() - barChart.getMinY()) * 1.0 / barChart.getGapY() * gapY);
                int rectY = h - padY - rectH;
                int rectW = gapX - BAR_DISTANCE;
                setGraphicsState(g2d, g2d.getFont(), BAR_COLOR, g2d.getStroke());
                g2d.fillRect(rectX, rectY, rectW, rectH);
            }

            // grid line
            setGraphicsState(g2d, g2d.getFont(), GRID_COLOR, new BasicStroke(2));
            g2d.drawLine(padX + i * gapX, padY, padX + i * gapX, h - padY + 5);

            loadGraphicsState(g2d);
        }
    }

    /**
     * Fills triangle at given positions.
     *
     * @param g2d graphics2d object
     * @param x1 x1 point
     * @param y1 y1 point
     * @param x2 x2 point
     * @param y2 y2 point
     * @param x3 x3 point
     * @param y3 y3 point
     */
    private void fillTriangle(Graphics2D g2d, int x1, int y1, int x2, int y2, int x3, int y3) {
        g2d.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
    }

    /**
     * Saves the current graphics state.
     *
     * @param g2d graphics2d object
     */
    private void saveGraphicsState(Graphics2D g2d) {
        graphicsState = new GraphicsState(g2d.getFont(), g2d.getColor(), g2d.getStroke());
    }

    /**
     * Loads the previously saved graphics state.
     *
     * @param g2d graphics2d object
     */
    private void loadGraphicsState(Graphics2D g2d) {
        g2d.setFont(graphicsState.font);
        g2d.setColor(graphicsState.color);
        g2d.setStroke(graphicsState.stroke);
    }

    /**
     * Sets the current graphics state.
     *
     * @param g2d graphics2d object
     * @param font font to set
     * @param color color to set
     * @param stroke stroke to set
     */
    private void setGraphicsState(Graphics2D g2d, Font font, Color color, Stroke stroke) {
        g2d.setFont(font);
        g2d.setColor(color);
        g2d.setStroke(stroke);
    }

    /**
     * Returns the width of the largest element on the y-axis.
     *
     * @return width of the largest element on the y-axis
     */
    private int maxYWidth() {
        return metrics.stringWidth(String.valueOf(barChart.getMaxY()));
    }

    /**
     * Keeps track of the current graphics state (font, color, stroke)
     *
     * @author matej
     */
    private static class GraphicsState {
        /**
         * Current font.
         */
        private Font font;
        /**
         * Current color.
         */
        private Color color;
        /**
         * Current stroke.
         */
        private Stroke stroke;

        /**
         * Constructor.
         *
         * @param font font to store
         * @param color color to store
         * @param stroke stroke to store
         */
        private GraphicsState(Font font, Color color, Stroke stroke) {
            this.font = font;
            this.color = color;
            this.stroke = stroke;
        }
    }
}
