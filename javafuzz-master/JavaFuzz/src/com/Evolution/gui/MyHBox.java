package com.Evolution.gui;

import com.Evolution.exceptions.*;
import com.Evolution.interfaces.ISpecies;
import com.Evolution.logic.Game;
import com.Evolution.logic.Species;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Created by brownba1 on 3/28/2016.
 * Class to create our version of an HBox so that children are
 * stored and easily accessible
 */
class MyHBox extends HBox {

    private GameScreenController gameScreen;
    private Game game;
    private HBox playerPane;
    private Label foodLabel;
    private ImageView firstPlayerMarker;
    private ArrayList<SpeciesBoard> playerSpeciesBoards = new ArrayList<>();
    private int playerIndex;

    /**
     * Constructor for our HBox that stores children
     */
    MyHBox(int playerIndex, Game g, GameScreenController gameScreen) {
        this.playerPane = new HBox();
        this.playerIndex = playerIndex;
        this.game = g;
        this.gameScreen = gameScreen;
    }

    /**
     * Set up the default screen with player info and single species
     * Places the first player marker on player one to start the game
     *
     * @return the pane created for this player
     */
    HBox createBox() throws InvalidPlayerSelectException, IllegalSpeciesIndexException,
            InvalidWateringHoleCardCountException, FoodBankEmptyException {
        this.firstPlayerMarker = (this.playerIndex == 0) ? new ImageView("/images/first_player_marker.png") :
                new ImageView("/images/empty.png");

        this.foodLabel = new Label("Food bag: " + 0);
        Label playerNumLabel = new Label("Player " + (this.playerIndex + 1));

        VBox playerInfo = new VBox();
        playerInfo.setAlignment(Pos.CENTER);
        playerInfo.getChildren().addAll(this.firstPlayerMarker, playerNumLabel, this.foodLabel);

        SpeciesBoard speciesBoard = new SpeciesBoard(this.playerIndex, 0, this, this.game, this.gameScreen);
        VBox speciesPane = speciesBoard.createSpeciesBoard();
        this.playerSpeciesBoards.add(speciesBoard);

        this.playerPane.getChildren().addAll(playerInfo, speciesPane);
        return this.playerPane;
    }

    /**
     * Sets the first player marker image to be empty if this player
     * is not the first player for this round, or to the first player
     * marker if they are
     *
     * @param firstPlayer true if this is the first player for this round, flase otherwise
     */
    private void setFirstPlayerMarker(boolean firstPlayer) {
        if (firstPlayer) {
            this.firstPlayerMarker.setImage(new Image("/images/first_player_marker.png"));
        } else {
            this.firstPlayerMarker.setImage(new Image("/images/empty.png"));
        }
    }

    /**
     * Adds a species to this player on the given side
     *
     * @param side "left" or "right" - the side to add the new species to
     */
    void addSpecies(int side) throws InvalidPlayerSelectException, IllegalSpeciesIndexException,
            InvalidWateringHoleCardCountException, FoodBankEmptyException {
        int numSpecies = this.game.getPlayerObjects().get(this.playerIndex).getSpecies().size();
        if (side == 0) {
            for (int i = 0; i < numSpecies; i++) {
                int oldNum = this.playerSpeciesBoards.get(i).getSpeciesNum();
                this.playerSpeciesBoards.get(i).setSpeciesNum(oldNum + 1);
            }
            SpeciesBoard speciesBoard = new SpeciesBoard(this.playerIndex, 0, this, this.game, this.gameScreen);
            VBox speciesPane = speciesBoard.createSpeciesBoard();
            this.playerSpeciesBoards.add(0, speciesBoard);
            this.playerPane.getChildren().add(1, speciesPane);
        } else {
            SpeciesBoard speciesBoard = new SpeciesBoard(this.playerIndex,
                    this.playerSpeciesBoards.size(), this, this.game, this.gameScreen);
            VBox speciesPane = speciesBoard.createSpeciesBoard();
            this.playerSpeciesBoards.add(speciesBoard);
            this.playerPane.getChildren().add(speciesPane);
        }
    }

    /**
     * Updates the static game objects if this player pane causes any changes
     */
    void updateGameScreen() {
        this.gameScreen.staticElementsUpdate();
        this.foodLabel.setText("Food Bag: " + this.game.getPlayerObjects().get(this.playerIndex).getFoodBag());
        for (SpeciesBoard b : this.playerSpeciesBoards) {
            for (ISpecies s : this.game.getPlayerObjects().get(this.playerIndex).getSpecies()) {
                b.setPopulationSizeLabel(s.getPopulation());
                b.setBodySizeLabel(s.getBodySize());
                b.setFoodLabel(s.getEatenFood());
            }
        }
        this.setFirstPlayerMarker(this.game.getFirstPlayer() == (this.playerIndex + 1));
    }

    /**
     * De/Activates this player pane's SpeciesBoards' ChoiceBoxes
     *
     * @param active whether or not this player's ChoiceBoxes are enables
     */
    int setChoicesActive(boolean active) throws DeckEmptyException, InvalidWateringHoleCardCountException,
            IllegalCardDirectionException, NullGameObjectException, InvalidPlayerSelectException, SpeciesPopulationException, IllegalSpeciesIndexException, WateringHoleEmptyException, SpeciesFullException, FoodBankEmptyException {
        if (this.game.getPhase().getNumber() == 4) {
            /** use this check once attack logic is added (checks full or carnivore can't attack)
            if ((this.game.getPlayerObjects().get(this.playerIndex).allSpeciesFull() ||
                    this.game.getPlayerObjects().get(this.playerIndex).unableToAttack())
                    && this.game.getTurn() == this.playerIndex + 1) { **/
            if (this.game.getPlayerObjects().get(this.playerIndex).allSpeciesFull()
                    && this.game.getTurn() == this.playerIndex + 1) {
                this.game.getPhase().execute();
                this.gameScreen.toggleChoiceBox();
                this.gameScreen.changeChoiceBox();
                return -1;
            }
        }
        for (SpeciesBoard board : this.playerSpeciesBoards) {
            board.setChoiceBoxViewable(active);
        }
        return 0;
    }

    /**
     * Commands each of this HBox's nested SpeciesBoards to update what their ChoiceBoxes are showing
     */
    void updateChoices() throws InvalidPlayerSelectException, IllegalSpeciesIndexException,
            InvalidWateringHoleCardCountException, FoodBankEmptyException {
        int phase = this.game.getPhase().getNumber();
        for (SpeciesBoard board : this.playerSpeciesBoards) {
            board.setChoiceBoxPhase(phase);
        }
    }
}
