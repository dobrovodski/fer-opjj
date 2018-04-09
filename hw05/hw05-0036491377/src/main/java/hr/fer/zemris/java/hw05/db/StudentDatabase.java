package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

import java.util.ArrayList;
import java.util.List;

public class StudentDatabase {
	private SimpleHashtable<String, StudentRecord> index;
	private List<StudentRecord> recordsList;

	public StudentDatabase(String[] databaseRows) {
		index = new SimpleHashtable<>();
		recordsList = new ArrayList<>();

		for (String row : databaseRows) {
			String[] studentInformation = row.split("\t");

			String jmbag = studentInformation[0].trim();
			String firstName = studentInformation[1].trim();
			String lastName = studentInformation[2].trim();
			int finalGrade;
			try {
				finalGrade = Integer.parseInt(studentInformation[3].trim());
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Could not parse as grade: " +  studentInformation[3]);
			}

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
