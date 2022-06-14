package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;

import java.util.List;

public class SearchDataUI extends BorderPane {
    private FSManager manager;
    private Label lbCurrentState;
    private ListView<String> lvData;


    public SearchDataUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        this.setStyle("-fx-background-color: white");
        this.setVisible(manager != null && (manager.getState() == ApplicationState.SEARCH ));


        this.lvData = new ListView<>();
        this.setCenter(lvData);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);

    }

    private void registerHandlers(){
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && (manager.getState() == ApplicationState.SEARCH));
            this.lbCurrentState.setText("Current State: " + manager.getState());
        });

        manager.addPropertyChangeListener(FSManager.PROP_PAA, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(List.of(manager.listAverageAttributions())));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_PMIN, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(List.of(manager.listMinimumAttributions())));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_PMAX, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(List.of(manager.listMaximumAttribution())));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SWP, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listStudentWithProposalAttributed()));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SWNPWC, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listStudentWithoutProposalAttributedAndWithCandidature()));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_AP, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listAvailableProposals()));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_ATTRIBP, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listAttributedProposals()));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SPA, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(List.of(manager.listSpecificProfessorAttribution())));
            }
        });
    }

    private void update(){

    }
}
