package hr.fer.zemris.java.hw16.trazilica;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * This class is responsible for finding similarities between two given documents based on tf-idf.
 *
 * @author marvin
 */
public class SearchEngine {
    /**
     * Path to stopword file.
     */
    private final String STOPWORD_PATH = "stop_rijeci.txt";
    /**
     * Vocabulary (all words minus stop words).
     */
    private Set<String> vocabulary;
    /**
     * List of words to be ignored while learning vocabulary.
     */
    private Set<String> stopWords;
    /**
     * List of documents that acts as a "database".
     */
    private List<Document> documents;
    /**
     * Inverse document frequency vector
     */
    private Map<String, Double> idf;
    /**
     * List of {@link Result}s after caclulating similarities.
     */
    private List<Result> results;

    /**
     * Constructor which takes the path to the "database" of documents from which to build the vocabulary from.
     *
     * @param docPath path to folder with txt files
     *
     * @throws IOException if something goes wrong while reading
     */
    public SearchEngine(String docPath) throws IOException {
        stopWords = buildStopWordSet(STOPWORD_PATH);
        vocabulary = buildVocabulary(docPath);
        documents = buildDocuments(docPath, stopWords);

        idf = buildIdf(documents);
        for (Document doc : documents) {
            doc.setIdf(idf);
        }
    }

    /**
     * Normalizes given tf-idf vector.
     *
     * @param tfidf tf-idf vector to normalize
     *
     * @return normalized tf-idf vector
     */
    private static double normTfidf(Map<String, Double> tfidf) {
        double sumSquares = 0;
        for (String word : tfidf.keySet()) {
            sumSquares += tfidf.get(word) * tfidf.get(word);
        }

        return Math.sqrt(sumSquares);
    }

