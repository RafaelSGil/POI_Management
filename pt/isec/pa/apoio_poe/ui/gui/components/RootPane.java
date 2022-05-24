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

public class RootPane extends BorderPane {
    private Color backgroundColor = Color.WHITE;
    private Label welcomeMessage;

    public RootPane() {
        createViews();
        registerEvents();
        update();
    }
    private void createViews() {
        setBackground(
                new Background(
                        new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)
                )
        );

        welcomeMessage = new Label();
        welcomeMessage.setText("Welcome to POI Management");
        welcomeMessage.setFont(new Font("Verdana", 20));
        setCenter(welcomeMessage);
    }
    private void registerEvents() {

    }
    private void update() {

    }

}
