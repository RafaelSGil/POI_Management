package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.files.csv_files.Files;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Class that represents the Candidature state</p>
 *
 * @author RafelGil and HugoFerreira
 */
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
        boolean bool = data.addCandidatureFile(attributes);

        setState(ApplicationState.CANDIDATURE);
        return bool;
    }

    @Override
    public boolean removeProposalFromCandidature(String id, String proposal) {
        boolean bool = data.removeCandidatureGivenStudentID(id, proposal);

        setState(ApplicationState.CANDIDATURE);

        return bool;
    }

    @Override
    public boolean removeCandidature(String id) {
        boolean bool = data.removeCompleteCandidatureGivenItsID(id);
        setState(ApplicationState.CANDIDATURE);
        return bool;
    }

    @Override
    public boolean studentTransitioning() {
        if (data.isLocked(ApplicationPhases.PHASE1)) {
            setState(ApplicationState.STUDENT_LOCKED);
            return true;
        }

        setState(ApplicationState.STUDENT);
        return true;
    }

    @Override
    public ArrayList<String> checkData() {
        ArrayList<String> str = data.getCandidatures();

        setState(ApplicationState.CANDIDATURE);

        return str;
    }

    @Override
    public boolean closeState() {
        if (data.isLocked(ApplicationPhases.PHASE1)) {
            data.lockPhase(ApplicationPhases.PHASE2);
            setState(ApplicationState.CANDIDATURE_LOCKED);
        }
        return true;
    }

    @Override
    public boolean editCandidatures(String id, String proposal) {
        boolean bool = data.editCandidatures(id, proposal);
        setState(ApplicationState.CANDIDATURE);
        return bool;
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
    public ArrayList<String> listStudentsWithCandidature() {
        ArrayList<String> str = data.listStudentsWithCandidatures();
        setState(ApplicationState.CANDIDATURE);
        return str;
    }

    @Override
    public ArrayList<String> listStudentsWithoutCandidature() {
        ArrayList<String> str = data.listStudentsWithoutCandidatures();
        setState(ApplicationState.CANDIDATURE);
        return str;
    }

    @Override
    public ArrayList<String> listProposalsFilters(List<String> filters) {
        ArrayList<String> str = data.listProposalsFilters(filters, ApplicationState.CANDIDATURE);
        setState(ApplicationState.CANDIDATURE);
        return str;
    }
}
