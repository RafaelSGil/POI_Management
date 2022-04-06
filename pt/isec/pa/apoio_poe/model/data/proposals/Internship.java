package pt.isec.pa.apoio_poe.model.data.proposals;
import pt.isec.pa.apoio_poe.model.data.person.Student;

public class Internship extends MidProposal{
    private String nameOfCompany;

    public Internship(String idOfProposal, String title, Student student, String branch, String nameOfCompany){
        super(idOfProposal, title, student, branch);
        this.nameOfCompany = nameOfCompany;
    }

    public static Internship createInternship(String idOfProposal, String title, Student student, String branch, String nameOfCompany){
        return new Internship(idOfProposal, title, student, branch, nameOfCompany);
    }

    public String getNameOfCompany(){return nameOfCompany;}

    public void setNameOfCompany(String nameOfCompany){this.nameOfCompany = nameOfCompany;}

    public static Internship createDummyInternship(String idOfProposal){
        return new Internship(idOfProposal, null, null, null, null);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Internship))
            return false;

        return this.getIdOfProposal() == ((MidProposal) obj).getIdOfProposal();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}