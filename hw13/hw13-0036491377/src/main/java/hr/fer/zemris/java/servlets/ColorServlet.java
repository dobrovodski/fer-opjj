package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/setcolor"})
public class ColorServlet extends HttpServlet {
    private static Map<String, String> colorTable = new HashMap<>();

    static {
        colorTable.put("red", "#e74c3c");
        colorTable.put("white", "#ff000");
        colorTable.put("green", "#2ecc71");
        colorTable.put("cyan", "#81ecec");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chosenColor = colorTable.get(req.getParameter("pickedBgCol"));
        if (chosenColor == null) {
            chosenColor = colorTable.get("white");
        }
        req.getSession().setAttribute("bgColor", chosenColor);
        req.getRequestDispatcher("/colors.jsp").forward(req, resp);
    }
}
