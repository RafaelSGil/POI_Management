package pt.isec.pa.apoio_poe.model.data.person;

import java.io.Serializable;

public abstract class Person implements Serializable {
    private String name;
    private final String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        return;
    }

    public boolean getAttributted() {
        return false;
    }

    public void setAttributted(boolean bol) {
        return;
    }

    public void setCourseBranch(String course) {
        return;
    }

    public String getCourseBranch() {
        return null;
    }

    public double getClassification() {
        return 0;
    }

    public void setClassification(double classification) {
        return;
    }

    public void setInternship(boolean internship) {
        return;
    }

    public void setAdvisor(boolean advisor) {
        return;
    }

    public boolean getInternship() {
        return false;
    }

    public long getId() {
        return 0;
    }

    @Override
    public String toString() {
        return "something went wrong. you are getting the abstract class Person toString";
    }
}
