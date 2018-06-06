package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(urlPatterns = {"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanjerezultati.txt");
        // napravi datoteku ako je potrebno inače pročitaj
        // pošalji glasove jspu
        Path path = Paths.get(fileName);
        List<Band> bandList = (List<Band>) req.getSession().getAttribute("bandList");

        if (!Files.exists(path)) {
            Files.createFile(path);

            FileWriter fw = new FileWriter(fileName);
            for (Band b : bandList) {
                fw.write(b.getId() + "\t" + b.getVoteCount() + "\r\n");
            }
        }

        String[] contents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8).split("\n");
        List<Band> resultList = new ArrayList<>();

        for (String line : contents) {
            br.readLine();
        }

        req.getSession().setAttribute("resultList", resultList);
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
