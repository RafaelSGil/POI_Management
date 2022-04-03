package pt.isec.pa.apoio_poe.model.data.proposals;
import pt.isec.pa.apoio_poe.model.data.person.Student;

abstract public class Proposal {
    private static int id = 0;
    private String idOfProposal;
    private String title;
    private Student student;

    public Proposal(String idOfProposal, String title, Student student){
        this.idOfProposal = idOfProposal + (++id);
        this.title = title;
        this.student = student;
    }

    public String getIdOfProposal(){return idOfProposal;}

    public void setIdOfProposal(String idOfProposal){this.idOfProposal = idOfProposal;}

    public String getTitle(){return title;}

    public void setTitle(String title){this.title = title;}

    public long getStudent(){return student.getId();}
}
