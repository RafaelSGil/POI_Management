package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.csv_files.Files;
import java.util.List;

public class ProfessorPhase extends StateAdapter {

    public ProfessorPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PROFESSOR;
    }

    @Override
    public boolean insertData(String file) {
        if (file == null)
            return false;

        List<List<String>> attributes = Files.openFile(file);
        data.addProfessorFile(attributes);

        return true;
    }

    @Override
    public String checkData() {
        return data.getAllProfessors();
    }

    @Override
    public boolean deleteData(String identifier) {
        return data.removeProfessorGivenItsEmail(identifier);
    }

    @Override
    public boolean studentTransitioning() {
        setState(ApplicationState.STUDENT);
        return true;
    }

    @Override
    public boolean editDataProfessor(String email, boolean advisor) {
        return data.editProfessor(email, advisor);
    }

    @Override
    public boolean proposalTransitioning() {
        setState(ApplicationState.PROPOSAL);
        return true;
    }

    @Override
    public boolean closeState() {
        if (data.lockPhase1()) {
            setState(ApplicationState.PROFESSOR_LOCKED);
            data.lockPhase(ApplicationState.STUDENT);
            data.lockPhase(ApplicationState.PROFESSOR);
            data.lockPhase(ApplicationState.PROPOSAL);
        }

        return true;
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
}
