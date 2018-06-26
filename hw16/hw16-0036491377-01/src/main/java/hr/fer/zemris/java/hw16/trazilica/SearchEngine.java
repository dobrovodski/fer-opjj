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

public class SearchEngine {
    private final String STOPWORD_PATH = "stop_rijeci.txt";

    private Set<String> vocabulary;
    private Set<String> stopWords;
    private List<Document> documents;
    private Map<String, Double> idf;
    private List<Result> results;

    public SearchEngine(String docPath) throws IOException {
        stopWords = buildStopWordSet(STOPWORD_PATH);
        vocabulary = buildVocabulary(docPath);
        documents = buildDocuments(docPath, stopWords);
    }

    private static double normTfidf(Map<String, Double> tfidf) {
        double sumSquares = 0;
        for (String word : tfidf.keySet()) {
            sumSquares += tfidf.get(word) * tfidf.get(word);
        }

        return Math.sqrt(sumSquares);
    }

    private Set<String> buildStopWordSet(String filepath) throws IOException {
        Set<String> stopWords = new TreeSet<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));
        String line;

        while ((line = br.readLine()) != null) {
            stopWords.add(line.trim().toLowerCase());
        }

        return stopWords;
    }

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

        return vocabulary;
    }

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

        return wordSet;
    }

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

        return documents;
    }

    private Map<String, Double> buildIdf(List<Document> documents) {
        int D = documents.size();
        Map<String, Double> idf = new TreeMap<>();

        for (String word : vocabulary) {
            word = word.trim().toLowerCase();
            double idfValue = Math.log(D * 1.0 / wordAppearance(word, documents));
            idf.put(word, idfValue);
        }

        for (Document doc : documents) {
            doc.setIdf(idf);
        }

        return idf;
    }

    private int wordAppearance(String word, List<Document> documents) {
        int count = 0;

        for (Document doc : documents) {
            if (doc.getTf().containsKey(word)) {
                count++;
            }
        }

        return count;
    }

    private double calculateSimilarity(Document queryDoc, Document existingDoc) {
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

        return numerator / denominator;
    }

    public void calculateSimilaritiesTo(List<String> words) {
        Document queryDoc = new Document(words, stopWords);
        if (results == null) {
            results = new ArrayList<>();
        }

        for (Document doc : documents) {
            double sim = calculateSimilarity(queryDoc, doc);
            results.add(new Result(doc, sim));
        }

        results.sort(Comparator.comparingDouble(Result::getSimilarity));
    }

    public List<Result> getResults() {
        if (results == null) {
            throw new IllegalStateException("Did not calculate similarities yet. Call calculateSimilaritiesTo first.");
        }

        return results;
    }

    public List<Result> getTopDocuments(int n) {
        if (results == null) {
            throw new IllegalStateException("Did not calculate similarities yet. Call calculateSimilaritiesTo first.");
        }

        List<Result> top = new ArrayList<>();
        n = results.size() < n ? results.size() : n;
        for (int i = 0; i < n; i++) {
            Result r = results.get(i);
            if (r.getSimilarity() <= 0) {
                break;
            }
            top.add(r);
        }

        return top;
    }

    public static class Result {
        private Document document;
        private double similarity;

        public Result(Document document, double similarity) {
            this.document = document;
            this.similarity = similarity;
        }

        public Document getDocument() {
            return document;
        }

        public double getSimilarity() {
            return similarity;
        }
    }
}
