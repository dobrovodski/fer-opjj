package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;
import hr.fer.zemris.java.hw05.db.filter.IFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models a simple database used to hold student records. It can filter the records and return the selected
 * ones through the {@code filter} method and it also provides O(1) access for selecting students by direct JMBAG
 * query.
 *
 * @author matej
 */
public class StudentDatabase {
    // Hashtable used to store StudentRecords via their JMBAG
    private SimpleHashtable<String, StudentRecord> index;
    // Stores all the StudentRecords
    private List<StudentRecord> recordsList;

    /**
     * Constructor.
     *
     * @param databaseRows List of rows in a database.
     */
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

    /**
     * Returns a {@link StudentRecord} in O(1) time using the student's JMBAG.
     *
     * @param jmbag JMBAG of the student
     *
     * @return StudentRecord with given jmbag
     */
    public StudentRecord forJMBAG(String jmbag) {
        return index.get(jmbag);
    }

    /**
     * Takes in a filter and returns a filtered list of records.
     *
     * @param filter filter used to select wanted records
     *
     * @return filtered list of records
     */
    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> filtered = new ArrayList<>();
        for (StudentRecord record : recordsList) {
            if (filter.accepts(record)) {
                filtered.add(record);
            }
        }

        return filtered;
    }

    /**
     * Returns a list of all the {@link StudentRecord}s.
     *
     * @return list of student records
     */
    public List<StudentRecord> getRecordsList() {
        return recordsList;
    }
}
