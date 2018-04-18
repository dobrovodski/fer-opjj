package hr.fer.zemris.java.hw06.demo4;

/**
 * This class models a single student record.
 *
 * @author matej
 */
public class StudentRecord {
    /**
     * Student's JMBAG.
     */
    private String jmbag;
    /**
     * Student's last name.
     */
    private String lastName;
    /**
     * Student's first name.
     */
    private String firstName;
    /**
     * Student's midterm score.
     */
    private double midtermScore;
    /**
     * Student's final exam score.
     */
    private double finalExamScore;
    /**
     * Student's lab score.
     */
    private double labScore;
    /**
     * Student's grade.
     */
    private int grade;

    public StudentRecord(String jmbag, String lastName, String firstName, double midtermScore, double finalExamScore,
            double labScore, int grade) {
        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.midtermScore = midtermScore;
        this.finalExamScore = finalExamScore;
        this.labScore = labScore;
        this.grade = grade;
    }

    /**
     * Returns student's JMBAG.
     *
     * @return student's JMBAG
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Returns student's last name.
     *
     * @return student's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns student's first name.
     *
     * @return student's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns student's midterm score.
     *
     * @return student's midterm score
     */
    public double getMidtermScore() {
        return midtermScore;
    }

    /**
     * Returns student's final exam score.
     *
     * @return student's final exam score
     */
    public double getFinalExamScore() {
        return finalExamScore;
    }

    /**
     * Returns student's lab score.
     *
     * @return student's lab score
     */
    public double getLabScore() {
        return labScore;
    }

    /**
     * Returns student's grade.
     *
     * @return student's grade
     */
    public int getGrade() {
        return grade;
    }
}
