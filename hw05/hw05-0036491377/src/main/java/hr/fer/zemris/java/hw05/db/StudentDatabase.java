package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;
import hr.fer.zemris.java.hw05.db.filter.IFilter;

import java.util.ArrayList;
import java.util.List;

public class StudentDatabase {
    private SimpleHashtable<String, StudentRecord> index;
    private List<StudentRecord> recordsList;

    public StudentDatabase(List<String> databaseRows) {
        index = new SimpleHashtable<>();
        recordsList = new ArrayList<>();

        for (String row : databaseRows) {
            String[] studentInformation = row.split("\t");

            if (studentInformation.length != 4) {
                throw new IllegalArgumentException("Invalid database entry. "
                                                   + "Needs to have 4 tab-separated fields: " + row);
            }

            String jmbag = studentInformation[0].trim();
            String firstName = studentInformation[1].trim();
            String lastName = studentInformation[2].trim();
            String finalGrade = studentInformation[3].trim();

            StudentRecord sr = new StudentRecord(jmbag, firstName, lastName, finalGrade);
            index.put(jmbag, sr);
            recordsList.add(sr);
        }
    }

    public StudentRecord forJMBAG(String jmbag) {
        return index.get(jmbag);
    }

    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> filtered = new ArrayList<>();
        for (StudentRecord record : recordsList) {
            if (filter.accepts(record)) {
                filtered.add(record);
            }
        }

        return filtered;
    }

    public List<StudentRecord> getRecordsList() {
        return recordsList;
    }
}
