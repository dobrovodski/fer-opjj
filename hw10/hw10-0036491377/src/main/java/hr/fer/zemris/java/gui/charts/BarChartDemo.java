package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI program to display bar chart data. The program requires a single argument - path to the file with the data. The
 * file has to be formatted in a special way: xAxisDescription yAxisDescription x1,y1 x2,y2 x3,y3 ... minYAxisValue
 * maxYAxisValue yAxisStep
 *
 * @author matej
 */
public class BarChartDemo extends JFrame {
    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Values of bar chart.
     */
    private static List<XYValue> values;
    /**
     * Smallest y-axis value.
     */
    private static int minY;
    /**
     * Largest y-axis value.
     */
    private static int maxY;
    /**
     * Y-axis step.
     */
    private static int stepY;
    /**
     * X-axis description.
     */
    private static String xDescription;
    /**
     * Y-axis description.
     */
    private static String yDescription;
    /**
     * Name of the file with the data.
     */
    private static String fileName;

    /**
     * Constructor.
     */
    public BarChartDemo() {
        setLocation(20, 50);
        setSize(600, 600);
        setTitle("Bar Chart Demo");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
    }

    /**
     * Entry point
     *
     * @param args command line arguments
     */
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

            stepY = Integer.parseInt(br.readLine());

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

    /**
     * Parses the given data into a list of {@link XYValue}s.
     *
     * @param dataStr list of potential {@link XYValue}s.
     *
     * @return list of {@link XYValue}s
     */
    private static List<XYValue> parseData(String[] dataStr) {
        List<XYValue> list = new ArrayList<>();

        for (String str : dataStr) {
            XYValue value = XYValue.fromString(str);
            list.add(value);
        }

        return list;
    }

    /**
     * Initializes the GUI.
     */
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
                stepY
        )));
    }
}
