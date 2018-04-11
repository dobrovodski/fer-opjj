package hr.fer.zemris.java.hw05.db.fieldgetters;

import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * This class holds the strategies responsible for obtaining a requested field from a given {@link StudentRecord}.
 *
 * @author matej
 */
public class FieldValueGetters {
    // First name getter
    public static final IFieldValueGetters FIRST_NAME;
    // Last name getter
    public static final IFieldValueGetters LAST_NAME;
    // JMBAG getter
    public static final IFieldValueGetters JMBAG;

    static {
        FIRST_NAME = StudentRecord::getFirstName;
        LAST_NAME = StudentRecord::getLastName;
        JMBAG = StudentRecord::getJmbag;
    }

    /**
     * Converts given string to corresponding FieldValueGetter
     *
     * @param field string representation of the getter
     *
     * @return converted FieldValueGetter
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
