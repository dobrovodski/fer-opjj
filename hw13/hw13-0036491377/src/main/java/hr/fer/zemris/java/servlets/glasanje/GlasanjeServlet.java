package hr.fer.zemris.java.servlets.glasanje;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * This servlet loads the file with the list of bands in the poll and creates a map of them according to their IDs to be
 * used in the voting page.
 *
 * @author matej
 */
@WebServlet(urlPatterns = {"/glasanje"})
public class GlasanjeServlet extends HttpServlet {
    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

        Map<Integer, Band> bandMap = GlasanjeUtil.loadBands(fileName);

        req.setAttribute("bandMap", bandMap);
        req.getRequestDispatcher("WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }
}
