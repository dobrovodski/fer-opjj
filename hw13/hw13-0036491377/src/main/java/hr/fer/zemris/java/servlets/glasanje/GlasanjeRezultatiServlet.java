package hr.fer.zemris.java.servlets.glasanje;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This servlet calculates the current results of the vote, sorts them and saves them in the current session variables
 * resultList and bestList to be used for displaying tables.
 *
 * @author matej
 */
@WebServlet(urlPatterns = {"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {
    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        String bandsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
        List<Band> resultList = GlasanjeUtil.getListOfBands(bandsFileName, fileName);

        int votes = resultList.get(0).getVoteCount();
        int index;
        for (index = 0; index < resultList.size(); index++) {
            Band b = resultList.get(index);
            if (b.getVoteCount() < votes) {
                break;
            }
        }
        List<Band> bestList = resultList.subList(0, index);

        req.setAttribute("resultList", resultList);
        req.setAttribute("bestList", bestList);
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
