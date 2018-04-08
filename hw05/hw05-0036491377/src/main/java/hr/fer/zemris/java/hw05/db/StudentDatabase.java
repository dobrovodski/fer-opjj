package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

import java.util.ArrayList;
import java.util.List;

public class StudentDatabase {
	SimpleHashtable<String, StudentRecord> index;
	List<StudentRecord> records;

	public StudentDatabase(String[] databaseRows) {

	}

	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}

	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filtered = new ArrayList<>();
		for (StudentRecord record : records) {
			if (filter.accepts(record)) {
				filtered.add(record);
			}
		}

		return filtered;
	}
}
