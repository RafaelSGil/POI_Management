package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.data.person.Person;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

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
    public ApplicationState getState() {
        return null;
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
    public ArrayList<String> listProposalsFilters(List<String> filters) {
        return null;
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
    public boolean manualProfessorRemoval(String email, String idOfProposal) {
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

    @Override
    public boolean insertData(String file) {
        return false;
    }

    @Override
    public ArrayList<String> checkData() {
        return null;
    }

    @Override
    public void exportData() {
        return;
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
    public boolean chooseType(String type) {
        return false;
    }

    @Override
    public boolean closeState() {
        return false;
    }

    @Override
    public boolean studentTransitioning() {
        return false;
    }

    @Override
    public boolean professorTransitioning() {
        return false;
    }

    @Override
    public boolean proposalTransitioning() {
        return false;
    }

    @Override
    public boolean candidatureTransitioning() {
        return false;
    }

    @Override
    public ArrayList<String> listStudentsWithAutoProposal() {
        return null;
    }

    @Override
    public ArrayList<String> listStudentsWithCandidature() {
        return null;
    }

    @Override
    public ArrayList<String> listStudentsWithoutCandidature() {
        return null;
    }

    @Override
    public String listAutoProposalWithoutCandidatures() {
        return null;
    }

    @Override
    public String listAutoProposalWithCandidatures() {
        return null;
    }

    @Override
    public boolean proposalAttributionTransitioning() {
        return false;
    }

    @Override
    public boolean associateAttribution() {
        return false;
    }

    @Override
    public boolean nonAssociateAttribution() {
        return false;
    }

    @Override
    public boolean chooseStudentToAssociate(int index) {
        return false;
    }

    @Override
    public ArrayList<Person> getTies() {
        return null;
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
    public boolean professorAttributionTransitioning() {
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

}
