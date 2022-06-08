package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class ProfessorUI extends BorderPane {
    private FSManager manager;
    Button btnStud, btnProp, btnClose, btnCandid;
    private Label lbCurrentState;
    private TextField tfPathProfessorData, tfRemoveProfessor;
    private Button btnLoadProfessorData, btnRemoveProfessor;
    private TextField tfEditEmailProfessor, tfEditValueProfessor;
    private Button btnEditProfessorData;

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

        ToolBar toolBar = new ToolBar(btnStud, btnProp, btnCandid, btnClose);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);

        this.tfPathProfessorData = new TextField();
        this.tfPathProfessorData.setPromptText("Enter path to professor data file");
        this.tfPathProfessorData.setMinWidth(250);
        this.btnLoadProfessorData = new Button("Load");
        Label lbPlaceHolder = new Label("Insert Professor Data ");
        lbPlaceHolder.setPadding(new Insets(4));
        HBox hBox1 = new HBox(lbPlaceHolder, tfPathProfessorData, btnLoadProfessorData);
        hBox1.setStyle("-fx-padding: 20 10 10 10");

        this.tfRemoveProfessor = new TextField();
        this.tfRemoveProfessor.setPromptText("Enter email of professor to remove");
        this.tfRemoveProfessor.setMinWidth(250);
        this.btnRemoveProfessor = new Button("Remove");
        Label lbPlaceholder1 = new Label("Remove Professor    ");
        lbPlaceholder1.setPadding(new Insets(4));
        HBox hBox2 = new HBox(lbPlaceholder1, tfRemoveProfessor, btnRemoveProfessor);
        hBox2.setStyle("-fx-padding: 20 10 10 10");

        this.tfEditEmailProfessor = new TextField();
        this.tfEditEmailProfessor.setPromptText("Email of professor");
        this.tfEditEmailProfessor.setMinWidth(350);
        this.tfEditValueProfessor = new TextField();
        this.tfEditValueProfessor.setPromptText("true/false");
        this.btnEditProfessorData = new Button("Edit");
        VBox vBox = new VBox(tfEditEmailProfessor, tfEditValueProfessor, btnEditProfessorData);
        vBox.setSpacing(20);
        vBox.setStyle("-fx-padding: 0 0 0 10");
        Label lbPlaceHolder2 = new Label("Edit Professor Data");
        HBox hBox3 = new HBox(lbPlaceHolder2, vBox);
        hBox3.setStyle("-fx-padding: 20 10 20 10");

        VBox vBox1 = new VBox(hBox1, hBox2, hBox3);
        this.setCenter(vBox1);

        this.tfEditEmailProfessor = new TextField();

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.lbCurrentState.setPadding(new Insets(2));
        this.setTop(lbCurrentState);
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

        btnLoadProfessorData.setOnAction(actionEvent -> {
            if(!manager.insertData(tfPathProfessorData.getText())){
                tfPathProfessorData.setStyle("-fx-background-color: #fa3434");
                tfPathProfessorData.setText("");
                return;
            }

            tfPathProfessorData.setStyle("-fx-background-color: white");
            tfPathProfessorData.setText("");
        });

        tfPathProfessorData.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                if(!tfPathProfessorData.getText().equals("")){
                    if(!manager.insertData(tfPathProfessorData.getText())){
                        tfPathProfessorData.setText("");
                        tfPathProfessorData.setStyle("-fx-background-color: #fa3434");
                        return;
                    }
                    tfPathProfessorData.setText("");
                    tfPathProfessorData.setStyle("-fx-background-color: white");
                }else{
                    tfPathProfessorData.setStyle("-fx-background-color: #fa3434");
                }
            }
        });

        btnRemoveProfessor.setOnAction(actionEvent -> {
            if(!manager.deleteData(tfRemoveProfessor.getText())){
                tfRemoveProfessor.setStyle("-fx-background-color: #fa3434");
                tfRemoveProfessor.setText("");
                return;
            }

            tfRemoveProfessor.setStyle("-fx-background-color: white");
            tfRemoveProfessor.setText("");
        });

        tfRemoveProfessor.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                if(!tfRemoveProfessor.getText().equals("")){
                    if(!manager.deleteData(tfRemoveProfessor.getText())){
                        tfRemoveProfessor.setText("");
                        tfRemoveProfessor.setStyle("-fx-background-color: #fa3434");
                        return;
                    }
                    tfRemoveProfessor.setText("");
                    tfRemoveProfessor.setStyle("-fx-background-color: white");
                }else{
                    tfRemoveProfessor.setStyle("-fx-background-color: #fa3434");
                }
            }
        });

        btnEditProfessorData.setOnAction(actionEvent -> {
            if(tfEditEmailProfessor.getText().equals("")){
                tfEditEmailProfessor.setStyle("-fx-background-color: #fa3434");
                return;
            }else{
                tfEditEmailProfessor.setStyle("-fx-background-color: white");
            }
            if(tfEditValueProfessor.getText().equals("") || !tfEditValueProfessor.getText().equals("true") || !tfEditValueProfessor.getText().equals("false")){
                tfEditValueProfessor.setStyle("-fx-background-color: #fa3434");
                return;
            }else{
                tfEditValueProfessor.setStyle("-fx-background-color: #fa3434");
            }
            if(!manager.editDataProfessor(tfEditEmailProfessor.getText(), Boolean.parseBoolean(tfEditValueProfessor.getText()))){
                tfEditEmailProfessor.setStyle("-fx-background-color: #fa3434");
                tfEditValueProfessor.setStyle("-fx-background-color: #fa3434");
            }
        });
    }

    private void update() {
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR);
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
