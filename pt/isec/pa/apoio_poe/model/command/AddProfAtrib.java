package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

/**
 * <p>Class that represents the command to add a professor attribution</p>
 *
 * @author RafelGil and HugoFerreira
 */
public class AddProfAtrib extends CommandAdapter{
    //ADD PROFESSOR ATTRIBUTION
    /**
     *
     */
    private String email, idOfProposal;


    /**
     *
     * @param context reference to context
     * @param email email
     * @param idOfProposal ido of proposal
     */
    public AddProfAtrib(ApplicationContext context, String email, String idOfProposal) {
        super(context);
        this.email = email;
        this.idOfProposal = idOfProposal;
    }

    @Override
    public boolean execute() {
        return context.manualProfessorAttribution(idOfProposal, email);
    }

    @Override
    public boolean undo() {
        return context.manualProfessorRemoval(email, idOfProposal);
    }
}
