package hr.fer.zemris.java.gui.layouts;

import javax.swing.*;
import java.awt.*;

public class Demo {
    public static void main(String[] args) {
        /*JPanel p = new JPanel(new CalcLayout(3));
        p.add(new JLabel("x"), new RCPosition(1,1));
        p.add(new JLabel("y"), new RCPosition(2,3));
        p.add(new JLabel("z"), new RCPosition(2,7));
        p.add(new JLabel("w"), new RCPosition(4,2));
        p.add(new JLabel("a"), new RCPosition(4,5));
        p.add(new JLabel("b"), new RCPosition(4,7));*/

        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(10, 30));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(20, 15));
        p.add(l1, new RCPosition(2, 2));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();

        /*JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
        p.add(l1, new RCPosition(1,1));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();*/

        /*JPanel p = new JPanel(new CalcLayout(3));
        p.add(new JLabel("x"), "1,1");
        p.add(new JLabel("y"), "2,3");
        p.add(new JLabel("z"), "2,7");
        p.add(new JLabel("w"), "4,2");
        p.add(new JLabel("a"), "4,5");
        p.add(new JLabel("b"), "4,7");*/
    }
}
