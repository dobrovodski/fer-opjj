package hr.fer.zemris.java.hw16.trazilica;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Konzola {
    private static final String PROMPT = "Enter command > ";
    private static final String STOPWORD_PATH = "stop_rijeci.txt";
    private static Set<String> vocabulary;
    private static Set<String> stopWords;
    private static double idf;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("This program requires only the path to a folder of text documents.");
            return;
        }

        String filepath = args[0];
        try {
            stopWords = buildStopWordSet(STOPWORD_PATH);
        } catch (IOException e) {
            System.out.println("Could not read stop word file: " + filepath);
            return;
        }

        try {
            vocabulary = buildVocabulary(filepath);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not build vocabulary: " + e.getMessage());
            return;
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(PROMPT);
            String[] in = sc.nextLine().trim().split(" ", 2);
            String command = in[0].trim();
            if (command.equals("query")) {

            } else if (command.equals("type")) {

            } else if (command.equals("results")) {

            } else if (command.equals("exit")) {
                break;
            } else {
                System.out.println("Nepoznata naredba.");
            }
        }
    }

    private static Set<String> buildVocabulary(String filepath) {
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

    private static Set<String> extractWords(Path path) throws IOException {
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
