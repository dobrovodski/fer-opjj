package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BarChartDemo extends JFrame {

    public BarChartDemo() {
        setLocation(20, 50);
        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
    }

    public static void main(String[] args) {
        /* if (args.length != 1) {
            System.out.println("Program requires one argument.");
            return;
        }

        String fileName = args[0];
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String xDescription = br.readLine();
            String yDescription = br.readLine();

            String[] dataStr = br.readLine().replaceAll("\\s+", " ").split(" ");
            List<XYValue> values = parseData(dataStr);

            int minY = Integer.parseInt(br.readLine());
            int maxY = Integer.parseInt(br.readLine());

            int gapY = Integer.parseInt(br.readLine());

            new BarChart(values, xDescription, yDescription, minY, maxY, gapY);
        } catch (IOException e) {
            System.out.println("File could not be read: " + fileName);
        } catch (NumberFormatException e) {
            System.out.println("Chart attributes must be whole numbers.");
        } */

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
        //cp.add(new BarChartComponent(new BarChart()));
    }
}
