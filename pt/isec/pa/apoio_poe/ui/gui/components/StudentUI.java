package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;



public class StudentUI extends BorderPane {
    private FSManager manager;
    Button btnProf, btnProp, btnClose, btnCandid;
    private Label lbCurrentState;

    public StudentUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #A08000;");

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnProf = new Button("Professor state");
        this.btnProp = new Button("Proposal state");
        this.btnCandid = new Button("Candidature state");
        this.btnClose = new Button("Close state");

        HBox hBox = new HBox(btnProp, btnProf, btnCandid, btnClose);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        this.setBottom(hBox);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
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

        btnClose.setOnAction(actionEvent -> {
            manager.closeState();
        });
    }

    private void update() {
        this.setVisible(manager != null && manager.getState() == ApplicationState.STUDENT);
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
