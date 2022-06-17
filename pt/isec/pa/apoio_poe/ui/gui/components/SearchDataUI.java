package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.collections.FXCollections;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SearchDataUI extends BorderPane {
    private FSManager manager;
    private Label lbCurrentState;
    private ListView<String> lvData;
    private BorderPane bpList, bpPieCharts, bpBarCharts;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarChart<String, Number> barChart;
    private PieChart pieChart;

    public SearchDataUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        this.setStyle("-fx-background-color: white");
        this.setVisible(manager != null && (manager.getState() == ApplicationState.SEARCH ));

        this.lvData = new ListView<>();
        this.bpList = new BorderPane();
        this.bpList.setCenter(lvData);
        this.bpList.setVisible(false);

        this.pieChart = new PieChart();
        this.bpPieCharts = new BorderPane();
        this.bpPieCharts.setCenter(pieChart);
        this.bpPieCharts.setVisible(false);

        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        this.barChart = new BarChart<>(xAxis, yAxis);
        this.bpBarCharts = new BorderPane();
        this.bpBarCharts.setCenter(barChart);
        this.bpBarCharts.setVisible(false);

        StackPane stackPane = new StackPane(bpList, bpPieCharts, bpBarCharts);

        this.setCenter(stackPane);

        this.lbCurrentState = new Label("Current State: " + manager.getState());
        this.setTop(lbCurrentState);
    }

    private void registerHandlers(){
        manager.addPropertyChangeListener(FSManager.PROP_STATE, evt -> {
            this.setVisible(manager != null && (manager.getState() == ApplicationState.SEARCH));
            this.lbCurrentState.setText("Current State: " + manager.getState());
        });

        manager.addPropertyChangeListener(FSManager.PROP_PAA, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(List.of(manager.listAverageAttributions())));
                this.bpList.setVisible(true);
                this.bpPieCharts.setVisible(false);
                this.bpBarCharts.setVisible(false);
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_PMIN, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(List.of(manager.listMinimumAttributions())));
                this.bpList.setVisible(true);
                this.bpPieCharts.setVisible(false);
                this.bpBarCharts.setVisible(false);
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_PMAX, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(List.of(manager.listMaximumAttribution())));
                this.bpList.setVisible(true);
                this.bpPieCharts.setVisible(false);
                this.bpBarCharts.setVisible(false);
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SWP, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listStudentWithProposalAttributed()));
                this.bpList.setVisible(true);
                this.bpPieCharts.setVisible(false);
                this.bpBarCharts.setVisible(false);
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SWNPWC, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listStudentWithoutProposalAttributedAndWithCandidature()));
                this.bpList.setVisible(true);
                this.bpPieCharts.setVisible(false);
                this.bpBarCharts.setVisible(false);
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_AP, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listAvailableProposals()));
                this.bpList.setVisible(true);
                this.bpPieCharts.setVisible(false);
                this.bpBarCharts.setVisible(false);
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_ATTRIBP, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(manager.listAttributedProposals()));
                this.bpList.setVisible(true);
                this.bpPieCharts.setVisible(false);
                this.bpBarCharts.setVisible(false);
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_SPA, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                //this.lvData.getItems().clear();
                this.lvData.setItems(FXCollections.observableList(List.of(manager.listSpecificProfessorAttribution())));
                this.bpList.setVisible(true);
                this.bpPieCharts.setVisible(false);
                this.bpBarCharts.setVisible(false);
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_TOPCOMPANY, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                this.xAxis.setLabel("Company");
                this.yAxis.setLabel("Nº of Internships");

                HashMap<String, Integer> map = manager.getTopCompanies();

                List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());

                XYChart.Series<String, Number> data = new XYChart.Series<>();

                int count = 0;

                for (Map.Entry<String, Integer> m : list) {
                    if(count == 5) break;
                    data.getData().add(new XYChart.Data<>(m.getKey(), m.getValue()));
                    ++count;
                }

                this.barChart.getData().clear();
                this.barChart.getData().add(data);

                this.bpList.setVisible(false);
                this.bpPieCharts.setVisible(false);
                this.bpBarCharts.setVisible(true);
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_TOPADVISOR, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                this.xAxis.setLabel("Advisor email");
                this.yAxis.setLabel("Nº of attributions");

                HashMap<String, Integer> map = manager.getTopAdvisors();

                List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());

                XYChart.Series<String, Number> data = new XYChart.Series<>();

                int count = 0;

                for (Map.Entry<String, Integer> m : list) {
                    if(count == 5) break;
                    data.getData().add(new XYChart.Data<>(m.getKey(), m.getValue()));
                    ++count;
                }

                this.barChart.getData().clear();
                this.barChart.getData().add(data);

                this.bpList.setVisible(false);
                this.bpPieCharts.setVisible(false);
                this.bpBarCharts.setVisible(true);
            }
        });


        manager.addPropertyChangeListener(FSManager.PROP_BRANCHES, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                this.pieChart.getData().clear();

                HashMap<String, Integer> map = manager.getNumberProposalsPerBranches();

                List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());

                int count = 0;
                for(Map.Entry<String, Integer> m : list){
                    if(count == 5) break;
                    this.pieChart.getData().add(new PieChart.Data(m.getKey(), m.getValue()));
                    ++count;
                }

                this.bpList.setVisible(false);
                this.bpPieCharts.setVisible(true);
                this.bpBarCharts.setVisible(false);
            }
        });

        manager.addPropertyChangeListener(FSManager.PROP_PERCENTATTRIB, evt -> {
            if(manager.getState() == ApplicationState.SEARCH){
                this.pieChart.getData().clear();

                HashMap<String, Integer> map = manager.getNumberOfProposalsAttrAndNotAttrib();

                List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());

                int count = 0;
                for(Map.Entry<String, Integer> m : list){
                    if(count == 5) break;
                    this.pieChart.getData().add(new PieChart.Data(m.getKey(), m.getValue()));
                    ++count;
                }

                this.bpList.setVisible(false);
                this.bpPieCharts.setVisible(true);
                this.bpBarCharts.setVisible(false);
            }
        });
    }

    private void update(){

    }
}
