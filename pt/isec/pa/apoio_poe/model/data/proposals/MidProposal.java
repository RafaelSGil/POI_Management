package pt.isec.pa.apoio_poe.model.data.proposals;

import java.util.List;

import pt.isec.pa.apoio_poe.model.data.person.Student;

abstract public class MidProposal extends Proposal{
    // abstract class that will be extended into internships and projects

    private List<String> branch;

    public MidProposal(String idOfProposal, String title, Student student, List<String> branch) {
        super(idOfProposal, title, student);
        this.branch = branch;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try{
            Proposal newProposal = (Proposal) super.clone();
            ((MidProposal) newProposal).branch = List.copyOf(this.branch);
            return newProposal;
        }catch (CloneNotSupportedException e){
            return null;
        }
    }

    public void setBranch(List<String> branch) {
        this.branch = branch;
    }

    public boolean compareBranch(String str) {
        return branch.contains(str);
    }

    public int getBranch() {
        return branch.size();
    }

    public String getProfessor() {
        return null;
    }

    public String getBranches() {
        StringBuilder sb = new StringBuilder();

        for (String str : branch) {
            sb.append(str).append(" ");
        }

        return sb.toString();
    }

}
