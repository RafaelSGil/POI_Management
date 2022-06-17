package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.ui.gui.resources.ImageManager;

public class ProfessorAttributionUI extends BorderPane {
    private FSManager manager;
    private Button btnPrev, btnClose, btnAttribution, btnManualAttribution, btnManualRemoval, btnEdit, btnConsultProf;
    private Button btnListAttributions, btnListProfessors, btnListStudents, btnEditProfessor, btnAttributeManually, btnRemoveManually;
    private Button btnListSWCP, btnListSWCNP, btnListPAA, btnListPMAX, btnListPMIN, btnListSPA, btnConsultSpecProf, btnConsultSpecProfData;
    private Label lbCurrentState;
    private TextField tfEditEmail, tfEditValue, tfAttributeEmailProf, tfAttributeIDProposal, tfRemoveEmailProf, tfRemoveIDProposal, tfEmailProf, tfEmailProfAttrib;
    private BorderPane bpManualAttribution;
    private BorderPane bpManualRemoval;
    private BorderPane bpListStudents;
    private BorderPane bpListProfessors;
    private BorderPane bpEdit;
    private BorderPane bpConsultProf;
    private VBox vBoxX;
    private final KeyCombination ctrlN = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    private final KeyCombination ctrlB = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);
    private final KeyCombination ctrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    private final KeyCombination ctrlY = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN);


    public ProfessorAttributionUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        BackgroundImage BI = new BackgroundImage(ImageManager.getImage("deis.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.setBackground(new Background(BI));
        this.setVisible(manager != null && manager.getState() == ApplicationState.PROFESSOR_ATTRIBUTION);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.lbCurrentState.setStyle("-fx-text-fill: white; -fx-font-size: 15");
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
        this.btnEditProfessor = new Button("Edit Prof.");
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
        lbPlaceHolder2.setStyle("-fx-text-fill: white; -fx-font-size: 15");
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
        lbPlaceHolder.setStyle("-fx-text-fill: white; -fx-font-size: 15");
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
        lbPlaceHolder3.setStyle("-fx-text-fill: white; -fx-font-size: 15");
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
        lbPlaceHolder1.setStyle("-fx-text-fill: white; -fx-font-size: 15");
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
        this.btnConsultSpecProfData = new Button("Consult");
        Label lbPlaceholder = new Label("Consult professor data individually");
        lbPlaceholder.setStyle("-fx-text-fill: white; -fx-font-size: 15");
        HBox hBox8 = new HBox(tfEmailProf, btnConsultSpecProfData);
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

        this.setOnKeyPressed(keyEvent -> {
            if(ctrlN.match(keyEvent)){
                manager.closeState();
            }
            if(ctrlB.match(keyEvent)){
                manager.proposalAttributionManager();
            }
            if(ctrlZ.match(keyEvent)){
                manager.undo();
            }
            if(ctrlY.match(keyEvent)){
                manager.redo();
            }
        });

        btnPrev.setOnAction(actionEvent -> manager.proposalAttributionManager());

        btnClose.setOnAction(actionEvent -> manager.closeState());

        btnListAttributions.setOnAction(actionEvent -> manager.callPA());

        btnConsultSpecProfData.setOnAction(actionEvent -> {
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

        btnConsultSpecProf.setOnAction(actionEvent -> {
            if(tfEmailProfAttrib.getText().equals("")){
                tfEmailProfAttrib.setStyle("-fx-background-color: #fa3434");
                return;
            }
            manager.callSPA(tfEmailProfAttrib.getText());
        });

        btnAttribution.setOnAction(actionEvent -> {
            manager.associateAttribution();
            manager.callPA();
        });

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

        btnRemoveManually.setOnAction(actionEvent -> {
            tfRemoveIDProposal.setStyle("-fx-background-color: white");
            tfRemoveEmailProf.setStyle("-fx-background-color: white");
            if(tfRemoveIDProposal.getText().equals("")){
                tfRemoveIDProposal.setStyle("-fx-background-color: #fa3434");
                return;
            }
            if(tfRemoveEmailProf.getText().equals("")){
                tfRemoveEmailProf.setStyle("-fx-background-color: #fa3434");
                return;
            }
            if(!manager.manualProfessorRemoval(tfRemoveEmailProf.getText(), tfRemoveIDProposal.getText())){
                tfRemoveIDProposal.setStyle("-fx-background-color: #fa3434");
                tfRemoveEmailProf.setStyle("-fx-background-color: #fa3434");
                tfRemoveEmailProf.setText("");
                tfRemoveIDProposal.setText("");
            }
        });

        btnAttributeManually.setOnAction(actionEvent -> {
            tfAttributeEmailProf.setStyle("-fx-background-color: white");
            tfAttributeIDProposal.setStyle("-fx-background-color: white");
            if(tfAttributeIDProposal.getText().equals("")){
                tfAttributeIDProposal.setStyle("-fx-background-color: #fa3434");
                return;
            }
            if(tfAttributeEmailProf.getText().equals("")){
                tfAttributeEmailProf.setStyle("-fx-background-color: #fa3434");
                return;
            }
            if(!manager.manualProfessorAttribution(tfAttributeIDProposal.getText(), tfAttributeEmailProf.getText())){
                tfAttributeEmailProf.setStyle("-fx-background-color: #fa3434");
                tfAttributeIDProposal.setStyle("-fx-background-color: #fa3434");
                tfAttributeEmailProf.setText("");
                tfAttributeIDProposal.setText("");
            }
        });

        btnEdit.setOnAction(actionEvent -> {
            tfEditValue.setStyle("-fx-background-color: white");
            tfEmailProf.setStyle("-fx-background-color: white");
            if(tfEditValue.getText().equals("")){
                tfEditValue.setStyle("-fx-background-color: #fa3434");
                return;
            }
            if(tfEditEmail.getText().equals("")){
                tfEditEmail.setStyle("-fx-background-color: #fa3434");
                return;
            }
            if(!manager.editDataProfessor(tfEditEmail.getText(), tfEditValue.getText())){
                tfEditValue.setStyle("-fx-background-color: #fa3434");
                tfEmailProf.setStyle("-fx-background-color: #fa3434");
                tfEditValue.setText("");
                tfEmailProf.setText("");
            }
        });
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
