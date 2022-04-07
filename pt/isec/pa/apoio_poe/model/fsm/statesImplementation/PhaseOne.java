package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.application.Application;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

public class PhaseOne extends StateAdapter {

    public PhaseOne(ApplicationContext context, Application application){
        super(context, application);
    }

    @Override
    public boolean chooseType(String type) {
        if(type.equals("Students"))
        {
            setState(ApplicationState.STUDENT_PHASE);
            return true;
        }
        else if(type.equals("Professors"))
        {
            setState(ApplicationState.PROFESSOR_PHASE);
            return true;
        }
        else if(type.equals("Proposals"))
        {
            setState(ApplicationState.PROPOSAL_PHASE);
            return true;
        }
        return false;
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.PHASE1;
    }
}
