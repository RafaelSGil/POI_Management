package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class CandidatureUI extends BorderPane {
    private FSManager manager;
    Button btnPrev, btnClose, btnPropAttrib;
    private Label lbCurrentState;

    public CandidatureUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #25f398;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.CANDIDATURE);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnPrev = new Button("Previous Phase");
        this.btnClose = new Button("Close state");
        this.btnPropAttrib = new Button("Prop. Attribution state");

        HBox hBox = new HBox(btnPrev, btnPropAttrib, btnClose);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        this.setBottom(hBox);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.CANDIDATURE);
            update();
        });

        btnPropAttrib.setOnAction(actionEvent -> {
            manager.proposalAttributionManager();
        });

        btnPrev.setOnAction(actionEvent -> {
            manager.studentManager();
        });

        btnClose.setOnAction(actionEvent -> {
            manager.closeState();
        });
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
