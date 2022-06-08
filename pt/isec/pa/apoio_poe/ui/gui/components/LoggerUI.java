package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.FSManager;

import java.util.ArrayList;

public class LoggerUI extends BorderPane {
    private FSManager manager;
    private Label lbLogger;


    public LoggerUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        this.setStyle("-fx-background-color: white");
        this.setPadding(new Insets(20));

        this.lbLogger = new Label();
        this.setCenter(lbLogger);
    }

    private void registerHandlers(){
        manager.addPropertyChangeListener(FSManager.PROP_DATA, evt -> {
            update();
        });
    }

    private void update(){
        this.lbLogger.setText(formatLogger());
    }

    private String formatLogger(){
        ArrayList<String> logger = manager.getLogger();
        StringBuilder sb = new StringBuilder();

        for (String str : logger){
            sb.append(str).append("\n");
        }

        return sb.toString();
    }

}
