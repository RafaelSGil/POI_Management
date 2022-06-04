package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class ProposalUI extends BorderPane {
    private FSManager manager;
    Button btnStud, btnProf, btnClose, btnCandid;
    private Label lbCurrentState;
    private TextField tfPathProposalData, tfRemoveProposal;
    private Button btnLoadProposalData, btnRemoveProposal;

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

        HBox hBox = new HBox(btnProf, btnStud, btnCandid, btnClose);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        this.setBottom(hBox);

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
