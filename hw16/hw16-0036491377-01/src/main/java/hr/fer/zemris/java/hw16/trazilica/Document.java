package hr.fer.zemris.java.hw16.trazilica;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Document {
    private Map<String, Integer> tf = new TreeMap<>();
    private Map<String, Double> tfidf = null;
    private String filepath;

    public Document(String filepath, Set<String> stopWords) throws IOException {
        this.filepath = filepath;

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


    public Map<String, Double> getTfidf(Map<String, Double> idf) {
        if (tfidf == null) {
            tfidf = new TreeMap<>();
            for (Map.Entry<String, Integer> tfEntry : tf.entrySet()) {
                double value = tfEntry.getValue() * idf.get(tfEntry.getKey());
                tfidf.put(tfEntry.getKey(), value);
            }
        }

        return tfidf;
    }
}
