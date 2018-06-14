package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.Poll;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * This servlet is used to display the list of polls to the user to select.
 *
 * @author matej
 */
@WebServlet("/servleti/index.html")
public class DisplayPollsServlet extends HttpServlet {
	/**
	 * UID.
	 */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Poll> polls = DAOProvider.getDao().getPollsInfo();
        polls.sort(Comparator.comparingInt(Poll::getId));
        req.setAttribute("polls", polls);
        req.getRequestDispatcher("/WEB-INF/pages/prikaz.jsp").forward(req, resp);
    }
}
