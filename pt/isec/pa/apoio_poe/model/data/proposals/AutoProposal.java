package pt.isec.pa.apoio_poe.model.data.proposals;

import pt.isec.pa.apoio_poe.model.data.person.Student;

public class AutoProposal extends Proposal {

    public AutoProposal(String idOfProposal, String title, Student student) {
        super(idOfProposal, title, student);
    }

    public static Proposal createAutoProposal(String idOfProposal, String title, Student student) {
        return new AutoProposal(idOfProposal, title, student);
    }

    public static Proposal createDummyAutoProposal(String idOfProposal) {
        return new AutoProposal(idOfProposal, null, null);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof AutoProposal))
            return false;

        AutoProposal aux = (AutoProposal) obj;
        return this.getIdOfProposal().equals(aux.getIdOfProposal());
    }

    @Override
    public String toString() {
        return String.format("Self proposal (%s) \"%s\" has student (%d) attributed to it",
                super.getIdOfProposal(), super.getTitle(), super.getStudent());
    }
}
