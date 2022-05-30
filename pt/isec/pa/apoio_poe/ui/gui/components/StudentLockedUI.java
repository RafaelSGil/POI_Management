package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class StudentLockedUI extends BorderPane {
    private FSManager manager;

    public StudentLockedUI(FSManager manager){
        this.manager = manager;createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

    }

    private void registerHandlers() { }

    private void update() {
        this.setVisible(manager != null && manager.getState() == ApplicationState.STUDENT_LOCKED);
    }
}
