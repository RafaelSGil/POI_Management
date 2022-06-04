package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.model.fsm.IApplicationState;

public class ProfessorUI extends BorderPane {
    private FSManager manager;
    Button btnStud, btnProp, btnClose, btnCandid;
    private Label lbCurrentState;
    private TextField tfPathProfessorData, tfRemoveProfessor;
    private Button btnLoadProfessorData, btnRemoveProfessor;

    public ProfessorUI(FSManager manager){
        this.manager = manager;createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #A080FF;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR);

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

        this.tfPathProfessorData = new TextField();
        this.tfPathProfessorData.setPromptText("Enter path to professor data file");
        this.tfPathProfessorData.setMinWidth(250);
        this.btnLoadProfessorData = new Button("Load");
        Label lbPlaceHolder = new Label("Insert Professor Data ");
        lbPlaceHolder.setPadding(new Insets(4));
        HBox hBox1 = new HBox(lbPlaceHolder, tfPathProfessorData, btnLoadProfessorData);


        this.tfRemoveProfessor = new TextField();
        this.tfRemoveProfessor.setPromptText("Enter email of professor to remove");
        this.tfRemoveProfessor.setMinWidth(250);
        this.btnRemoveProfessor = new Button("Remove");
        Label lbPlaceholder1 = new Label("Remove Professor    ");
        lbPlaceholder1.setPadding(new Insets(4));
        HBox hBox2 = new HBox(lbPlaceholder1, tfRemoveProfessor, btnRemoveProfessor);

        VBox vBox = new VBox(hBox1, hBox2);
        vBox.setPadding(new Insets(15));
        this.setCenter(vBox);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR);
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

        btnLoadProfessorData.setOnAction(actionEvent -> {
            manager.insertData(tfPathProfessorData.getText());
            tfPathProfessorData.setText("");
        });

        btnRemoveProfessor.setOnAction(actionEvent -> {
            manager.deleteData(tfRemoveProfessor.getText());
            tfRemoveProfessor.setText("");
        });
    }

    private void update() {
        //this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR);
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
