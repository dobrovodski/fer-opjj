package hr.fer.zemris.java.gui.layouts;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

/**
 * JUnit tests for {@link CalcLayout}.
 *
 * @author matej
 * @see <a href= "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html"> Naming standards for unit
 *         tests </a>
 */
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
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(108, 15));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(16, 30));
        p.add(l1, new RCPosition(1, 1));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();
        assertEquals(152, (int) dim.getWidth());
        assertEquals(158, (int) dim.getHeight());
    }

    @Test(expected = CalcLayoutException.class)
    public void AddLayoutComponent_RestrictionOutOfRange_Exception() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        p.add(l1, new RCPosition(8, 4));
    }

    @Test(expected = CalcLayoutException.class)
    public void AddLayoutComponent_RestrictionInsideOfFirstElement_Exception() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        p.add(l1, new RCPosition(1, 4));
    }

    @Test(expected = CalcLayoutException.class)
    public void AddLayoutComponent_MultipleComponentsSameRestriction_Exception() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        JLabel l2 = new JLabel("");
        JLabel l3 = new JLabel("");
        p.add(l1, new RCPosition(1, 1));
        p.add(l2, new RCPosition(4, 4));
        p.add(l3, new RCPosition(4, 4));
    }

    @Test
    public void AddLayoutComponent_SameComponentSameRestriction_NoException() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        JLabel l2 = new JLabel("");
        p.add(l1, new RCPosition(1, 1));
        p.add(l2, new RCPosition(4, 4));
        p.add(l2, new RCPosition(4, 4));
    }
}
