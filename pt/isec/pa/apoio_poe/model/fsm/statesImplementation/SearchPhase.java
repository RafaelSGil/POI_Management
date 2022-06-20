package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

import java.util.ArrayList;

/**
 * <p>Class that represents the Search state</p>
 *
 * @author RafelGil and HugoFerreira
 */
public class SearchPhase extends StateAdapter {
    public SearchPhase(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.SEARCH;
    }

    @Override
    public ArrayList<String> listStudentWithProposalAttributed() {
        ArrayList<String> str = data.listStudentWithProposalAttributed();
        setState(ApplicationState.SEARCH);
        return str;
    }

    @Override
    public String minimumProfessorAttributions() {
        String str = data.getMinProfessorsAttributions();
        setState(ApplicationState.SEARCH);
        return str;
    }

    @Override
    public String maximumProfessorAttributions() {
        String str = data.getMaxProfessorsAttributions();
        setState(ApplicationState.SEARCH);
        return str;
    }

    @Override
    public String averageProfessorAttributions() {
        String str = data.getAverageNumberOfAttributionsForProfessors();
        setState(ApplicationState.SEARCH);
        return str;
    }

    @Override
    public String specificProfessorAttributions(String email) {
        String str = data.getSpecificProfessorAttributions(email);
        setState(ApplicationState.SEARCH);
        return str;
    }
}
