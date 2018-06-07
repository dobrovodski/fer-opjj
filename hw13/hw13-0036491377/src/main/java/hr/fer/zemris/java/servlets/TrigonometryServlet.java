package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculates the sine and cosine values of all integer values from the range selected in the form and displays them in
 * a table.
 *
 * @author matej
 */
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

    /**
     * Simple class used to store read-only data for the calculated sine and cosine values.
     *
     * @author matej
     */
    public static class SinCosValue {
        /**
         * Actual value.
         */
        private int value;
        /**
         * Cosine value.
         */
        private double cos;
        /**
         * Sine value.
         */
        private double sin;

        /**
         * Constructor.
         *
         * @param value value to use
         */
        public SinCosValue(int value) {
            this.value = value;
            this.cos = Math.cos(Math.toRadians(value));
            this.sin = Math.sin(Math.toRadians(value));
        }

        /**
         * Returns the value.
         *
         * @return value
         */
        public int getValue() {
            return value;
        }

        /**
         * Returns the cosine.
         *
         * @return cosine of value
         */
        public double getCos() {
            return cos;
        }

        /**
         * Returns the sine.
         *
         * @return sine of value
         */
        public double getSin() {
            return sin;
        }
    }

}
