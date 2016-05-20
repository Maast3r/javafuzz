package com.Evolution;

/**
 * Main class for running the game
 * Created by brownba1 on 3/21/2016.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Opens the application by loading the start screen from xml
     *
     * @param stage Initial stage on which views are built, this is auto populated by as a JavaFX Applicationy
     * @throws Exception any number of exceptions that could have been thrown throughout the Game's lifetime. No
     *                   exceptions should actually get to this point, as they should be caught by the GUI controller
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/start_screen.fxml"));
        stage.setTitle("Evolution!");
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setScene(new Scene(root, Color.BLACK));

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
