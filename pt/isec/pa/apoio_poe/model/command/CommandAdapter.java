package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;


public class CommandAdapter implements ICommand {
    protected ApplicationContext context;

    public CommandAdapter(ApplicationContext context){
        this.context = context;
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public boolean undo() {
        return false;
    }
}
