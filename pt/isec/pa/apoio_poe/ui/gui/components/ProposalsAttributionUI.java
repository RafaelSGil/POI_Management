package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationPhases;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

import java.util.List;

public class ProposalsAttributionUI extends BorderPane {
    private FSManager manager;
    private Button btnPrev, btnClose, btnProfAttrib, btnListStudent, btnListSWC, btnListSWSP, btnListSWP, btnListSWNP, btnListProposals, btnListProposalsFilters;
    private Button btnAttribution, btnManualAttribution, btnManualRemoval, btnAttribute, btnRemove;
    private Label lbCurrentState;
    private BorderPane bpListStudents, bpListProposals, bpManualRemoval, bpManualAttribution;
    private TextField tfFilters, tfMRStudentID, tfMRProposalID, tfMAStudentID, tfMAProposalID;
    private HBox hBox1;
    private final KeyCombination ctrlN = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    private final KeyCombination ctrlB = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);
    private final KeyCombination ctrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    private final KeyCombination ctrlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);


    public ProposalsAttributionUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #25f398;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnPrev = new Button("Previous Phase");
        this.btnClose = new Button("Close state");
        this.btnClose.setVisible(false);
        this.btnProfAttrib = new Button("Prof. Attribution state");
        this.btnProfAttrib.setVisible(false);

        this.btnListStudent = new Button("List Students");
        this.btnListProposals = new Button("List Proposals");
        this.btnAttribution = new Button("Attribution");
        HBox hBox = new HBox(btnAttribution, btnListProposals, btnListStudent);
        hBox.setSpacing(20);
        this.btnManualAttribution = new Button("Manual Attrib.");
        this.btnManualRemoval = new Button("Remove Attrib");
        hBox1= new HBox(btnManualAttribution, btnManualRemoval);
        hBox1.setSpacing(20);
        hBox1.setVisible(false);
        hBox1.setStyle("-fx-padding: 10 0 0 50");
        VBox vBox = new VBox(hBox, hBox1);
        vBox.setSpacing(10);
        vBox.setStyle("-fx-padding: 30 50 20 120; -fx-alignment: center");

        Label lbPlaceHolder31 = new Label("1 - AutoProposals from students");
        Label lbPlaceHolder32 = new Label("2 - Proposals from professors");
        Label lbPlaceHolder33 = new Label("3 - Available proposals");
        Label lbPlaceHolder34 = new Label("4 - Proposals attributed");
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
        this.bpListProposals.setPadding(new Insets(6));
        this.bpListProposals.setVisible(false);

        this.btnListSWC = new Button("Student With Candidature");
        this.btnListSWSP = new Button("Student With Self-Proposals");
        HBox hBox2 = new HBox(btnListSWC, btnListSWSP);
        hBox2.setSpacing(20);
        this.btnListSWP = new Button("Student With Proposal Attrib.");
        this.btnListSWNP = new Button("Student Without Proposal Attrib.");
        HBox hBox3 = new HBox(btnListSWP, btnListSWNP);
        hBox3.setSpacing(20);
        VBox vBox1 = new VBox(hBox2, hBox3);
        vBox1.setSpacing(10);
        this.bpListStudents = new BorderPane();
        this.bpListStudents.setCenter(vBox1);
        this.bpListStudents.setPadding(new Insets(6));
        this.bpListStudents.setVisible(false);

        this.tfMAProposalID = new TextField();
        this.tfMAProposalID.setPromptText("Proposal ID");
        this.tfMAProposalID.setMinWidth(250);
        this.tfMAStudentID = new TextField();
        this.tfMAStudentID.setPromptText("Student ID");
        this.tfMAStudentID.setMinWidth(250);
        Label lbPlaceHolder = new Label("Manually attribute a proposal to a student");
        this.btnAttribute = new Button("Attribution");
        VBox vBox3 = new VBox(tfMAStudentID, tfMAProposalID, btnAttribute);
        vBox3.setSpacing(20);
        HBox hBox4 = new HBox(lbPlaceHolder, vBox3);
        hBox4.setSpacing(20);
        this.bpManualAttribution = new BorderPane();
        this.bpManualAttribution.setCenter(hBox4);
        this.bpManualAttribution.setVisible(false);
        this.bpManualAttribution.setStyle("-fx-padding: 0 0 0 -20");

        this.tfMRProposalID = new TextField();
        this.tfMRProposalID.setPromptText("Proposal ID");
        this.tfMRProposalID.setMinWidth(250);
        this.tfMRStudentID = new TextField();
        this.tfMRStudentID.setPromptText("Student ID");
        this.tfMRStudentID.setMinWidth(250);
        Label lbPlaceHolder1 = new Label("Remove an attribution from a student");
        this.btnRemove = new Button("Remove");
        VBox vBox4 = new VBox(tfMRStudentID, tfMRProposalID, btnRemove);
        vBox4.setSpacing(20);
        HBox hBox5 = new HBox(lbPlaceHolder1, vBox4);
        hBox5.setSpacing(20);
        this.bpManualRemoval = new BorderPane();
        this.bpManualRemoval.setCenter(hBox5);
        this.bpManualRemoval.setVisible(false);
        this.bpManualRemoval.setStyle("-fx-padding: 0 0 0 -20");

        StackPane stackPane = new StackPane(bpListProposals, bpListStudents, bpManualAttribution, bpManualRemoval);
        stackPane.setStyle("-fx-padding: 20 20 0 50");

        VBox vBoxFinal = new VBox(vBox, stackPane);
        this.setCenter(vBoxFinal);

        ToolBar toolBar = new ToolBar(btnPrev, btnProfAttrib, btnClose);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION);
            if(manager.isLocked(ApplicationPhases.PHASE2)){
                hBox1.setVisible(true);
                btnClose.setVisible(true);
                btnProfAttrib.setVisible(true);
            }
            update();
        });

        this.setOnKeyPressed(keyEvent -> {
            if(ctrlN.match(keyEvent)){
                manager.proposalAttributionManager();
            }
            if(ctrlB.match(keyEvent)){
                manager.candidatureManager();
            }
            if(ctrlZ.match(keyEvent)){
                manager.undo();
            }
            if(ctrlY.match(keyEvent)){
                manager.redo();
            }
        });

        btnProfAttrib.setOnAction(actionEvent -> manager.professorAttributionManager());

        btnPrev.setOnAction(actionEvent -> manager.candidatureManager());

        btnClose.setOnAction(actionEvent -> manager.closeState());

        btnListProposals.setOnAction(actionEvent -> {
            if(bpListProposals.isVisible()){
                bpListProposals.setVisible(false);
                return;
            }

            bpListProposals.setVisible(true);
            bpListStudents.setVisible(false);
            bpManualAttribution.setVisible(false);
            bpManualRemoval.setVisible(false);
        });

        btnListStudent.setOnAction(actionEvent -> {
            if(bpListStudents.isVisible()){
                bpListStudents.setVisible(false);
                return;
            }

            bpListProposals.setVisible(false);
            bpListStudents.setVisible(true);
            bpManualAttribution.setVisible(false);
            bpManualRemoval.setVisible(false);
        });

        btnManualAttribution.setOnAction(actionEvent -> {
            if(bpManualAttribution.isVisible()){
                bpManualAttribution.setVisible(false);
                return;
            }

            bpListProposals.setVisible(false);
            bpListStudents.setVisible(false);
            bpManualAttribution.setVisible(true);
            bpManualRemoval.setVisible(false);
        });

        btnManualRemoval.setOnAction(actionEvent -> {
            if(bpManualRemoval.isVisible()){
                bpManualRemoval.setVisible(false);
                return;
            }

            bpListProposals.setVisible(false);
            bpListStudents.setVisible(false);
            bpManualAttribution.setVisible(false);
            bpManualRemoval.setVisible(true);
        });

        btnAttribution.setOnAction(actionEvent -> {
            btnAttribution.setStyle("-fx-text-fill: #383838");

            if(!manager.associateAttribution()){
                btnAttribution.setStyle("-fx-text-fill: #fa3434");
            }
        });

        btnRemove.setOnAction(actionEvent -> {
            tfMRStudentID.setStyle("-fx-background-color: white");
            tfMRProposalID.setStyle("-fx-background-color: white");
            if(tfMRStudentID.getText().equals("")){
                tfMRStudentID.setStyle("-fx-background-color: #fa3434");
                tfMRStudentID.setText("");
                return;
            }
            if(tfMRProposalID.getText().equals("")){
                tfMRProposalID.setStyle("-fx-background-color: #fa3434");
                tfMRProposalID.setText("");
                return;
            }
            if(!manager.manualRemoval(tfMRProposalID.getText(), tfMRStudentID.getText())){
                tfMRStudentID.setStyle("-fx-background-color: #fa3434");
                tfMRProposalID.setStyle("-fx-background-color: #fa3434");
                tfMRProposalID.setText("");
                tfMRStudentID.setText("");
            }
        });

        btnAttribute.setOnAction(actionEvent -> {
            tfMAStudentID.setStyle("-fx-background-color: white");
            tfMAProposalID.setStyle("-fx-background-color: white");
            if(tfMAStudentID.getText().equals("")){
                tfMAStudentID.setStyle("-fx-background-color: #fa3434");
                tfMAStudentID.setText("");
                return;
            }
            if(tfMAProposalID.getText().equals("")){
                tfMAProposalID.setStyle("-fx-background-color: #fa3434");
                tfMAProposalID.setText("");
                return;
            }
            if(!manager.manualAttribution(tfMAProposalID.getText(), tfMAStudentID.getText())){
                tfMAStudentID.setStyle("-fx-background-color: #fa3434");
                tfMAProposalID.setStyle("-fx-background-color: #fa3434");
                tfMAProposalID.setText("");
                tfMAStudentID.setText("");
            }
        });

        btnListProposalsFilters.setOnAction(actionEvent -> {
            if(tfFilters.getText().equals("")){
                tfFilters.setStyle("-fx-background-color: #fa3434");
                return;
            }
            String str = tfFilters.getText();
            String [] s = str.split(",");
            manager.callPF(List.of(s));
        });

        btnListSWC.setOnAction(actionEvent -> manager.callSWC());

        btnListSWP.setOnAction(actionEvent -> manager.callSWP());

        btnListSWSP.setOnAction(actionEvent -> manager.callSWSP());

        btnListSWNP.setOnAction(actionEvent -> manager.callSWNP());
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
