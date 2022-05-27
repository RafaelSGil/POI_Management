package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

public class RemovePropAtrib extends CommandAdapter{
    //REMOVE PROPOSALS ATTRIBUTION

    private String idOfProposal;
    private long idOfStudent;

    public RemovePropAtrib(ApplicationContext context, String idOfProposal, long idOfStudent){
        super(context);
        this.idOfProposal = idOfProposal;
        this.idOfStudent = idOfStudent;
    }

    @Override
    public boolean execute() {
        return context.manualRemoval(idOfProposal);
    }

    @Override
    public boolean undo() {
        return context.manualAttribution(idOfProposal, idOfStudent);
    }
}
