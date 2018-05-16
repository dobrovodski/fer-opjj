package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Demo extends JFrame {

    public Demo() throws HeadlessException {
        setLocation(20, 50);
        setSize(800, 800);
        setTitle("BarChart Demo");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new Demo();
            frame.setVisible(true);
        });
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.add(new BarChartComponent(new BarChart(
                Arrays.asList(
                        new XYValue(1, 8), new XYValue(2, 20), new XYValue(3, 22),
                        new XYValue(4, 10), new XYValue(5, 4)
                ),
                "Number of people in the car",
                "Frequency",
                0, // y-os kreće od 0
                22, // y-os ide do 22
                2
        )));
    }
}
