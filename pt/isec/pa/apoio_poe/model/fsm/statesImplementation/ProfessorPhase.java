package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.files.csv_files.Files;

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

        setState(ApplicationState.PROFESSOR);

        return true;
    }

    @Override
    public String checkData() {
        String str = data.getAllProfessors();
        setState(ApplicationState.PROFESSOR);
        return str;
    }

    @Override
    public boolean deleteData(String identifier) {
        boolean bool = data.removeProfessorGivenItsEmail(identifier);
        setState(ApplicationState.PROFESSOR);
        return bool;
    }

    @Override
    public boolean studentTransitioning() {
        setState(ApplicationState.STUDENT);
        return true;
    }

    @Override
    public boolean editDataProfessor(String email, boolean advisor) {
        boolean bool = data.editProfessor(email, advisor);
        setState(ApplicationState.PROFESSOR);
        return bool;
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
}
