package pt.isec.pa.apoio_poe.model.data.person;

import java.util.Objects;

public class Student extends Person {
    private final long id;
    private String course;
    private String courseBranch;
    private double classification;
    private boolean internship;

    public Student(String name, String email, long id, String course, String courseBranch, double classification,
            boolean internship) {
        super(name, email);
        this.id = id;
        this.course = course;
        this.courseBranch = courseBranch;
        this.classification = classification;
        this.internship = internship;
    }

    public static Person createStudent(String name, String email, long id, String course, String courseBranch,
            double classification, boolean internship) {
        return new Student(name, email, id, course, courseBranch, classification, internship);
    }

    public static Person createDummyStudent(long id) {
        return new Student(null, null, id, null, null, 0, false);
    }

    public long getId() {
        return id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourseBranch() {
        return courseBranch;
    }

    public void setCourseBranch(String courseBranch) {
        this.courseBranch = courseBranch;
    }

    public double getClassification() {
        return classification;
    }

    public void setClassification(double classification) {
        this.classification = classification;
    }

    public boolean getInternship() {
        return internship;
    }

    public void setInternship(boolean internship) {
        this.internship = internship;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student))
            return false;

        Student aux = (Student) obj;

        return this.id == aux.getId();
    }

    @Override
    public String toString() {
        return String.format(
                "%s with number = %d and email = %s, from the course %s and branch %s, has a classification of %f and internship habilitation = %b",
                super.getName(), id, super.getEmail(), classification, internship);
    }
}
