package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;

public class ProposalDataUI extends BorderPane {
    private FSManager manager;
    private Label lbData, lbCurrentState;


    public ProposalDataUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        this.setStyle("-fx-background-color: red");
        this.setVisible(manager != null && (manager.getState() == ApplicationState.PROPOSAL || manager.getState() == ApplicationState.PROPOSAL_LOCKED));


        this.lbData = new Label();
        this.lbData.setPadding(new Insets(20));
        this.setCenter(lbData);

        this.lbCurrentState = new Label();
        this.setTop(lbCurrentState);

    }

    private void registerHandlers(){
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && (manager.getState() == ApplicationState.PROPOSAL || manager.getState() == ApplicationState.PROPOSAL_LOCKED));
            this.lbCurrentState.setText("Current State: " + manager.getState());
        });

        manager.addPropertyChangeListener(FSManager.PROP_DATA, evt -> {
            if((manager.getState() == ApplicationState.PROPOSAL || manager.getState() == ApplicationState.PROPOSAL_LOCKED)){
                this.lbData.setText(manager.checkData());
            }
        });
    }

    private void update(){
    }
}
