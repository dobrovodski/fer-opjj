package hr.fer.zemris.java.servlets.glasanje;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * This servlet loads file with the current votes and updates the vote with whichever vote has been casted. Afterwards
 * it redirects straight to the results page of the vote.
 *
 * @author matej
 */
@WebServlet(urlPatterns = {"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {
    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        synchronized (GlasanjeGlasajServlet.class) {
            String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
            String bandsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

            Map<Integer, Band> bandMap = GlasanjeUtil.loadBands(bandsFileName);
            GlasanjeUtil.updateBandResults(fileName, bandMap);

            int id = Integer.parseInt(req.getParameter("id"));
            Band band = bandMap.get(id);
            band.setVoteCount(band.getVoteCount() + 1);

            GlasanjeUtil.saveBandResults(fileName, bandMap);
        }

        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }
}
