package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Console application used to check the similarity of documents. The program requires one argument: a folder with text
 * files that it will use as a database for comparisons. The command "query (words)" finds the top 10 best results which
 * match the given query. The command "type (number)" then lists the n-th best result. "Results" lists the results once
 * again, and "exit" exits the application.
 *
 * @author marvin
 */
public class Konzola {
    /**
     * Prompt shown to user.
     */
    private static final String PROMPT = "Enter command > ";
    /**
     * Delimiter.
     */
    private static final String DELIMITER = "----------------------------------------------------------------";
    /**
     * Number of top results.
     */
    private static final int TOP_N = 10;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("This program requires only the path to a folder of text documents.");
            return;
        }

        SearchEngine se;
        try {
            se = new SearchEngine(args[0]);
        } catch (IOException e) {
            System.out.println("Could not create search engine using documents in: " + args[0]);
            return;
        }

        Scanner sc = new Scanner(System.in);
        List<SearchEngine.Result> top = null;
        while (true) {
            System.out.print(PROMPT);
            String[] in = sc.nextLine().trim().split(" ", 2);
            String command = in[0].toLowerCase().trim();

            if (command.equals("query")) {
                List<String> words = Arrays.asList(in[1].replaceAll("\\s+", " ").split(" "));
                se.calculateSimilaritiesTo(words);
                top = se.getTopDocuments(TOP_N);

                if (top.size() == 0) {
                    System.out.println("No results!");
                    continue;
                }

                System.out.println("Top " + TOP_N + " results: ");
                printResults(top);
            } else if (command.equals("type")) {
                if (top == null) {
                    System.out.println("A query must be made first.");
                    continue;
                }

                int n;
                try {
                    n = Integer.parseInt(in[1].trim());
                } catch (NumberFormatException ex) {
                    System.out.println("Could not parse as integer: " + in[1]);
                    continue;
                }

                if (top.size() <= n) {
                    System.out.println("There aren't that many results. Number of results: " + top.size());
                    continue;
                }

                Document doc = top.get(n).getDocument();

                System.out.println(doc.getFilepath());
                System.out.println(DELIMITER);
                doc.print(System.out);
                System.out.println(DELIMITER);
            } else if (command.equals("results")) {
                if (top == null) {
                    System.out.println("A query must be made first.");
                    continue;
                }

                se.getTopDocuments(TOP_N);
            } else if (command.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Unknown command.");
            }
        }

        sc.close();
    }

    /**
     * Prints the results from the given list of {@link SearchEngine.Result}s.
     */
    private static void printResults(List<SearchEngine.Result> results) {
        int count = 0;

        for (SearchEngine.Result res : results) {
            double sim = res.getSimilarity();
            String fp = res.getDocument().getFilepath();
            System.out.println(String.format("[%2d] (%.4f) %s", count++, sim, fp));
        }
    }
}
