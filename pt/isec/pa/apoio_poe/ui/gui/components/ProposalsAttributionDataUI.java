package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;

public class ProposalsAttributionDataUI extends BorderPane {
    private FSManager manager;
    private Label lbCurrentState;
    private ListView<String> lvData;


    public ProposalsAttributionDataUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        this.setStyle("-fx-background-color: white");
        this.setVisible(manager != null && (manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION || manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED));


        this.lvData = new ListView<>();
        this.setCenter(lvData);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);

    }

    private void registerHandlers(){
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && (manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION || manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED));
            this.lbCurrentState.setText("Current State: " + manager.getState());
        });

        manager.addPropertyChangeListener(FSManager.PROP_DATA, evt -> {
            if(manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION || manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED){
                this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.getProposalAttributions()));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SWC, evt -> {
            if(manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION || manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED){
                this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listStudentsWithCandidatures()));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SWP, evt -> {
            if(manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION || manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED){
                this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listStudentWithProposalAttributed()));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SWSP, evt -> {
            if(manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION || manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED){
                this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listStudentsWithAutoProposals()));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SWNP, evt -> {
            if(manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION || manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED){
                this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listStudentWithoutProposalAttributed()));
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_PF, evt -> {
            if(manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION || manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED){
                this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listProposalFiltersGUI()));
            }
        });
    }

    private void update(){
    }
}
