package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CandidatureUI extends BorderPane {
    private FSManager manager;
    Button btnPrev, btnClose, btnPropAttrib;
    private Label lbCurrentState;
    private TextField tfPathCandidaturesData, tfRemoveCandidatures, tfEditCandidatureStudent, tfEditCandidatureProposal, tfFilters;
    private Button btnLoadCandidaturesData, btnRemoveCandidatures, btnEditCandidature, btnListStudent, btnListSWC, btnListSWNC, btnListSWSP, btnListProposals, btnListProposalsFilters;
    private BorderPane bpListStudents, bpListProposals;
    private final KeyCombination ctrlN = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    private final KeyCombination ctrlB = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);


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

        ToolBar toolBar = new ToolBar(btnPrev, btnPropAttrib, btnClose);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);

        this.tfPathCandidaturesData = new TextField();
        this.tfPathCandidaturesData.setPromptText("Enter path to candidature data file");
        this.tfPathCandidaturesData.setMinWidth(250);
        this.btnLoadCandidaturesData = new Button("Load");
        Label lbPlaceHolder = new Label("Insert Candidature Data ");
        lbPlaceHolder.setPadding(new Insets(4));
        HBox hBox1 = new HBox(lbPlaceHolder, tfPathCandidaturesData, btnLoadCandidaturesData);
        hBox1.setStyle("-fx-padding: 20 10 10 10");

        this.tfRemoveCandidatures = new TextField();
        this.tfRemoveCandidatures.setPromptText("Student ID of the candidature to remove");
        this.tfRemoveCandidatures.setMinWidth(250);
        this.btnRemoveCandidatures = new Button("Remove");
        Label lbPlaceholder1 = new Label("Remove Candidature     ");
        lbPlaceholder1.setPadding(new Insets(4));
        HBox hBox2 = new HBox(lbPlaceholder1, tfRemoveCandidatures, btnRemoveCandidatures);
        hBox2.setStyle("-fx-padding: 20 10 10 10");

        this.tfEditCandidatureStudent = new TextField();
        this.tfEditCandidatureStudent.setPromptText("Student ID");
        this.tfEditCandidatureStudent.setMinWidth(105);
        this.tfEditCandidatureProposal = new TextField();
        this.tfEditCandidatureProposal.setPromptText("Proposal ID");
        this.tfEditCandidatureProposal.setMinWidth(105);
        this.btnEditCandidature = new Button("Edit");
        Label lbPlaceHolder2 = new Label("Edit Candidature           ");
        lbPlaceHolder2.setPadding(new Insets(4));
        HBox hBox4 = new HBox(lbPlaceHolder2, tfEditCandidatureStudent, tfEditCandidatureProposal, btnEditCandidature);
        hBox4.setStyle("-fx-padding: 20 10 10 10");


        this.btnListStudent = new Button("List Students");
        this.btnListSWC = new Button("Student With Candidature");
        this.btnListSWNC = new Button("Student Without Candidature");
        this.btnListSWSP = new Button("Student With Self-Proposals");
        HBox hBox3 = new HBox(btnListSWC, btnListSWNC, btnListSWSP);
        hBox3.setStyle("-fx-padding: 20 0 0 0");
        hBox3.setSpacing(20);
        this.bpListStudents = new BorderPane();
        this.bpListStudents.setCenter(hBox3);
        this.bpListStudents.setVisible(false);

        Label lbPlaceHolder31 = new Label("1 - AutoProposals from students");
        Label lbPlaceHolder32 = new Label("2 - Proposals from professors");
        Label lbPlaceHolder33 = new Label("3 - Proposals with candidatures");
        Label lbPlaceHolder34 = new Label("4 - Proposals without candidatures");
        VBox vBox13 = new VBox(lbPlaceHolder31, lbPlaceHolder33);
        vBox13.setSpacing(20);
        VBox vBox24 = new VBox(lbPlaceHolder32, lbPlaceHolder34);
        vBox24.setSpacing(20);
        HBox hBox1324 = new HBox(vBox13, new Separator(Orientation.VERTICAL), vBox24);
        this.tfFilters = new TextField();
        this.tfFilters.setPromptText("Select the filters");
        this.tfFilters.setMinWidth(250);
        this.btnListProposalsFilters = new Button("List");
        HBox hBox6 = new HBox(tfFilters, btnListProposalsFilters);
        VBox vBox2 = new VBox(hBox1324, hBox6);
        vBox2.setSpacing(20);
        this.bpListProposals = new BorderPane();
        this.bpListProposals.setCenter(vBox2);
        this.bpListProposals.setVisible(false);
        this.bpListProposals.setPadding(new Insets(5));

        StackPane stackPane = new StackPane(bpListProposals, bpListStudents);
        stackPane.setStyle("-fx-padding: 20 0 0 0");

        this.btnListProposals = new Button("List proposals");

        HBox hBox5 = new HBox(btnListStudent, btnListProposals);
        hBox5.setSpacing(20);
        hBox5.setStyle("-fx-alignment: center");

        VBox vBoxFinal = new VBox(hBox1, hBox2, hBox4, hBox5, stackPane);
        vBoxFinal.setPadding(new Insets(15));
        this.setCenter(vBoxFinal);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.CANDIDATURE);
            update();
        });

        this.setOnKeyPressed(keyEvent -> {
            if(ctrlN.match(keyEvent)){
                manager.proposalAttributionManager();
            }
            if(ctrlB.match(keyEvent)){
                manager.studentManager();
            }
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

        btnLoadCandidaturesData.setOnAction(actionEvent -> {
            manager.insertData(tfPathCandidaturesData.getText());
            tfPathCandidaturesData.setText("");
        });

        btnRemoveCandidatures.setOnAction(actionEvent -> {
            manager.removeCandidature(tfRemoveCandidatures.getText());
            tfRemoveCandidatures.setText("");
        });

        btnListSWSP.setOnAction(actionEvent -> {
            manager.callSWSP();
        });

        btnListSWNC.setOnAction(actionEvent -> {
            manager.callSWNC();
        });

        btnListSWC.setOnAction(actionEvent -> {
            manager.callSWC();
        });

        btnEditCandidature.setOnAction(actionEvent -> {
            tfEditCandidatureStudent.setStyle("-fx-background-color: white");
            tfEditCandidatureProposal.setStyle("-fx-background-color: white");

            if(tfEditCandidatureStudent.getText().equals("")){
                tfEditCandidatureStudent.setStyle("-fx-background-color: #fa3434");
                tfEditCandidatureStudent.setText("");
                return;
            }

            if(tfEditCandidatureProposal.getText().equals("")){
                tfEditCandidatureProposal.setStyle("-fx-background-color: #fa3434");
                tfEditCandidatureProposal.setText("");
                return;
            }

            if(!manager.editCandidatures(tfEditCandidatureStudent.getText(), tfEditCandidatureProposal.getText())){
                tfEditCandidatureStudent.setStyle("-fx-background-color: #fa3434");
                tfEditCandidatureStudent.setText("");
                tfEditCandidatureProposal.setStyle("-fx-background-color: #fa3434");
                tfEditCandidatureProposal.setText("");
            }
        });


        btnListStudent.setOnAction(actionEvent -> {
            if(bpListStudents.isVisible()){
                bpListStudents.setVisible(false);
                return;
            }

            bpListStudents.setVisible(true);
            bpListProposals.setVisible(false);
        });

        btnListProposals.setOnAction(actionEvent -> {
            if(bpListProposals.isVisible()){
                bpListProposals.setVisible(false);
                return;
            }

            bpListProposals.setVisible(true);
            bpListStudents.setVisible(false);
        });

        btnListProposalsFilters.setOnAction(actionEvent -> {
            if(tfFilters.getText().equals("")) {
                tfFilters.setStyle("-fx-background-color: #fa3434");
                return;
            }
            String str = tfFilters.getText();
            String [] div = str.split(",");
            manager.callPF(List.of(div));
        });

        tfPathCandidaturesData.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                if(!tfPathCandidaturesData.getText().equals("")){
                    if(!manager.insertData(tfPathCandidaturesData.getText())){
                        tfPathCandidaturesData.setStyle("-fx-background-color: #fa3434");
                        tfPathCandidaturesData.setText("");
                        return;
                    }
                    tfPathCandidaturesData.setStyle("-fx-background-color: white");
                    tfPathCandidaturesData.setText("");
                }else{
                    tfPathCandidaturesData.setStyle("-fx-background-color: #fa3434");
                }
            }
        });

        tfRemoveCandidatures.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                if(!tfRemoveCandidatures.getText().equals("")){
                    if(!manager.deleteData(tfRemoveCandidatures.getText())){
                        tfRemoveCandidatures.setStyle("-fx-background-color: #fa3434");
                        tfRemoveCandidatures.setText("");
                        return;
                    }
                    tfRemoveCandidatures.setStyle("-fx-background-color: white");
                    tfRemoveCandidatures.setText("");
                }else{
                    tfRemoveCandidatures.setStyle("-fx-background-color: #fa3434");
                }
            }
        });

    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
