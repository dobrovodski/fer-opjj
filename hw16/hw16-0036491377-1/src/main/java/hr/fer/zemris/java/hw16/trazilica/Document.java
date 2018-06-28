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

/**
 * Represents a document which holds term frequency data. If given an IDF vector, it can also calculate the TF-IDF
 * factor.
 *
 * @author marvin
 */
public class Document {
    /**
     * Tf vector.
     */
    private Map<String, Integer> tf = new TreeMap<>();
    /**
     * Idf vector.
     */
    private Map<String, Double> idf = null;
    /**
     * Tfidf vector.
     */
    private Map<String, Double> tfidf = null;
    /**
     * Keeps track of if idf vector has changed.
     */
    private boolean changed = false;
    /**
     * Filepath to the file of the actual physical document.
     */
    private String filepath;

    /**
     * Constructor.
     *
     * @param filepath path to the document
     * @param stopWords set of stop-words to ignore while reading documents
     *
     * @throws IOException if something goes wrong while reading files
     */
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

        br.close();
    }

    /**
     * Constructor which doesn't take a path. Used for querying.
     *
     * @param words words of the query
     * @param stopWords set of stop-words
     */
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

    /**
     * Returns the term frequency vector (Maps words -> number of occurences).
     *
     * @return tf vector
     */
    public Map<String, Integer> getTf() {
        return tf;
    }

    /**
     * Returns filepath associated with the document.
     *
     * @return filepath
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * Sets the inverse document frequency vector. After this is set, user may call getTfidf() to get the tf-idf
     * vector.
     *
     * @param idf inverse document frequency vector
     */
    public void setIdf(Map<String, Double> idf) {
        this.idf = idf;
        this.changed = true;
    }

    /**
     * Returns the term frequency - inverse document frequency vector. Can only be called after setting idf vector.
     *
     * @return term frequency - inverse document frequency vector
     */
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

    /**
     * Prints document to the given stream.
     *
     * @param out stream to print to
     *
     * @throws IOException if cannot use stream
     */
    public void print(PrintStream out) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));
        String line;

        while ((line = br.readLine()) != null) {
            out.println(line);
        }

        br.close();
    }
}
