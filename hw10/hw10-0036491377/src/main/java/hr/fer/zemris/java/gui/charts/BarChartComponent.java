package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class BarChartComponent extends JComponent {
    private BarChart barChart;

    private int w;
    private int h;

    private static final int LEFT_PAD_FIXED = 20;
    private static final int BOTTOM_PAD_FIXED = 20;
    private static final int ARROW_SIZE = 10;

    private static int FONT_SIZE;
    private FontMetrics metrics;
    private List<XYValue> values;

    public BarChartComponent(BarChart barChart) {
        this.barChart = barChart;
        values = barChart.getValues();
        String maxLabel = String.valueOf(values.get(0).getY());

        for (XYValue v : values) {
            maxLabel = maxLabel.length() > String.valueOf(v.getY()).length() ? maxLabel : String.valueOf(v.getY());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        metrics = g.getFontMetrics(g.getFont());
        FONT_SIZE = metrics.getHeight();
        paintComponent((Graphics2D) g);
    }

    private void paintComponent(Graphics2D g) {
        w = getWidth();
        h = getHeight();
        drawAxes(g);
        drawLabels(g);
    }

    private void drawAxes(Graphics2D g) {
        final int padX = LEFT_PAD_FIXED + metrics.stringWidth(String.valueOf(barChart.getMaxY())) + FONT_SIZE + 10;
        final int padY = BOTTOM_PAD_FIXED + FONT_SIZE;

        // y-axis line
        g.drawLine(padX, h - padY + 5, padX, padY);
        // x-axis line
        g.drawLine(padX, h - padY, w - padX, h - padY);

        // y-axis arrow
        fillTriangle(
                g,
                padX - ARROW_SIZE / 2, // left of y-axis
                padY,
                padX + ARROW_SIZE / 2, // right of y-axis
                padY,
                padX,                 // center of y-axis
                padY - ARROW_SIZE
        );

        // x-axis arrow
        fillTriangle(
                g,
                w - padX,                   // above of x-axis
                h - padY - ARROW_SIZE / 2,
                w - padX,                   // below of x-axis
                h - padY + ARROW_SIZE / 2,
                w - padX + ARROW_SIZE,      // center of x-axis
                h - padY
        );

        // x-label
        String xDescription = barChart.getxDescription();
        g.drawString(xDescription, w / 2 - metrics.stringWidth(xDescription) / 2, h - BOTTOM_PAD_FIXED + FONT_SIZE);

        // y-label
        String yDescription = barChart.getyDescription();
        AffineTransform at = AffineTransform.getQuadrantRotateInstance(-1);
        g.setTransform(at);
        g.drawString(yDescription, -(h / 2 + metrics.stringWidth(yDescription) / 2), LEFT_PAD_FIXED);
        at.quadrantRotate(1);
        g.setTransform(at);

    }

    private void drawLabels(Graphics2D g) {
        final int padX = LEFT_PAD_FIXED + metrics.stringWidth(String.valueOf(barChart.getMaxY())) + FONT_SIZE + 10;
        final int countX = values.size();
        final int gapX = (int) ((w - 2.0 * padX) / countX);

        final int padY = BOTTOM_PAD_FIXED + FONT_SIZE;
        final int countY = (barChart.getMaxY() - barChart.getMinY()) / barChart.getGapY();
        final int gapY = (int) ((h - 2.0 * padY) / countY);

        final int spacing = barChart.getGapY();

        for (int i = 0; i <= countY; i++) {
            String str = String.valueOf(barChart.getMinY() + i * barChart.getGapY());
            int maxWidth = metrics.stringWidth(String.valueOf(barChart.getMaxY()));
            int x = LEFT_PAD_FIXED + FONT_SIZE + maxWidth - metrics.stringWidth(str);

            g.drawString(str, x, h - i * gapY - BOTTOM_PAD_FIXED - 3 * FONT_SIZE / 4);
            g.drawLine(padX - 5, h - padY - i * gapY, w - padX, h - padY - i * gapY);
        }

        for (int i = 0; i <= countX; i++) {
            if (i != countX) {
                String str = String.valueOf(values.get(i).getX());
                int x = padX + i * gapX + gapX / 2 - metrics.stringWidth(str) / 2;
                g.drawString(str, x, h - BOTTOM_PAD_FIXED);
                g.fillRect(padX + i * gapX + spacing / 2, h - BOTTOM_PAD_FIXED - 50, gapX - spacing, 50);
            }

            g.drawLine(padX + i * gapX, padY, padX + i * gapX, h - padY + 5);
        }
    }

    private void drawRectangles(Graphics2D g) {

    }

    private void fillTriangle(Graphics2D g, int x1, int y1, int x2, int y2, int x3, int y3) {
        g.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
    }
}
