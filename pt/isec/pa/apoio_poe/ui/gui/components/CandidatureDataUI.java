package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;

public class CandidatureDataUI extends BorderPane {
    private FSManager manager;
    private Label lbCurrentState;
    private ListView<String> lvData;


    public CandidatureDataUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        this.setStyle("-fx-background-color: white");
        this.setVisible(manager != null && (manager.getState() == ApplicationState.CANDIDATURE || manager.getState() == ApplicationState.CANDIDATURE_LOCKED));


        this.lvData = new ListView<>();
        this.setCenter(lvData);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);

    }

    private void registerHandlers(){
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && (manager.getState() == ApplicationState.CANDIDATURE || manager.getState() == ApplicationState.CANDIDATURE_LOCKED));
            this.lbCurrentState.setText("Current State: " + manager.getState());
        });

        manager.addPropertyChangeListener(FSManager.PROP_DATA, evt -> {
            if(manager.getState() == ApplicationState.CANDIDATURE || manager.getState() == ApplicationState.CANDIDATURE_LOCKED){
                this.lvData.setItems(FXCollections.observableList(manager.checkData()));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SWC, evt -> {
            if(manager.getState() == ApplicationState.CANDIDATURE || manager.getState() == ApplicationState.CANDIDATURE_LOCKED){
                this.lvData.setItems(FXCollections.observableList(manager.listStudentsWithCandidatures()));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SWNC, evt -> {
            if(manager.getState() == ApplicationState.CANDIDATURE || manager.getState() == ApplicationState.CANDIDATURE_LOCKED){
                this.lvData.setItems(FXCollections.observableList(manager.listStudentsWithoutCandidatures()));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SWSP, evt -> {
            if(manager.getState() == ApplicationState.CANDIDATURE || manager.getState() == ApplicationState.CANDIDATURE_LOCKED){
                this.lvData.setItems(FXCollections.observableList(manager.listStudentsWithAutoProposals()));
            }
        });
    }

    private void update(){
    }
}
