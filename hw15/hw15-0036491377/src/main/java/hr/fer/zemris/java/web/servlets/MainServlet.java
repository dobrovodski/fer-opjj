package hr.fer.zemris.java.web.servlets;

import hr.fer.zemris.java.crypto.Crypto;
import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.BlogUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This servlet is the entry point of the application. It is used as a landing page and home page as well as used for
 * logging in to the application or to get to the registration form.
 *
 * @author matej
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

    /**
     * UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setUsersAttribute(req);
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String pwd = Crypto.getDigest(req.getParameter("password"));
        BlogUser u = DAOProvider.getDAO().getBlogUser(user);

        if (u == null) {
            req.setAttribute("message", "That account doesn't exist!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        if (!u.getPasswordHash().equals(pwd)) {
            req.setAttribute("message", "The password you entered is incorrect!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        req.getSession().setAttribute("current.user.id", u.getId());
        req.getSession().setAttribute("current.user.nick", u.getNick());
        req.getSession().setAttribute("current.user.fn", u.getFirstName());
        req.getSession().setAttribute("current.user.ln", u.getLastName());

        setUsersAttribute(req);
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }

    /**
     * Sets the attribute "users" by getting the registered users and putting them as an attribute.
     *
     * @param req request
     */
    private void setUsersAttribute(HttpServletRequest req) {
        List<BlogUser> users = DAOProvider.getDAO().getRegisteredUsers();
        if (users.size() > 0) {
            req.setAttribute("users", users);
        }
    }
}