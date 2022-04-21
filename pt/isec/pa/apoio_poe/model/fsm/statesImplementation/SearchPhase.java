package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

public class SearchPhase extends StateAdapter {
    public SearchPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.SEARCH;
    }

    @Override
    public String listStudentWithProposalAttributed() {
        return data.listStudentWithProposalAttributed();
    }

    @Override
    public String minimumProfessorAttributions() {
        return data.getMinProfessorsAttributions();
    }

    @Override
    public String maximumProfessorAttributions() {
        return data.getMaxProfessorsAttributions();
    }

    @Override
    public String averageProfessorAttributions() {
        return data.getAverageNumberOfAttributionsForProfessors();
    }

    @Override
    public String specificProfessorAttributions(String email) {
        return data.getSpecificProfessorAttributions(email);
    }
}
