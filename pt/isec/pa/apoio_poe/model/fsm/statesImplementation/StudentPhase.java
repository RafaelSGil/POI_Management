package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import java.util.List;

import pt.isec.pa.apoio_poe.csv_files.Files;

public class StudentPhase extends StateAdapter {

    public StudentPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public boolean insertData(String file) {
        if (file == null)
            return false;

        List<List<String>> attributes = Files.openFile(file);
        data.addStudentFile(attributes);

        return true;
    }

    @Override
    public String checkData() {
        return data.getAllStudents();
    }

    @Override
    public boolean deleteData(String identifier) {
        long id = Long.parseLong(identifier);
        return data.removeStudentGivenItsId(id);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.STUDENT;
    }

    @Override
    public boolean professorManager() {
        setState(ApplicationState.PROFESSOR);
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
            setState(ApplicationState.STUDENT_LOCKED);
            data.lockPhase(ApplicationState.STUDENT);
            data.lockPhase(ApplicationState.PROFESSOR);
            data.lockPhase(ApplicationState.PROPOSAL);
        }

        return true;
    }

    @Override
    public boolean candidatureManager() {
        if(data.isLocked(ApplicationState.CANDIDATURE)){
            setState(ApplicationState.CANDIDATURE_LOCKED);
            return true;
        }
        setState(ApplicationState.CANDIDATURE);
        return true;
    }
}
