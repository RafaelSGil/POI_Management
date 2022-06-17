package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.ui.gui.resources.ImageManager;

public class StudentLockedUI extends BorderPane {
    private FSManager manager;
    Button btnProf, btnProp, btnCandid;
    private Label lbCurrentState;
    private final KeyCombination ctrlN = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    private final KeyCombination ctrlB = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);


    public StudentLockedUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        BackgroundImage BI = new BackgroundImage(ImageManager.getImage("deis.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(BI));
        this.setVisible(manager != null && manager.getState() == ApplicationState.STUDENT_LOCKED);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        lbCurrentState.setStyle("-fx-text-fill: white; -fx-font-size: 15");
        this.lbCurrentState.setPadding(new Insets(2));
        this.setTop(lbCurrentState);
        Label label = new Label("PHASE 1 LOCKED");
        label.setStyle("-fx-text-fill: white;-fx-font-size: 25");
        this.setCenter(label);
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

        this.setOnKeyPressed(keyEvent -> {
            if(ctrlN.match(keyEvent)){
                manager.professorManager();
            }
            if(ctrlB.match(keyEvent)){
                manager.proposalManager();
            }
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
