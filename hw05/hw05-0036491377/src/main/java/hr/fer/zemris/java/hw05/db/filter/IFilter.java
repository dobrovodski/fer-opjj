package hr.fer.zemris.java.hw05.db.filter;

import hr.fer.zemris.java.hw05.db.StudentRecord;

public interface IFilter {
    public boolean accepts(StudentRecord record);
}
