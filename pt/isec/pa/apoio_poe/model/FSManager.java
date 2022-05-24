package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.model.command.*;
import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.data.person.Person;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.IApplicationState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FSManager {
    //Facade for FSM

    private ApplicationContext context;
    private CommandManager cmd;

    public FSManager(){
        this.context = new ApplicationContext();
        this.cmd = new CommandManager();
    }

    public ApplicationState getState() {
        return context.getState();
    }

    public void setState(IApplicationState state) {
        context.setState(state);
    }

    public boolean chooseType(String type) {
        return context.chooseType(type);
    }

    public ArrayList<String> insertData(String file) {
        return context.insertData(file);
    }

    public String checkData() {
        return context.checkData();
    }

    public int checkTypeProposal(String identifier) {
        return context.checkTypeProposal(identifier);
    }

    public boolean editDataProposals(String identifier, String attribute, List<String> newValue) {
        return context.editDataProposals(identifier, attribute, newValue);
    }

    public boolean editDataProfessor(String email, boolean advisor) {
        return context.editDataProfessor(email, advisor);
    }

    public boolean editDataStudent(String identifier, String change, String whatToChange) {
        return context.editDataStudent(identifier, change, whatToChange);
    }

    public boolean deleteData(String identifier) {
        return context.deleteData(identifier);
    }

    public boolean closeState() {
        return context.closeState();
    }

    public boolean professorManager() {
        return context.professorManager();
    }

    public boolean proposalManager() {
        return context.proposalManager();
    }

    public boolean studentManager() {
        return context.studentManager();
    }

    public boolean candidatureManager() {
        return context.candidatureManager();
    }

    public boolean removeProposalFromCandidature(String id, String proposal) {
        return context.removeProposalFromCandidature(id, proposal);
    }

    public boolean removeCandidature(String id) {
        return context.removeCandidature(id);
    }

    public boolean editCandidatures(String id, String proposal) {
        return context.editCandidatures(id, proposal);
    }

    public String listStudentsWithCandidatures() {
        return context.listStudentsWithCandidatures();
    }

    public String listStudentsWithoutCandidatures() {
        return context.listStudentsWithoutCandidatures();
    }

    public String listStudentsWithAutoProposals() {
        return context.listStudentsWithAutoProposals();
    }

    public String listProposalsFilters(List<Integer> filters) {
        return context.listProposalsFilters(filters);
    }

    public ArrayList<String> getBranches() {
        return context.getBranches();
    }

    public boolean isLocked(ApplicationPhases s) {
        return context.isLocked(s);
    }

    public boolean associateAttribution() {
        return context.associateAttribution();
    }

    public boolean nonAssociatedAttribution() {
        return context.nonAssociatedAttribution();
    }

    public ArrayList<Person> getTies(){return context.getTies();}

    public boolean chooseStudentToAssociate(int index) {
        return context.chooseStudentToAssociate(index);
    }

    public boolean proposalAttributionManager() {
        return context.proposalAttributionManager();
    }

    public String listStudentWithProposalAttributed() {
        return context.listStudentWithProposalAttributed();
    }

    public String listStudentWithoutProposalAttributed() {
        return context.listStudentWithoutProposalAttributed();
    }

    public boolean manualAttribution(String idOfProposal, long idOfStudent) {
        return cmd.invoke(new AddPropAtrib(context, idOfProposal, idOfStudent));
    }

    public boolean manualRemoval(String idOfProposal) {
        return cmd.invoke(new RemovePropAtrib(context, idOfProposal));
    }

    public String getProfessorByEmail(String email) {
        return context.getProfessorByEmail(email);
    }

    public String listProfessorAttributions() {
        return context.listProfessorAttributions();
    }

    public boolean professorAttributionManager() {
        return context.professorAttributionManager();
    }

    public boolean manualProfessorAttribution(String idOfProposal, String email) {
        return cmd.invoke(new AddProfAtrib(context, email, idOfProposal));
    }

    public boolean manualProfessorRemoval(String email) {
        return cmd.invoke(new RemoveProfAtrib(context, email));
    }

    public String listStudentsWithProposalAndProfessorAttributed() {
        return context.listStudentsWithProposalAndProfessorAttributed();
    }

    public String listStudentsWithProposalAttributedAndWithoutProfessorAttributed() {
        return context.listStudentsWithProposalAttributedAndWithoutProfessorAttributed();
    }

    public String listAverageAttributions() {
        return context.listAverageAttributions();
    }

    public String listMinimumAttributions() {
        return context.listMinimumAttributions();
    }

    public String listMaximumAttribution() {
        return context.listMaximumAttribution();
    }

    public String listSpecificProfessorAttribution(String email) {
        return context.listSpecificProfessorAttribution(email);
    }

    public String listStudentWithoutProposalAttributedAndWithCandidature() {
        return context.listStudentWithoutProposalAttributedAndWithCandidature();
    }

    public String listAvailableProposals() {
        return context.listAvailableProposals();
    }

    public String listAttributedProposals() {
        return context.listAttributedProposals();
    }

    public boolean serializationOfProgram(String path) {
        return context.serializationOfProgram(path);
    }

    public boolean loadSave(String path) throws ClassNotFoundException {
        return context.loadSave(path);
    }

    public boolean undo(){
        return cmd.undo();
    }

    public boolean redo(){
        return cmd.redo();
    }
}
