package pt.isec.pa.apoio_poe.model.context;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.data.proposals.Internship;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.IApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.StudentPhase;

import java.util.List;
import java.util.Map;

public class ApplicationContext {
    private Data data;
    private IApplicationState state;

    public ApplicationContext() {
        this.data = new Data();
        state = new StudentPhase(this, data);
    }

    public ApplicationState getState() {
        return state.getState();
    }

    public void setState(IApplicationState state) {
        this.state = state;
    }

    public boolean chooseType(String type) {
        return state.chooseType(type);
    }

    public boolean insertData(String file) {
        return state.insertData(file);
    }

    public String checkData() {
        return state.checkData();
    }

    public int checkTypeProposal(String identifier) {
        return data.checkTypeProposal(identifier);
    }

    public boolean editDataProposals(String identifier, String attribute, List<String> newValue) {
        return state.editDataProposal(identifier, attribute, newValue);
    }

    public boolean editDataProfessor(String email, boolean advisor) {
        return state.editDataProfessor(email, advisor);
    }

    public boolean editDataStudent(String identifier, String change, String whatToChange) {
        return state.editDataStudent(identifier, change, whatToChange);
    }

    public boolean deleteData(String identifier) {
        return state.deleteData(identifier);
    }

    public boolean closeState() {
        return state.closeState();
    }

    public boolean professorManager() {
        return state.professorManager();
    }

    public boolean proposalManager() {
        return state.proposalManager();
    }

    public boolean studentManager() {
        return state.studentManager();
    }

    public boolean candidatureManager() {
        return state.candidatureManager();
    }

    public boolean removeProposalFromCandidature(String id, String proposal) {
        return state.removeProposalFromCandidature(id, proposal);
    }

    public boolean removeCandidature(String id) {
        return state.removeCandidature(id);
    }

    public boolean editCandidatures(String id, String proposal) {
        return state.editCandidatures(id, proposal);
    }

    public String listStudentsWithCandidatures(){
        return data.listStudentsWithCandidatures();
    }

    public String listStudentsWithoutCandidatures(){
        return data.listStudentsWithoutCandidatures();
    }

    public String listStudentsWithAutoProposals(){
        return data.listStudentsWithAutoProposals();
    }

    public String listProposalsFilters(List<Integer> filters){
        return data.listProposalsFilters(filters);
    }

    @Override
    public String toString() {
        return data + ", phase = " + state + "\n";
    }

    public boolean isBranchValid(String branch) {
        if (branch.equals("DA") || branch.equals("RAS") || branch.equals("SI")) {
            return true;
        }

        return false;
    }
}
