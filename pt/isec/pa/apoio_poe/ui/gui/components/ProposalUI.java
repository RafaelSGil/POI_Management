package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

import java.util.ArrayList;

public class ProposalUI extends BorderPane {
    private FSManager manager;
    Button btnStud, btnProf, btnClose, btnCandid;
    private Label lbCurrentState;
    private TextField tfPathProposalData, tfRemoveProposal;
    private Button btnLoadProposalData, btnRemoveProposal;
    private TextField tfEditProposalID, tfEditInternshipTitle, tfEditInternshipBranch, tfEditInternshipCompany, tfEditInternshipStudent;
    private TextField tfEditProjectTitle, tfEditProjectBranch, tfEditProjectProfessor, tfEditProjectStudent;
    private TextField tfEditAutoProposalTitle, tfEditAutoProposalStudent;
    private ArrayList<String> olProposals, olOptionsInternship, olOptionsProject, olOptionsAutoProposal;
    private ObservableList<String> listInternship, listProject, listAutoProposal;
    private Pane pInternships, pProjects, pAutoProposals;

    public ProposalUI(FSManager manager){
        this.manager = manager;createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #c15fb0;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnStud = new Button("Student state");
        this.btnProf = new Button("Professor state");
        this.btnCandid = new Button("Candidature state");
        this.btnClose = new Button("Close state");

        ToolBar toolBar = new ToolBar(btnProf, btnStud, btnCandid, btnClose);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);

        this.tfPathProposalData = new TextField();
        this.tfPathProposalData.setPromptText("Enter path to proposal data file");
        this.tfPathProposalData.setMinWidth(250);
        this.btnLoadProposalData = new Button("Load");
        Label lbPlaceHolder = new Label("Insert Proposal Data ");
        lbPlaceHolder.setPadding(new Insets(4));
        HBox hBox1 = new HBox(lbPlaceHolder, tfPathProposalData, btnLoadProposalData);


        this.tfRemoveProposal = new TextField();
        this.tfRemoveProposal.setPromptText("Enter id of proposal to remove");
        this.tfRemoveProposal.setMinWidth(250);
        this.btnRemoveProposal = new Button("Remove");
        Label lbPlaceholder1 = new Label("Remove Proposal    ");
        lbPlaceholder1.setPadding(new Insets(4));
        HBox hBox2 = new HBox(lbPlaceholder1, tfRemoveProposal, btnRemoveProposal);


        this.tfEditProposalID = new TextField();
        this.tfEditProposalID.setPromptText("Enter id of proposal to edit");

        this.olOptionsInternship = new ArrayList<>();
        this.olOptionsInternship.add("title");
        this.olOptionsInternship.add("branch");
        this.olOptionsInternship.add("company");
        this.olOptionsInternship.add("student");

        this.listInternship = FXCollections.observableList(olOptionsInternship);

        this.pInternships = new Pane((Node) listInternship);
        this.pInternships.setVisible(false);



        VBox vBox = new VBox(hBox1, hBox2);
        vBox.setPadding(new Insets(15));
        this.setCenter(vBox);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.PROPOSAL);
            update();
        });

        btnStud.setOnAction(actionEvent -> {
            manager.studentManager();
        });

        btnCandid.setOnAction(actionEvent -> {
            manager.candidatureManager();
        });

        btnProf.setOnAction(actionEvent -> {
            manager.professorManager();
        });

        btnClose.setOnAction(actionEvent -> {
            manager.closeState();
        });

        btnLoadProposalData.setOnAction(actionEvent -> {
            manager.insertData(tfPathProposalData.getText());
            tfPathProposalData.setText("");
        });

        btnRemoveProposal.setOnAction(actionEvent -> {
            manager.deleteData(tfRemoveProposal.getText());
            tfRemoveProposal.setText("");
        });
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
