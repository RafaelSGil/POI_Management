package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.FSManager;
import pt.isec.pa.apoio_poe.ui.gui.components.RootPane;

public class JFXMain extends Application {
    private FSManager manager;

    public JFXMain(){
        this.manager = new FSManager();
    }


    @Override
    public void start(Stage stage) throws Exception {
        RootPane root = new RootPane(manager);
        Scene scene = new Scene(root, 600, 400, Color.INDIGO);
        stage.setScene(scene);
        stage.setTitle("JavaFX");
        stage.setMinWidth(500);
        stage.setMinHeight(150);
        stage.show();
    }
    @Override
    public void init() throws Exception {
        super.init();
    }
    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
