package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;

public class ProfessorDataUI extends BorderPane {
    private FSManager manager;
    private Label lbData, lbCurrentState;


    public ProfessorDataUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        this.setStyle("-fx-background-color: white");
        this.setVisible(manager != null && (manager.getState() == ApplicationState.PROFESSOR || manager.getState() == ApplicationState.PROFESSOR_LOCKED));


        this.lbData = new Label();
        this.lbData.setPadding(new Insets(20));
        this.setCenter(lbData);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);

    }

    private void registerHandlers(){
        manager.addPropertyChangeListener(FSManager.PROP_DATA, evt -> {
            update();
        });

        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && (manager.getState() == ApplicationState.PROFESSOR || manager.getState() == ApplicationState.PROFESSOR_LOCKED));
        });
    }

    private void update(){
        this.lbData.setText(manager.checkData());
        this.lbCurrentState = new Label("Current State: " + manager.getState());
    }
}
