package pt.isec.pa.apoio_poe.ui.gui.components;

import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.fsm.ApplicationState;
import pt.isec.pa.apoio_poe.model.fsm.FSManager;

import java.awt.*;

public class CandidatureLockedUI extends BorderPane {
    private FSManager manager;
    private Button btnPrevious, btnPropAttrib;


    public CandidatureLockedUI(FSManager manager){
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

    }

    private void registerHandlers() { }

    private void update() {
        this.setVisible(manager != null && manager.getState() == ApplicationState.CANDIDATURE_LOCKED);
    }
}
