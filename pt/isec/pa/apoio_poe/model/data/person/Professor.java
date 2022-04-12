package pt.isec.pa.apoio_poe.model.data.person;

import java.util.Objects;

public class Professor extends Person {
    boolean advisor;

    public Professor(String name, String email, boolean advisor) {
        super(name, email);
        this.advisor = advisor;
    }

    public boolean getAdvisor() {
        return advisor;
    }

    @Override
    public void setAdvisor(boolean advisor) {
        this.advisor = advisor;
    }

    public static Person createProfessor(String name, String email, boolean advisor) {
        return new Professor(name, email, advisor);
    }

    public static Person createDummyProfessor(String email) {
        return new Professor(null, email, false);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Professor)) {
            return false;
        }

        return Objects.equals(this.getEmail(), ((Professor) obj).getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getEmail());
    }

    @Override
    public String toString() {
        return String.format("Prof. %s with email = %s has advisor hability = %b",
                super.getName(), super.getEmail(), advisor);
    }
}
