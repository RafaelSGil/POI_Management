package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class ProposalAttributionPhaseLocked extends StateAdapter {
    public ProposalAttributionPhaseLocked(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED;
    }

    @Override
    public boolean candidatureTransitioning() {
        if (data.isLocked(ApplicationPhases.PHASE2)) {
            setState(ApplicationState.CANDIDATURE_LOCKED);
            return true;
        }
        setState(ApplicationState.CANDIDATURE);
        return true;
    }

    @Override
    public ArrayList<String> listStudentsWithCandidature() {
        ArrayList<String> str = data.listStudentsWithCandidatures();
        setState(ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
        return str;
    }

    @Override
    public ArrayList<String> listStudentsWithAutoProposal() {
        ArrayList<String> str = data.listStudentsWithAutoProposals();
        setState(ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
        return str;
    }

    @Override
    public String listStudentWithProposalAttributed() {
        String str = data.listStudentWithProposalAttributed();
        setState(ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
        return str;
    }

    @Override
    public String listStudentWithoutProposalAttributed() {
        String str = data.listStudentWithoutProposalAttributed();
        setState(ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
        return str;
    }

    @Override
    public ArrayList<String> listProposalsFilters(List<String> filters) {
        ArrayList<String> str = data.listProposalsFilters(filters, ApplicationState.PROPOSAL_ATTRIBUTION);
        setState(ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
        return str;
    }

    @Override
    public boolean professorAttributionTransitioning() {
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return true;
    }
}
