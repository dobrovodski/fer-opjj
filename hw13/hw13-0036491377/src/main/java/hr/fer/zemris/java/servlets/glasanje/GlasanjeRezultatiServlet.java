package hr.fer.zemris.java.servlets.glasanje;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Integer, Band> bandMap = (Map<Integer, Band>) req.getSession().getAttribute("bandMap");

        List<Band> resultList = new ArrayList<>();
        for (Map.Entry<Integer, Band> entry : bandMap.entrySet()) {
            Band b = entry.getValue();
            resultList.add(b);
        }
        resultList.sort((b1, b2) -> Integer.compare(b2.getVoteCount(), b1.getVoteCount()));

        req.getSession().setAttribute("resultList", resultList);
        ArrayList<Integer> a = new ArrayList<>();
        a.add(5);
        req.getSession().setAttribute("a", a);
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
