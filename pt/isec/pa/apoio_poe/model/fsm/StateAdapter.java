package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.data.person.Person;
import java.util.ArrayList;
import java.util.List;;

public class StateAdapter implements IApplicationState {
    protected ApplicationContext context;
    protected Data data;

    protected StateAdapter(ApplicationContext context, Data data) {
        this.data = data;
        this.context = context;
    }

    protected void setState(ApplicationState state) {
        context.setState(state.createState(context, data));
    }

    @Override
    public boolean insertData(String file) {
        return false;
    }

    @Override
    public String checkData() {
        return "";
    }

    @Override
    public boolean editDataProposal(String identifier, String attribute, List<String> newValue) {
        return false;
    }

    @Override
    public boolean editDataProfessor(String email, boolean advisor) {
        return false;
    }

    @Override
    public boolean editDataStudent(String identifier, String change, String whatToChange) {
        return false;
    }

    @Override
    public boolean deleteData(String identifier) {
        return false;
    }

    @Override
    public ApplicationState getState() {
        return null;
    }

    @Override
    public boolean chooseType(String type) {
        return false;
    }

    @Override
    public boolean closeState() {
        return false;
    }

    @Override
    public boolean studentManager() {
        return false;
    }

    @Override
    public boolean professorManager() {
        return false;
    }

    @Override
    public boolean proposalManager() {
        return false;
    }

    @Override
    public boolean candidatureManager() {
        return false;
    }

    @Override
    public String listStudentsWithAutoProposal() {
        return "Can't list";
    }

    @Override
    public String listStudentsWithCandidature() {
        return "Can't list";
    }

    @Override
    public String listStudentsWithoutCandidature() {
        return "Can't list";
    }

    @Override
    public String listAutoProposalWithoutCandidatures() {
        return "Can't list";
    }

    @Override
    public String listAutoProposalWithCandidatures() {
        return "Can't list";
    }

    @Override
    public boolean proposalAttributionManager() {
        return false;
    }

    @Override
    public boolean associateAttribution() {
        return false;
    }

    @Override
    public ArrayList<Person> nonAssociateAttribution() {
        return null;
    }

    @Override
    public boolean chooseStudentToAssociate(ArrayList<Person> studentsProposals, int index) {
        return false;
    }

    @Override
    public boolean manualAttribution(String idOfProposal, long idOfStudent) {
        return false;
    }

    @Override
    public boolean manualRemoval(String idOfProposal) {
        return false;
    }

    @Override
    public boolean professorAttributionManager() {
        return false;
    }

    @Override
    public String listStudentsWithProposalWithoutProfessor() {
        return null;
    }

    @Override
    public String listStudentsWithProposalAndProfessor() {
        return null;
    }

    @Override
    public String averageProfessorAttributions() {
        return null;
    }

    @Override
    public String minimumProfessorAttributions() {
        return null;
    }

    @Override
    public String maximumProfessorAttributions() {
        return null;
    }

    @Override
    public String specificProfessorAttributions(String email) {
        return null;
    }

    @Override
    public boolean listProposalsWithAttributions() {
        return false;
    }

    @Override
    public boolean listProposalsWithoutAttributions() {
        return false;
    }

    @Override
    public boolean listStudentsWithProposals() {
        return false;
    }

    @Override
    public boolean listStudentsWithoutProposalsWithCandidature() {
        return false;
    }

    @Override
    public boolean removeProposalFromCandidature(String id, String proposal) {
        return false;
    }

    @Override
    public boolean removeCandidature(String id) {
        return false;
    }

    @Override
    public boolean editCandidatures(String id, String proposal) {
        return false;
    }

    @Override
    public String listProposalsFilters(List<Integer> filters) {
        return "Can't list";
    }

    @Override
    public String listStudentWithProposalAttributed() {
        return null;
    }

    @Override
    public String listStudentWithoutProposalAttributed() {
        return null;
    }

    @Override
    public boolean manualProfessorAttribution(String idOfProposal, String emailProfessor) {
        return false;
    }

    @Override
    public boolean manualProfessorRemoval(String email) {
        return false;
    }

    @Override
    public String getProfessorByEmail(String email) {
        return null;
    }

    @Override
    public String listProfessorAttributions() {
        return null;
    }

}
