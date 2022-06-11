package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class ProfessorLockedUI extends BorderPane {
    private FSManager manager;
    Button btnStud, btnProp, btnCandid;
    private Label lbCurrentState;
    private final KeyCombination ctrlN = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    private final KeyCombination ctrlB = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);


    public ProfessorLockedUI(FSManager manager){
        this.manager = manager;createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #c09b51;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR_LOCKED);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnStud = new Button("Student state");
        this.btnProp = new Button("Proposal state");
        this.btnCandid = new Button("Candidature state");

        Label label = new Label("PHASE 1 LOCKED");
        label.setStyle("-fx-text-fill: white;-fx-font-size: 25");
        this.setCenter(label);

        ToolBar toolBar = new ToolBar(btnProp, btnStud, btnCandid);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR_LOCKED);
            update();
        });

        this.setOnKeyPressed(keyEvent -> {
            if(ctrlN.match(keyEvent)){
                manager.proposalManager();
            }
            if(ctrlB.match(keyEvent)){
                manager.studentManager();
            }
        });

        btnStud.setOnAction(actionEvent -> {
            manager.studentManager();
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
