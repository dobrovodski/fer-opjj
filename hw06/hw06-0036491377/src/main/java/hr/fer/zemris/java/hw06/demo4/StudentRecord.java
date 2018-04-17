package hr.fer.zemris.java.hw06.demo4;

public class StudentRecord {
    private String jmbag;
    private String lastName;
    private String firstName;
    private double midtermScore;
    private double finalExamScore;
    private double labScore;
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

    public String getJmbag() {
        return jmbag;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public double getMidtermScore() {
        return midtermScore;
    }

    public double getFinalExamScore() {
        return finalExamScore;
    }

    public double getLabScore() {
        return labScore;
    }

    public int getGrade() {
        return grade;
    }
}
