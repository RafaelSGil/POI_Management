package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;
import pt.isec.pa.apoio_poe.model.data.Data;

import java.util.ArrayList;

public class ProfessorPhaseLocked extends StateAdapter {
    public ProfessorPhaseLocked(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PROFESSOR_LOCKED;
    }

    @Override
    public boolean studentTransitioning() {
        setState(ApplicationState.STUDENT_LOCKED);
        return true;
    }

    @Override
    public boolean proposalTransitioning() {
        setState(ApplicationState.PROPOSAL_LOCKED);
        return true;
    }

    @Override
    public ArrayList<String> checkData() {
        ArrayList<String> str = data.getAllProfessors();
        setState(ApplicationState.PROFESSOR_LOCKED);
        return str;
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
