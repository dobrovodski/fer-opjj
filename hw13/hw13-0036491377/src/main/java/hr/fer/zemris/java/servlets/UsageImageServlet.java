package hr.fer.zemris.java.servlets;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/reportImage"})
public class UsageImageServlet extends HttpServlet {
    private static DefaultPieDataset dataset = new DefaultPieDataset();

    static {
        dataset.setValue("Android", 54);
        dataset.setValue("iOS/macOS", 12.3);
        dataset.setValue("Windows", 11.7);
        dataset.setValue("Others", 22);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JFreeChart chart = ChartFactory.createPieChart("OS Usage", dataset, true, true, false);
        resp.setContentType("image/png");
        ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 600, 600);
    }
}
