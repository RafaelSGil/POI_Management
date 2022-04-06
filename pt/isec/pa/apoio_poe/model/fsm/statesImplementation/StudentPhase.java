package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.application.Application;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

public class StudentPhase extends StateAdapter {

    public StudentPhase(ApplicationContext context, Application application){
        super(context, application);
    }


    @Override
    public ApplicationState getState() {
        return ApplicationState.STUDENT_PHASE;
    }
    
}
