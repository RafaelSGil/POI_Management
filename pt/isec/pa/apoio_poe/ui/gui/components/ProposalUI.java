package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

import java.util.ArrayList;
import java.util.List;

public class ProposalUI extends BorderPane {
    private FSManager manager;
    Button btnStud, btnProf, btnClose, btnCandid;
    private Label lbCurrentState;
    private TextField tfPathProposalData, tfRemoveProposal, tfEditProposalID;
    private Button btnLoadProposalData, btnRemoveProposal;
    private TextField tfEditInternshipTitle, tfEditInternshipBranch, tfEditInternshipCompany, tfEditInternshipStudent;
    private TextField tfEditProjectTitle, tfEditProjectBranch, tfEditProjectProfessor, tfEditProjectStudent;
    private TextField tfEditAutoProposalTitle, tfEditAutoProposalStudent;
    private ArrayList<String> olProposals, olOptionsInternship, olOptionsProject, olOptionsAutoProposal;
    private ObservableList<String> listInternship, listProject, listAutoProposal;
    private Pane pInternshipsBranch, pInternshipsTitle, pInternshipsCompany, pInternshipsStudent;
    private Pane pProjectsTitle, pProjectsBranch, pProjectsProfessor, pProjectsStudents;
    private Pane pAutoProposalsTitle, pAutoProposalsStudent;
    private StackPane spInternship, spProject, spAutoProposals;
    private ComboBox<String> cbInternships, cbProjects, cbAutoProposals;
    private BorderPane pInternship, pProject, pAutoProposal;
    private Button btnEditInternshipTitle, btnEditInternshipBranch, btnEditInternshipCompany, btnEditInternshipStudent;
    private Button btnEditProjectTitle, btnEditProjectBranch, btnEditProjectProfessor, btnEditProjectStudent;
    private Button btnEditAutoProposalTitle, btnEditAutoProposalStudent;
    private String idOfProposal;

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
        hBox1.setStyle("-fx-padding: 20 10 10 10");


        this.tfRemoveProposal = new TextField();
        this.tfRemoveProposal.setPromptText("Enter id of proposal to remove");
        this.tfRemoveProposal.setMinWidth(250);
        this.btnRemoveProposal = new Button("Remove");
        Label lbPlaceholder1 = new Label("Remove Proposal      ");
        lbPlaceholder1.setPadding(new Insets(4));
        HBox hBox2 = new HBox(lbPlaceholder1, tfRemoveProposal, btnRemoveProposal);
        hBox2.setStyle("-fx-padding: 20 10 10 10");


        this.tfEditProposalID = new TextField();
        this.tfEditProposalID.setPromptText("Enter id of proposal to edit");
        this.tfEditProposalID.setMinWidth(250);
        Label lbPlaceholder2 = new Label("Edit proposal data   ");
        lbPlaceholder2.setPadding(new Insets(4));
        HBox hBox3 = new HBox(lbPlaceholder2, tfEditProposalID);
        hBox3.setStyle("-fx-padding: 20 10 10 10");

        //INTERNSHIP STACK PANE

        //INTERNSHIP OPTIONS
        Label lbInternship = new Label("Options to edit for internships");

        this.olOptionsInternship = new ArrayList<>();
        this.olOptionsInternship.add("title");
        this.olOptionsInternship.add("branch");
        this.olOptionsInternship.add("company");
        this.olOptionsInternship.add("student");

        this.listInternship = FXCollections.observableList(olOptionsInternship);

        this.cbInternships = new ComboBox<>(listInternship);


        //INTERNSHIP TITLE PANE
        this.tfEditInternshipTitle = new TextField();
        this.tfEditInternshipTitle.setPromptText("New Title");
        Label lbInternshipTitle = new Label("Insert new title");
        lbInternshipTitle.setPadding(new Insets(4));
        this.btnEditInternshipTitle = new Button("Edit");
        HBox hBoxInternshipTitle = new HBox(lbInternshipTitle, tfEditInternshipTitle, btnEditInternshipTitle);
        hBoxInternshipTitle.setSpacing(15);
        hBoxInternshipTitle.setPadding(new Insets(6));
        this.pInternshipsTitle = new Pane(hBoxInternshipTitle);
        this.pInternshipsTitle.setVisible(false);

