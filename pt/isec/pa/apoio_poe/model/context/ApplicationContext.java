package pt.isec.pa.apoio_poe.model.context;

import pt.isec.pa.apoio_poe.model.application.Application;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.IApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.PhaseZero;

public class ApplicationContext {
    private Application application;
    private IApplicationState state;

    public ApplicationContext(){
        application = new Application();
        state = new PhaseZero(this, application);
    }

    public ApplicationState getState(){return state.getState();}
}
