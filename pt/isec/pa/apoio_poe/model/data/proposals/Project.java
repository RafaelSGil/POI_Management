package pt.isec.pa.apoio_poe.model.data.proposals;

import pt.isec.pa.apoio_poe.model.data.person.Professor;
import pt.isec.pa.apoio_poe.model.data.person.Student;

public class Project extends MidProposal{
    private Professor professor;

    public Project(String idOfProposal, String title, Student student, String branch, Professor professor){
        super(idOfProposal, title, student, branch);
        this.professor = professor;
    }

    public String getProfessor(){return professor.getName();}
}
