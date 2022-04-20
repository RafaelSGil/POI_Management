package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.data.Data;

public class ProfessorPhaseLocked extends StateAdapter {
    public ProfessorPhaseLocked(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PROFESSOR_LOCKED;
    }

    @Override
    public boolean studentManager() {
        setState(ApplicationState.STUDENT_LOCKED);
        return true;
    }

    @Override
    public boolean proposalManager() {
        setState(ApplicationState.PROPOSAL_LOCKED);
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
}
