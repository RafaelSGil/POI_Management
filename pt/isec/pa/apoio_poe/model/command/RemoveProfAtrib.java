package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

public class RemoveProfAtrib extends CommandAdapter{
    //REMOVE PROFESSOR ATTRIBUTION

    private String email, idOfProposal;

    public RemoveProfAtrib(ApplicationContext context, String email, String idOfProposal){
        super(context);
        this.email = email;
        this.idOfProposal = idOfProposal;
    }

    @Override
    public boolean undo() {
        return context.manualProfessorAttribution(idOfProposal, email);
    }

    @Override
    public boolean execute() {
        return context.manualProfessorRemoval(email);
    }
}
