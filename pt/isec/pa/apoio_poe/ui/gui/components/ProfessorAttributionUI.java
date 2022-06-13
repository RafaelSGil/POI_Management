package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class ProfessorAttributionUI extends BorderPane {
    private FSManager manager;
    private Button btnPrev, btnClose, btnAttribution, btnManualAttribution, btnManualRemoval, btnEdit, btnConsultProf;
    private Button btnListAttributions, btnListProfessors, btnListStudents, btnEditProfessor, btnAttributeManually, btnRemoveManually;
    private Button btnListSWCP, btnListSWCNP, btnListPAA, btnListPMAX, btnListPMIN, btnListSPA, btnConsultSpecProf;
    private Label lbCurrentState;
    private TextField tfEditEmail, tfEditValue, tfAttributeEmailProf, tfAttributeIDProposal, tfRemoveEmailProf, tfRemoveIDProposal, tfEmailProf, tfEmailProfAttrib;
    private BorderPane bpManualAttribution;
    private BorderPane bpManualRemoval;
    private BorderPane bpListStudents;
    private BorderPane bpListProfessors;
    private BorderPane bpEdit;
    private BorderPane bpConsultProf;
    private VBox vBoxX;

    public ProfessorAttributionUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #7cd60d;");
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR_ATTRIBUTION);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
        this.btnPrev = new Button("Previous Phase");
        this.btnClose = new Button("Close state");

        this.btnListAttributions = new Button("List Attributions");
        this.btnListProfessors = new Button("List Professors");
        this.btnListStudents = new Button("List Students");
        this.btnAttribution = new Button("Attribution");
        this.btnManualAttribution = new Button("Manual attribution");
        this.btnManualRemoval = new Button("Remove");
        this.btnConsultProf = new Button("Consult professor");
        this.btnEditProfessor = new Button("Edit Attribution");
        HBox hBox = new HBox(btnAttribution, btnManualAttribution, btnManualRemoval, btnConsultProf);
        hBox.setSpacing(20);
        HBox hBox1 = new HBox(btnEditProfessor, btnListAttributions, btnListProfessors, btnListStudents);
        hBox1.setSpacing(20);
        VBox vBox = new VBox(hBox, hBox1);
        vBox.setSpacing(10);


        this.tfEditEmail = new TextField();
        this.tfEditEmail.setPromptText("Email of professor");
        this.tfEditEmail.setMinWidth(250);
        this.tfEditValue = new TextField();
        this.tfEditValue.setPromptText("true/false");
        this.tfEditValue.setMinWidth(250);
        this.btnEdit = new Button("Edit");
        VBox vBox1 = new VBox(tfEditEmail, tfEditValue, btnEdit);
        vBox1.setSpacing(20);
        Label lbPlaceHolder2 = new Label("Edit Professor Data");
        HBox hBox3 = new HBox(lbPlaceHolder2, vBox1);
        hBox3.setSpacing(20);
        this.bpEdit = new BorderPane();
        this.bpEdit.setCenter(hBox3);
        this.bpEdit.setVisible(false);

        this.tfAttributeEmailProf = new TextField();
        this.tfAttributeEmailProf.setPromptText("Email of professor");
        this.tfAttributeEmailProf.setMinWidth(250);
        this.tfAttributeIDProposal = new TextField();
        this.tfAttributeIDProposal.setPromptText("ID of proposal");
        this.tfAttributeIDProposal.setMinWidth(250);
        this.btnAttributeManually = new Button("Attribute");
        Label lbPlaceHolder = new Label("Attribute a professor manually");
        VBox vBox2 = new VBox(tfAttributeEmailProf, tfAttributeIDProposal, btnAttributeManually);
        vBox2.setSpacing(20);
        HBox hBox2 = new HBox(lbPlaceHolder, vBox2);
        hBox2.setSpacing(20);
        this.bpManualAttribution = new BorderPane();
        this.bpManualAttribution.setCenter(hBox2);
        this.bpManualAttribution.setVisible(false);

        this.tfRemoveEmailProf = new TextField();
        this.tfRemoveEmailProf.setPromptText("Email of professor");
        this.tfRemoveEmailProf.setMinWidth(250);
        this.tfRemoveIDProposal = new TextField();
        this.tfRemoveIDProposal.setPromptText("ID of proposal");
        this.tfRemoveIDProposal.setMinWidth(250);
        this.btnRemoveManually = new Button("Remove");
        Label lbPlaceHolder3 = new Label("Remove an attribution manually");
        VBox vBox3 = new VBox(tfRemoveEmailProf, tfRemoveIDProposal, btnRemoveManually);
        vBox3.setSpacing(20);
        HBox hBox4 = new HBox(lbPlaceHolder3, vBox3);
        hBox4.setSpacing(20);
        this.bpManualRemoval = new BorderPane();
        this.bpManualRemoval.setCenter(hBox4);
        this.bpManualRemoval.setVisible(false);

        this.btnListPAA = new Button("Average Attributions");
        this.btnListSPA = new Button("Specific Prof. Attrib.");
        this.btnListPMAX = new Button("Max. Attributions");
        this.btnListPMIN = new Button("Min. attributions");
        HBox hBox5 = new HBox(btnListPAA, btnListSPA);
        hBox5.setSpacing(20);
        hBox5.setStyle("-fx-alignment: center");
        HBox hBox6 = new HBox(btnListPMAX, btnListPMIN);
        hBox6.setSpacing(20);
        hBox6.setStyle("-fx-alignment: center");
        this.tfEmailProfAttrib = new TextField();
        this.tfEmailProfAttrib.setPromptText("Email of professor");
        this.tfEmailProfAttrib.setMinWidth(250);
        this.btnConsultSpecProf = new Button("Consult");
        Label lbPlaceHolder1 = new Label("Consult professor attrib. individually");
        HBox hBox9 = new HBox(tfEmailProfAttrib, btnConsultSpecProf);
        this.vBoxX = new VBox(lbPlaceHolder1, hBox9);
        vBoxX.setSpacing(10);
        vBoxX.setVisible(false);
        VBox vBox4 = new VBox(hBox5, hBox6, vBoxX);
        vBox4.setSpacing(10);
        this.bpListProfessors = new BorderPane();
        this.bpListProfessors.setCenter(vBox4);
        this.bpListProfessors.setVisible(false);

        this.btnListSWCP = new Button("With Cand. & Prof.");
        this.btnListSWCNP = new Button("With Cand. & No Prof.");
        HBox hBox7 = new HBox(btnListSWCP, btnListSWCNP);
        hBox7.setSpacing(20);
        hBox7.setStyle("-fx-alignment: center");
        this.bpListStudents = new BorderPane();
        this.bpListStudents.setCenter(hBox7);
        this.bpListStudents.setVisible(false);

        this.tfEmailProf = new TextField();
        this.tfEmailProf.setPromptText("Email of the professor to consult");
        this.tfEmailProf.setMinWidth(250);
        this.btnConsultSpecProf = new Button("Consult");
        Label lbPlaceholder = new Label("Consult professor data individually");
        HBox hBox8 = new HBox(tfEmailProf, btnConsultSpecProf);
        hBox8.setSpacing(20);
        VBox vBox5 = new VBox(lbPlaceholder, hBox8);
        vBox5.setSpacing(10);
        this.bpConsultProf = new BorderPane();
        this.bpConsultProf.setCenter(vBox5);
        this.bpConsultProf.setVisible(false);

        StackPane stackPane = new StackPane(bpEdit, bpManualAttribution, bpManualRemoval, bpListStudents, bpListProfessors, bpConsultProf);
        stackPane.setStyle("-fx-padding: 40 20 20 20");

        VBox vBoxFinal = new VBox(vBox, stackPane);
        vBoxFinal.setStyle("-fx-padding: 20 20 20 20; -fx-alignment: center");
        this.setCenter(vBoxFinal);

        ToolBar toolBar = new ToolBar(btnPrev, btnClose);
        toolBar.setStyle("-fx-spacing: 20px; -fx-padding: 10 20 10 20; -fx-alignment: center");
        this.setBottom(toolBar);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR_ATTRIBUTION);
            update();
        });

        btnPrev.setOnAction(actionEvent -> manager.proposalAttributionManager());

        btnClose.setOnAction(actionEvent -> manager.closeState());

        btnListAttributions.setOnAction(actionEvent -> manager.callPA());

        btnConsultSpecProf.setOnAction(actionEvent -> {
            if(tfEmailProf.getText().equals("")){
                tfEmailProf.setStyle("-fx-background-color: #fa3434");
                return;
            }
            manager.callINDIVPROF(tfEmailProf.getText());
            tfEmailProf.setStyle("-fx-background-color: white");
        });

        btnListPAA.setOnAction(actionEvent -> manager.callPAA());

        btnListPMIN.setOnAction(actionEvent -> manager.callPMIN());

        btnListPMAX.setOnAction(actionEvent -> manager.callPMAX());

        btnListSPA.setOnAction(actionEvent -> {
            if(tfEmailProfAttrib.getText().equals("")){
                tfEmailProfAttrib.setStyle("-fx-background-color: #fa3434");
                return;
            }
            manager.callSPA(tfEmailProfAttrib.getText());
        });

        btnAttribution.setOnAction(actionEvent -> manager.associateAttribution());

        btnListSWCNP.setOnAction(actionEvent -> manager.callSWNCP());

        btnListSWCP.setOnAction(actionEvent -> manager.callSWCP());

        btnListProfessors.setOnAction(actionEvent -> {
            if(bpListProfessors.isVisible()){
                bpListProfessors.setVisible(false);
                return;
            }

            bpListProfessors.setVisible(true);
            bpListStudents.setVisible(false);
            bpConsultProf.setVisible(false);
            bpManualAttribution.setVisible(false);
            bpEdit.setVisible(false);
            bpManualRemoval.setVisible(false);
        });

        btnListStudents.setOnAction(actionEvent -> {
            if(bpListStudents.isVisible()){
                bpListStudents.setVisible(false);
                return;
            }

            bpListProfessors.setVisible(false);
            bpListStudents.setVisible(true);
            bpConsultProf.setVisible(false);
            bpManualAttribution.setVisible(false);
            bpEdit.setVisible(false);
            bpManualRemoval.setVisible(false);
        });

        btnConsultProf.setOnAction(actionEvent -> {
            if(bpConsultProf.isVisible()){
                bpConsultProf.setVisible(false);
                return;
            }

            bpListProfessors.setVisible(false);
            bpListStudents.setVisible(false);
            bpConsultProf.setVisible(true);
            bpManualAttribution.setVisible(false);
            bpEdit.setVisible(false);
            bpManualRemoval.setVisible(false);
        });

        btnManualAttribution.setOnAction(actionEvent -> {
            if(bpManualAttribution.isVisible()){
                bpManualAttribution.setVisible(false);
                return;
            }

            bpListProfessors.setVisible(false);
            bpListStudents.setVisible(false);
            bpConsultProf.setVisible(false);
            bpManualAttribution.setVisible(true);
            bpEdit.setVisible(false);
            bpManualRemoval.setVisible(false);
        });

        btnEditProfessor.setOnAction(actionEvent -> {
            if(bpEdit.isVisible()){
                bpEdit.setVisible(false);
                return;
            }

            bpListProfessors.setVisible(false);
            bpListStudents.setVisible(false);
            bpConsultProf.setVisible(false);
            bpManualAttribution.setVisible(false);
            bpEdit.setVisible(true);
            bpManualRemoval.setVisible(false);
        });

        btnManualRemoval.setOnAction(actionEvent -> {
            if(bpManualRemoval.isVisible()){
                bpManualRemoval.setVisible(false);
                return;
            }

            bpListProfessors.setVisible(false);
            bpListStudents.setVisible(false);
            bpConsultProf.setVisible(false);
            bpManualAttribution.setVisible(false);
            bpEdit.setVisible(false);
            bpManualRemoval.setVisible(true);
        });

        btnListSPA.setOnAction(actionEvent -> {
            if(vBoxX.isVisible()){
                vBoxX.setVisible(false);
                return;
            }

            vBoxX.setVisible(true);
        });
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
