package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class ProfessorLockedUI extends BorderPane {
    private FSManager manager;
    Button btnStud, btnProp, btnCandid;
    private Label lbCurrentState;

    public ProfessorLockedUI(FSManager manager){
        this.manager = manager;createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #c09b51;");

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnStud = new Button("Student state");
        this.btnProp = new Button("Proposal state");
        this.btnCandid = new Button("Candidature state");

        HBox hBox = new HBox(btnProp, btnStud, btnCandid);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        this.setBottom(hBox);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            update();
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
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR_LOCKED);
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
