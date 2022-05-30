package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class ProposalUI extends BorderPane {
    private FSManager manager;
    Button btnStud, btnProf, btnClose, btnCandid;
    private Label lbCurrentState;

    public ProposalUI(FSManager manager){
        this.manager = manager;createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #c15fb0;");

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnStud = new Button("Student state");
        this.btnProf = new Button("Professor state");
        this.btnCandid = new Button("Candidature state");
        this.btnClose = new Button("Close state");

        HBox hBox = new HBox(btnProf, btnStud, btnCandid, btnClose);
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

        btnProf.setOnAction(actionEvent -> {
            manager.professorManager();
        });

        btnClose.setOnAction(actionEvent -> {
            manager.closeState();
        });
    }

    private void update() {
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL);
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
