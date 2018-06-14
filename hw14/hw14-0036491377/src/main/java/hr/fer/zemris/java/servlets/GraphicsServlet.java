package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;
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
@WebServlet(urlPatterns = {"/servleti/glasanje-grafika"})
public class GraphicsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<PollOption> resultList = DAOProvider.getDao().getPollOptions(Integer.parseInt(req.getParameter("pollID")));

        for (PollOption b : resultList) {
            dataset.setValue(b.getTitle(), b.getVoteCount());
        }

        JFreeChart chart = ChartFactory.createPieChart("Rezultati glasovanja", dataset, true, true, false);
        resp.setContentType("image/png");
        ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 500, 500);
    }
}
