package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
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
    private Menu menu;
    private MenuItem menuItem1, menuItem2;

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

        HBox hBox = new HBox(btnProp, btnProf, btnCandid, btnClose);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(4));
        this.setBottom(hBox);

        this.tfPathStudentData = new TextField();
        this.tfPathStudentData.setPromptText("Enter path to student data file");
        this.tfPathStudentData.setText("");
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

        this.menu = new Menu("File");
        this.menuItem1 = new MenuItem("Save");
        this.menuItem2 = new MenuItem("Load");
        this.menu.getItems().addAll(menuItem1, menuItem2);
        MenuBar menuBar = new MenuBar(menu);

        this.lbCurrentState = new Label("Current State: " + manager.getState());

        VBox vBox2 = new VBox(menuBar, lbCurrentState);
        vBox2.setSpacing(10);
        this.setTop(vBox2);

    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.STUDENT);
        });

        manager.addPropertyChangeListener(FSManager.PROP_DATA, evt -> {
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

        tfPathStudentData.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                if(!tfPathStudentData.getText().equals("")){
                    manager.insertData(tfPathStudentData.getText());
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

        menuItem1.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        menuItem1.setOnAction(actionEvent -> {
            if(!manager.serializationOfProgram()){
                Stage error = new Stage();
                error.initModality(Modality.NONE);
                VBox vBox = new VBox(20);
                vBox.getChildren().addAll(new Text("Save did not work!"));
                Scene scene = new Scene(vBox, 150, 100);
                error.setScene(scene);
                error.show();
            }
        });

        menuItem2.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        menuItem2.setOnAction(actionEvent -> {
            try {
                if(!manager.loadSave()){
                    Stage error = new Stage();
                    error.initModality(Modality.NONE);
                    VBox vBox = new VBox(20);
                    vBox.getChildren().addAll(new Text("Load did not work!"));
                    Scene scene = new Scene(vBox, 150, 100);
                    error.setScene(scene);
                    error.show();
                }
            } catch (ClassNotFoundException e) {
                Stage error = new Stage();
                error.initModality(Modality.NONE);
                VBox vBox = new VBox(20);
                vBox.getChildren().addAll(new Text("Load did not work!"));
                Scene scene = new Scene(vBox, 150, 100);
                error.setScene(scene);
                error.show();
            }
        });
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
