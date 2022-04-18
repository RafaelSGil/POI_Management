package pt.isec.pa.apoio_poe.model.data.proposals;

import java.util.List;

import pt.isec.pa.apoio_poe.model.data.person.Professor;
import pt.isec.pa.apoio_poe.model.data.person.Student;

public class Project extends MidProposal {
    private Professor professor;

    public Project(String idOfProposal, String title, Student student, List<String> branch, Professor professor) {
        super(idOfProposal, title, student, branch);
        this.professor = professor;
    }

    public static MidProposal createProject(String idOfProposal, String title, Student student, List<String> branch,
            Professor professor) {
        return new Project(idOfProposal, title, student, branch, professor);
    }

    @Override
    public String getProfessor() {
        return professor.getEmail();
    }

    public static MidProposal createDummyProject(String idOfProposal) {
        return new Project(idOfProposal, null, null, null, null);
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Project))
            return false;

        Project aux = (Project) obj;

        return this.getIdOfProposal().equals(aux.getIdOfProposal());
    }

    @Override
    public String toString() {
        return String.format(
                "(%s) - \"%s\". student (%s). branch \"%s\". professor (%s)",
                super.getIdOfProposal(), super.getTitle(), super.getStudent(), super.getBranches(), professor);
    }
}
