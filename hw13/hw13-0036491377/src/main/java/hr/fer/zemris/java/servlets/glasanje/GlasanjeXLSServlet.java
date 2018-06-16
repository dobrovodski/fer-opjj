package hr.fer.zemris.java.servlets.glasanje;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Creates XLS file when called. The file is single-sheet with 4 columns: ID/Name/Vote count/Link to song. The bands are
 * in order of the score they got.
 *
 * @author matej
 */
@WebServlet(urlPatterns = {"/glasanje-xls"})
public class GlasanjeXLSServlet extends HttpServlet {
    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        String bandsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
        List<Band> resultList = GlasanjeUtil.getListOfBands(bandsFileName, fileName);

        HSSFWorkbook hwb = createTable(resultList);
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati_glasovanja.xls\"");
        hwb.write(resp.getOutputStream());
    }

    /**
     * Creates XLS table using given poll results.
     *
     * @param results results of the poll
     *
     * @return HSSFWorkbook made from the results
     */
    private HSSFWorkbook createTable(List<Band> results) {
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("results");

        int row = 0;
        HSSFRow head = sheet.createRow(row++);
        head.createCell(0).setCellValue("ID");
        head.createCell(1).setCellValue("Naziv");
        head.createCell(2).setCellValue("Broj glasova");
        head.createCell(3).setCellValue("Link");
        for (Band b : results) {
            HSSFRow tblRow = sheet.createRow(row++);
            tblRow.createCell(0).setCellValue(b.getId());
            tblRow.createCell(1).setCellValue(b.getName());
            tblRow.createCell(2).setCellValue(b.getVoteCount());
            tblRow.createCell(3).setCellValue(b.getLink());
        }
        return hwb;
    }
}
