package pt.isec.pa.apoio_poe.model.context;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.IApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.statesImplementation.StudentPhase;

public class ApplicationContext {
    private Data data;
    private IApplicationState state;

    public ApplicationContext() {
        this.data = new Data();
        state = new StudentPhase(this, data);
    }

    public ApplicationState getState() {
        return state.getState();
    }

    public void setState(IApplicationState state) {
        this.state = state;
    }

    public boolean chooseType(String type) {
        return state.chooseType(type);
    }

    public boolean insertData(String file) {
        return state.insertData(file);
    }

    public String checkData() {
        return state.checkData();
    }

    public boolean editData(String identifier) {
        return state.editData(identifier);
    }

    public boolean deleteData(String identifier) {
        return state.deleteData(identifier);
    }

    public boolean closeState() {
        return state.closeState();
    }

    public boolean professorManager() {
        return state.professorManager();
    }

    public boolean proposalManager() {
        return state.proposalManager();
    }

    public boolean studentManager() {
        return state.studentManager();
    }

    public boolean candidatureManager(){
        return state.candidatureManager();
    }

    @Override
    public String toString() {
        return data + ", phase = " + state + "\n";
    }
}
