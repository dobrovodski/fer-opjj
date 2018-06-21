package hr.fer.zemris.java.web.servlets;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.BlogComment;
import hr.fer.zemris.java.model.BlogCommentForm;
import hr.fer.zemris.java.model.BlogEntry;
import hr.fer.zemris.java.model.BlogEntryForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This servlet takes care of the main logic of the application. It is called when a user wants to either view, edit or
 * add a new blog entry to the blog.
 *
 * @author matej
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

    /**
     * UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] path = req.getPathInfo().split("/");
        String nick = path[1];
        req.setAttribute("nick", nick);

        if (!DAOProvider.getDAO().userExists(nick)) {
            req.setAttribute("message", "That account doesn't exist!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        if (path.length > 3) {
            req.setAttribute("message", "Oops, something went wrong!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        if (path.length == 3) {
            if (path[2].equals("new") || path[2].equals("edit")) {
                showBlogEntryEdit(req, resp);
                return;
            }

            Long eid;
            try {
                eid = Long.parseLong(path[2]);
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Oops, something went wrong!");
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                return;
            }

            if (DAOProvider.getDAO().blogExists(eid)) {
                showBlogEntry(req, resp, eid);
                return;
            }
        }

        showBlogEntries(req, resp, path[1]);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id;
        BlogEntry u;

        if (req.getParameter("comment") != null) {
            postComment(req, resp);
            return;
        }

        try {
            id = Long.parseLong(req.getParameter("id"));
            u = DAOProvider.getDAO().getBlogEntry(id);
        } catch (NumberFormatException e) {
            u = new BlogEntry();
            u.setCreator(DAOProvider.getDAO().getBlogUser((String) req.getSession().getAttribute("current.user.nick")));
            u.setCreatedAt(new Date());
            u.setComments(new ArrayList<>());
        }

        BlogEntryForm uf = new BlogEntryForm(req.getParameter("title"), req.getParameter("text"));
        if (!uf.validate()) {
            req.setAttribute("message", uf.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        u.setLastModifiedAt(new Date());
        u.setText(req.getParameter("text"));
        u.setTitle(req.getParameter("title"));

        DAOProvider.getDAO().addBlogEntry(u);
        String red = req.getContextPath() + "/servleti/author/" + req.getSession().getAttribute("current.user.nick");
        resp.sendRedirect(red);
    }

    /**
     * Puts comment into database depending on the parameters it receieved through the request.
     *
     * @param req request
     * @param resp response
     *
     * @throws ServletException servlet exception
     * @throws IOException ioexception
     */
    private void postComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String comment = req.getParameter("comment");
        String blogId = req.getParameter("blogId");
        String email = req.getParameter("email");
        String nick = req.getParameter("nick");

        Long id;
        try {
            id = Long.parseLong(blogId);
        } catch (NumberFormatException e) {
            req.setAttribute("message", "Oops, couldn't leave a comment on this post!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }


        String usersEmail;
        if (email != null) {
            usersEmail = email;
        } else {
            usersEmail = DAOProvider.getDAO().getBlogUser(nick).getEmail();
        }


        BlogCommentForm cf = new BlogCommentForm(usersEmail, comment);
        if (!cf.validate()) {
            req.setAttribute("message", cf.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        BlogComment c = new BlogComment();
        BlogEntry e = DAOProvider.getDAO().getBlogEntry(id);
        c.setBlogEntry(e);
        c.setMessage(comment);
        c.setPostedOn(new Date());
        c.setUsersEMail(usersEmail);

        DAOProvider.getDAO().addEntryComment(c);
        req.setAttribute("entry", e);
        req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
    }

    /**
     * Shows all existing blog entries for the given username.
     *
     * @param req request
     * @param resp response
     * @param nick username of the account to look up
     *
     * @throws ServletException servlet exception
     * @throws IOException ioexception
     */
    private void showBlogEntries(HttpServletRequest req, HttpServletResponse resp, String nick) throws
            ServletException, IOException {
        req.setAttribute("nick", nick);
        List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(nick);
        req.setAttribute("entries", entries);
        req.getRequestDispatcher("/WEB-INF/pages/entries_list.jsp").forward(req, resp);
    }

    /**
     * Shows blog entry determined by the provided id.
     *
     * @param req request
     * @param resp response
     * @param eid id of blog entry to look up
     *
     * @throws ServletException servlet exception
     * @throws IOException ioexception
     */
    private void showBlogEntry(HttpServletRequest req, HttpServletResponse resp, Long eid) throws
            ServletException, IOException {
        req.setAttribute("entry", DAOProvider.getDAO().getBlogEntry(eid));
        req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
    }

    /**
     * Shows the blog entry edit page (either to create a new or to edit an existing entry).
     *
     * @param req request
     * @param resp response
     *
     * @throws ServletException servlet exception
     * @throws IOException ioexception
     */
    private void showBlogEntryEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        String eid = req.getParameter("id");
        if (eid == null) {
            BlogEntry u = new BlogEntry();
            u.setText("");
            u.setTitle("");
            req.setAttribute("entry", u);
        } else {
            Long id = Long.parseLong(eid);
            BlogEntry u = DAOProvider.getDAO().getBlogEntry(id);

            req.setAttribute("entry", u);
        }

        req.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(req, resp);
    }

    /**
     * Returns true if provided email is valid.
     *
     * @param mail mail to check
     *
     * @return true if valid
     */
    private boolean validEmail(String mail) {
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(mail);
        return matcher.find();
    }
}