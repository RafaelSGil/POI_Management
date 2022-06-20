package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;
import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;

import java.util.ArrayList;

/**
 * <p>Class that represents the Candidature locked state</p>
 *
 * @author RafelGil and HugoFerreira
 */
public class CandidaturePhaseLocked extends StateAdapter {
    /**
     *
     * @param context reference to FSM context
     * @param data reference to Data
     */
    public CandidaturePhaseLocked(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.CANDIDATURE_LOCKED;
    }

    @Override
    public boolean studentTransitioning() {
        if(data.isLocked(ApplicationPhases.PHASE1)){
            setState(ApplicationState.STUDENT_LOCKED);
            return true;
        }

        setState(ApplicationState.STUDENT);
        return true;
    }

    @Override
    public ArrayList<String> checkData() {
        ArrayList<String> str = data.getCandidatures();
        setState(ApplicationState.CANDIDATURE_LOCKED);
        return str;
    }

    @Override
    public boolean proposalAttributionTransitioning() {
        if(data.isLocked(ApplicationPhases.PHASE3)){
            setState(ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
            return true;
        }

        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return true;
    }
}
