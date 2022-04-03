package pt.isec.pa.apoio_poe.model.data.proposals;
import pt.isec.pa.apoio_poe.model.data.person.Student;

public class Internship extends MidProposal{
    private String nameOfCompany;

    public Internship(String idOfProposal, String title, Student student, String branch, String nameOfCompany){
        super(idOfProposal, title, student, branch);
        this.nameOfCompany = nameOfCompany;
    }

    public String getNameOfCompany(){return nameOfCompany;}

    public void setNameOfCompany(String nameOfCompany){this.nameOfCompany = nameOfCompany;}
}