        //INTERNSHIP BRANCH PANE
        this.tfEditInternshipBranch = new TextField();
        this.tfEditInternshipBranch.setPromptText("New Branch (use , to separate)");
        Label lbInternshipBranch = new Label("Insert new branch(es)");
        lbInternshipBranch.setPadding(new Insets(4));
        this.btnEditInternshipBranch = new Button("Edit");
        HBox hBoxInternshipBranch = new HBox(lbInternshipBranch, tfEditInternshipBranch, btnEditInternshipBranch);
        hBoxInternshipBranch.setSpacing(25);
        hBoxInternshipBranch.setPadding(new Insets(6));
        this.pInternshipsBranch = new Pane(hBoxInternshipBranch);
        this.pInternshipsBranch.setVisible(false);

        //INTERNSHIP COMPANY PANE
        this.tfEditInternshipCompany = new TextField();
        this.tfEditInternshipCompany.setPromptText("New Company");
        Label lbInternshipCompany = new Label("Insert new company");
        lbInternshipCompany.setPadding(new Insets(4));
        this.btnEditInternshipCompany = new Button("Edit");
        HBox hBoxInternshipCompany = new HBox(lbInternshipCompany, tfEditInternshipCompany, btnEditInternshipCompany);
        hBoxInternshipCompany.setSpacing(25);
        hBoxInternshipCompany.setPadding(new Insets(6));
        this.pInternshipsCompany = new Pane(hBoxInternshipCompany);
        this.pInternshipsCompany.setVisible(false);

        //INTERNSHIP STUDENT PANE
        this.tfEditInternshipStudent = new TextField();
        this.tfEditInternshipStudent.setPromptText("New Student");
        Label lbInternshipStudent = new Label("Insert new student ID");
        lbInternshipStudent.setPadding(new Insets(4));
        this.btnEditInternshipStudent = new Button("Edit");
        HBox hBoxInternshipStudent = new HBox(lbInternshipStudent, tfEditInternshipStudent, btnEditInternshipStudent);
        hBoxInternshipStudent.setSpacing(20);
        hBoxInternshipStudent.setPadding(new Insets(6));
        this.pInternshipsStudent = new Pane(hBoxInternshipStudent);
        this.pInternshipsStudent.setVisible(false);

        HBox hBoxInternship = new HBox(lbInternship, cbInternships);
        hBoxInternship.setSpacing(20);

        this.spInternship = new StackPane(pInternshipsBranch, pInternshipsTitle, pInternshipsCompany, pInternshipsStudent);
        this.spInternship.setStyle("-fx-padding: 25 20 0 0");

        this.pInternship = new BorderPane();
        this.pInternship.setTop(hBoxInternship);
        this.pInternship.setCenter(spInternship);
        this.pInternship.setVisible(false);

        //PROJECT STACK PANE

        //PROJECT OPTIONS
        Label lbProject = new Label("Options to edit for projects");

        this.olOptionsProject = new ArrayList<>();
        this.olOptionsProject.add("title");
        this.olOptionsProject.add("branch");
        this.olOptionsProject.add("professor");
        this.olOptionsProject.add("student");

        this.listProject = FXCollections.observableList(olOptionsProject);

        this.cbProjects = new ComboBox<>(listProject);

        //PROJECT TITLE PANE
        this.tfEditProjectTitle = new TextField();
        this.tfEditProjectTitle.setPromptText("New Title");
        Label lbProjectTitle = new Label("Insert new title");
        lbProjectTitle.setPadding(new Insets(4));
        this.btnEditProjectTitle = new Button("Edit");
        HBox hBoxProjectTitle = new HBox(lbProjectTitle, tfEditProjectTitle, btnEditProjectTitle);
        hBoxProjectTitle.setSpacing(20);
        hBoxProjectTitle.setPadding(new Insets(6));
        this.pProjectsTitle = new Pane(hBoxProjectTitle);
        this.pProjectsTitle.setVisible(false);

