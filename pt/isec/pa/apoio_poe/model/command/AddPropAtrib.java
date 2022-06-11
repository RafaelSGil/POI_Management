package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

public class AddPropAtrib extends CommandAdapter{
    //ADD PROPOSALS ATTRIBUTION

    private String idOfProposal;
    private String idOfStudent;

    public AddPropAtrib(ApplicationContext context, String idOfProposal, String idOfStudent){
        super(context);
        this.idOfProposal = idOfProposal;
        this.idOfStudent = idOfStudent;
    }

    @Override
    public boolean execute() {
        return context.manualAttribution(idOfProposal, idOfStudent);
    }

    @Override
    public boolean undo() {
        return context.manualRemoval(idOfProposal);
    }
}
