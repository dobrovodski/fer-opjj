package hr.fer.zemris.java.web.servlets;

import hr.fer.zemris.java.crypto.Crypto;
import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.BlogUser;
import hr.fer.zemris.java.model.BlogUserForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet takes care of the registration logic (including validation, redirection in case of wrong parameters,
 * actual database updating).
 *
 * @author matej
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {
    /**
     * UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("current.user.nick") != null) {
            req.setAttribute("message", "Please log out first!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        String user = req.getParameter("user");
        String pwd = Crypto.getDigest(req.getParameter("password"));
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");

        if (DAOProvider.getDAO().getBlogUser(user) != null) {
            req.setAttribute("message", "Username with that name already exists!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        BlogUserForm buf = new BlogUserForm(firstName, lastName, user, email, req.getParameter("password"));
        if (!buf.validate()) {
            req.setAttribute("message", buf.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        BlogUser bu = new BlogUser();
        bu.setNick(user);
        bu.setPasswordHash(pwd);
        bu.setFirstName(firstName);
        bu.setLastName(lastName);
        bu.setEmail(email);
        DAOProvider.getDAO().addBlogUser(bu);

        req.setAttribute("message", "Account successfully created!");
        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
    }
}