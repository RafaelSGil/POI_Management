package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class ProposalsAttributionUI extends BorderPane {
    private FSManager manager;
    Button btnPrev, btnClose, btnProfAttrib;
    private Label lbCurrentState;

    public ProposalsAttributionUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #25f398;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnPrev = new Button("Previous Phase");
        this.btnClose = new Button("Close state");
        this.btnProfAttrib = new Button("Prof. Attribution state");

        ToolBar toolBar = new ToolBar(btnPrev, btnProfAttrib, btnClose);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION);
            update();
        });

        btnProfAttrib.setOnAction(actionEvent -> {
            manager.professorAttributionManager();
        });

        btnPrev.setOnAction(actionEvent -> {
            manager.candidatureManager();
        });

        btnClose.setOnAction(actionEvent -> {
            manager.closeState();
        });
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
