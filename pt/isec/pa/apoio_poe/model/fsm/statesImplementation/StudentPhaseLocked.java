package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

import java.util.ArrayList;

public class StudentPhaseLocked extends StateAdapter {

    public StudentPhaseLocked(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ArrayList<String> checkData() {
        ArrayList<String> str = data.getAllStudents();
        setState(ApplicationState.STUDENT_LOCKED);
        return str;
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.STUDENT_LOCKED;
    }

    @Override
    public boolean professorTransitioning() {
        setState(ApplicationState.PROFESSOR_LOCKED);
        return true;
    }

    @Override
    public boolean proposalTransitioning() {
        setState(ApplicationState.PROPOSAL_LOCKED);
        return true;
    }
    @Override
    public boolean candidatureTransitioning() {
        if(data.isLocked(ApplicationPhases.PHASE2)){
            setState(ApplicationState.CANDIDATURE_LOCKED);
            return true;
        }
        setState(ApplicationState.CANDIDATURE);
        return true;
    }

}
