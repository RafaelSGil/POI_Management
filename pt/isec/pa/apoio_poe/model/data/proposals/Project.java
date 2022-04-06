package pt.isec.pa.apoio_poe.model.data.proposals;

import pt.isec.pa.apoio_poe.model.data.person.Professor;
import pt.isec.pa.apoio_poe.model.data.person.Student;

public class Project extends MidProposal{
    private Professor professor;

    public Project(String idOfProposal, String title, Student student, String branch, Professor professor){
        super(idOfProposal, title, student, branch);
        this.professor = professor;
    }

    public static Project createProject(String idOfProposal, String title, Student student, String branch, Professor professor){
        return new Project(idOfProposal, title, student, branch, professor);
    }

    public String getProfessor(){return professor.getName();}

    public static Project createDummyProject(String idOfProposal){
        return new Project(idOfProposal, null, null, null, null);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Project))
            return false;
        
        Project aux = (Project) obj;

        return this.getIdOfProposal().equals(aux.getIdOfProposal());
    }
}
