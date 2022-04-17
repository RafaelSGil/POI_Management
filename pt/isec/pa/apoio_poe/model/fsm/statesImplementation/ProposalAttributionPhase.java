package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.person.Person;
import pt.isec.pa.apoio_poe.model.data.proposals.Proposal;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Map;

public class ProposalAttributionPhase extends StateAdapter {
    public ProposalAttributionPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PROPOSAL_ATTRIBUTION;
    }

    @Override
    public boolean candidatureManager() {
        if (data.isLocked(ApplicationState.CANDIDATURE)) {
            setState(ApplicationState.CANDIDATURE_LOCKED);
            return true;
        }
        setState(ApplicationState.CANDIDATURE);
        return true;
    }

    @Override
    public boolean associateAttribution() {
        return data.associatedAttribution();
    }

    @Override
    public boolean nonAssociateAttribution() {
        return data.nonAssociateAttribution();
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
    public boolean chooseStudentToAssociate(Person student, String proposal) {
        return data.chooseStudentToAssociate(student, proposal);
    }
}
