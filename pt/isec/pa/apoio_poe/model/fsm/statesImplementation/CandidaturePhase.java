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
    public boolean studentManager() {
        if(data.isLocked(ApplicationState.STUDENT)){
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
        setState(ApplicationState.CANDIDATURE_LOCKED);
        return true;
    }
}