        //PROJECT BRANCH PANE
        this.tfEditProjectBranch = new TextField();
        this.tfEditProjectBranch.setPromptText("New branch (use , to separate)");
        Label lbProjectBranch = new Label("Insert new branch(es)");
        lbProjectBranch.setPadding(new Insets(4));
        this.btnEditProjectBranch = new Button("Edit");
        HBox hBoxProjectBranch = new HBox(lbProjectBranch, tfEditProjectBranch, btnEditProjectBranch);
        hBoxProjectBranch.setSpacing(20);
        hBoxProjectBranch.setPadding(new Insets(6));
        this.pProjectsBranch = new Pane(hBoxProjectBranch);
        this.pProjectsBranch.setVisible(false);

        //PROJECT PROFESSOR PANE
        this.tfEditProjectProfessor = new TextField();
        this.tfEditProjectProfessor.setPromptText("New professor");
        Label lbProjectProfessor = new Label("Insert new professor email");
        lbProjectProfessor.setPadding(new Insets(4));
        this.btnEditProjectProfessor = new Button("Edit");
        HBox hBoxProjectProfessor = new HBox(lbProjectProfessor, tfEditProjectProfessor, btnEditProjectProfessor);
        hBoxProjectProfessor.setSpacing(20);
        hBoxProjectProfessor.setPadding(new Insets(6));
        this.pProjectsProfessor = new Pane(hBoxProjectProfessor);
        this.pProjectsProfessor.setVisible(false);

        //PROJECT STUDENT PANE
        this.tfEditProjectStudent = new TextField();
        this.tfEditProjectStudent.setPromptText("New student");
        Label lbProjectStudent = new Label("Insert new student ID");
        lbProjectStudent.setPadding(new Insets(4));
        this.btnEditProjectStudent = new Button("Edit");
        HBox hBoxProjectStudent = new HBox(lbProjectStudent, tfEditProjectStudent, btnEditProjectStudent);
        hBoxProjectStudent.setSpacing(20);
        hBoxProjectStudent.setPadding(new Insets(6));
        this.pProjectsStudents = new Pane(hBoxProjectStudent);
        this.pProjectsStudents.setVisible(false);

        HBox hBoxProjects = new HBox(lbProject, cbProjects);
        hBoxProjects.setSpacing(20);

        this.spProject = new StackPane(pProjectsBranch,pProjectsProfessor,pProjectsStudents,pProjectsTitle);
        this.spProject.setStyle("-fx-padding: 25 20 0 0");

        this.pProject = new BorderPane();
        this.pProject.setTop(hBoxProjects);
        this.pProject.setCenter(spProject);
        this.pProject.setVisible(false);


        //AUTOPROPOSALS STACK PANE

        //AUTOPROPOSALS OPTIONS
        Label lbAutoProposals = new Label("Options to edit for auto proposals");

        this.olOptionsAutoProposal = new ArrayList<>();
        this.olOptionsAutoProposal.add("title");
        this.olOptionsAutoProposal.add("student");

        this.listAutoProposal = FXCollections.observableList(olOptionsAutoProposal);

        this.cbAutoProposals = new ComboBox<>(listAutoProposal);

        //AUTOPROPOSALS TITLE PANE
        this.tfEditAutoProposalTitle = new TextField();
        this.tfEditAutoProposalTitle.setPromptText("New title");
        Label lbAutoProposalsTitle = new Label("Insert new title");
        lbAutoProposalsTitle.setPadding(new Insets(4));
        this.btnEditAutoProposalTitle = new Button("Edit");
        HBox hBoxAutoProposalTitle = new HBox(lbAutoProposalsTitle, tfEditAutoProposalTitle, btnEditAutoProposalTitle);
        hBoxAutoProposalTitle.setSpacing(20);
        hBoxAutoProposalTitle.setPadding(new Insets(6));
        this.pAutoProposalsTitle = new Pane(hBoxAutoProposalTitle);
        this.pAutoProposalsTitle.setVisible(false);

