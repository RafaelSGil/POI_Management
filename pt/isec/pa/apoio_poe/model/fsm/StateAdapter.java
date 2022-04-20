package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;

public class StateAdapter implements IApplicationState {
    protected ApplicationContext context;
    protected Data data;

    protected StateAdapter(ApplicationContext context, Data data) {
        this.data = data;
        this.context = context;
    }

    protected void setState(ApplicationState state) {
        context.setState(state.createState(context, data));
    }

    @Override
    public ApplicationState getState() {
        return null;
    }

    @Override
    public boolean chooseType(String type) {
        return false;
    }

    @Override
    public boolean closeState() {
        return false;
    }

    @Override
    public boolean studentManager() {
        return false;
    }

    @Override
    public boolean professorManager() {
        return false;
    }

    @Override
    public boolean proposalManager() {
        return false;
    }

    @Override
    public boolean candidatureManager() {
        return false;
    }

    @Override
    public boolean proposalAttributionManager() {
        return false;
    }

    @Override
    public boolean professorAttributionManager() {
        return false;
    }

}
