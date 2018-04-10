package hr.fer.zemris.java.hw05.db.fieldgetters;

public enum FieldValueGetterType {
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    JMBAG("jmbag");

    private String name;

    FieldValueGetterType(String name) {
        this.name = name;
    }

    public static FieldValueGetterType getType(String name) {
        for (FieldValueGetterType t : FieldValueGetterType.values()) {
            if (t.name.equals(name)) {
                return t;
            }
        }

        throw new IllegalArgumentException("Could not interpret as field value getter: " + name);
    }
}

