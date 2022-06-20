package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;


public class CommandAdapter implements ICommand {
    /**
     * reference to FSM context
     */
    protected ApplicationContext context;

    /**
     *
     * @param context reference to FSM context
     */
    public CommandAdapter(ApplicationContext context){
        this.context = context;
    }

    /**
     *
     * @return bool
     */
    @Override
    public boolean execute() {
        return false;
    }

    /**
     *
     * @return bool
     */
    @Override
    public boolean undo() {
        return false;
    }
}