    /**
     * Builds the set of stop words from the given file.
     *
     * @param filepath file to stopwords
     *
     * @return set of strings which are stop words
     *
     * @throws IOException if cannot read file
     */
    private Set<String> buildStopWordSet(String filepath) throws IOException {
        Set<String> stopWords = new TreeSet<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));
        String line;

        while ((line = br.readLine()) != null) {
            stopWords.add(line.trim().toLowerCase());
        }

        br.close();
        return stopWords;
    }

    /**
     * Builds vocabulary from words in given files excluding any words that are a stop word.
     *
     * @param filepath path to database of files
     *
     * @return set of words
     */
    private Set<String> buildVocabulary(String filepath) {
        Path path = Paths.get(filepath);
        Stream<Path> filePathStream;
        Set<String> vocabulary = new TreeSet<>();

        try {
            filePathStream = Files.walk(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not walk path " + path);
        }

        filePathStream.forEach(p -> {
            if (Files.isRegularFile(p)) {
                try {
                    vocabulary.addAll(extractWords(p));
                } catch (IOException e) {
                    System.out.println("Could not read file: " + p.toString());
                    System.out.println("Going to next file.");
                }
            }
        });

        filePathStream.close();
        return vocabulary;
    }

    /**
     * Gets non-stop-words from given file.
     *
     * @param path path to a file
     *
     * @return set of words excluding stop-words
     *
     * @throws IOException if could not read file
     */
    private Set<String> extractWords(Path path) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString()), "UTF-8"));
        Set<String> wordSet = new TreeSet<>();

        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.trim().split("[^\\p{L}]+");

            for (String word : words) {
                word = word.trim().toLowerCase();
                if (!word.isEmpty() && !stopWords.contains(word)) {
                    wordSet.add(word);
                }
            }
        }

        br.close();
        return wordSet;
    }

    /**
     * Builds a list of {@link Document} from the given database of text files using a set of stop words.
     *
     * @param filepath path to text files
     * @param stopWords stop words
     *
     * @return list of documents.
     */
    private List<Document> buildDocuments(String filepath, Set<String> stopWords) {
        List<Document> documents = new ArrayList<>();

        Path path = Paths.get(filepath);
        Stream<Path> filePathStream;
        try {
            filePathStream = Files.walk(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not walk path " + path);
        }

        filePathStream.forEach(p -> {
            if (Files.isRegularFile(p)) {
                try {
                    documents.add(new Document(p.toString(), stopWords));
                } catch (IOException e) {
                    System.out.println("Could not read file: " + p.toString());
                    System.out.println("Going to next file.");
                }
            }
        });

        filePathStream.close();
        return documents;
    }

    /**
     * Builds inverse document frequency vector from given documents
     *
     * @param documents documents to use
     *
     * @return idf vector
     */
    private Map<String, Double> buildIdf(List<Document> documents) {
        int D = documents.size();
        Map<String, Double> idf = new TreeMap<>();

        for (String word : vocabulary) {
            word = word.trim().toLowerCase();
            int wordAppearance = wordAppearance(word, documents);
            if (wordAppearance == 0) {
                continue;
            }

            double idfValue = Math.log(D * 1.0 / wordAppearance(word, documents));
            idf.put(word, idfValue);
        }

        for (Document doc : documents) {
            doc.setIdf(idf);
        }

        return idf;
    }

    /**
     * Counts how many times a word appears in different documents.
     *
     * @param word word to check
     * @param documents list of documents to look in
     *
     * @return number of time word appears in separate documents
     */
    private int wordAppearance(String word, List<Document> documents) {
        int count = 0;

        for (Document doc : documents) {
            if (doc.getTf().containsKey(word)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Returns a value which measures similarities between a text document and a document which was built from a user
     * query. The higher the value, the more similar they are. The value is based on a scalar product between tf-idf
     * vectors.
     *
     * @param queryDoc first doc
     * @param existingDoc second doc
     *
     * @return similarity between the documents
     */
    private double calculateSimilarity(Document queryDoc, Document existingDoc) {
        queryDoc.setIdf(idf);
        double numerator = 0;
        Document shorter = queryDoc.getTfidf().size() < existingDoc.getTfidf().size() ? queryDoc : existingDoc;
        Document longer = queryDoc.getTfidf().size() <= existingDoc.getTfidf().size() ? existingDoc : queryDoc;
        for (String word : shorter.getTfidf().keySet()) {
            if (!longer.getTfidf().containsKey(word)) {
                continue;
            }

            numerator += shorter.getTfidf().get(word) * longer.getTfidf().get(word);
        }

        double denominator = normTfidf(shorter.getTfidf()) * normTfidf(longer.getTfidf());
        if (denominator == 0) {
            return 0;
        }

        return numerator / denominator;
    }

    /**
     * Calculates similarities between given list of words and all the documents in the folder specified earlier.
     *
     * @param words list of words to check
     */
    public void calculateSimilaritiesTo(List<String> words) {
        Document queryDoc = new Document(words, stopWords);
        results = new ArrayList<>();

        for (Document doc : documents) {
            double sim = calculateSimilarity(queryDoc, doc);
            results.add(new Result(doc, sim));
        }

        results.sort(Comparator.comparingDouble(Result::getSimilarity).reversed());
    }

    /**
     * Returns list of results after calling calculateSimilaritiesTo.
     *
     * @return list of results
     */
    public List<Result> getResults() {
        if (results == null) {
            throw new IllegalStateException("Did not calculate similarities yet. Call calculateSimilaritiesTo first.");
        }

        return results;
    }

    /**
     * Returns top n results or as many results as can be returned.
     *
     * @param n number of results wanted
     *
     * @return list of top n results.
     */
    public List<Result> getTopDocuments(int n) {
        if (results == null) {
            throw new IllegalStateException("Did not calculate similarities yet. Call calculateSimilaritiesTo first.");
        }

        List<Result> top = new ArrayList<>();
        n = results.size() <= n ? results.size() : n;
        for (int i = 0; i < n; i++) {
            Result r = results.get(i);
            if (r.getSimilarity() <= 0) {
                break;
            }
            top.add(r);
        }

        return top;
    }

    /**
     * Tuple util class which maps a document and the value of similarity between the doc and the user query.
     *
     * @author marvin
     */
    public static class Result {
        /**
         * Document.
         */
        private Document document;
        /**
         * Similarity.
         */
        private double similarity;

        /**
         * Constructor.
         *
         * @param document document
         * @param similarity similarity
         */
        public Result(Document document, double similarity) {
            this.document = document;
            this.similarity = similarity;
        }

        /**
         * Returns document.
         *
         * @return document
         */
        public Document getDocument() {
            return document;
        }

        /**
         * Returns similarity.
         *
         * @return similarity.
         */
        public double getSimilarity() {
            return similarity;
        }
    }
}
