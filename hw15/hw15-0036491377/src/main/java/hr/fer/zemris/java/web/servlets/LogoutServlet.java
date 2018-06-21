package hr.fer.zemris.java.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet logs out any logged in user by invalidating the session.
 *
 * @author matej
 */
@WebServlet("/servleti/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/servleti/main");
    }

}