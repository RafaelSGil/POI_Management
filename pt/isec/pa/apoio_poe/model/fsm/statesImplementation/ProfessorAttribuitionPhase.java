package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;

public class ProfessorAttribuitionPhase extends StateAdapter {
    public ProfessorAttribuitionPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PROFESSOR_ATTRIBUTION;
    }

    @Override
    public boolean closeState() {
        data.lockPhase(ApplicationState.PROFESSOR_ATTRIBUTION);
        setState(ApplicationState.SEARCH);
        return true;
    }

    @Override
    public boolean proposalAttributionManager() {
        if (data.isLocked(ApplicationState.PROPOSAL_ATTRIBUTION)) {
            setState(ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
            return true;
        }

        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return true;
    }

}
