package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class CandidatureUI extends BorderPane {
    private FSManager manager;
    Button btnPrev, btnClose, btnPropAttrib;
    private Label lbCurrentState;
    private TextField tfPathCandidaturesData, tfRemoveCandidatures;
    private Button btnLoadCandidaturesData, btnRemoveCandidatures;

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


        this.tfRemoveCandidatures = new TextField();
        this.tfRemoveCandidatures.setPromptText("Enter student id of the candidature to remove");
        this.tfRemoveCandidatures.setMinWidth(250);
        this.btnRemoveCandidatures = new Button("Remove");
        Label lbPlaceholder1 = new Label("Remove Candidature    ");
        lbPlaceholder1.setPadding(new Insets(4));
        HBox hBox2 = new HBox(lbPlaceholder1, tfRemoveCandidatures, btnRemoveCandidatures);

        VBox vBox = new VBox(hBox1, hBox2);
        vBox.setPadding(new Insets(15));
        this.setCenter(vBox);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && manager.getState() == ApplicationState.CANDIDATURE);
            update();
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
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
