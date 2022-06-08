package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class ProposalsAttributionLockedUI extends BorderPane {
    private FSManager manager;
    private Button btnPrev, btnProfAttrib;
    private Label lbCurrentState;

    public ProposalsAttributionLockedUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #10d4cd;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnPrev = new Button("Previous Phase");
        this.btnProfAttrib = new Button("Prof. Attribution state");

        ToolBar toolBar = new ToolBar(btnPrev, btnProfAttrib);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
            update();
        });

        btnProfAttrib.setOnAction(actionEvent -> {
            manager.professorAttributionManager();
        });

        btnPrev.setOnAction(actionEvent -> {
            manager.candidatureManager();
        });
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
