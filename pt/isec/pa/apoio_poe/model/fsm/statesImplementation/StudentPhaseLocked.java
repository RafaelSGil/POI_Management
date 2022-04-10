package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

public class StudentPhaseLocked extends StateAdapter {

    public StudentPhaseLocked(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public String checkData() {
        return data.getAllStudents();
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.STUDENT_LOCKED;
    }

    @Override
    public boolean professorManager() {
        setState(ApplicationState.PROFESSOR_LOCKED);
        return true;
    }

    @Override
    public boolean proposalManager() {
        setState(ApplicationState.PROPOSAL_LOCKED);
        return true;
    }
    @Override
    public boolean candidatureManager() {
        if(isLocked(ApplicationState.CANDIDATURE)){
            setState(ApplicationState.CANDIDATURE_LOCKED);
            return true;
        }
        setState(ApplicationState.CANDIDATURE);
        return true;
    }

}
