package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class BarChartComponent extends JComponent {
    private BarChart barChart;

    private int w;
    private int h;

    private static final int VERTICAL_PAD = 30;
    private static final int HORIZONTAL_PAD = 30;
    private static final int ARROW_SIZE = 10;
    private static final int DESCRIPTION_MARGIN = 10;
    private static final int BAR_DISTANCE = 2;

    private int fontSize;
    private FontMetrics metrics;
    private List<XYValue> values;
    private GraphicsState graphicsState;

    private static final Color BAR_COLOR = new Color(244, 119, 72);
    private static final Color GRID_COLOR = new Color(238, 221, 190);
    private static final Color AXES_COLOR = new Color(151, 151, 151);
    private static final Color LABEL_COLOR = new Color(0, 0, 0);
    private static final Font DESCRIPTION_FONT = new Font("default", Font.PLAIN, 16);
    private static final Font LABEL_FONT = new Font("default", Font.BOLD, 16);

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

    private void paintComponent(Graphics2D g2d) {
        drawChart(g2d);
        drawAxes(g2d);
    }

    private void drawAxes(Graphics2D g2d) {
        final int padX = VERTICAL_PAD + metrics.stringWidth(String.valueOf(barChart.getMaxY())) + fontSize +
                         DESCRIPTION_MARGIN;
        final int padY = HORIZONTAL_PAD + fontSize;

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
        g2d.drawString(xDescription, w / 2 - metrics.stringWidth(xDescription) / 2, h - HORIZONTAL_PAD + fontSize);

        // y-description
        String yDescription = barChart.getyDescription();
        AffineTransform at = AffineTransform.getQuadrantRotateInstance(-1);
        g2d.setTransform(at);
        g2d.drawString(yDescription, -(h / 2 + metrics.stringWidth(yDescription) / 2), VERTICAL_PAD);

        loadGraphicsState(g2d);

    }

    private void drawChart(Graphics2D g2d) {
        final int maxYSize = metrics.stringWidth(String.valueOf(barChart.getMaxY()));
        final int padX = VERTICAL_PAD + maxYSize + fontSize + DESCRIPTION_MARGIN;
        final int countX = values.size();
        final int gapX = (int) ((w - 2.0 * padX) / countX);

        final int padY = HORIZONTAL_PAD + fontSize;
        final int countY = (barChart.getMaxY() - barChart.getMinY()) / barChart.getGapY();
        final int gapY = (int) ((h - 2.0 * padY) / countY);

        saveGraphicsState(g2d);
        setGraphicsState(g2d, LABEL_FONT, g2d.getColor(), g2d.getStroke());

        for (int i = 0; i <= countY; i++) {
            String str = String.valueOf(barChart.getMinY() + i * barChart.getGapY());
            int x = VERTICAL_PAD + maxYSize - metrics.stringWidth(str) + DESCRIPTION_MARGIN;

            // y-label
            setGraphicsState(g2d, g2d.getFont(), LABEL_COLOR, g2d.getStroke());
            g2d.setColor(LABEL_COLOR);
            g2d.drawString(str, x, (h - i * gapY - HORIZONTAL_PAD) - (3 * fontSize / 4));

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
                g2d.drawString(str, x, h - HORIZONTAL_PAD);

                // rectangle
                int rectX = padX + i * gapX + BAR_DISTANCE / 2;
                int rectH = values.get(i).getY() / barChart.getGapY() * gapY;
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

    private void fillTriangle(Graphics2D g2d, int x1, int y1, int x2, int y2, int x3, int y3) {
        g2d.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
    }

    private void saveGraphicsState(Graphics2D g2d) {
        graphicsState = new GraphicsState(g2d.getFont(), g2d.getColor(), g2d.getStroke());
    }

    private void loadGraphicsState(Graphics2D g2d) {
        g2d.setFont(graphicsState.font);
        g2d.setColor(graphicsState.color);
        g2d.setStroke(graphicsState.stroke);
    }

    private void setGraphicsState(Graphics2D g2d, Font font, Color color, Stroke stroke) {
        g2d.setFont(font);
        g2d.setColor(color);
        g2d.setStroke(stroke);
    }

    private static class GraphicsState {
        private Font font;
        private Color color;
        private Stroke stroke;

        private GraphicsState(Font font, Color color, Stroke stroke) {
            this.font = font;
            this.color = color;
            this.stroke = stroke;
        }
    }
}
