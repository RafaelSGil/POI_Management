package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import java.util.List;
import java.util.Map;

import pt.isec.pa.apoio_poe.csv_files.Files;

public class ProposalPhase extends StateAdapter {

    public ProposalPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public boolean insertData(String file) {
        if (file == null)
            return false;

        List<List<String>> attributes = Files.openFile(file);

        data.addProposalFile(attributes);

        return true;
    }

    @Override
    public boolean deleteData(String identifier) {
        return data.removeProposals(identifier);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PROPOSAL;
    }

    @Override
    public boolean professorManager() {
        setState(ApplicationState.PROFESSOR);
        return true;
    }

    @Override
    public boolean studentManager() {
        setState(ApplicationState.STUDENT);
        return true;
    }

    @Override
    public String checkData() {
        return data.getAllProjects();
    }

    @Override
    public boolean closeState() {
        if (data.lockPhase1()) {
            setState(ApplicationState.PROPOSAL_LOCKED);
            data.lockPhase(ApplicationState.STUDENT);
            data.lockPhase(ApplicationState.PROFESSOR);
            data.lockPhase(ApplicationState.PROPOSAL);
        }

        return true;
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
    public boolean editDataProposal(String identifier, String attribute, List<String> newValue) {
        return data.editProposals(identifier, attribute, newValue);
    }
}
