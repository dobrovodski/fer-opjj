package hr.fer.zemris.java.servlets.glasanje;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@WebServlet(urlPatterns = {"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        synchronized (req.getSession()) {
            String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
            Path path = Paths.get(fileName);
            Map<Integer, Band> bandMap = (Map<Integer, Band>) req.getSession().getAttribute("bandMap");

            if (!Files.exists(path)) {
                Files.createFile(path);

                FileWriter fw = new FileWriter(fileName);
                for (Map.Entry<Integer, Band> entry : bandMap.entrySet()) {
                    Band b = entry.getValue();
                    fw.write(b.getId() + "\t" + b.getVoteCount() + "\r\n");
                }
            }

            int id = Integer.parseInt(req.getParameter("id"));

            String[] contents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8).split("\n");

            for (int i = 0; i < contents.length; i++) {
                String line = contents[i];
                String[] data = line.split("\t");
                int bandId = Integer.parseInt(data[0].trim());
                int votes = Integer.parseInt(data[1].trim());
                Band b = bandMap.get(bandId);
                if (id == bandId) {
                    b.setVoteCount(votes + 1);
                } else {
                    b.setVoteCount(votes);
                }
            }

            FileWriter fw = new FileWriter(fileName);
            for (Map.Entry<Integer, Band> entry : bandMap.entrySet()) {
                Band b = entry.getValue();
                fw.write(b.getId() + "\t" + b.getVoteCount() + "\r\n");
            }
            fw.flush();
            fw.close();
        }
        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }
}
