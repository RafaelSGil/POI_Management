package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class SearchUI extends BorderPane {
    private FSManager manager;
    private Label lbCurrentState;

    public SearchUI(FSManager manager){
        this.manager = manager;createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #b53717;");

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
    }

    private void registerHandlers() { }

    private void update() {
        this.setVisible(manager != null && manager.getState() == ApplicationState.SEARCH);
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
