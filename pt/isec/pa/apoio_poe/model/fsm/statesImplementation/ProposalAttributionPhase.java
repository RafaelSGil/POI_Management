package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.person.Person;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ProposalAttributionPhase extends StateAdapter {
    public ProposalAttributionPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PROPOSAL_ATTRIBUTION;
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
    public boolean associateAttribution() {
        boolean bool = data.associatedAttribution();
        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return bool;
    }

    @Override
    public boolean nonAssociateAttribution() {
        boolean bool = data.nonAssociateAttribution();
        if(!bool){
            setState(ApplicationState.TIE);
        }else{
            setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        }

        return bool;
    }

    @Override
    public boolean manualAttribution(String idOfProposal, long idOfStudent) {
        boolean bool = data.manualAttribution(idOfProposal, idOfStudent);
        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return bool;
    }

    @Override
    public boolean manualRemoval(String idOfProposal) {
        boolean bool = data.manualRemoval(idOfProposal);
        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return bool;
    }

    @Override
    public ArrayList<String> listStudentsWithCandidature() {
        ArrayList<String> str = data.listStudentsWithCandidatures();
        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return str;
    }

    @Override
    public ArrayList<String> listStudentsWithAutoProposal() {
        ArrayList<String> str = data.listStudentsWithAutoProposals();
        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return str;
    }

    @Override
    public String listStudentWithProposalAttributed() {
        String str = data.listStudentWithProposalAttributed();
        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return str;
    }

    @Override
    public String listStudentWithoutProposalAttributed() {
        String str = data.listStudentWithoutProposalAttributed();
        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return str;
    }

    @Override
    public String listProposalsFilters(List<Integer> filters) {
        String str = data.listProposalsFilters(filters, ApplicationState.PROPOSAL_ATTRIBUTION);
        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return str;
    }

    @Override
    public boolean closeState() {
        if (!data.isEveryStudentAttributed()) {
            return false;
        }

        data.lockPhase(ApplicationPhases.PHASE3);
        setState(ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
        try {
            data.exportData(false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean professorAttributionTransitioning() {
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return true;
    }
}
