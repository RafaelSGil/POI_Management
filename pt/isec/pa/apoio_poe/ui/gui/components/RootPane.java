package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.scene.layout.*;
import pt.isec.pa.apoio_poe.model.FSManager;

public class RootPane extends BorderPane {
    private FSManager manager;

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
    }
    private void registerEvents() {

    }
    private void update() {

    }

}
