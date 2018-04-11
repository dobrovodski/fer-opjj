package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class models a single student record to be stored in the database. It holds information about the student's
 * first name, last name, JMBAG and final grade.
 *
 * @author matej
 */
public class StudentRecord {
    // student JMBAG
    private String jmbag;
    // student's last name
    private String lastName;
    // student's first name
    private String firstName;
    // student's final grad
    private String finalGrade;

    /**
     * Constructor for the class.
     *
     * @param jmbag student jmbag
     * @param lastName last name
     * @param firstName first name
     * @param finalGrade final grade
     */
    public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StudentRecord that = (StudentRecord) o;
        return Objects.equals(jmbag, that.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }

    /**
     * Returns the jmbag.
     *
     * @return jmbag
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Returns the last name.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the first name.
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the final grade
     *
     * @return final grade
     */
    public String getFinalGrade() {
        return finalGrade;
    }
}