        //AUTOPROPOSALS STUDENT PANE
        this.tfEditAutoProposalStudent = new TextField();
        this.tfEditAutoProposalStudent.setPromptText("New Student");
        Label lbAutoProposalsStudent = new Label("New Student ID");
        lbAutoProposalsStudent.setPadding(new Insets(4));
        this.btnEditAutoProposalStudent = new Button("Edit");
        HBox hBoxAutoProposalStudent = new HBox(lbAutoProposalsStudent, tfEditAutoProposalStudent, btnEditAutoProposalStudent);
        hBoxAutoProposalStudent.setSpacing(20);
        hBoxAutoProposalStudent.setPadding(new Insets(6));
        this.pAutoProposalsStudent = new Pane(hBoxAutoProposalStudent);
        this.pAutoProposalsStudent.setVisible(false);

        HBox hBoxAutoProposals = new HBox(lbAutoProposals, cbAutoProposals);
        hBoxAutoProposals.setSpacing(20);

        this.spAutoProposals = new StackPane(pAutoProposalsStudent, pAutoProposalsTitle);
        this.spAutoProposals.setStyle("-fx-padding: 25 20 0 0");

        this.pAutoProposal = new BorderPane();
        this.pAutoProposal.setTop(hBoxAutoProposals);
        this.pAutoProposal.setCenter(spAutoProposals);
        this.pAutoProposal.setVisible(false);


        //GLOBAL EDIT STACK PANE
        StackPane stackPane = new StackPane(pInternship, pProject, pAutoProposal);
        stackPane.setStyle("-fx-padding: 20 10 10 10");

        VBox vBoxFinal = new VBox(hBox1, hBox2, hBox3, stackPane);
        vBoxFinal.setPadding(new Insets(15));
        this.setCenter(vBoxFinal);
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

