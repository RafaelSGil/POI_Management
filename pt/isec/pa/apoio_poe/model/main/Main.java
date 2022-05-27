package pt.isec.pa.apoio_poe.model.main;

import javafx.application.Application;
import pt.isec.pa.apoio_poe.model.fsm.FSManager;
import pt.isec.pa.apoio_poe.ui.gui.JFXMain;
import pt.isec.pa.apoio_poe.ui.text.CommandLineUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - Graphic UI\n2 - Command Line UI");
        System.out.println("Choose which one you want: ");
        int value = scanner.nextInt();
        switch (value){
            case 1: {
                Application.launch(JFXMain.class, args);
            }
            case 2:{
                FSManager fsManager = new FSManager();
                CommandLineUI UI = new CommandLineUI(fsManager);
                UI.start();
            }
            default:
                System.out.println("Closing...");
        }
    }
}
