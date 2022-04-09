package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

public class StudentPhaseLocked extends StateAdapter {

    public StudentPhaseLocked(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public String checkData() {
        return data.getAllStudents();
    }
}
