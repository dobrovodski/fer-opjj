package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet(urlPatterns = {"/story"})
public class StoryServlet extends HttpServlet {

    private static List<String> colors = new ArrayList<>();

    static {
        colors.add("#750787");
        colors.add("#004dff");
        colors.add("#008026");
        colors.add("#ffed00");
        colors.add("#ff8c00");
        colors.add("#e40303");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Random rand = new Random();
        req.setAttribute("fontColor", colors.get(rand.nextInt(colors.size())));
        req.getRequestDispatcher("WEB-INF/pages/stories/funny.jsp").forward(req, resp);
    }
}
