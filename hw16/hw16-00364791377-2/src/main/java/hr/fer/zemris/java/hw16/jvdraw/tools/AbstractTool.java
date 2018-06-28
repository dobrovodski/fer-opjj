package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class AbstractTool implements Tool {
    boolean drawing = false;

    int startX;
    int startY;
    int endX;
    int endY;

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        endX = e.getX();
        endY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (drawing) {
            endX = e.getX();
            endY = e.getY();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void paint(Graphics2D g2d) {
    }
}
