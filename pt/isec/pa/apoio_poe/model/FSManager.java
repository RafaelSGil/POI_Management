package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.model.command.*;
import pt.isec.pa.apoio_poe.model.data.person.Person;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.IApplicationState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class FSManager {
    //Facade for FSM

    public static final String PROP_STATE = "state";
    public static final String PROP_DATA = "data";
    public static final String PROP_SWC = "studentWithCandidature";
    public static final String PROP_SWNC = "studentWithoutCandidature";
    public static final String PROP_SWSP = "studentWithSelfProposals";
    public static final String PROP_PF = "proposalFilters";
    public static final String PROP_SWNP = "studentWithoutProposalsAttributed";
    public static final String PROP_SWP = "studentWithProposalsAttributed";

    private ApplicationContext context;
    private CommandManager cmd;
    private PropertyChangeSupport pcs;
    private ArrayList<String> proposalsFilters;

    public FSManager(){
        this.context = new ApplicationContext();
        this.cmd = new CommandManager();
        this.pcs = new PropertyChangeSupport(this);
        this.proposalsFilters = new ArrayList<>();
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener){
        pcs.addPropertyChangeListener(property, listener);
    }

    public ApplicationState getState() {
        return context.getState();
    }

    public ArrayList<String> getLogger(){
        return context.getLogger();
    }

    public void setState(IApplicationState state) {
        context.setState(state);
        pcs.firePropertyChange(PROP_STATE, null, context.getState());
    }

    public boolean chooseType(String type) {
        return context.chooseType(type);
    }

    public boolean insertData(String file) {
        boolean bool = context.insertData(file);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public ArrayList<String> checkData() {
        ArrayList<String> str = context.checkData();
        pcs.firePropertyChange(PROP_STATE, null, null);
        return str;
    }

    public int checkTypeProposal(String identifier) {
        return context.checkTypeProposal(identifier);
    }

    public boolean editDataProposals(String identifier, String attribute, List<String> newValue) {
        boolean bool =  context.editDataProposals(identifier, attribute, newValue);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public boolean editDataProfessor(String email, boolean advisor) {
        boolean bool =  context.editDataProfessor(email, advisor);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public boolean editDataStudent(String identifier, String change, String whatToChange) {
        boolean bool = context.editDataStudent(identifier, change, whatToChange);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public boolean deleteData(String identifier) {
        boolean bool = context.deleteData(identifier);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public boolean closeState() {
        boolean bool = context.closeState();
        pcs.firePropertyChange(PROP_STATE, null, context.getState());
        return bool;
    }

    public boolean professorManager() {
        boolean bool = context.professorManager();
        pcs.firePropertyChange(PROP_STATE, null, context.getState());
        return bool;
    }

    public boolean proposalManager() {
        boolean bool = context.proposalManager();
        pcs.firePropertyChange(PROP_STATE, null, context.getState());
        return bool;
    }

    public boolean studentManager() {
        boolean bool = context.studentManager();
        pcs.firePropertyChange(PROP_STATE, null, context.getState());
        return bool;
    }

    public boolean candidatureManager() {
        boolean bool = context.candidatureManager();
        pcs.firePropertyChange(PROP_STATE, null, context.getState());
        return bool;
    }

    public boolean removeProposalFromCandidature(String id, String proposal) {
        boolean bool = context.removeProposalFromCandidature(id, proposal);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public boolean removeCandidature(String id) {
        boolean bool = context.removeCandidature(id);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public boolean editCandidatures(String id, String proposal) {
        boolean bool = context.editCandidatures(id, proposal);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public ArrayList<String> listStudentsWithCandidatures() {
        ArrayList<String> str = context.listStudentsWithCandidatures();
        return str;
    }

    public void callSWC(){
        pcs.firePropertyChange(PROP_SWC, null, null);
    }

    public ArrayList<String> listStudentsWithoutCandidatures() {
        ArrayList<String> str = context.listStudentsWithoutCandidatures();
        return str;
    }

    public void callSWNC(){
        pcs.firePropertyChange(PROP_SWNC, null, null);
    }

    public ArrayList<String> listStudentsWithAutoProposals() {
        ArrayList<String> str = context.listStudentsWithAutoProposals();
        return str;
    }

    public void callSWSP(){
        pcs.firePropertyChange(PROP_SWSP, null, null);
    }

    public ArrayList<String> listProposalsFilters(List<String> filters) {
        ArrayList<String> str = context.listProposalsFilters(filters);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return str;
    }

    public ArrayList<String> listProposalFiltersGUI(){
        ArrayList<String> arr = context.listProposalsFilters(proposalsFilters);
        return arr;
    }

    public void callPF(List<String> filter){
        proposalsFilters.addAll(filter);
        pcs.firePropertyChange(PROP_PF, null, null);
    }

    public ArrayList<String> getBranches() {
        return context.getBranches();
    }

    public boolean isLocked(ApplicationPhases s) {
        return context.isLocked(s);
    }

    public boolean associateAttribution() {
        boolean bool = context.associateAttribution();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public boolean nonAssociatedAttribution() {
        boolean bool = context.nonAssociatedAttribution();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public ArrayList<Person> getTies(){
        ArrayList<Person> arr = context.getTies();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return arr;
    }

    public boolean chooseStudentToAssociate(int index) {
        boolean bool = context.chooseStudentToAssociate(index);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public boolean proposalAttributionManager() {
        boolean bool = context.proposalAttributionManager();
        pcs.firePropertyChange(PROP_STATE, null, null);
        return bool;
    }

    public String listStudentWithProposalAttributed() {
        String str = context.listStudentWithProposalAttributed();
        return str;
    }

    public void callSWP(){
        pcs.firePropertyChange(PROP_SWP, null, null);
    }

    public String listStudentWithoutProposalAttributed() {
        String str = context.listStudentWithoutProposalAttributed();
        return str;
    }

    public void callSWNP(){
        pcs.firePropertyChange(PROP_SWNP, null, null);
    }

    public boolean manualAttribution(String idOfProposal, String idOfStudent) {
        boolean bool = cmd.invokeProp(new AddPropAtrib(context, idOfProposal, idOfStudent));
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public boolean manualRemoval(String idOfProposal, String idOfStudent) {
        boolean bool = cmd.invokeProp(new RemovePropAtrib(context, idOfProposal, idOfStudent));
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public String getProfessorByEmail(String email) {
        String str = context.getProfessorByEmail(email);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return str;
    }

    public String listProfessorAttributions() {
        String str = context.listProfessorAttributions();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return str;
    }

    public boolean professorAttributionManager() {
        boolean bool = context.professorAttributionManager();
        pcs.firePropertyChange(PROP_STATE, null, context.getState());
        return bool;
    }

    public boolean manualProfessorAttribution(String idOfProposal, String email) {
        boolean bool = cmd.invokeProf(new AddProfAtrib(context, email, idOfProposal));
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public boolean manualProfessorRemoval(String email, String idOfProposal) {
        boolean bool = cmd.invokeProf(new RemoveProfAtrib(context, email, idOfProposal));
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public String listStudentsWithProposalAndProfessorAttributed() {
        String str = context.listStudentsWithProposalAndProfessorAttributed();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return str;
    }

    public String listStudentsWithProposalAttributedAndWithoutProfessorAttributed() {
        String str = context.listStudentsWithProposalAttributedAndWithoutProfessorAttributed();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return str;
    }

    public String listAverageAttributions() {
        String str = context.listAverageAttributions();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return str;
    }

    public String listMinimumAttributions() {
        String str = context.listMinimumAttributions();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return str;
    }

    public String listMaximumAttribution() {
        String str = context.listMaximumAttribution();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return str;
    }

    public String listSpecificProfessorAttribution(String email) {
        String str = context.listSpecificProfessorAttribution(email);
        pcs.firePropertyChange(PROP_DATA, null, null);
        return str;
    }

    public String listStudentWithoutProposalAttributedAndWithCandidature() {
        String str = context.listStudentWithoutProposalAttributedAndWithCandidature();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return str;
    }

    public String listAvailableProposals() {
        String str = context.listAvailableProposals();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return str;
    }

    public String listAttributedProposals() {
        String str = context.listAttributedProposals();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return str;
    }

    public boolean serializationOfProgram() {
        boolean bool = context.serializationOfProgram();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public boolean loadSave() throws ClassNotFoundException {
        boolean bool = context.loadSave();
        pcs.firePropertyChange(PROP_DATA, null, null);
        return bool;
    }

    public void undo(){
        if(getState() == ApplicationState.PROPOSAL_ATTRIBUTION){
            cmd.undoProp();
            return;
        }

        cmd.undoProf();
        pcs.firePropertyChange(PROP_DATA, null, null);
    }

    public void redo(){
        if(getState() == ApplicationState.PROPOSAL_ATTRIBUTION){
            cmd.redoProp();
            return;
        }

        cmd.redoProf();
        pcs.firePropertyChange(PROP_DATA, null, null);
    }
}
