package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

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
    public boolean editDataProposal(Map<String, Map<String, String>> attributes) {
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
    public boolean listStudenstWithAutoProposal() {
        return false;
    }

    @Override
    public boolean listStudentsWithCandidature() {
        return false;
    }

    @Override
    public boolean listStudentsWithoutCandidature() {
        return false;
    }

    @Override
    public boolean listAutoProposalWithouCandidatures() {
        return false;
    }

    @Override
    public boolean listAutoProposalWithCandidatures() {
        return false;
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
    public boolean nonAssociateAttribution() {
        return false;
    }

    @Override
    public boolean manualAttribution() {
        return false;
    }

    @Override
    public boolean listStudents() {
        return false;
    }

    @Override
    public boolean listProposals() {
        return false;
    }

    @Override
    public boolean professorAttributionManager() {
        return false;
    }

    @Override
    public boolean listStudentsWithProposalWihoutProfessor() {
        return false;
    }

    @Override
    public boolean listStudentsWithProposalAndProfessor() {
        return false;
    }

    @Override
    public boolean averageProfessorAtribuitions() {
        return false;
    }

    @Override
    public boolean minimumProfessorAtribuitions() {
        return false;
    }

    @Override
    public boolean maximumProfessorAtribuitions() {
        return false;
    }

    @Override
    public boolean specificProfessorAtribuitions() {
        return false;
    }

    @Override
    public boolean listProposalsWithAtribuitions() {
        return false;
    }

    @Override
    public boolean listProposalsWihoutAtribuitions() {
        return false;
    }

    @Override
    public boolean listStudentsWithProposals() {
        return false;
    }

    @Override
    public boolean listStudentsWihoutProposalsWithCandidature() {
        return false;
    }


}
