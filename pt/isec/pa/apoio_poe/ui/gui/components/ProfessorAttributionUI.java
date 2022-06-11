package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class ProfessorAttributionUI extends BorderPane {
    private FSManager manager;
    private Button btnPrev, btnClose, btnAttribution, btnManualAttribution, btnManualRemoval, btnEdit, btnConsultProf;
    private Button btnListAttributions, btnListProfessors, btnListStudents;
    private Label lbCurrentState;
    private TextField tfEditEmail, tfEditValue;

    public ProfessorAttributionUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #7cd60d;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR_ATTRIBUTION);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnPrev = new Button("Previous Phase");
        this.btnClose = new Button("Close state");

        this.tfEditEmail = new TextField();
        this.tfEditEmail.setPromptText("Email of professor");
        this.tfEditEmail.setMinWidth(350);
        this.tfEditValue = new TextField();
        this.tfEditValue.setPromptText("true/false");
        this.btnEdit = new Button("Edit");
        VBox vBox = new VBox(tfEditEmail, tfEditValue, btnEdit);
        vBox.setSpacing(20);
        vBox.setStyle("-fx-padding: 0 0 0 10");
        Label lbPlaceHolder2 = new Label("Edit Professor Data");
        HBox hBox3 = new HBox(lbPlaceHolder2, vBox);
        hBox3.setStyle("-fx-padding: 20 10 20 10");

        ToolBar toolBar = new ToolBar(btnPrev, btnClose);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR_ATTRIBUTION);
            update();
        });

        btnPrev.setOnAction(actionEvent -> manager.proposalAttributionManager());

        btnClose.setOnAction(actionEvent -> manager.closeState());
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
