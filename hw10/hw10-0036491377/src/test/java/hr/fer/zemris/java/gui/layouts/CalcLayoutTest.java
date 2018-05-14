package hr.fer.zemris.java.gui.layouts;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class CalcLayoutTest {
    @Test
    public void GetPreferredSize_NoFirstComponent_Correct() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(10, 30));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(20, 15));
        p.add(l1, new RCPosition(2, 2));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();
        assertEquals(152, (int) dim.getWidth());
        assertEquals(158, (int) dim.getHeight());
    }

    @Test
    public void GetPreferredSize_WithFirstComponent_Correct() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
        p.add(l1, new RCPosition(1,1));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();
        assertEquals(152, (int) dim.getWidth());
        assertEquals(158, (int) dim.getHeight());
    }
}
