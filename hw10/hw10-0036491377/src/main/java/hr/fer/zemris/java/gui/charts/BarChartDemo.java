package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BarChartDemo extends JFrame {
    private static List<XYValue> values;
    private static int minY;
    private static int maxY;
    private static int gapY;
    private static String xDescription;
    private static String yDescription;
    private static String fileName;

    public BarChartDemo() {
        setLocation(20, 50);
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Program requires one argument.");
            return;
        }

        fileName = args[0];
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            xDescription = br.readLine();
            yDescription = br.readLine();

            String[] dataStr = br.readLine().replaceAll("\\s+", " ").split(" ");
            values = parseData(dataStr);

            minY = Integer.parseInt(br.readLine());
            maxY = Integer.parseInt(br.readLine());

            gapY = Integer.parseInt(br.readLine());

        } catch (IOException e) {
            System.out.println("File could not be read: " + fileName);
            return;
        } catch (NumberFormatException e) {
            System.out.println("Chart attributes must be whole numbers.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new BarChartDemo();
            frame.setVisible(true);
        });
    }

    private static List<XYValue> parseData(String[] dataStr) {
        List<XYValue> list = new ArrayList<>();

        for (String str : dataStr) {
            XYValue value = XYValue.fromString(str);
            list.add(value);
        }

        return list;
    }

    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(new JLabel(fileName, JLabel.CENTER), BorderLayout.PAGE_START);
        cp.add(new BarChartComponent(new BarChart(
                values,
                xDescription,
                yDescription,
                minY,
                maxY,
                gapY
        )));
    }
}
