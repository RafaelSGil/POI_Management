package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;

public class TieUI extends BorderPane {
    private FSManager manager;
    private Label lbCurrentState;

    public TieUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #433645;");

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
    }

    private void registerHandlers() { }

    private void update() {
        this.setVisible(manager != null && manager.getState() == ApplicationState.TIE);
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
