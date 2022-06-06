package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.FSManager;

public class RootPane extends BorderPane {
    private FSManager manager;
    private Menu menu;
    private MenuItem menuItem1, menuItem2;

    public RootPane(FSManager manager) {
        this.manager = manager;

        createViews();
        registerEvents();
        update();
    }

    private void createViews() {
        StackPane stackPane = new StackPane(new StudentUI(manager), new StudentLockedUI(manager),
                new ProfessorUI(manager), new ProfessorLockedUI(manager),
                new ProposalUI(manager), new ProposalLockedUI(manager),
                new CandidatureUI(manager), new CandidatureLockedUI(manager),
                new ProposalsAttributionUI(manager), new ProposalsAttributionLockedUI(manager),
                new ProfessorAttributionUI(manager), new SearchUI(manager),
                new TieUI(manager));

        this.setCenter(stackPane);

        this.menu = new Menu("File");
        this.menuItem1 = new MenuItem("Save");
        this.menuItem2 = new MenuItem("Load");
        this.menu.getItems().addAll(menuItem1, menuItem2);
        MenuBar menuBar = new MenuBar(menu);

        VBox vBox2 = new VBox(menuBar);
        vBox2.setSpacing(10);
        this.setTop(vBox2);
    }
    private void registerEvents() {
        menuItem1.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        menuItem1.setOnAction(actionEvent -> {
            if(!manager.serializationOfProgram()){
                Stage error = new Stage();
                error.initModality(Modality.NONE);
                VBox vBox = new VBox(20);
                vBox.getChildren().addAll(new Text("Save did not work!"));
                Scene scene = new Scene(vBox, 150, 100);
                error.setScene(scene);
                error.show();
            }
        });

        menuItem2.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        menuItem2.setOnAction(actionEvent -> {
            try {
                if(!manager.loadSave()){
                    Stage error = new Stage();
                    error.initModality(Modality.NONE);
                    VBox vBox = new VBox(20);
                    vBox.getChildren().addAll(new Text("Load did not work!"));
                    Scene scene = new Scene(vBox, 150, 100);
                    error.setScene(scene);
                    error.show();
                }
            } catch (ClassNotFoundException e) {
                Stage error = new Stage();
                error.initModality(Modality.NONE);
                VBox vBox = new VBox(20);
                vBox.getChildren().addAll(new Text("Load did not work!"));
                Scene scene = new Scene(vBox, 150, 100);
                error.setScene(scene);
                error.show();
            }
        });

    }
    private void update() {

    }

}
