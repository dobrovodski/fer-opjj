package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class QueryParserTest {
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

    private List<String> loader(String path) throws IOException {
        return Files.readAllLines(
                Paths.get(path),
                StandardCharsets.UTF_8
        );
    }
}
