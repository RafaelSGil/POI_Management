package pt.isec.pa.apoio_poe.model.main;

import javafx.application.Application;
import pt.isec.pa.apoio_poe.model.FSManager;
import pt.isec.pa.apoio_poe.ui.gui.JFXMain;
import pt.isec.pa.apoio_poe.ui.text.CommandLineUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Application.launch(JFXMain.class, args);

/*        FSManager manager = new FSManager();
        CommandLineUI commandLineUI = new CommandLineUI(manager);
        commandLineUI.start();*/
    }
}
