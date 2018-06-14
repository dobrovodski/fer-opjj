package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;
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
@WebServlet(urlPatterns = {"/servleti/glasanje-xls"})
public class XLSServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<PollOption> resultList = DAOProvider.getDao().getPollOptions(Integer.parseInt(req.getParameter("pollID")));
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
    private HSSFWorkbook createTable(List<PollOption> results) {
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("results");

        int row = 0;
        HSSFRow head = sheet.createRow(row++);
        head.createCell(0).setCellValue("ID");
        head.createCell(1).setCellValue("Naziv");
        head.createCell(2).setCellValue("Broj glasova");
        head.createCell(3).setCellValue("Link");
        for (PollOption b : results) {
            HSSFRow tblRow = sheet.createRow(row++);
            tblRow.createCell(0).setCellValue(b.getId());
            tblRow.createCell(1).setCellValue(b.getTitle());
            tblRow.createCell(2).setCellValue(b.getVoteCount());
            tblRow.createCell(3).setCellValue(b.getLink());
        }
        return hwb;
    }
}