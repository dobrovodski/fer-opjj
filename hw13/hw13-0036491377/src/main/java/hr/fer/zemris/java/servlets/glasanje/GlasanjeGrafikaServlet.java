package hr.fer.zemris.java.servlets.glasanje;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This servlet produces a very basic pie chart of the current votes and writes it to the response output stream in the
 * form of a PNG image.
 *
 * @author matej
 */
@WebServlet(urlPatterns = {"/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet {
    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        String bandsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
        List<Band> resultList = GlasanjeUtil.getListOfBands(bandsFileName, fileName);

        for (Band b : resultList) {
            dataset.setValue(b.getName(), b.getVoteCount());
        }
        JFreeChart chart = ChartFactory.createPieChart("Rezultati glasovanja", dataset, true, true, false);
        resp.setContentType("image/png");
        ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 400, 400);
    }
}
