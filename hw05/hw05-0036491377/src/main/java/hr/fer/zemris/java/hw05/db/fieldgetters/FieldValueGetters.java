package hr.fer.zemris.java.hw05.db.fieldgetters;

import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 *
 */
public class FieldValueGetters {
    //
    public static final IFieldValueGetters FIRST_NAME;
    //
    public static final IFieldValueGetters LAST_NAME;
    //
    public static final IFieldValueGetters JMBAG;

    static {
        FIRST_NAME = StudentRecord::getFirstName;
        LAST_NAME = StudentRecord::getLastName;
        JMBAG = StudentRecord::getJmbag;
    }

    /**
     *
     * @param field
     * @return
     */
    public static IFieldValueGetters from(String field) {
        FieldValueGetterType type = FieldValueGetterType.getType(field);

        switch (type) {
            case FIRST_NAME:
                return FieldValueGetters.FIRST_NAME;
            case LAST_NAME:
                return FieldValueGetters.LAST_NAME;
            case JMBAG:
                return FieldValueGetters.JMBAG;
            default:
                throw new IllegalArgumentException("Could not turn given type into field.");
        }
    }
}
