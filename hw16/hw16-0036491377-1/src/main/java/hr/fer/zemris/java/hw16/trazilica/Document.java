package hr.fer.zemris.java.hw16.trazilica;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Document {
    private Map<String, Integer> tf = new TreeMap<>();
    private Map<String, Double> idf = null;
    private Map<String, Double> tfidf = null;
    private boolean changed = false;
    private String filepath;

    public Document(String filepath, Set<String> stopWords) throws IOException {
        this.filepath = Paths.get(filepath).toAbsolutePath().toString();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));
        String line;

        while ((line = br.readLine()) != null) {
            String[] words = line.trim().split("[^\\p{L}]+");

            for (String word : words) {
                word = word.trim().toLowerCase();
                if (!word.isEmpty()) {
                    if (!tf.containsKey(word)) {
                        tf.put(word, 1);
                    } else {
                        tf.put(word, tf.get(word) + 1);
                    }
                }
            }
        }
    }

    public Document(List<String> words, Set<String> stopWords) {
        for (String word : words) {
            word = word.trim().toLowerCase();
            if (!word.isEmpty()) {
                if (!tf.containsKey(word)) {
                    tf.put(word, 1);
                } else {
                    tf.put(word, tf.get(word) + 1);
                }
            }
        }
    }

    public Map<String, Integer> getTf() {
        return tf;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setIdf(Map<String, Double> idf) {
        this.idf = idf;
        this.changed = true;
    }

    public Map<String, Double> getTfidf() {
        if (this.idf == null) {
            throw new IllegalStateException("Must set Idf first.");
        }

        if (tfidf == null || changed) {
            changed = false;
            tfidf = new TreeMap<>();
            for (Map.Entry<String, Integer> tfEntry : tf.entrySet()) {
                Double idfValue = idf.get(tfEntry.getKey());
                double value = tfEntry.getValue() * (idfValue == null ? 0 : idfValue);
                tfidf.put(tfEntry.getKey(), value);
            }
        }

        return tfidf;
    }

    public void print(PrintStream out) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));
        String line;

        while ((line = br.readLine()) != null) {
            out.println(line);
        }
    }
}
