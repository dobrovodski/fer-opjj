package hr.fer.zemris.java.hw05.db.fieldgetters;

import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Responsible for obtaining a requested field from given {@link StudentRecord}.
 */
public interface IFieldValueGetters {
    /**
     * Obtains a requested field from the given {@link StudentRecord}.
     * @param record record from which the field will be taken
     * @return requested field
     */
    public String get(StudentRecord record);
}
