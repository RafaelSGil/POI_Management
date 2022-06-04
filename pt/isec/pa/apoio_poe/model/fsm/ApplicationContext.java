package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.data.person.Person;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.IApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.StudentPhase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<String> getLogger(){
        return data.getLog();
    }

    public boolean chooseType(String type) {
        return state.chooseType(type);
    }

    public void insertData(String file) {
        state.insertData(file);
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
        return state.professorTransitioning();
    }

    public boolean proposalManager() {
        return state.proposalTransitioning();
    }

    public boolean studentManager() {
        return state.studentTransitioning();
    }

    public boolean candidatureManager() {
        return state.candidatureTransitioning();
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

    public String listStudentsWithCandidatures() {
        return state.listStudentsWithCandidature();
    }

    public String listStudentsWithoutCandidatures() {
        return state.listStudentsWithoutCandidature();
    }

    public String listStudentsWithAutoProposals() {
        return state.listStudentsWithAutoProposal();
    }

    public String listProposalsFilters(List<Integer> filters) {
        return state.listProposalsFilters(filters);
    }

    @Override
    public String toString() {
        return data + ", phase = " + state + "\n";
    }

    public ArrayList<String> getBranches() {
        return data.getBranches();
    }

    public boolean isLocked(ApplicationPhases s) {
        return data.isLocked(s);
    }

    public boolean associateAttribution() {
        return state.associateAttribution();
    }

    public boolean nonAssociatedAttribution() {
        return state.nonAssociateAttribution();
    }

    public ArrayList<Person> getTies(){return state.getTies();}

    public boolean chooseStudentToAssociate(int index) {
        return state.chooseStudentToAssociate(index);
    }

    public boolean proposalAttributionManager() {
        return state.proposalAttributionTransitioning();
    }

    public String listStudentWithProposalAttributed() {
        return state.listStudentWithProposalAttributed();
    }

    public String listStudentWithoutProposalAttributed() {
        return state.listStudentWithoutProposalAttributed();
    }

    public boolean manualAttribution(String idOfProposal, long idOfStudent) {
        return state.manualAttribution(idOfProposal, idOfStudent);
    }

    public boolean manualRemoval(String idOfProposal) {
        return state.manualRemoval(idOfProposal);
    }

    public String getProfessorByEmail(String email) {
        return state.getProfessorByEmail(email);
    }

    public String listProfessorAttributions() {
        return state.listProfessorAttributions();
    }

    public boolean professorAttributionManager() {
        return state.professorAttributionTransitioning();
    }

    public boolean manualProfessorAttribution(String idOfProposal, String email) {
        return state.manualProfessorAttribution(idOfProposal, email);
    }

    public boolean manualProfessorRemoval(String email, String idOfProposal) {
        return state.manualProfessorRemoval(email, idOfProposal);
    }

    public String listStudentsWithProposalAndProfessorAttributed() {
        return state.listStudentsWithProposalAndProfessor();
    }

    public String listStudentsWithProposalAttributedAndWithoutProfessorAttributed() {
        return state.listStudentsWithProposalWithoutProfessor();
    }

    public String listAverageAttributions() {
        return state.averageProfessorAttributions();
    }

    public String listMinimumAttributions() {
        return state.minimumProfessorAttributions();
    }

    public String listMaximumAttribution() {
        return state.maximumProfessorAttributions();
    }

    public String listSpecificProfessorAttribution(String email) {
        return state.specificProfessorAttributions(email);
    }

    public String listStudentWithoutProposalAttributedAndWithCandidature() {
        return data.listStudentWithoutProposalAttributedAndWithCandidature();
    }

    public String listAvailableProposals() {
        return data.listAvailableProposals();
    }

    public String listAttributedProposals() {
        return data.listAttributedProposals();
    }

    public boolean serializationOfProgram(String path) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path + "/data.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(data);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public boolean loadSave(String path) throws ClassNotFoundException {
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.data = (Data) in.readObject();
            in.close();
            fileIn.close();
            setState(data.isLocked(ApplicationPhases.PHASE1) ?
                    ApplicationState.STUDENT_LOCKED.createState(this, data) :
                    ApplicationState.STUDENT.createState(this, data));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
