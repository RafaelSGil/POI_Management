package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
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

    public StudentUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #A08000;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.STUDENT);

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

        this.tfPathStudentData = new TextField();
        this.tfPathStudentData.setPromptText("Enter path to student data file");
        this.tfPathStudentData.setText("");
        this.tfPathStudentData.setMinWidth(250);
        this.btnLoadStudentData = new Button("Load");
        Label lbPlaceHolder = new Label("Insert Student Data ");
        lbPlaceHolder.setPadding(new Insets(4));
        HBox hBox1 = new HBox(lbPlaceHolder, tfPathStudentData, btnLoadStudentData);


        this.tfRemoveStudent = new TextField();
        this.tfRemoveStudent.setPromptText("Enter id of student to remove");
        this.tfRemoveStudent.setMinWidth(250);
        this.btnRemoveStudent = new Button("Remove");
        Label lbPlaceholder1 = new Label("Remove Student     ");
        lbPlaceholder1.setPadding(new Insets(4));
        HBox hBox2 = new HBox(lbPlaceholder1, tfRemoveStudent, btnRemoveStudent);

        VBox vBox = new VBox(hBox1, hBox2);
        vBox.setPadding(new Insets(15));
        this.setCenter(vBox);

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
            manager.insertData(tfPathStudentData.getText());
            tfPathStudentData.setText("");
        });

        btnRemoveStudent.setOnAction(actionEvent -> {
            manager.deleteData(tfRemoveStudent.getText());
            tfRemoveStudent.setText("");
        });

        tfPathStudentData.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                if(!tfPathStudentData.getText().equals("")){
                    manager.insertData(tfPathStudentData.getText());
                    tfPathStudentData.setText("");
                }else{
                    tfPathStudentData.setStyle("-fx-background-color: red");
                }
            }
        });
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
