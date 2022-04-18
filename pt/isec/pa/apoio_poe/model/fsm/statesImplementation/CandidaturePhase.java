package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.csv_files.Files;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;

import java.util.List;

public class CandidaturePhase extends StateAdapter {
    public CandidaturePhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.CANDIDATURE;
    }

    @Override
    public boolean insertData(String file) {
        if (file == null)
            return false;

        List<List<String>> attributes = Files.openFile(file);
        data.addCandidatureFile(attributes);

        return true;
    }

    @Override
    public boolean removeProposalFromCandidature(String id, String proposal) {
        return data.removeCandidatureGivenStudentID(id, proposal);
    }

    @Override
    public boolean removeCandidature(String id) {
        return data.removeCompleteCandidatureGivenItsID(id);
    }

    @Override
    public boolean studentManager() {
        if (data.isLocked(ApplicationState.STUDENT)) {
            setState(ApplicationState.STUDENT_LOCKED);
            return true;
        }

        setState(ApplicationState.STUDENT);
        return true;
    }

    @Override
    public String checkData() {
        return data.getCandidatures();
    }

    @Override
    public boolean closeState() {
        data.lockPhase(ApplicationState.CANDIDATURE);
        setState(ApplicationState.CANDIDATURE_LOCKED);
        return true;
    }

    @Override
    public boolean editCandidatures(String id, String proposal) {
        return data.editCandidatures(id, proposal);
    }

    @Override
    public boolean proposalAttributionManager() {
        if(data.isLocked(ApplicationState.PROPOSAL_ATTRIBUTION)){
            setState(ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
            return true;
        }

        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return true;
    }

    @Override
    public String listStudentsWithCandidature() {
        return data.listStudentsWithCandidatures();
    }

    @Override
    public String listStudentsWithoutCandidature() {
        return data.listStudentsWithoutCandidatures();
    }

    @Override
    public String listProposalsFilters(List<Integer> filters) {
        return data.listProposalsFilters(filters, ApplicationState.CANDIDATURE);
    }
}
