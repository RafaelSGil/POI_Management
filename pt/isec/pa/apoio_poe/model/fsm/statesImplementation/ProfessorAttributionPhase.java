package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;

public class ProfessorAttributionPhase extends StateAdapter {
    public ProfessorAttributionPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PROFESSOR_ATTRIBUTION;
    }

    @Override
    public boolean closeState() {
        data.lockPhase(ApplicationState.PROFESSOR_ATTRIBUTION);
        setState(ApplicationState.SEARCH);
        return true;
    }

    @Override
    public boolean associateAttribution() {
        data.associatedAdvisor();
        return true;
    }

    @Override
    public boolean manualProfessorAttribution(String idOfProposal, String emailProfessor) {
        return data.manualProfessorAttribution(idOfProposal, emailProfessor);
    }

    @Override
    public boolean manualProfessorRemoval(String email) {
        return data.manualProfessorRemoval(email);
    }

    @Override
    public String checkData() {
        return data.getAllProfessors();
    }

    @Override
    public String getProfessorByEmail(String email) {
        return data.getProfessorGivenItsEmail(email);
    }

    @Override
    public String listProfessorAttributions() {
        return data.listProfessorAttributions();
    }

    @Override
    public boolean proposalAttributionTransitioning() {
        if(data.isLocked(ApplicationState.PROPOSAL_ATTRIBUTION)){
            setState(ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
            return true;
        }

        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return true;
    }

    @Override
    public String listStudentsWithProposalAndProfessor() {
        return data.listStudentsWithProposalAndProfessorAttributed();
    }

    @Override
    public String listStudentsWithProposalWithoutProfessor() {
        return data.listStudentsWithProposalAttributedAndWithoutProfessorAttributed();
    }

    @Override
    public String minimumProfessorAttributions() {
        return data.getMinProfessorsAttributions();
    }

    @Override
    public String maximumProfessorAttributions() {
        return data.getMaxProfessorsAttributions();
    }

    @Override
    public String averageProfessorAttributions() {
        return data.getAverageNumberOfAttributionsForProfessors();
    }

    @Override
    public String specificProfessorAttributions(String email) {
        return data.getSpecificProfessorAttributions(email);
    }
}
