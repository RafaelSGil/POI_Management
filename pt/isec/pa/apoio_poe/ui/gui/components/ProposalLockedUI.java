package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class ProposalLockedUI extends BorderPane {
    private FSManager manager;
    Button btnProf, btnStud, btnCandid;
    private Label lbCurrentState;

    public ProposalLockedUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #bf01bd;");

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnProf = new Button("Professor state");
        this.btnStud = new Button("Student state");
        this.btnCandid = new Button("Candidature state");

        HBox hBox = new HBox(btnStud, btnProf, btnCandid);
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

        btnStud.setOnAction(actionEvent -> {
            manager.studentManager();
        });
    }


    private void update() {
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL_LOCKED);
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }

}
