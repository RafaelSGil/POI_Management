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
    private Button btnListSWCP, btnListSWCNP, btnListPAA, btnListPMAX, btnListPMIN, btnListSPA, btnListSpecificProfAttrib;
    private Label lbCurrentState;
    private TextField tfEditEmail, tfEditValue, tfAttributeEmailProf, tfAttributeIDProposal, tfRemoveEmailProf, tfRemoveIDProposal;
    private BorderPane bpManualAttribution, bpManualRemoval, bpListAttributions, bpListStudents, bpListProfessors, bpEdit;

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
        this.tfEditEmail.setMinWidth(350);
        this.tfEditValue = new TextField();
        this.tfEditValue.setPromptText("true/false");
        this.btnEdit = new Button("Edit");
        VBox vBox1 = new VBox(tfEditEmail, tfEditValue, btnEdit);
        vBox.setSpacing(20);
        vBox.setStyle("-fx-padding: 0 0 0 10");
        Label lbPlaceHolder2 = new Label("Edit Professor Data");
        HBox hBox3 = new HBox(lbPlaceHolder2, vBox1);
        hBox3.setStyle("-fx-padding: 20 10 20 10");
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

        StackPane stackPane = new StackPane(bpEdit, bpManualAttribution, bpManualRemoval);

        VBox vBoxFinal = new VBox(vBox);
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
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
