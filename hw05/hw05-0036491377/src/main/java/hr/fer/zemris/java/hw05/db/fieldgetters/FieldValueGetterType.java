package hr.fer.zemris.java.hw05.db.fieldgetters;

/**
 * Enum for representing the different {@link FieldValueGetters}.
 *
 * @author matej
 */
public enum FieldValueGetterType {
    /**
     * The firstName getter enum.
     */
    FIRST_NAME("firstName"),
    /**
     * The lastName getter enum.
     */
    LAST_NAME("lastName"),
    /**
     * The jmbag getter enum.
     */
    JMBAG("jmbag");

    // String representation of getter
    private String name;

    FieldValueGetterType(String name) {
        this.name = name;
    }

    /**
     * Returns enum based on given string. Case sensitive.
     *
     * @param name field to convert
     *
     * @return enum representing the selected {@link FieldValueGetters} strategy
     *
     * @throws IllegalArgumentException if the string cannot be converted to an existing getter
     */
    public static FieldValueGetterType getType(String name) {
        for (FieldValueGetterType t : FieldValueGetterType.values()) {
            if (t.name.equals(name)) {
                return t;
            }
        }

        throw new IllegalArgumentException("Could not interpret as field value getter: " + name);
    }
}

