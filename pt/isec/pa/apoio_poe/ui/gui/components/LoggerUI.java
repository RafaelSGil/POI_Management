package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.FSManager;

import java.util.ArrayList;

public class LoggerUI extends BorderPane {
    private FSManager manager;
    private Label lbState;
    private ListView<String> lvLogger;


    public LoggerUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        this.setStyle("-fx-background-color: white");

        this.lbState = new Label();
        this.setTop(lbState);

        this.lvLogger = new ListView<>();
        this.setCenter(lvLogger);
    }

    private void registerHandlers(){
        manager.addPropertyChangeListener(FSManager.PROP_DATA, evt -> {
            update();
        });
    }

    private void update(){
        this.lvLogger.setItems(FXCollections.observableList(manager.getLogger()));
        this.lbState.setText("Errors in: " + manager.getState());
    }
}
