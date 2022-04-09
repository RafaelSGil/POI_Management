package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.data.Data;

public class ProfessorPhaseLocked extends StateAdapter {
    public ProfessorPhaseLocked(ApplicationContext context, Data data) {
        super(context, data);
    }
}
