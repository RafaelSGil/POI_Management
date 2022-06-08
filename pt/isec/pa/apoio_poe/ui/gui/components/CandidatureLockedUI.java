package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class CandidatureLockedUI extends BorderPane {
    private FSManager manager;
    private Button btnPrev, btnPropAttrib;
    private javafx.scene.control.Label lbCurrentState;

    public CandidatureLockedUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #3d05c9;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.CANDIDATURE_LOCKED);

        this.lbCurrentState = new javafx.scene.control.Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnPrev = new javafx.scene.control.Button("Previous Phase");
        this.btnPropAttrib = new Button("Prop. Attribution state");

        ToolBar toolBar = new ToolBar(btnPrev, btnPropAttrib);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);

    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.CANDIDATURE_LOCKED);
            update();
        });

        btnPropAttrib.setOnAction(actionEvent -> {
            manager.proposalAttributionManager();
        });

        btnPrev.setOnAction(actionEvent -> {
            manager.studentManager();
        });
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
