package hr.fer.zemris.java.servlets;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/powers"})
public class ExcelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int a;
        int b;
        int n;
        try {
            a = Integer.parseInt(req.getParameter("a"));
            b = Integer.parseInt(req.getParameter("b"));
            n = Integer.parseInt(req.getParameter("n"));
        } catch (NumberFormatException e) {
            req.setAttribute("errorMsg", "couldn't parse parameters as numbers!");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
            req.setAttribute("errorMsg", "parameters are out of range!");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
        }

        HSSFWorkbook hwb = createTable(a, b, n);
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
        hwb.write(resp.getOutputStream());
    }

    private HSSFWorkbook createTable(int a, int b, int n) {
        HSSFWorkbook hwb = new HSSFWorkbook();
        for (int i = 0; i < n; i++) {
            HSSFSheet sheet = hwb.createSheet(String.valueOf(i));
            HSSFRow head = sheet.createRow(0);
            head.createCell(0).setCellValue("Number");
            head.createCell(1).setCellValue("Number raised to the power of " + String.valueOf(i + 1));

            for (int curr = a; curr <= b; curr++) {
                HSSFRow row = sheet.createRow(curr - a + 1);
                row.createCell(0).setCellValue(String.valueOf(curr));
                row.createCell(1).setCellValue(String.valueOf(Math.pow(curr, i + 1)));
            }
        }
        return hwb;
    }
}
