package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.application.Application;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

import javax.naming.Context;

public class ProfessorPhase extends StateAdapter {

    public ProfessorPhase(Context context, Application application){
        super(context, application);
    }
}
