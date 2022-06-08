package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class StudentLockedUI extends BorderPane {
    private FSManager manager;
    Button btnProf, btnProp, btnCandid;
    private Label lbCurrentState;

    public StudentLockedUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #bf01bd;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.STUDENT_LOCKED);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.lbCurrentState.setPadding(new Insets(2));
        this.setTop(lbCurrentState);
        this.btnProf = new Button("Professor state");
        this.btnProp = new Button("Proposal state");
        this.btnCandid = new Button("Candidature state");

        ToolBar toolBar = new ToolBar(btnProp, btnProf, btnCandid);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.STUDENT_LOCKED);
            update();
        });

        btnProf.setOnAction(actionEvent -> {
            manager.professorManager();
        });

        btnCandid.setOnAction(actionEvent -> {
            manager.candidatureManager();
        });

        btnProp.setOnAction(actionEvent -> {
            manager.proposalManager();
        });
    }


    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
