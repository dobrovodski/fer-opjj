package hr.fer.zemris.java.servlets.glasanje;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

@WebServlet(urlPatterns = {"/glasanje"})
public class GlasanjeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
        String[] contents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8).split("\n");

        Map<Integer, Band> bandMap = new TreeMap<>();
        for (String line : contents) {
            String[] data = line.split("\t");
            int id = Integer.parseInt(data[0]);
            bandMap.put(id, new Band(id, data[1], data[2]));
        }

        req.getSession().setAttribute("bandMap", bandMap);
        req.getRequestDispatcher("WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }
}
