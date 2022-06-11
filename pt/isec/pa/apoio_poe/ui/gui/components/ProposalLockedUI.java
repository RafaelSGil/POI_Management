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

public class ProposalLockedUI extends BorderPane {
    private FSManager manager;
    Button btnProf, btnStud, btnCandid;
    private Label lbCurrentState;
    private final KeyCombination ctrlN = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    private final KeyCombination ctrlB = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);


    public ProposalLockedUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #1c500a;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL_LOCKED);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnProf = new Button("Professor state");
        this.btnStud = new Button("Student state");
        this.btnCandid = new Button("Candidature state");

        Label label = new Label("PHASE 1 LOCKED");
        label.setStyle("-fx-text-fill: white;-fx-font-size: 25");
        this.setCenter(label);

        ToolBar toolBar = new ToolBar(btnStud, btnProf, btnCandid);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL_LOCKED);
            update();
        });

        this.setOnKeyPressed(keyEvent -> {
            if(ctrlN.match(keyEvent)){
                manager.candidatureManager();
            }
            if(ctrlB.match(keyEvent)){
                manager.professorManager();
            }
        });

        btnProf.setOnAction(actionEvent -> {
            manager.professorManager();
        });

        btnCandid.setOnAction(actionEvent -> {
            manager.candidatureManager();
        });

        btnStud.setOnAction(actionEvent -> {
            manager.studentManager();
        });
    }


    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }

}
