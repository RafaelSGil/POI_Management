package pt.isec.pa.apoio_poe.model.data.proposals;

import pt.isec.pa.apoio_poe.model.data.person.Student;

abstract public class MidProposal extends Proposal{
    //abstract class that will be extended into internships and projects

    private String branch;

    public MidProposal(String idOfProposal, String title, Student student, String branch){
        super(idOfProposal, title, student);
        this.branch = branch;
    }

    public void setBranch(String branch){this.branch = branch;}

    public String getBranch(){return branch;}
    
}
