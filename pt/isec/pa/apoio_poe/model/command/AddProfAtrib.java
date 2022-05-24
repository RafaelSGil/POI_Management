package pt.isec.pa.apoio_poe.model.command;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

public class AddProfAtrib extends CommandAdapter{
    //ADD PROFESSOR ATTRIBUTION

    private String email, idOfProposal;


    public AddProfAtrib(ApplicationContext context, String email, String idOfProposal) {
        super(context);
        this.email = email;
        this.idOfProposal = idOfProposal;
    }

    @Override
    public boolean execute() {
        return context.manualProfessorAttribution(idOfProposal, email);
    }
}
