package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Konzola {
    private static final String PROMPT = "Enter command > ";

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
        while (true) {
            System.out.print(PROMPT);
            String[] in = sc.nextLine().trim().split(" ", 2);
            String command = in[0].trim();

            if (command.equals("query")) {
                String arg = in[1].trim();
                List<String> words = Arrays.asList(in[1].replaceAll("\\s+", " ").split(" "));
                se.calculateSimilaritiesTo(words);
                se.getTopDocuments(10);
            } else if (command.equals("type")) {
                String arg = in[1].trim();
                se.getResults().get(Integer.parseInt(arg)).getDocument().print(System.out);
            } else if (command.equals("results")) {
                se.getTopDocuments(10);
            } else if (command.equals("exit")) {
                break;
            } else {
                System.out.println("Nepoznata naredba.");
            }
        }
    }
}
