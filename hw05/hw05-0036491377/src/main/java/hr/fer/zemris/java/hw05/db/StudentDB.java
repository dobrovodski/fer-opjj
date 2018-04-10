package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.filter.QueryFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class StudentDB {
    private static final String QUERY_KEYWORD = "query";
    private static final String END_KEYWORD = "exit";

    public static void main(String[] args) {
        List<String> lines;

        try {
            lines = Files.readAllLines(
                    Paths.get("database.txt"),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            System.err.println("Could not find file database.txt in current directory.");
            return;
        }

        StudentDatabase db = new StudentDatabase(lines);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            // Loop exit condition
            if (input.equals(END_KEYWORD)) {
                System.out.println("Goodbye!");
                break;
            }

            if (!input.startsWith(QUERY_KEYWORD)) {
                System.out.println("Database query has to start with keyword " + QUERY_KEYWORD);
                continue;
            }

            input = input.substring(QUERY_KEYWORD.length());
            QueryParser parser;

            try {
                parser = new QueryParser(input);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                continue;
            }

            List<StudentRecord> records = new ArrayList<>();

            if (parser.isDirectQuery()) {
                StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
                System.out.println("Using index for record retrieval.");
                records.add(r);
                printRecords(records);
                continue;
            }

            records.addAll(db.filter(new QueryFilter(parser.getQuery())));
            printRecords(records);
        }

        scanner.close();
    }

    /**
     *
     * @param records
     */
    private static void printRecords(List<StudentRecord> records) {
        if (records.size() == 0) {
            System.out.println("Records selected: " + records.size());
            return;
        }

        int jmbagWidth = 0;
        int lastNameWidth = 0;
        int firstNameWidth = 0;
        int gradeWidth = 0;

        for (StudentRecord r : records) {
            if (r.getJmbag().length() > jmbagWidth) {
                jmbagWidth = r.getJmbag().length();
            }

            if (r.getLastName().length() > lastNameWidth) {
                lastNameWidth = r.getLastName().length();
            }

            if (r.getFirstName().length() > firstNameWidth) {
                firstNameWidth = r.getFirstName().length();
            }

            if (r.getFinalGrade().length() > gradeWidth) {
                gradeWidth = r.getFinalGrade().length();
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("+")
          .append(repeat("=", jmbagWidth + 2))
          .append("+")
          .append(repeat("=", lastNameWidth + 2))
          .append("+")
          .append(repeat("=", firstNameWidth + 2))
          .append("+")
          .append(repeat("=", gradeWidth + 2))
          .append("+\n");
        String header = sb.toString();

        sb.setLength(0);
        sb.append(header);
        for (StudentRecord r : records) {
            int jmbagPad = jmbagWidth - r.getJmbag().length();
            int lastNamePad = lastNameWidth - r.getLastName().length();
            int firstNamePad = firstNameWidth - r.getFirstName().length();
            int gradePad = gradeWidth - r.getFinalGrade().length();

            sb.append("| ")
              .append(r.getJmbag())
              .append(repeat(" ", jmbagPad))
              .append(" | ")
              .append(r.getLastName())
              .append(repeat(" ", lastNamePad))
              .append(" | ")
              .append(r.getFirstName())
              .append(repeat(" ", firstNamePad))
              .append(" | ")
              .append(r.getFinalGrade())
              .append(repeat(" ", gradePad))
              .append(" |\n");
        }
        sb.append(header);
        sb.append("Records selected: ").append(records.size());

        System.out.println(sb.toString());
    }

    /**
     *
     * @param template
     * @param n
     * @return
     */
    private static String repeat(String template, int n) {
        return new String(new char[n]).replace("\0", template);
    }
}
