package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;

public class ProposalPhaseLocked extends StateAdapter {
    public ProposalPhaseLocked(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PROPOSAL_LOCKED;
    }

    @Override
    public boolean studentManager() {
        setState(ApplicationState.STUDENT_LOCKED);
        return true;
    }

    @Override
    public boolean professorManager() {
        setState(ApplicationState.PROFESSOR_LOCKED);
        return true;
    }

    @Override
    public String checkData() {
        return data.getAllProjects();
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
