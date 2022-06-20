package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

import java.util.ArrayList;
import java.util.List;

import pt.isec.pa.apoio_poe.files.csv_files.Files;

/**
 * <p>Class that represents the Proposal state</p>
 *
 * @author RafelGil and HugoFerreira
 */
public class ProposalPhase extends StateAdapter {

    public ProposalPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public boolean insertData(String file) {
        if (file == null)
            return false;

        List<List<String>> attributes = Files.openFile(file);

        boolean bool = data.addProposalFile(attributes);

        setState(ApplicationState.PROPOSAL);

        return bool;
    }

    @Override
    public boolean deleteData(String identifier) {
        boolean bool = data.removeProposals(identifier);
        setState(ApplicationState.PROPOSAL);
        return bool;
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PROPOSAL;
    }

    @Override
    public boolean professorTransitioning() {
        setState(ApplicationState.PROFESSOR);
        return true;
    }

    @Override
    public boolean studentTransitioning() {
        setState(ApplicationState.STUDENT);
        return true;
    }

    @Override
    public ArrayList<String> checkData() {
        ArrayList<String> str = data.getAllProjects();
        setState(ApplicationState.PROPOSAL);
        return str;
    }

    @Override
    public boolean closeState() {
        if (data.lockPhase1()) {
            setState(ApplicationState.PROPOSAL_LOCKED);
            data.lockPhase(ApplicationPhases.PHASE1);
        }

        return true;
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
    public boolean editDataProposal(String identifier, String attribute, List<String> newValue) {
        boolean bool = data.editProposals(identifier, attribute, newValue);
        setState(ApplicationState.PROPOSAL);
        return bool;
    }
}
