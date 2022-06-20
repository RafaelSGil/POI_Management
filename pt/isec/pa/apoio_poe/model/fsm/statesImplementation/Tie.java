package pt.isec.pa.apoio_poe.model.fsm.statesImplementation;

import pt.isec.pa.apoio_poe.model.data.Data;
import pt.isec.pa.apoio_poe.model.data.person.Person;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationContext;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.StateAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * <p>Class that represents the Tie state</p>
 *
 * @author RafelGil and HugoFerreira
 */
public class Tie extends StateAdapter {
    public Tie(ApplicationContext context, Data data) {
        super(context, data);
    }

    @Override
    public ApplicationState getState() {
        return ApplicationState.TIE;
    }

    @Override
    public ArrayList<Person> getTies() {
        ArrayList<Person> arr = data.getTies();
        setState(ApplicationState.TIE);
        return arr;
    }

    @Override
    public boolean chooseStudentToAssociate(String index) {
        boolean bool =  data.chooseStudentToAssociate(index);
        setState(ApplicationState.PROPOSAL_ATTRIBUTION);
        return bool;
    }
}
