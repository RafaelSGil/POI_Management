package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pt.isec.pa.apoio_poe.model.fsm.FSManager;

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
                new ProfessorAttributionUI(manager), new SearchUI(manager));

        this.setCenter(stackPane);
    }
    private void registerEvents() {

    }
    private void update() {

    }

}
