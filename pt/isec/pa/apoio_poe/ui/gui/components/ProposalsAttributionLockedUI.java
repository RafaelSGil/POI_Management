package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

import java.util.List;

public class ProposalsAttributionLockedUI extends BorderPane {
    private FSManager manager;
    private Button btnPrev, btnProfAttrib;
    private Label lbCurrentState;
    private final KeyCombination ctrlN = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    private final KeyCombination ctrlB = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);
    private Button btnListStudent, btnListSWC, btnListSWSP, btnListSWP, btnListSWNP, btnListProposals, btnListProposalsFilters;
    private Button btnAttribution, btnManualAttribution, btnManualRemoval, btnAttribute, btnRemove;
    private TextField tfFilters;
    private HBox hBox1;
    private BorderPane bpListProposals, bpListStudents;

    public ProposalsAttributionLockedUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #10d4cd;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnPrev = new Button("Previous Phase");
        this.btnProfAttrib = new Button("Prof. Attribution state");

        Label label = new Label("PHASE 3 LOCKED");
        label.setStyle("-fx-text-fill: white;-fx-font-size: 25");

        this.btnListStudent = new Button("List Students");
        this.btnListProposals = new Button("List Proposals");
        HBox hBox = new HBox(btnListProposals, btnListStudent);
        hBox.setSpacing(20);
        VBox vBox = new VBox(label, hBox);
        vBox.setSpacing(30);
        vBox.setStyle("-fx-padding: 30 50 20 150;");

        Label lbPlaceHolder31 = new Label("1 - AutoProposals from students");
        Label lbPlaceHolder32 = new Label("2 - Proposals from professors");
        Label lbPlaceHolder33 = new Label("3 - Available proposals");
        Label lbPlaceHolder34 = new Label("4 - Proposals attributed");
        VBox vBox13 = new VBox(lbPlaceHolder31, lbPlaceHolder33);
        vBox13.setSpacing(20);
        VBox vBox24 = new VBox(lbPlaceHolder32, lbPlaceHolder34);
        vBox24.setSpacing(20);
        HBox hBox1324 = new HBox(vBox13, new Separator(Orientation.VERTICAL), vBox24);
        //hBox1324.setStyle("-fx-padding: 0 20 0 50");
        this.tfFilters = new TextField();
        this.tfFilters.setPromptText("Select the filters");
        this.tfFilters.setMinWidth(250);
        this.btnListProposalsFilters = new Button("List");
        HBox hBox6 = new HBox(tfFilters, btnListProposalsFilters);
        //hBox6.setStyle("-fx-padding: 0 20 0 50");
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

        StackPane stackPane = new StackPane(bpListProposals, bpListStudents);
        stackPane.setStyle("-fx-padding: 20 20 0 50");

        VBox vBoxFinal = new VBox(vBox, stackPane);
        this.setCenter(vBoxFinal);

        ToolBar toolBar = new ToolBar(btnPrev, btnProfAttrib);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL_ATTRIBUTION_LOCKED);
            update();
        });

        this.setOnKeyPressed(keyEvent -> {
            if(ctrlN.match(keyEvent)){
                manager.professorAttributionManager();
            }
            if(ctrlB.match(keyEvent)){
                manager.candidatureManager();
            }
        });

        btnProfAttrib.setOnAction(actionEvent -> {
            manager.professorAttributionManager();
        });

        btnPrev.setOnAction(actionEvent -> {
            manager.candidatureManager();
        });

        btnListProposals.setOnAction(actionEvent -> {
            if(bpListProposals.isVisible()){
                bpListProposals.setVisible(false);
                return;
            }

            bpListProposals.setVisible(true);
            bpListStudents.setVisible(false);
        });

        btnListStudent.setOnAction(actionEvent -> {
            if(bpListStudents.isVisible()){
                bpListStudents.setVisible(false);
                return;
            }

            bpListProposals.setVisible(false);
            bpListStudents.setVisible(true);
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
