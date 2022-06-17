package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.FSManager;

public class SearchUI extends BorderPane {
    private FSManager manager;
    private Label lbCurrentState;
    private Button btnListSWP, btnListAttribP, btnListSWNPWC, btnListAP, btnListPAA, btnListSPA, btnListPMAX, btnListPMIN, btnConsultSpecProf;
    private Button btnChartBranches, btnChartPercentageAttrib, btnChartTopCompanies, btnChartTopAdvisors;
    private VBox vBoxSpec;
    private TextField tfEmailProfAttrib;

    public SearchUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #b53717;");
        this.setVisible(manager.getState() == ApplicationState.SEARCH);

        this.btnListAP = new Button("Available Prop.");
        this.btnListSWNPWC = new Button("Student With Cand. & No Prop.");
        this.btnListSWP = new Button("Student With Prop.");
        this.btnListPAA = new Button("Average Prof. Attrib.");
        this.btnListSPA = new Button("Specific Prof. Attrib.");
        this.btnListPMAX = new Button("Prof. Max. Attrib.");
        this.btnListPMIN = new Button("Prof. Min. Attrib.");
        this.btnListAttribP = new Button("Prop. attributed");
        this.btnChartBranches = new Button("Branches Distribution");
        this.btnChartPercentageAttrib= new Button("Attrib. Percentage");
        this.btnChartTopAdvisors = new Button("Top Advisors");
        this.btnChartTopCompanies = new Button("Top Companies");
        HBox hBox = new HBox(btnListAP, btnListSWP, btnListPAA);
        hBox.setSpacing(20);
        hBox.setStyle("-fx-alignment: center");
        HBox hBox1 = new HBox(btnListSPA, btnListPMAX, btnListPMIN);
        hBox1.setSpacing(20);
        hBox1.setStyle("-fx-alignment: center");
        HBox hBox2 = new HBox(btnListSWNPWC, btnListAttribP, btnChartBranches);
        hBox2.setSpacing(20);
        hBox2.setStyle("-fx-alignment: center");
        HBox hBox3 = new HBox(btnChartPercentageAttrib, btnChartTopAdvisors, btnChartTopCompanies);
        hBox3.setSpacing(20);
        hBox3.setStyle("-fx-alignment: center");
        VBox vBox = new VBox(hBox, hBox2, hBox3, hBox1);
        vBox.setSpacing(10);

        this.tfEmailProfAttrib = new TextField();
        this.tfEmailProfAttrib.setPromptText("Email of professor");
        this.tfEmailProfAttrib.setMinWidth(250);
        this.btnConsultSpecProf = new Button("Consult");
        Label lbPlaceHolder1 = new Label("Consult professor attrib. individually");
        HBox hBox9 = new HBox(tfEmailProfAttrib, btnConsultSpecProf);
        this.vBoxSpec = new VBox(lbPlaceHolder1, hBox9);
        vBoxSpec.setSpacing(10);
        vBoxSpec.setVisible(false);
        vBoxSpec.setStyle("-fx-padding: 35 20 40 20;");
        VBox vBox1 = new VBox(vBox, vBoxSpec);


        VBox vBoxFinal = new VBox(vBox1);
        vBoxFinal.setStyle("-fx-padding: 50 20 20 20");
        this.setCenter(vBoxFinal);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager.getState() == ApplicationState.SEARCH);
            update();
        });

        btnListSPA.setOnAction(actionEvent -> {
            if(vBoxSpec.isVisible()){
                vBoxSpec.setVisible(false);
                return;
            }

            vBoxSpec.setVisible(true);
        });

        btnConsultSpecProf.setOnAction(actionEvent -> {
            if(tfEmailProfAttrib.getText().equals("")){
                tfEmailProfAttrib.setStyle("-fx-background-color: #fa3434");
                return;
            }
            manager.callSPA(tfEmailProfAttrib.getText());
        });

        btnListPAA.setOnAction(actionEvent -> manager.callPAA());

        btnListPMIN.setOnAction(actionEvent -> manager.callPMIN());

        btnListPMAX.setOnAction(actionEvent -> manager.callPMAX());

        btnListSWP.setOnAction(actionEvent -> manager.callSWP());

        btnListSWNPWC.setOnAction(actionEvent -> manager.callSWNPWC());

        btnListAP.setOnAction(actionEvent -> manager.callAP());

        btnListAttribP.setOnAction(actionEvent -> manager.callATTRIBP());

        btnChartTopCompanies.setOnAction(actionEvent -> manager.callTOPCOMPANY());

        btnChartTopAdvisors.setOnAction(actionEvent -> manager.callTOPADVISOR());

        btnChartBranches.setOnAction(actionEvent -> manager.callBRANCHES());

        btnChartPercentageAttrib.setOnAction(actionEvent -> manager.callPERCENTATTRIB());
    }

    private void update() {
        this.lbCurrentState.setText("Current State: " + manager.getState());
    }
}
