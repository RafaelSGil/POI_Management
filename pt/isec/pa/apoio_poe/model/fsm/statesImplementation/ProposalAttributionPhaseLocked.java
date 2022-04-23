package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;

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
        if (data.isLocked(ApplicationState.CANDIDATURE)) {
            setState(ApplicationState.CANDIDATURE_LOCKED);
            return true;
        }
        setState(ApplicationState.CANDIDATURE);
        return true;
    }

    @Override
    public String listStudentsWithCandidature() {
        return data.listStudentsWithCandidatures();
    }

    @Override
    public String listStudentsWithAutoProposal() {
        return data.listStudentsWithAutoProposals();
    }

    @Override
    public String listStudentWithProposalAttributed() {
        return data.listStudentWithProposalAttributed();
    }

    @Override
    public String listStudentWithoutProposalAttributed() {
        return data.listStudentWithoutProposalAttributed();
    }

    @Override
    public String listProposalsFilters(List<Integer> filters) {
        return data.listProposalsFilters(filters, ApplicationState.PROPOSAL_ATTRIBUTION);
    }

    @Override
    public boolean professorAttributionTransitioning() {
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return true;
    }
}
