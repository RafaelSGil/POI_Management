package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.model.fsm.IApplicationState;

public class ProfessorUI extends BorderPane {
    private FSManager manager;
    Button btnStud, btnProp, btnClose, btnCandid;
    private Label lbCurrentState;

    public ProfessorUI(FSManager manager){
        this.manager = manager;createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #A080FF;");

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnStud = new Button("Student state");
        this.btnProp = new Button("Proposal state");
        this.btnCandid = new Button("Candidature state");
        this.btnClose = new Button("Close state");

        HBox hBox = new HBox(btnProp, btnStud, btnCandid, btnClose);
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

        btnClose.setOnAction(actionEvent -> {
            manager.closeState();
        });
    }

    private void update() {
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR);
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
