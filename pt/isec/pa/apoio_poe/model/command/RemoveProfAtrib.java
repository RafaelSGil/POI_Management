package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

public class RemoveProfAtrib extends CommandAdapter{
    //REMOVE PROFESSOR ATTRIBUTION

    private String email;

    public RemoveProfAtrib(ApplicationContext context, String email){
        super(context);
        this.email = email;
    }

    @Override
    public boolean undo() {
        return context.manualProfessorRemoval(email);
    }
}
