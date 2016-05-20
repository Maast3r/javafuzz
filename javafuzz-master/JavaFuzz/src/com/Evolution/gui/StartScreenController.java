package com.Evolution.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the start screen
 * Created by brownba1 on 3/21/2016.
 */
public class StartScreenController implements Initializable {

    @FXML
    private ChoiceBox<Integer> numPlayersChoiceBox;

    @FXML
    private Button playGameButton;

    /**
     * Initialize the start screen
     *
     * @param fxmlFileLocation fxml file location for the screen (start_screen.fxml)
     * @param resources        resources available to the screen
     */
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert this.numPlayersChoiceBox != null : "fx:id=\"numPlayerChoiceBox\" was not injected: check your FXML " +
                "file 'start_screen.fxml'.";
        assert this.playGameButton != null : "fx:id=\"playGameButton\" was not injected: check your FXML file" +
                " 'start_screen.fxml'.";

        this.numPlayersChoiceBox.setItems(FXCollections.observableArrayList(3, 4, 5));
        this.numPlayersChoiceBox.getSelectionModel().selectFirst();

        this.playGameButton.setOnMouseClicked(event -> goToGameScene());
    }

    /**
     * Navigate to the game screen after passing along the selected number of players
     */
    private void goToGameScene() {
        int players = this.numPlayersChoiceBox.getValue();
        try {
            Stage s = (Stage) this.numPlayersChoiceBox.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/game_screen.fxml"));
            GameScreenController controller = new GameScreenController(players, s);
            loader.setController(controller);
            Parent p = loader.load();
            s.setScene(new Scene(p, Color.BLACK));
            s.setMaximized(true);
            s.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}