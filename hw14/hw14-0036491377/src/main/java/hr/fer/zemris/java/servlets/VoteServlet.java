package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * This servlet displays the vote options to the user for the selected poll.
 *
 * @author matej
 */
@WebServlet(urlPatterns = {"/servleti/glasanje"})
public class VoteServlet extends HttpServlet {
	/**
	 * UID.
	 */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("pollID"));
        Poll poll = DAOProvider.getDao().getPoll(id);
        List<PollOption> pollOptions = DAOProvider.getDao().getPollOptionsInfo(id);
        pollOptions.sort(Comparator.comparingInt(PollOption::getId));
        req.setAttribute("poll", poll);
        req.setAttribute("pollOptions", pollOptions);
        req.getRequestDispatcher("/WEB-INF/pages/glasanje.jsp").forward(req, resp);
    }
}
