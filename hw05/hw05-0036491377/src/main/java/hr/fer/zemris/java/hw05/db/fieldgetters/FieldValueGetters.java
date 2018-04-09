package hr.fer.zemris.java.hw05.db.fieldgetters;

import hr.fer.zemris.java.hw05.db.StudentRecord;

public class FieldValueGetters {
	public static final IFieldValueGetters FIRST_NAME;
	public static final IFieldValueGetters LAST_NAME;
	public static final IFieldValueGetters JMBAG;

	static {
		FIRST_NAME = StudentRecord::getFirstName;
		LAST_NAME = StudentRecord::getLastName;
		JMBAG = StudentRecord::getJmbag;
	}
}
