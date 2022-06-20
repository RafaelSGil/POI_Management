package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

/**
 * <p>Class that represents the command to add a proposal attribution</p>
 *
 * @author RafelGil and HugoFerreira
 */
public class AddPropAtrib extends CommandAdapter{
    //ADD PROPOSALS ATTRIBUTION

    private String idOfProposal;
    private String idOfStudent;

    /**
     *
     * @param context reference to FSM context
     * @param idOfProposal id of proposal
     * @param idOfStudent id of student
     */
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
