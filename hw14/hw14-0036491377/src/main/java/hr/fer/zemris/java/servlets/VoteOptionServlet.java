package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.dao.sql.SQLConnectionProvider;
import hr.fer.zemris.java.model.PollOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/servleti/odabir"})
public class VoteOptionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pollid = Integer.parseInt(req.getParameter("pollID"));
        int optionId = Integer.parseInt(req.getParameter("optionID"));
        try {
            PreparedStatement pst = SQLConnectionProvider.getConnection().prepareStatement("update polloptions set "
                                                                                           + "votesCount=votesCount+1 "
                                                                                           + "where pollId=? and "
                                                                                           + "id=?");
            pst.setLong(1, pollid);
            pst.setLong(2, optionId);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Couldn't update value: " + e.getMessage());
            return;
        }

        List<PollOption> resultList = DAOProvider.getDao().getPollOptions(pollid);
        resultList.sort((b1, b2) -> Integer.compare(b2.getVoteCount(), b1.getVoteCount()));

        int votes = resultList.get(0).getVoteCount();
        int index;
        for (index = 0; index < resultList.size(); index++) {
            PollOption b = resultList.get(index);
            if (b.getVoteCount() < votes) {
                break;
            }
        }
        List<PollOption> bestList = resultList.subList(0, index);


        req.setAttribute("pollID", pollid);
        req.setAttribute("resultList", resultList);
        req.setAttribute("bestList", bestList);
        req.getRequestDispatcher("/WEB-INF/pages/rezultati.jsp").forward(req, resp);
    }
}
