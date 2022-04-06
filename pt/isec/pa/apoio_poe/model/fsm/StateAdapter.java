package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.application.Application;

import javax.naming.Context;

public class StateAdapter implements IApplicationState{
    protected Context context;
    protected Application application;

    protected StateAdapter(Context context, Application application){
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
