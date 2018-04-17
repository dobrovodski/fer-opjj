package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDemo {
    public static void main(String[] args) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("./studenti.txt"),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        List<StudentRecord> records = lines.stream().map(r -> {
            String[] split = r.split("\t");
            String jmbag = split[0];
            String lastName = split[1];
            String firstName = split[2];
            double midtermScore = Double.parseDouble(split[3]);
            double finalExamScore = Double.parseDouble(split[4]);
            double labScore = Double.parseDouble(split[5]);
            int grade = Integer.parseInt(split[6]);
            return new StudentRecord(jmbag, lastName, firstName, midtermScore, finalExamScore, labScore, grade);
        }).collect(Collectors.toCollection(ArrayList::new));

        long broj = vratiBodovaViseOd25(records);
        long broj5 = vratiBrojOdlikasa(records);
        List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
        List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
        List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
        Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
        Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
        Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
    }

    private static long vratiBodovaViseOd25(List<StudentRecord> records) {
        return records.stream()
                      .filter(r -> r.getFinalExamScore() + r.getMidtermScore() + r.getLabScore() > 25)
                      .count();
    }

    private static long vratiBrojOdlikasa(List<StudentRecord> records) {
        return records.stream()
                      .filter(r -> r.getGrade() == 5)
                      .count();
    }

    private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
        return records.stream()
                      .filter(r -> r.getGrade() == 5)
                      .collect(Collectors.toList());
    }

    private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
        return records.stream()
                      .filter(r -> r.getGrade() == 5)
                      .sorted((r1, r2) -> {
                          double score1 = r1.getLabScore() + r1.getMidtermScore() + r1.getFinalExamScore();
                          double score2 = r2.getLabScore() + r2.getMidtermScore() + r2.getFinalExamScore();
                          return Double.compare(score1, score2);
                      })
                      .collect(Collectors.toList());
    }

    private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
        return records.stream()
                      .filter(r -> r.getGrade() == 1)
                      .map(StudentRecord::getJmbag)
                      .sorted(Comparator.naturalOrder())
                      .collect(Collectors.toList());
    }

    private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
        return records.stream()
                      .collect(Collectors.groupingBy(StudentRecord::getGrade));
    }

    private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
        return records.stream()
                      .collect(Collectors.toMap(StudentRecord::getGrade, r -> 1, (a, b) -> a + b));
    }

    private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
        return records.stream()
                      .collect(Collectors.partitioningBy(s -> s.getGrade() > 1));
    }
}