        tfEditProposalID.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                if(!tfEditProposalID.getText().equals("")){
                    switch (manager.checkTypeProposal(tfEditProposalID.getText())){
                        case -1 ->{
                            idOfProposal = tfEditProposalID.getText();
                            tfEditProposalID.setStyle("-fx-background-color: #fa3434");
                            tfEditProposalID.setText("");
                            this.pInternship.setVisible(false);
                            this.pProject.setVisible(false);
                            this.pAutoProposal.setVisible(false);
                        }
                        case 0 ->{
                            idOfProposal = tfEditProposalID.getText();
                            tfEditProposalID.setStyle("-fx-background-color: white");
                            tfEditProposalID.setText("");
                            this.pInternship.setVisible(true);
                            this.pProject.setVisible(false);
                            this.pAutoProposal.setVisible(false);
                        }
                        case 1 -> {
                            idOfProposal = tfEditProposalID.getText();
                            tfEditProposalID.setStyle("-fx-background-color: white");
                            tfEditProposalID.setText("");
                            this.pInternship.setVisible(false);
                            this.pProject.setVisible(true);
                            this.pAutoProposal.setVisible(false);
                        }
                        case 2 ->{
                            idOfProposal = tfEditProposalID.getText();
                            tfEditProposalID.setStyle("-fx-background-color: white");
                            tfEditProposalID.setText("");
                            this.pInternship.setVisible(false);
                            this.pProject.setVisible(false);
                            this.pAutoProposal.setVisible(true);
                        }
                    }
                }else{
                    tfEditProposalID.setStyle("-fx-background-color: #fa3434");
                }
            }
        });

        cbInternships.setOnAction(actionEvent -> {
            int index = cbInternships.getSelectionModel().getSelectedIndex();

            switch (index){
                case 0 -> {
                    this.pInternshipsTitle.setVisible(true);
                    this.pInternshipsBranch.setVisible(false);
                    this.pInternshipsCompany.setVisible(false);
                    this.pInternshipsStudent.setVisible(false);
                }
                case 1 ->{
                    this.pInternshipsTitle.setVisible(false);
                    this.pInternshipsBranch.setVisible(true);
                    this.pInternshipsCompany.setVisible(false);
                    this.pInternshipsStudent.setVisible(false);
                }
                case 2 ->{
                    this.pInternshipsTitle.setVisible(false);
                    this.pInternshipsBranch.setVisible(false);
                    this.pInternshipsCompany.setVisible(true);
                    this.pInternshipsStudent.setVisible(false);
                }
                case 3 ->{
                    this.pInternshipsTitle.setVisible(false);
                    this.pInternshipsBranch.setVisible(false);
                    this.pInternshipsCompany.setVisible(false);
                    this.pInternshipsStudent.setVisible(true);
                }
            }
        });

        cbProjects.setOnAction(actionEvent -> {
            int index = cbProjects.getSelectionModel().getSelectedIndex();

            switch (index){
                case 0 -> {
                    this.pProjectsTitle.setVisible(true);
                    this.pProjectsBranch.setVisible(false);
                    this.pProjectsProfessor.setVisible(false);
                    this.pProjectsStudents.setVisible(false);
                }
                case 1 ->{
                    this.pProjectsTitle.setVisible(false);
                    this.pProjectsBranch.setVisible(true);
                    this.pProjectsProfessor.setVisible(false);
                    this.pProjectsStudents.setVisible(false);
                }
                case 2 ->{
                    this.pProjectsTitle.setVisible(false);
                    this.pProjectsBranch.setVisible(false);
                    this.pProjectsProfessor.setVisible(true);
                    this.pProjectsStudents.setVisible(false);
                }
                case 3 ->{
                    this.pProjectsTitle.setVisible(false);
                    this.pProjectsBranch.setVisible(false);
                    this.pProjectsProfessor.setVisible(false);
                    this.pProjectsStudents.setVisible(true);
                }
            }
        });

        cbAutoProposals.setOnAction(actionEvent -> {
            int index = cbAutoProposals.getSelectionModel().getSelectedIndex();

            switch (index){
                case 0 -> {
                    this.pAutoProposalsTitle.setVisible(true);
                    this.pAutoProposalsStudent.setVisible(false);
                }
                case 1 ->{
                    this.pAutoProposalsTitle.setVisible(false);
                    this.pAutoProposalsStudent.setVisible(true);
                }
            }
        });

        tfPathProposalData.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                if(!tfPathProposalData.getText().equals("")){
                    if(!manager.insertData(tfPathProposalData.getText())){
                        tfPathProposalData.setStyle("-fx-background-color: #fa3434");
                        tfPathProposalData.setText("");
                        return;
                    }

                    tfPathProposalData.setStyle("-fx-background-color: white");
                    tfPathProposalData.setText("");
                }else{
                    tfPathProposalData.setStyle("-fx-background-color: #fa3434");
                    tfPathProposalData.setText("");
                }
            }
        });

        btnEditInternshipTitle.setOnAction(actionEvent -> {
            if(!manager.editDataProposals(idOfProposal, cbInternships.getSelectionModel().getSelectedItem(), List.of(tfEditInternshipTitle.getText()))){
                tfEditInternshipTitle.setStyle("-fx-background-color: #fa3434");
                tfEditInternshipTitle.setText("");
                return;
            }

            tfEditInternshipTitle.setStyle("-fx-background-color: white");
            tfEditInternshipTitle.setText("");
        });

        btnEditInternshipBranch.setOnAction(actionEvent -> {
            String str = tfEditInternshipBranch.getText();
            String[] aux = str.split(",");

            if(!manager.editDataProposals(idOfProposal, cbInternships.getSelectionModel().getSelectedItem(), List.of(aux))){
                tfEditInternshipBranch.setStyle("-fx-background-color: #fa3434");
                tfEditInternshipBranch.setText("");
                return;
            }

            tfEditInternshipBranch.setStyle("-fx-background-color: white");
            tfEditInternshipBranch.setText("");

        });

        btnEditInternshipCompany.setOnAction(actionEvent -> {
            if(!manager.editDataProposals(idOfProposal, cbInternships.getSelectionModel().getSelectedItem(), List.of(tfEditInternshipCompany.getText()))){
                tfEditInternshipCompany.setStyle("-fx-background-color: #fa3434");
                tfEditInternshipCompany.setText("");
                return;
            }

            tfEditInternshipCompany.setStyle("-fx-background-color: white");
            tfEditInternshipCompany.setText("");
        });

        btnEditInternshipStudent.setOnAction(actionEvent -> {
            if(!manager.editDataProposals(idOfProposal, cbInternships.getSelectionModel().getSelectedItem(), List.of(tfEditInternshipStudent.getText()))){
                tfEditInternshipStudent.setStyle("-fx-background-color: #fa3434");
                tfEditInternshipStudent.setText("");
                return;
            }

            tfEditInternshipStudent.setStyle("-fx-background-color: white");
            tfEditInternshipStudent.setText("");
        });

        btnEditProjectTitle.setOnAction(actionEvent -> {
            if(!manager.editDataProposals(idOfProposal, cbProjects.getSelectionModel().getSelectedItem(), List.of(tfEditProjectTitle.getText()))){
                tfEditProjectTitle.setStyle("-fx-background-color: #fa3434");
                tfEditProjectTitle.setText("");
                return;
            }

            tfEditProjectTitle.setStyle("-fx-background-color: white");
            tfEditProjectTitle.setText("");
        });

        btnEditProjectBranch.setOnAction(actionEvent -> {
            String str = tfEditProjectBranch.getText();
            String[] aux = str.split(",");

            if(!manager.editDataProposals(idOfProposal, cbProjects.getSelectionModel().getSelectedItem(), List.of(aux))){
                tfEditProjectBranch.setStyle("-fx-background-color: #fa3434");
                tfEditProjectBranch.setText("");
                return;
            }

            tfEditProjectBranch.setStyle("-fx-background-color: white");
            tfEditProjectBranch.setText("");
        });

        btnEditProjectProfessor.setOnAction(actionEvent -> {
            if(!manager.editDataProposals(idOfProposal, cbProjects.getSelectionModel().getSelectedItem(), List.of(tfEditProjectProfessor.getText()))){
                tfEditProjectProfessor.setStyle("-fx-background-color: #fa3434");
                tfEditProjectProfessor.setText("");
                return;
            }

            tfEditProjectProfessor.setStyle("-fx-background-color: white");
            tfEditProjectProfessor.setText("");
        });

        btnEditProjectStudent.setOnAction(actionEvent -> {
            if(!manager.editDataProposals(idOfProposal, cbProjects.getSelectionModel().getSelectedItem(), List.of(tfEditProjectStudent.getText()))){
                tfEditProjectStudent.setStyle("-fx-background-color: #fa3434");
                tfEditProjectStudent.setText("");
                return;
            }

            tfEditProjectStudent.setStyle("-fx-background-color: white");
            tfEditProjectStudent.setText("");
        });

        btnEditAutoProposalTitle.setOnAction(actionEvent -> {
            if(!manager.editDataProposals(idOfProposal, cbProjects.getSelectionModel().getSelectedItem(), List.of(tfEditAutoProposalTitle.getText()))){
                tfEditAutoProposalTitle.setStyle("-fx-background-color: #fa3434");
                tfEditAutoProposalTitle.setText("");
                return;
            }

            tfEditAutoProposalTitle.setStyle("-fx-background-color: white");
            tfEditAutoProposalTitle.setText("");
        });

        btnEditAutoProposalStudent.setOnAction(actionEvent -> {
            if(!manager.editDataProposals(idOfProposal, cbProjects.getSelectionModel().getSelectedItem(), List.of(tfEditAutoProposalStudent.getText()))){
                tfEditAutoProposalStudent.setStyle("-fx-background-color: #fa3434");
                tfEditAutoProposalStudent.setText("");
                return;
            }

            tfEditAutoProposalStudent.setStyle("-fx-background-color: white");
            tfEditAutoProposalStudent.setText("");
        });
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
