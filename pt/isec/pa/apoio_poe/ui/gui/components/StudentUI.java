package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

        this.tfRemoveStudent = new TextField();
        this.tfRemoveStudent.setPromptText("Enter id of student to remove");
        this.btnRemoveStudent = new Button("Remove");
        HBox hBox2 = new HBox(tfRemoveStudent, btnRemoveStudent);


        this.tfPathStudentData = new TextField();
        this.tfPathStudentData.setPromptText("Enter path to student data file");
        this.btnLoadStudentData = new Button("Load");

        HBox hBox1 = new HBox(tfPathStudentData, btnLoadStudentData);
        VBox vBox = new VBox(hBox1, hBox2);
        vBox.setPadding(new Insets(15));
        this.setCenter(vBox);

/*        ListView<String> options = new ListView<>();
        options.getItems().addAll("Insert student data", "Delete student data",
                "Edit student data", "Consult student data", "Professor Management", "Proposal Management",
                "Candidature Management",
                "Close state", "Save & quit", "Load save");

        options.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        options.setStyle("-fx-background-color: #A08000;");
        options.setPadding(new Insets(20));
        VBox vBox = new VBox(options);
        this.setCenter(vBox);*/
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.STUDENT);
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
        });

        btnRemoveStudent.setOnAction(actionEvent -> {
            manager.deleteData(tfRemoveStudent.getText());
        });
    }

    private void update() {
        //this.setVisible(manager != null && manager.getState() == ApplicationState.STUDENT);
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
