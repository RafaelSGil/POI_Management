package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.apoio_poe.model.FSManager;

public class RootPane2 extends BorderPane {
    private FSManager manager;

    public RootPane2(FSManager manager) {
        this.manager = manager;

        createViews();
        registerEvents();
        update();
    }

    private void createViews() {
        StackPane stackPane = new StackPane(new StudentDataUI(manager), new ProfessorDataUI(manager), new ProposalDataUI(manager),
                                            new CandidatureDataUI(manager), new ProposalsAttributionDataUI(manager), new ProfessorAttributionDataUI(manager));

        this.setCenter(stackPane);
    }
    private void registerEvents() {

    }
    private void update() {

    }

}
