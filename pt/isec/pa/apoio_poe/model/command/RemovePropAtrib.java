package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

public class RemovePropAtrib extends CommandAdapter{
    //REMOVE PROPOSALS ATTRIBUTION

    private String idOfProposal;

    public RemovePropAtrib(ApplicationContext context, String idOfProposal){
        super(context);
        this.idOfProposal = idOfProposal;
    }
}
