package pt.isec.pa.apoio_poe.model.data.proposals;

import java.util.List;

import pt.isec.pa.apoio_poe.model.data.person.Student;

public class Internship extends MidProposal {
    private String nameOfCompany;

    public Internship(String idOfProposal, String title, Student student, List<String> branch,
            String nameOfCompany) {
        super(idOfProposal, title, student, branch);
        this.nameOfCompany = nameOfCompany;
    }

    public static MidProposal createInternship(String idOfProposal, String title, Student student,
            List<String> branch,
            String nameOfCompany) {
        return new Internship(idOfProposal, title, student, branch, nameOfCompany);
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public static MidProposal createDummyInternship(String idOfProposal) {
        return new Internship(idOfProposal, null, null, null, null);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Internship))
            return false;

        Internship aux = (Internship) obj;

        return this.getIdOfProposal().equals(aux.getIdOfProposal());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "(%s) - \"%s\". student (%d). branch \"%s\" and it takes place at %s",
                super.getIdOfProposal(), super.getTitle(), super.getStudent(), super.getBranches(), nameOfCompany);
    }
}