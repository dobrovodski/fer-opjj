package hr.fer.zemris.java.hw16.trazilica;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SearchEngine {
    private final String STOPWORD_PATH = "stop_rijeci.txt";

    private Set<String> vocabulary;
    private Set<String> stopWords;
    private Map<String, Double> idf;
    private Map<Document, Double> results;

    public SearchEngine(String docPath) {
        stopWords = buildStopWordSet(STOPWORD_PATH);
    }

    private static Set<String> buildStopWordSet(String filepath) throws IOException {
        Set<String> stopWords = new TreeSet<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));
        String line;

        while ((line = br.readLine()) != null) {
            stopWords.add(line.trim().toLowerCase());
        }

        return stopWords;
    }
}
