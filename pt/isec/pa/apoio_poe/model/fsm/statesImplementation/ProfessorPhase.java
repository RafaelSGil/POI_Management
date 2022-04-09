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
    public boolean studentManager() {
        setState(ApplicationState.STUDENT);
        return true;
    }

    @Override
    public boolean proposalManager() {
        setState(ApplicationState.PROPOSAL);
        return true;
    }

    @Override
    public boolean closeState() {
        if (data.lockPhase1()) {
            setState(ApplicationState.PROFESSOR_LOCKED);
            lockPhase(ApplicationState.STUDENT);
            lockPhase(ApplicationState.PROFESSOR);
            lockPhase(ApplicationState.PROPOSAL);
        }

        return true;
    }
}
