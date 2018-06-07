package hr.fer.zemris.java.servlets.glasanje;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Band> resultList = (List<Band>) req.getSession().getAttribute("resultList");

        for (Band b : resultList) {
            dataset.setValue(b.getName(), b.getVoteCount());
        }
        JFreeChart chart = ChartFactory.createPieChart("Rezultati glasovanja", dataset, true, true, false);
        resp.setContentType("image/png");
        ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 400, 400);
    }
}
