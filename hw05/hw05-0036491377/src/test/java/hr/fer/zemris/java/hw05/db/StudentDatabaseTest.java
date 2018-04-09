package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class StudentDatabaseTest {
	private StudentDatabase db;

	@Before
	public void initialize() {
		String[] databaseText = loader("database.txt").split("\n");
		db = new StudentDatabase(databaseText);
	}

	@Test
	public void ForJMBAG_FirstEntryInDatabase_CorrectlyReturned() {
		StudentRecord expected = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		Assert.assertEquals(expected, db.forJMBAG("0000000001"));
	}

	@Test
	public void ForJMBAG_RandomEntryInDatabase_CorrectlyReturned() {
		StudentRecord expected = new StudentRecord("0000000045", "Rahle", "Vedran", 2);
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

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is =
				     this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1) break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
}
