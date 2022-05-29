package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.data.Data;

import java.io.FileNotFoundException;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

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
        try {
            data.exportData(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setState(ApplicationState.SEARCH);
        return true;
    }

    @Override
    public boolean associateAttribution() {
        data.associatedAdvisor();
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return true;
    }

    @Override
    public boolean manualProfessorAttribution(String idOfProposal, String emailProfessor) {
        boolean bool = data.manualProfessorAttribution(idOfProposal, emailProfessor);
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return bool;
    }

    @Override
    public boolean manualProfessorRemoval(String email, String idOfProposal) {
        boolean bool = data.manualProfessorRemoval(email, idOfProposal);
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return bool;
    }

    @Override
    public String checkData() {
        String str = data.getAllProfessors();
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return str;
    }

    @Override
    public String getProfessorByEmail(String email) {
        String str = data.getProfessorGivenItsEmail(email);
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return str;
    }

    @Override
    public String listProfessorAttributions() {
        String str = data.listProfessorAttributions();
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return str;
    }

    @Override
    public boolean proposalAttributionTransitioning() {
        if (data.isLocked(ApplicationPhases.PHASE3)) {
            setState(ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
            return true;
        }

        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return true;
    }

    @Override
    public String listStudentsWithProposalAndProfessor() {
        String str = data.listStudentsWithProposalAndProfessorAttributed();
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return str;
    }

    @Override
    public String listStudentsWithProposalWithoutProfessor() {
        String str = data.listStudentsWithProposalAttributedAndWithoutProfessorAttributed();
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return str;
    }

    @Override
    public String minimumProfessorAttributions() {
        String str = data.getMinProfessorsAttributions();
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return str;
    }

    @Override
    public String maximumProfessorAttributions() {
        String str = data.getMaxProfessorsAttributions();
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return str;
    }

    @Override
    public String averageProfessorAttributions() {
        String str = data.getAverageNumberOfAttributionsForProfessors();
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return str;
    }

    @Override
    public String specificProfessorAttributions(String email) {
        String str = data.getSpecificProfessorAttributions(email);
        setState(ApplicationState.PROFESSOR_ATTRIBUTION);
        return str;
    }
}
