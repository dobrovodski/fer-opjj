package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/trigonometric"})
public class TrigonometryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int a, b;
        try {
            a = Integer.parseInt(req.getParameter("a"));
        } catch (NumberFormatException e) {
            a = 0;
        }
        try {
            b = Integer.parseInt(req.getParameter("b"));
        } catch (NumberFormatException e) {
            b = 360;
        }

        if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
        }

        if (b > a + 720) {
            b = a + 720;
        }

        List<SinCosValue> values = new ArrayList<>();

        for (int i = a; i <= b; i++) {
            values.add(new SinCosValue(i));
        }

        req.setAttribute("values", values);
        req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
    }

    public static class SinCosValue {
        private int value;
        private double cos;
        private double sin;

        public SinCosValue(int value) {
            this.value = value;
            this.cos = Math.cos(Math.toRadians(value));
            this.sin = Math.sin(Math.toRadians(value));
        }

        public int getValue() {
            return value;
        }

        public double getCos() {
            return cos;
        }

        public double getSin() {
            return sin;
        }
    }

}
