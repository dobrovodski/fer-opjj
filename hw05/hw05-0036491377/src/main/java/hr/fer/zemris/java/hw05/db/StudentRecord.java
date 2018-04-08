package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

public class StudentRecord {
	private String jmbag;
	private String lastName;
	private String firstName;
	private short finalGrade;

	public StudentRecord(String jmbag, String lastName, String firstName, short finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StudentRecord that = (StudentRecord) o;
		return Objects.equals(jmbag, that.jmbag);
	}

	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}
}
