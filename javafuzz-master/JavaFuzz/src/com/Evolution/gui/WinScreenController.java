package com.Evolution.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

class WinScreenController implements Initializable {
    private final int winner;
    private Stage s;
    @FXML
    private Button newGameButton;
    @FXML
    private Label winLabel;

    WinScreenController(int winner, Stage stage) {
        this.winner = winner;
        this.s = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert this.newGameButton != null : "fx:id=\"newGameButton\" was not injected: check your FXML file " +
                "'win_screen.fxml'.";
        assert this.winLabel != null : "fx:id=\"winLabel\" was not injected: check your FXML file 'win_screen.fxml'.";
        this.winLabel.setText(String.format("Player %d Wins!", winner));
        this.newGameButton.setOnMouseClicked(event->goToStartScreen());
    }

    private void goToStartScreen() {
        try {
            Parent p = new FXMLLoader(getClass().getResource("/layouts/start_screen.fxml")).load();
            this.s.setScene(new Scene(p, Color.BLACK));
            this.s.sizeToScene();
            this.s.centerOnScreen();
            this.s.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
