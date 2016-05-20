package com.Evolution.gui;

import com.Evolution.interfaces.ISpecies;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the attack selection popup
 * Created by brownba1 on 5/4/2016.
 */
class AttackPopupController implements Initializable {

    private SpeciesBoard board;
    private ArrayList<int[]> speciesList;
    private boolean successfulAttack = false;

    @FXML
    private Label infoLabel;
    @FXML
    private Label selectedLabel;
    @FXML
    private GridPane gridPane;
    @FXML
    private Pane infoPane;

    /**
     * Set the current player hand after initializing the controller
     *
     * @param board the SpeciesBoard to be able to call its public functions
     */
    AttackPopupController(SpeciesBoard board, ArrayList<int[]> attackableSpecies) {
        this.board = board;
        this.speciesList = attackableSpecies;
    }

    /**
     * Initialize the attack controller and popup
     *
     * @param fxmlFileLocation fxml file this controller is for (attack_popup.fxml)
     * @param resources        resources available in the package
     */
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert this.gridPane != null : "fx:id=\"gridPane\" was not injected: check your FXML file 'attack_popup.fxml'.";
        assert this.infoLabel != null : "fx:id=\"infoLabel\" was not injected: check your FXML file 'attack_popup.fxml'.";
        assert this.selectedLabel != null : "fx:id=\"selectedLabel\" was not injected: check your FXML file 'attack_popup.fxml'.";
        assert this.infoPane != null : "fx:id=\"infoPane\" was not injected: check your FXML file 'attack_popup.fxml'.";
        gridSetup();
        displaySpecies();
    }

    /**
     * Add the species that the player can attack to the window
     */
    private void displaySpecies() {
        int index = 0;
        if (this.speciesList.size() == 0) {
            this.infoLabel.setText("There are no species for you to attack!");
            return;
        }
        for (int[] s : this.speciesList) {
            this.infoLabel.setText("Left click to select which species to attack, right click and the selected species" +
                    " will show here");
            VBox speciesPane = new VBox();
            speciesPane.setAlignment(Pos.CENTER);
            Label playerIndex = new Label("Player " + (s[0] + 1));
            Label speciesIndex = new Label("Species " + (s[1] + 1));
            speciesPane.getChildren().addAll(playerIndex, speciesIndex);
            addToGrid(speciesPane, index);
            index++;
            speciesPane.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    this.selectedLabel.setText("Attack Player: " + (s[0] + 1) + " Species: " + (s[1] + 1));
                } else {
                    this.board.setSelectedSpeciesToAttack(s);
                    this.gridPane.getScene().getWindow().hide();
                }
            });
        }

    }

    /**
     * Adds the provided species to the grid pane at the correct position
     * based on the species index
     *
     * @param speciesPane the species to add
     * @param num         the species index within the speciesList
     */
    private void addToGrid(VBox speciesPane, int num) {
        int row = (int) Math.ceil(num / 3);
        int col;
        if (num < 3) {
            col = num;
        } else {
            col = num % 3;
        }
        this.gridPane.add(speciesPane, col, row);
    }

    /**
     * Sets up the grid pane with the appropriate number of rows based on the number
     * of species the player is able to attack
     * Minimum 1 row, 3 columns (these are the default setup, no changes required)
     */
    private void gridSetup() {
        int numRows = 0;
        if (this.speciesList.size() > 3) {
            numRows = (int) Math.ceil(this.speciesList.size() / 3);
        }
        for (int i = 0; i < numRows - 1; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            this.gridPane.getRowConstraints().add(rowConst);
        }
    }

    /**
     * Returns whether or not the attack was successful
     *
     * @return successfulAttack
     */
    public boolean getSuccessfulAttack() {
        return this.successfulAttack;
    }


}