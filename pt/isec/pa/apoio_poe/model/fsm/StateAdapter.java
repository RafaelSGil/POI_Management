package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.application.Application;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;

import javax.naming.Context;

public class StateAdapter implements IApplicationState{
    protected ApplicationContext context;
    protected Application application;

    protected StateAdapter(ApplicationContext context, Application application){
        this.application = application;
        this.context = context;
    }

    @Override
    public boolean InsertData() {
        return false;
    }

    @Override
    public boolean checkData() {
        return false;
    }

    @Override
    public boolean editData() {
        return false;
    }

    @Override
    public boolean deleteData() {
        return false;
    }
}
