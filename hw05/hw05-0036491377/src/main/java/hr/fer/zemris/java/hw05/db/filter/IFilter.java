package hr.fer.zemris.java.hw05.db.filter;

import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * This interface holds one method which is used to check if a given record satisfies the query.
 * @author matej
 */
public interface IFilter {
    /**
     * This method returns {@code true} if the filter accepts the given {@link StudentRecord}, {@code false} otherwise.
     * @param record record to be checked
     * @return {@code true} if the filter accepts the record, {@code false} otherwise
     */
    public boolean accepts(StudentRecord record);
}
