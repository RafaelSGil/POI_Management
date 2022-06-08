package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

import java.util.ArrayList;
import java.util.List;

import pt.isec.pa.apoio_poe.files.csv_files.Files;

public class StudentPhase extends StateAdapter {

    public StudentPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public boolean insertData(String file) {
        if (file == null)
            return false;

        List<List<String>> attributes = Files.openFile(file);
        boolean bool = data.addStudentFile(attributes);

        setState(ApplicationState.STUDENT);
        return bool;
    }

    @Override
    public ArrayList<String> checkData() {
        ArrayList<String> str = data.getAllStudents();
        setState(ApplicationState.STUDENT);
        return str;
    }

    @Override
    public boolean deleteData(String identifier) {
        boolean bool = data.removeStudentGivenItsId(identifier);
        setState(ApplicationState.STUDENT);
        return bool;
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.STUDENT;
    }

    @Override
    public boolean professorTransitioning() {
        setState(ApplicationState.PROFESSOR);
        return true;
    }

    @Override
    public boolean proposalTransitioning() {
        setState(ApplicationState.PROPOSAL);
        return true;
    }

    @Override
    public boolean editDataStudent(String identifier, String change, String whatToChange) {
        boolean bool = data.editStudent(identifier, change, whatToChange);
        setState(ApplicationState.STUDENT);
        return bool;
    }

    @Override
    public boolean closeState() {
        if (data.lockPhase1()) {
            setState(ApplicationState.STUDENT_LOCKED);
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
