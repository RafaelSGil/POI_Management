package pt.isec.pa.apoio_poe.model.main;

import pt.isec.pa.apoio_poe.model.context.ApplicationContext;
import pt.isec.pa.apoio_poe.ui.text.CommandLineUI;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext();
        CommandLineUI UI = new CommandLineUI(context);
        UI.start();
    }
}

