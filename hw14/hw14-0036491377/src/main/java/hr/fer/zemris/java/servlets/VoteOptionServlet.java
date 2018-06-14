package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.dao.sql.SQLConnectionProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This servlet takes care of processing a user vote and updating the database.
 *
 * @author matej
 */
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

        req.setAttribute("pollID", pollid);
        req.setAttribute("optionID", optionId);
        // This is so that you can't keep refreshing to vote
        resp.sendRedirect(req.getContextPath() + "/servleti/rezultati?pollID=" + pollid + "&optionID=" + optionId);
    }
}
