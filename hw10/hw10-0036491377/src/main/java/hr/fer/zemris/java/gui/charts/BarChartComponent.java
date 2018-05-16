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
        drawLabels(g);
        drawAxes(g);
    }

    private void drawAxes(Graphics2D g) {
        final int padX = LEFT_PAD_FIXED + metrics.stringWidth(String.valueOf(barChart.getMaxY())) + FONT_SIZE + 10;
        final int padY = BOTTOM_PAD_FIXED + FONT_SIZE;

        saveGraphicsState(g);
        setGraphicsState(g, graphicsState.font, AXES_COLOR, new BasicStroke(2));
        // y-axis line
        g.drawLine(padX, h - padY + 5, padX, padY);
        // x-axis line
        g.drawLine(padX, h - padY, w - padX, h - padY);
        loadGraphicsState(g);

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

        saveGraphicsState(g);
        setGraphicsState(g, DESCRIPTION_FONT, LABEL_COLOR, new BasicStroke(1));

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

        loadGraphicsState(g);

    }

    private void drawLabels(Graphics2D g) {
        final int padX = LEFT_PAD_FIXED + metrics.stringWidth(String.valueOf(barChart.getMaxY())) + FONT_SIZE + 10;
        final int countX = values.size();
        final int gapX = (int) ((w - 2.0 * padX) / countX);

        final int padY = BOTTOM_PAD_FIXED + FONT_SIZE;
        final int countY = (barChart.getMaxY() - barChart.getMinY()) / barChart.getGapY();
        final int gapY = (int) ((h - 2.0 * padY) / countY);

        final int spacing = barChart.getGapY();

        saveGraphicsState(g);

        setGraphicsState(g, LABEL_FONT, g.getColor(), g.getStroke());
        for (int i = 0; i <= countY; i++) {
            String str = String.valueOf(barChart.getMinY() + i * barChart.getGapY());
            int maxWidth = metrics.stringWidth(String.valueOf(barChart.getMaxY()));
            int x = LEFT_PAD_FIXED + FONT_SIZE + maxWidth - metrics.stringWidth(str);

            // y label
            setGraphicsState(g, g.getFont(), LABEL_COLOR, g.getStroke());
            g.setColor(LABEL_COLOR);
            g.drawString(str, x, h - i * gapY - BOTTOM_PAD_FIXED - 3 * FONT_SIZE / 4);

            // grid line
            setGraphicsState(g, g.getFont(), GRID_COLOR, new BasicStroke(2));
            g.drawLine(padX - 5, h - padY - i * gapY, w - padX, h - padY - i * gapY);
        }

        for (int i = 0; i <= countX; i++) {
            if (i != countX) {
                String str = String.valueOf(values.get(i).getX());
                int x = padX + i * gapX + gapX / 2 - metrics.stringWidth(str) / 2;

                // x label
                setGraphicsState(g, LABEL_FONT, LABEL_COLOR, g.getStroke());
                g.drawString(str, x, h - BOTTOM_PAD_FIXED);

                // Rectangle
                int rectX = padX + i * gapX + spacing / 2;
                int rectH = values.get(i).getY() / barChart.getGapY() * gapY;
                int rectY = h - padY - rectH;
                int rectW = gapX - spacing;
                setGraphicsState(g, g.getFont(), BAR_COLOR, g.getStroke());
                g.fillRect(rectX, rectY, rectW, rectH);
            }

            // grid line
            g.setColor(GRID_COLOR);
            Stroke cur = g.getStroke();
            g.setStroke(new BasicStroke(2));
            g.drawLine(padX + i * gapX, padY, padX + i * gapX, h - padY + 5);
            g.setStroke(cur);

            loadGraphicsState(g);
        }
    }

    private void fillTriangle(Graphics2D g, int x1, int y1, int x2, int y2, int x3, int y3) {
        g.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
    }

    private void saveGraphicsState(Graphics2D g) {
        graphicsState = new GraphicsState(g.getFont(), g.getColor(), g.getStroke());
    }

    private void loadGraphicsState(Graphics2D g) {
        g.setFont(graphicsState.font);
        g.setColor(graphicsState.color);
        g.setStroke(graphicsState.stroke);
    }

    private void setGraphicsState(Graphics2D g, Font font, Color color, Stroke stroke) {
        g.setFont(font);
        g.setColor(color);
        g.setStroke(stroke);
    }

    private static class GraphicsState {
        private Font font;
        private Color color;
        private Stroke stroke;

        public GraphicsState(Font font, Color color, Stroke stroke) {
            this.font = font;
            this.color = color;
            this.stroke = stroke;
        }
    }
}
