package pt.isec.pa.apoio_poe.model.context;
import pt.isec.pa.apoio_poe.model.application.Application;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.IApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.PhaseOne;

public class ApplicationContext {
    private Application application;
    private IApplicationState state;

    public ApplicationContext(){
        application = new Application();
        state = new PhaseOne(this, application);
    }

    public ApplicationState getState(){return state.getState();}

    public void setState(IApplicationState state){this.state = state;}

    public boolean chooseType(String type){return state.chooseType(type);}

    public boolean insertData(){return state.insertData();}

    public boolean checkData(){return state.checkData();}

    public boolean editData(){return state.editData();}

    public boolean deleteData(){return state.deleteData();}

    @Override
    public String toString() {
        return application + ", phase = " + state + "\n";
    }
}
