package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;


public class StudentUI extends BorderPane {
    private FSManager manager;
    Button btnProf, btnProp, btnClose, btnCandid;
    private Label lbCurrentState;
    private TextField tfPathStudentData;
    private TextField tfRemoveStudent;
    private Button btnLoadStudentData;
    private Button btnRemoveStudent;
    private TextField tfEditIDStudent, tfEditFieldStudent, tfEditValueStudent;
    private Button btnEditStudentData;
    private final KeyCombination ctrlN = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    private final KeyCombination ctrlB = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);

    public StudentUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #A08000;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.STUDENT);


        this.btnProf = new Button("Professor state");
        this.btnProp = new Button("Proposal state");
        this.btnCandid = new Button("Candidature state");
        this.btnClose = new Button("Close state");

        ToolBar toolBar = new ToolBar(btnProf, btnProp, btnCandid, btnClose);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);

        this.tfPathStudentData = new TextField();
        this.tfPathStudentData.setPromptText("Enter path to student data file");
        this.tfPathStudentData.setMinWidth(250);
        this.btnLoadStudentData = new Button("Load");
        Label lbPlaceHolder = new Label("Insert Student Data ");
        lbPlaceHolder.setPadding(new Insets(4));
        HBox hBox1 = new HBox(lbPlaceHolder, tfPathStudentData, btnLoadStudentData);
        hBox1.setStyle("-fx-padding: 20 10 10 10");


        this.tfRemoveStudent = new TextField();
        this.tfRemoveStudent.setPromptText("Enter id of student to remove");
        this.tfRemoveStudent.setMinWidth(250);
        this.btnRemoveStudent = new Button("Remove");
        Label lbPlaceholder1 = new Label("Remove Student     ");
        lbPlaceholder1.setPadding(new Insets(4));
        HBox hBox2 = new HBox(lbPlaceholder1, tfRemoveStudent, btnRemoveStudent);
        hBox2.setStyle("-fx-padding: 20 10 10 10");

        this.tfEditIDStudent = new TextField();
        this.tfEditIDStudent.setPromptText("Set id of student");
        this.tfEditIDStudent.setMinWidth(350);
        this.tfEditFieldStudent = new TextField();
        this.tfEditFieldStudent.setPromptText("Name/Course/Branch/Classification/Internships");
        this.tfEditFieldStudent.setMinWidth(350);
        this.tfEditValueStudent = new TextField();
        this.tfEditValueStudent.setPromptText("New value");
        this.tfEditValueStudent.setMinWidth(350);
        this.btnEditStudentData = new Button("Edit");
        VBox vBox = new VBox(tfEditIDStudent, tfEditFieldStudent, tfEditValueStudent, btnEditStudentData);
        vBox.setSpacing(20);
        vBox.setStyle("-fx-padding: 0 0 0 10");
        Label lbPlaceHolder2 = new Label("Edit Student Data");
        lbPlaceHolder2.setPadding(new Insets(6));
        HBox hBox3 = new HBox(lbPlaceHolder2, vBox);
        hBox3.setStyle("-fx-padding: 20 10 10 10");

        VBox vBox1 = new VBox(hBox1, hBox2, hBox3);
        this.setCenter(vBox1);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.lbCurrentState.setPadding(new Insets(2));
        this.setTop(lbCurrentState);

    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
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

        btnClose.setOnAction(actionEvent -> {
            manager.closeState();
        });

        btnLoadStudentData.setOnAction(actionEvent -> {
            if(!manager.insertData(tfPathStudentData.getText())){
                tfPathStudentData.setStyle("-fx-background-color: #fa3434");
            }
            tfPathStudentData.setText("");
        });

        btnRemoveStudent.setOnAction(actionEvent -> {
            if(!manager.deleteData(tfRemoveStudent.getText())){
                tfRemoveStudent.setText("");
                tfRemoveStudent.setStyle("-fx-background-color: #fa3434");
            }
        });

        tfRemoveStudent.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                if(!tfRemoveStudent.getText().equals("")){
                    if(!manager.deleteData(tfRemoveStudent.getText())){
                        tfRemoveStudent.setText("");
                        tfRemoveStudent.setStyle("-fx-background-color: #fa3434");
                        return;
                    }
                    tfRemoveStudent.setText("");
                    tfRemoveStudent.setStyle("-fx-background-color: white");
                }else{
                    tfRemoveStudent.setStyle("-fx-background-color: #fa3434");
                }
            }
        });

        tfPathStudentData.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                if(!tfPathStudentData.getText().equals("")){
                    if(!manager.insertData(tfPathStudentData.getText())){
                        tfPathStudentData.setText("");
                        tfPathStudentData.setStyle("-fx-background-color: #fa3434");
                        return;
                    }

                    tfPathStudentData.setText("");
                    tfPathStudentData.setStyle("-fx-background-color: white");
                }else{
                    tfPathStudentData.setStyle("-fx-background-color: #fa3434");
                }
            }
        });

        btnEditStudentData.setOnAction(actionEvent -> {
            if(tfEditIDStudent.getText().equals("")){
                tfEditIDStudent.setStyle("-fx-background-color: #fa3434");
                return;
            }else{
                tfEditIDStudent.setStyle("-fx-background-color: white");
            }
            if(tfEditFieldStudent.getText().equals("")){
                tfEditFieldStudent.setStyle("-fx-background-color: #fa3434");
                return;
            }else{
                tfEditFieldStudent.setStyle("-fx-background-color: white");
            }
            if(tfEditValueStudent.getText().equals("")){
                tfEditValueStudent.setStyle("-fx-background-color: #fa3434");
                return;
            }else{
                tfEditValueStudent.setStyle("-fx-background-color: white");
            }
            if(!manager.editDataStudent(tfEditIDStudent.getText(), tfEditFieldStudent.getText(), tfEditValueStudent.getText())){
                tfEditIDStudent.setStyle("-fx-background-color: #fa3434");
                tfEditFieldStudent.setStyle("-fx-background-color: #fa3434");
                tfEditValueStudent.setStyle("-fx-background-color: #fa3434");
            }
        });
    }

    private void update() {
        this.setVisible(manager != null && manager.getState() == ApplicationState.STUDENT);
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
