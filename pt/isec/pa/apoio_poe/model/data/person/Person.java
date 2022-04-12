package pt.isec.pa.apoio_poe.model.data.person;

public abstract class Person {
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

    public void setCourseBranch(String course) {
        return;
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

    @Override
    public String toString() {
        return "something went wrong. you are getting the abstract class Person toString";
    }
}
