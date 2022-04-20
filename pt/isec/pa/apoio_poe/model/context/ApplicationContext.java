package pt.isec.pa.apoio_poe.model.context;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.data.person.Person;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.IApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.StudentPhase;
import java.util.ArrayList;
import java.util.List;
import pt.isec.pa.apoio_poe.csv_files.Files;

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

    public boolean insertStudentData(String file) {
        if (file == null)
            return false;

        List<List<String>> attributes = Files.openFile(file);

        data.addStudentFile(attributes);

        return true;
    }

    public boolean insertProfessorData(String file) {

        if (file == null)
            return false;

        List<List<String>> attributes = Files.openFile(file);
        data.addProfessorFile(attributes);

        return true;
    }

    public boolean insertProposalsData(String file) {
        if (file == null)
            return false;

        List<List<String>> attributes = Files.openFile(file);
        data.addProposalFile(attributes);

        return true;

    }

    public boolean insertCandidatureData(String file) {
        if (file == null)
            return false;

        List<List<String>> attributes = Files.openFile(file);
        data.addCandidatureFile(attributes);

        return true;
    }

    public String checkStudentData() {
        return data.getAllStudents();
    }

    public String checkProfessorData() {
        return data.getAllProfessors();
    }

    public String checkProposalsData() {
        return data.getAllProjects();
    }

    public String checkCandidaturesData() {
        return data.getCandidatures();
    }

    public int checkTypeProposal(String identifier) {
        return data.checkTypeProposal(identifier);
    }

    public boolean editDataProposals(String identifier, String attribute, List<String> newValue) {
        return data.editProposals(identifier, attribute, newValue);
    }

    public boolean editDataProfessor(String email, boolean advisor) {
        return data.editProfessor(email, advisor);
    }

    public boolean editDataStudent(String identifier, String change, String whatToChange) {
        return data.editStudent(identifier, change, whatToChange);
    }

    public boolean removeStudentData(long idOfStudent) {
        return data.removeStudentGivenItsId(idOfStudent);
    }

    public boolean removeProfessorData(String email) {
        return data.removeProfessorGivenItsEmail(email);
    }

    public boolean removeProposals(String idOfProposal) {
        return data.removeProposals(idOfProposal);
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
        return data.removeCandidatureGivenStudentID(id, proposal);
    }

    public boolean removeCandidature(String id) {
        return data.removeCompleteCandidatureGivenItsID(id);
    }

    public boolean editCandidatures(String id, String proposal) {
        return data.editCandidatures(id, proposal);
    }

    public String listStudentsWithCandidatures() {
        return data.listStudentsWithCandidatures();
    }

    public String listStudentsWithoutCandidatures() {
        return data.listStudentsWithoutCandidatures();
    }

    public String listStudentsWithAutoProposals() {
        return data.listStudentsWithAutoProposals();
    }

    public String listProposalsFiltersCandidature(List<Integer> filters) {
        return data.listProposalsFilters(filters, ApplicationState.CANDIDATURE);
    }

    public String listProposalFiltersProposalAttribuition(List<Integer> filters) {
        return data.listProposalsFilters(filters, ApplicationState.PROPOSAL_ATTRIBUTION);
    }

    @Override
    public String toString() {
        return data + ", phase = " + state + "\n";
    }

    public boolean isBranchValid(String branch) {
        return branch.equals("DA") || branch.equals("RAS") || branch.equals("SI");
    }

    public boolean isLocked(ApplicationState s) {
        return data.isLocked(s);
    }

    public boolean associateAttributionProposal() {
        return data.associatedAttribution();
    }

    public boolean associateProfessorAttribution() {
        data.associatedAdvisor();
        return true;
    }

    public ArrayList<Person> nonAssociatedAttribution() {
        return data.nonAssociateAttribution();
    }

    public ArrayList<Person> chooseStudentToAssociate(ArrayList<Person> studentsProposals, int index) {
        return data.chooseStudentToAssociate(studentsProposals, index);
    }

    public boolean proposalAttributionManager() {
        return state.proposalAttributionManager();
    }

    public String listStudentWithProposalAttributed() {
        return data.listStudentWithProposalAttributed();
    }

    public String listStudentWithoutProposalAttributed() {
        return data.listStudentWithoutProposalAttributed();
    }

    public boolean manualAttribution(String idOfProposal, long idOfStudent) {
        return data.manualAttribution(idOfProposal, idOfStudent);
    }

    public boolean manualRemoval(String idOfProposal) {
        return data.manualRemoval(idOfProposal);
    }

    public String getProfessorByEmail(String email) {
        return data.getProfessorGivenItsEmail(email);
    }

    public String listProfessorAttributions() {
        return data.listProfessorAttributions();
    }

    public boolean professorAttributionManager() {
        return state.professorAttributionManager();
    }

    public boolean manualProfessorAttribution(String idOfProposal, String email) {
        return data.manualProfessorAttribution(idOfProposal, email);
    }

    public boolean manualProfessorRemoval(String email) {
        return data.manualProfessorRemoval(email);
    }

    public String listStudentsWithProposalAndProfessorAttributed() {
        return data.listStudentsWithProposalAndProfessorAttributed();
    }

    public String listStudentsWithProposalAttributedAndWithoutProfessorAttributed() {
        return data.listStudentsWithProposalAttributedAndWithoutProfessorAttributed();
    }

    public String listAverageAttributions() {
        return data.getAverageNumberOfAttributionsForProfessors();
    }

    public String listMinimumAttributions() {
        return data.getMinProfessorsAttributions();
    }

    public String listMaximumAttribution() {
        return data.getMaxProfessorsAttributions();
    }

    public String listSpecificProfessorAttribution(String email) {
        return data.getSpecificProfessorAttributions(email);
    }
}
