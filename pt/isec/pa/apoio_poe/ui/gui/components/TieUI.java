package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.model.data.person.Person;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;

import java.util.ArrayList;

public class TieUI extends BorderPane {
    private FSManager manager;
    private Label lbCurrentState;
    private TextField tfIndex;
    private Button btnSubmit;
    private ListView<String> lvData;

    public TieUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #fcba03;");

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);

        BorderPane borderPane = new BorderPane();
        this.lvData = new ListView<>();
        borderPane.setMaxHeight(450);
        borderPane.setCenter(lvData);

        this.tfIndex = new TextField();
        this.tfIndex.setPromptText("Student Index");
        this.tfIndex.setMinWidth(250);
        this.btnSubmit = new Button("Submit");
        Label lbPlaceHolder = new Label("Insert the student' index you wish to attribute to");
        HBox hBox = new HBox(tfIndex, btnSubmit);
        VBox vBox = new VBox(lbPlaceHolder, hBox);
        vBox.setSpacing(10);

        VBox vBox1 = new VBox(borderPane, vBox);
        vBox1.setSpacing(20);
        vBox1.setStyle("-fx-padding: 20 30 40 30");

        this.setCenter(vBox1);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            update();
        });

        tfIndex.setOnKeyPressed(keyEvent -> {
            this.tfIndex.setStyle("-fx-background-color: white");
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                if(!manager.chooseStudentToAssociate(tfIndex.getText())){
                    this.tfIndex.setStyle("-fx-background-color: #fa3434");
                    this.tfIndex.setText("");
                }
            }
        });

        btnSubmit.setOnAction(actionEvent -> {
            this.tfIndex.setStyle("-fx-background-color: white");
            if(tfIndex.getText().equals("")){
                this.tfIndex.setStyle("-fx-background-color: #fa3434");
                return;
            }
            if(!manager.chooseStudentToAssociate(tfIndex.getText())){
                this.tfIndex.setStyle("-fx-background-color: #fa3434");
            }
        });
    }

    private void update() {
        this.setVisible(manager != null && manager.getState() == ApplicationState.TIE);
        this.lbCurrentState.setText("Current State: " + manager.getState());

        if(manager.getState() == ApplicationState.TIE){
            ArrayList<Person> arr = manager.getTies();
            ArrayList<String> str = new ArrayList<>();
            int i = 0;

            for (Person student : arr){
                str.add("Index " + i + " - Name: " + student.getName() + " ID: " + student.getId() + " E-mail: "
                        + student.getEmail()
                        + " Course Branch: " + student.getCourseBranch());
                ++i;
            }


            this.lvData.setItems(FXCollections.observableList(str));
        }
    }
}
