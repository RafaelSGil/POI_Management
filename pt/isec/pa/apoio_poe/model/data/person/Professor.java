package pt.isec.pa.apoio_poe.model.data.person;

import java.util.Objects;

public class Professor extends Person {
    public Professor(String name, String email) {
        super(name, email);
    }

    public static Person createProfessor(String name, String email) {
        return new Professor(name, email);
    }

    public static Person createDummyProfessor(String email) {
        return new Professor(null, email);
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
        return String.format("Prof. %s with email = %s",
                super.getName(), super.getEmail());
    }
}
