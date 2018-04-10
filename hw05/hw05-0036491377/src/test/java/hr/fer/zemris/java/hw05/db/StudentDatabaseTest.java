package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * JUnit tests for StudentDatabase.
 *
 * @author matej
 * @see <a href= "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html"> Naming standards for unit
 *         tests </a>
 */
public class StudentDatabaseTest {
    private StudentDatabase db;

    @Before
    public void initialize() {
        List<String> databaseText = null;
        try {
            databaseText = loader("./database.txt");
        } catch (IOException e) {
            Assert.fail();
        }

        db = new StudentDatabase(databaseText);
    }

    @Test
    public void ForJMBAG_FirstEntryInDatabase_CorrectlyReturned() {
        StudentRecord expected = new StudentRecord("0000000001", "Akšamović", "Marin", "2");
        Assert.assertEquals(expected, db.forJMBAG("0000000001"));
    }

    @Test
    public void ForJMBAG_RandomEntryInDatabase_CorrectlyReturned() {
        StudentRecord expected = new StudentRecord("0000000045", "Rahle", "Vedran", "2");
        Assert.assertEquals(expected, db.forJMBAG("0000000045"));
    }

    @Test
    public void Filter_FilterAlwaysTrue_AllRecordsReturned() {
        List<StudentRecord> filtered = db.filter(sr -> true);
        filtered.removeAll(db.getRecordsList());
        Assert.assertEquals(0, filtered.size());
    }

    @Test
    public void Filter_FilterAlwaysFalse_NoRecordsReturned() {
        List<StudentRecord> filtered = db.filter(sr -> false);
        Assert.assertEquals(0, filtered.size());
    }

    private List<String> loader(String path) throws IOException {
        return Files.readAllLines(
                Paths.get(path),
                StandardCharsets.UTF_8
        );
    }

}
