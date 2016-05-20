package com.Evolution.gui;

import com.Evolution.exceptions.*;
import com.Evolution.interfaces.ICard;
import com.Evolution.interfaces.InvalidAttackException;
import com.Evolution.logic.Game;
import com.Evolution.logic.Species;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by brownba1 on 3/28/2016.
 * Creates a new SpeciesBoard to add to a player's list of species
 * when they add a species
 */
class SpeciesBoard extends VBox {

    private Game game;
    private GameScreenController gameController;
    private VBox board;
    private MyHBox playerPane;
    private Label populationSizeLabel;
    private Label bodySizeLabel;
    private Label foodLabel;
    private ChoiceBox<String> actionChoiceBox;
    private Label traitLabel1;
    private Label traitLabel2;
    private Label traitLabel3;
    private int playerIndex;
    private int speciesNum;
    private ICard selectedCard;
    private ICard[] traits = new ICard[3];
    private int[] selectedSpeciesToAttack;
    private boolean carnivore = false;
    private boolean fatTissue = false;

    private ChangeListener actionListener;

    private ObservableList<String> phase2Options = FXCollections.observableArrayList(Actions.ACTIONS.getName(),
            Actions.VIEW_CARDS.getName(), Actions.DISCARD_TO_WATERING_HOLE.getName());

    private ObservableList<String> phase3Options = FXCollections.observableArrayList(Actions.ACTIONS.getName(),
            Actions.VIEW_CARDS.getName(), Actions.ADD_TRAIT.getName(), Actions.REMOVE_TRAIT.getName(),
            Actions.ADD_SPECIES_LEFT.getName(),
            Actions.ADD_SPECIES_RIGHT.getName(), Actions.INCREASE_POPULATION.getName(),
            Actions.INCREASE_BODY_SIZE.getName(), Actions.END_TURN.getName());

    private ObservableList<String> phase4Options = FXCollections.observableArrayList(Actions.ACTIONS.getName(),
            Actions.VIEW_CARDS.getName(), Actions.FEED_SPECIES.getName());
    private ObservableList<String> phase4CarnivoreOptions = FXCollections.observableArrayList(Actions.ACTIONS.getName(),
            Actions.VIEW_CARDS.getName(), Actions.ATTACK_SPECIES.getName());
    private ObservableList<String> phase4NoAttackOptions = FXCollections.observableArrayList(Actions.ACTIONS.getName(),
            Actions.VIEW_CARDS.getName(), Actions.END_TURN.getName());
    private ObservableList<String> phase4FatTissue = FXCollections.observableArrayList(Actions.ACTIONS.getName(),
            Actions.VIEW_CARDS.getName(), Actions.FEED_SPECIES.getName(), Actions.FAT_TISSUE.getName());


    /**
     * Enum for the actions in the choiceBox
     * Name is the string to show in drop down
     */
    private enum Actions {
        ACTIONS("Actions"),
        VIEW_CARDS("View Cards"),
        ADD_TRAIT("Add Trait"),
        REMOVE_TRAIT("Remove Trait"),
        ADD_SPECIES_LEFT("Add Species (Left)"),
        ADD_SPECIES_RIGHT("Add Species (Right)"),
        INCREASE_POPULATION("Increase Population"),
        INCREASE_BODY_SIZE("Increase Body Size"),
        FEED_SPECIES("Eat Food"),
        ATTACK_SPECIES("Attack Another Species"),
        END_TURN("End Your Turn"),
        DISCARD_TO_WATERING_HOLE("Discard to Watering Hole"),
        FAT_TISSUE("Add Fat Tissue food"),
        STARVE("Carnivore loses 1 food");

        private String name;

        /**
         * Used in enum initialization to store its corresponding string as a field
         *
         * @param name Action string representation
         */
        Actions(String name) {
            this.name = name;
        }

        /**
         * Returns the string representation of this Action.
         *
         * @return Action.name
         */
        public String getName() {
            return name;
        }
    }

    /**
     * Constructor for the species board
     */
    SpeciesBoard(int index, int num, MyHBox playerPane, Game game, GameScreenController controller) {
        this.board = new VBox();
        this.playerIndex = index;
        this.speciesNum = num;
        this.playerPane = playerPane;
        this.game = game;
        this.gameController = controller;
    }

    /**
     * Create a default species pane with traits, board, and actions
     *
     * @return the species pane
     */
    VBox createSpeciesBoard() throws InvalidPlayerSelectException, IllegalSpeciesIndexException,
            InvalidWateringHoleCardCountException, FoodBankEmptyException {
        VBox speciesBoard = new VBox();

        this.traitLabel1 = new Label("Trait 1: ");
        this.traitLabel2 = new Label("Trait 2: ");
        this.traitLabel3 = new Label("Trait 3: ");

        this.populationSizeLabel = new Label("Population: " + 1);
        this.populationSizeLabel.setStyle("-fx-text-fill: black;");
        this.bodySizeLabel = new Label("Body Size: " + 1);
        this.bodySizeLabel.setStyle("-fx-text-fill: black;");
        this.foodLabel = new Label("Food: " + 0);
        this.foodLabel.setStyle("-fx-text-fill: black;");

        speciesBoard.getChildren().addAll(this.populationSizeLabel, this.bodySizeLabel, this.foodLabel);
        speciesBoard.setStyle("-fx-padding: 20, 0, 20, 0; -fx-min-width: 75;" +
                " -fx-min-height: 150; -fx-background-color: burlywood; -fx-alignment: center;");

        this.actionChoiceBox = new ChoiceBox<>();
        setChoiceBoxPhase(this.game.getPhase().getNumber());
        initChangeListener();
        this.actionChoiceBox.getSelectionModel().selectedIndexProperty().addListener(actionListener);
        actionChoiceBox.getSelectionModel().selectFirst();

        this.board.getChildren().addAll(this.traitLabel1, this.traitLabel2, this.traitLabel3, speciesBoard,
                this.actionChoiceBox);
        this.board.setStyle("-fx-padding: 20, 20, 20, 20;");
        return this.board;
    }

    /**
     * Creates a ChangeListener which is used by a ChoiceBox to perform actions
     */
    private void initChangeListener() {
        this.actionListener = (ObservableValue observable, Object oldValue, Object newValue) -> {
            int val = ((int) newValue < 0) ? 0 : (int) newValue;
            switch (this.game.getPhase().getNumber()) {
                case 2:
                    if (val == 2) {
                        performAction(Actions.values()[11]);
                    } else {
                        performAction(Actions.values()[val]);
                    }
                    break;
                case 3:
                    if (val == 8) {
                        performAction(Actions.values()[10]);
                    } else {
                        performAction(Actions.values()[val]);
                    }
                    break;
                case 4:
                    if (carnivore) {
                        if(this.game.getAttackableSpecies(this.playerIndex, this.speciesNum).size() == 0){
                            this.actionChoiceBox.setItems(this.phase4NoAttackOptions);
                            if(val == 2){
                                performAction(Actions.values()[13]);
                            } else {
                                performAction(Actions.values()[val]);
                            }
                            break;
                        }
                        if (val == 2) {
                            performAction(Actions.values()[9]);
                        } else {
                            performAction(Actions.values()[val]);
                        }
                    } else if(fatTissue){
                        if (val == 2) {
                            performAction(Actions.values()[8]);
                        } else if (val == 3) {
                            performAction(Actions.values()[12]);
                        } else {
                            performAction(Actions.values()[val]);
                        }
                    } else {
                        if (val == 2) {
                            performAction(Actions.values()[8]);
                        } else {
                            performAction(Actions.values()[val]);
                        }
                    }
                    break;
                default:
                    performAction(Actions.values()[val]);
                    break;
            }
        };
    }

    /**
     * Performs the action that the user selected from the action choiceBox for this species
     *
     * @param action the selected action
     */
    private void performAction(Actions action) {
        // perform selected action
        switch (action) {
            case ACTIONS:
                // do nothing
                break;
            case VIEW_CARDS:
                openCardWindow(Actions.VIEW_CARDS);
                this.selectedCard = null;
                break;
            case ADD_TRAIT:
                try {
                    boolean add = openCardWindow(Actions.ADD_TRAIT);
                    if (this.selectedCard != null && add) {
                        this.game.addTraitToSpecies(this.playerIndex, this.speciesNum, this.selectedCard);
                        this.playerPane.updateGameScreen();
                        this.selectedCard = null;
                    }
                } catch (SpeciesDuplicateTraitException | InvalidPlayerSelectException | IllegalSpeciesIndexException
                        | IllegalCardRemovalException | NullGameObjectException | SpeciesNumberTraitsException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                break;
            case REMOVE_TRAIT:
                try {
                    openTraitWindow();
                    if (this.selectedCard != null) {
                        this.game.removeTraitFromSpecies(this.playerIndex, this.speciesNum, this.selectedCard);
                        this.playerPane.updateGameScreen();
                        this.selectedCard = null;
                    }
                } catch (SpeciesTraitNotFoundException | NullGameObjectException | IllegalSpeciesIndexException
                        | InvalidPlayerSelectException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                break;
            case ADD_SPECIES_LEFT:
                try {
                    openCardWindow(Actions.ADD_SPECIES_LEFT);
                    if (this.selectedCard != null) {
                        this.playerPane.updateGameScreen();
                        this.playerPane.addSpecies(0);
                        this.game.discardForLeftSpecies(this.playerIndex, this.selectedCard, new Species());
                        this.selectedCard = null;
                    }
                } catch (InvalidPlayerSelectException | IllegalCardRemovalException |
                        InvalidWateringHoleCardCountException | NullGameObjectException |
                        IllegalCardDiscardException | FoodBankEmptyException | IllegalSpeciesIndexException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                break;
            case ADD_SPECIES_RIGHT:
                try {
                    openCardWindow(Actions.ADD_SPECIES_RIGHT);
                    if (this.selectedCard != null) {
                        this.playerPane.updateGameScreen();
                        this.playerPane.addSpecies(1);
                        this.game.discardForRightSpecies(this.playerIndex, this.selectedCard, new Species());
                        this.selectedCard = null;
                    }
                } catch (InvalidPlayerSelectException | IllegalCardRemovalException |
                        InvalidWateringHoleCardCountException | NullGameObjectException |
                        IllegalCardDiscardException | FoodBankEmptyException | IllegalSpeciesIndexException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                break;
            case INCREASE_POPULATION:
                try {
                    openCardWindow(Actions.INCREASE_POPULATION);
                    if (this.selectedCard != null) {
                        this.game.increasePopulation(this.playerIndex, this.speciesNum, this.selectedCard);
                        this.playerPane.updateGameScreen();
                        setPopulationSizeLabel(this.game.getPlayerObjects()
                                .get(this.playerIndex).getSpecies().get(this.speciesNum).getPopulation());
                        this.selectedCard = null;
                    }
                } catch (InvalidPlayerSelectException | SpeciesPopulationException | IllegalCardRemovalException |
                        NullGameObjectException | IllegalCardDiscardException | IllegalSpeciesIndexException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                break;
            case INCREASE_BODY_SIZE:
                try {
                    openCardWindow(Actions.INCREASE_BODY_SIZE);
                    if (this.selectedCard != null) {
                        this.game.increaseBodySize(this.playerIndex, this.speciesNum, this.selectedCard);
                        this.playerPane.updateGameScreen();
                        setBodySizeLabel(this.game.getPlayerObjects()
                                .get(this.playerIndex).getSpecies().get(this.speciesNum).getBodySize());
                        this.selectedCard = null;
                    }
                } catch (InvalidPlayerSelectException | IllegalCardDiscardException | IllegalSpeciesIndexException |
                        NullGameObjectException | SpeciesBodySizeException | IllegalCardRemovalException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                break;
            case FEED_SPECIES:
                try {
                    this.game.feedPlayerSpecies(this.playerIndex, this.speciesNum);
                    this.game.getPhase().execute();
                    if(this.game.getPhase().getNumber() == 1) {
                        this.game.getPhase().execute();
                    }
                    this.gameController.updatePlayerPanes();
                    this.gameController.toggleChoiceBox();
                    this.gameController.changeChoiceBox();
                    this.gameController.staticElementsUpdate();
                } catch (SpeciesFullException | IllegalSpeciesIndexException | IllegalCardDirectionException |
                        SpeciesPopulationException | InvalidWateringHoleCardCountException | FoodBankEmptyException |
                        InvalidPlayerSelectException | WateringHoleEmptyException | NullGameObjectException |
                        DeckEmptyException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                break;
            case ATTACK_SPECIES:
                try {
                    openAttackWindow();
                    if (this.selectedSpeciesToAttack != null) {
                        this.game.attackSpecies(this.playerIndex, this.speciesNum, this.selectedSpeciesToAttack[0],
                                this.selectedSpeciesToAttack[1]);
                        this.game.getPhase().execute();
                        this.gameController.updatePlayerPanes();
                        this.gameController.toggleChoiceBox();
                        this.gameController.changeChoiceBox();
                        this.gameController.staticElementsUpdate();
                        this.selectedSpeciesToAttack = null;
                    }
                } catch (SpeciesFullException | InvalidPlayerSelectException | BodySizeIllegalAttack |
                        InvalidWateringHoleCardCountException | AttackingSelfException | InvalidAttackException |
                        IllegalSpeciesIndexException | DeckEmptyException | FoodBankEmptyException |
                        SpeciesPopulationException | NonCarnivoreAttacking | NullGameObjectException |
                        WateringHoleEmptyException | IllegalCardDirectionException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                break;
            case END_TURN:
                try {
                    this.game.getPhase().execute();
                    this.playerPane.updateGameScreen();
                    this.gameController.toggleChoiceBox();
                    this.gameController.changeChoiceBox();
                    this.selectedCard = null;
                } catch (SpeciesFullException | NullGameObjectException | IllegalCardDirectionException |
                        SpeciesPopulationException | InvalidWateringHoleCardCountException |
                        InvalidPlayerSelectException | IllegalSpeciesIndexException | FoodBankEmptyException |
                        WateringHoleEmptyException | DeckEmptyException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                break;
            case DISCARD_TO_WATERING_HOLE:

                try {
                    openCardWindow(Actions.DISCARD_TO_WATERING_HOLE);
                    if (this.selectedCard != null) {
                        this.game.discardToWateringHole(this.playerIndex, this.selectedCard);
                        this.game.getPhase().execute();
                        this.playerPane.updateGameScreen();
                        this.gameController.changeChoiceBox();
                        this.gameController.toggleChoiceBox();
                        this.selectedCard = null;
                    }
                } catch (IllegalCardDirectionException | InvalidPlayerSelectException | DeckEmptyException |
                        IllegalCardRemovalException | InvalidWateringHoleCardCountException |
                        FoodBankEmptyException | WateringHoleEmptyException | SpeciesPopulationException |
                        NullGameObjectException | IllegalCardDiscardException | IllegalSpeciesIndexException |
                        InvalidDiscardToWateringHoleException | InvalidAddToWateringHoleException |
                        SpeciesFullException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                break;
            case FAT_TISSUE:
                try {
                    this.game.fatTissueEat(this.playerIndex, this.speciesNum);
                    this.game.getPhase().execute();
                    if(this.game.getPhase().getNumber() == 1) {
                        this.game.getPhase().execute();
                    }
                    this.gameController.updatePlayerPanes();
                    this.gameController.toggleChoiceBox();
                    this.gameController.changeChoiceBox();
                    this.gameController.staticElementsUpdate();
                } catch (InvalidPlayerSelectException | IllegalSpeciesIndexException | SpeciesPopulationException
                        | TempFoodMaxException | WateringHoleEmptyException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                } catch (SpeciesFullException | DeckEmptyException | InvalidWateringHoleCardCountException
                        | NullGameObjectException | FoodBankEmptyException | IllegalCardDirectionException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                break;
            case STARVE:
                System.out.println("im starving");
//                this.game.starve(this.playerIndex, this.speciesNum);
                try {
                    this.game.getPhase().execute();
                    if(this.game.getPhase().getNumber() == 1) {
                        this.game.getPhase().execute();
                    }
                } catch (IllegalCardDirectionException | DeckEmptyException | NullGameObjectException
                        | InvalidPlayerSelectException | IllegalSpeciesIndexException | WateringHoleEmptyException
                        | InvalidWateringHoleCardCountException | FoodBankEmptyException | SpeciesFullException
                        | SpeciesPopulationException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                this.gameController.updatePlayerPanes();
                try {
                    this.gameController.toggleChoiceBox();
                } catch (DeckEmptyException | InvalidPlayerSelectException | NullGameObjectException
                        | InvalidWateringHoleCardCountException | IllegalCardDirectionException
                        | SpeciesPopulationException | WateringHoleEmptyException | IllegalSpeciesIndexException
                        | FoodBankEmptyException | SpeciesFullException e) {
                    openExceptionPopup(e);
                    e.printStackTrace();
                }
                this.gameController.changeChoiceBox();
                this.gameController.staticElementsUpdate();
                break;

        }
    }

    /**
     * Opens up the card window for the player to view their cards
     */
    private boolean openCardWindow(Actions action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/card_popup.fxml"));
            CardPopupController controller =
                    new CardPopupController(this.game.getPlayerObjects().get(this.playerIndex).getCards(), this);
            if (action == Actions.ADD_TRAIT) {
                controller.setAddTrait(true);
            }
            loader.setController(controller);
            Parent p = loader.load();
            Stage s = new Stage();
            s.setTitle("Evolution!");
            s.getIcons().add(new Image("/images/icon.png"));
            s.setScene(new Scene(p, Color.BLACK));
            s.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    s.hide();
                    this.actionChoiceBox.getSelectionModel().selectFirst();
                }
            });

            s.showAndWait();
            return controller.getSuccessfulAdd();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Opens up the trait window for the player to view their species' traits
     */
    private void openTraitWindow() {
        try {
            ArrayList<ICard> traitCards = new ArrayList<>();
            for (ICard trait : this.traits) {
                if (trait != null) {
                    traitCards.add(trait);
                }
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/card_popup.fxml"));
            CardPopupController controller =
                    new CardPopupController(traitCards, this);
            controller.setRemoveTrait(true);
            loader.setController(controller);
            Parent p = loader.load();
            Stage s = new Stage();
            s.setTitle("Evolution!");
            s.getIcons().add(new Image("/images/icon.png"));
            s.setScene(new Scene(p, Color.BLACK));
            s.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    s.hide();
                    this.actionChoiceBox.getSelectionModel().selectFirst();
                }
            });
            s.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens up the attack window for the player to view the possible species to attack
     */
    private boolean openAttackWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/attack_popup.fxml"));
            AttackPopupController controller =
                    new AttackPopupController(this, this.game.getAttackableSpecies(this.playerIndex, this.speciesNum));
            loader.setController(controller);
            Parent p = loader.load();
            Stage s = new Stage();
            s.setTitle("Evolution!");
            s.getIcons().add(new Image("/images/icon.png"));
            s.setScene(new Scene(p, Color.BLACK));
            s.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    s.hide();
                    this.actionChoiceBox.getSelectionModel().selectFirst();
                }
            });
            s.showAndWait();
            return controller.getSuccessfulAttack();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Opens up the exception window for the player to view any possible exceptions that arise
     *
     * @param e
     */
    private void openExceptionPopup(Throwable e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/exception_popup.fxml"));
            ExceptionPopupController controller =
                    new ExceptionPopupController(e);
            loader.setController(controller);
            Parent p = loader.load();
            Stage s = new Stage();
            s.setTitle("Evolution!");
            s.getIcons().add(new Image("/images/icon.png"));
            s.setScene(new Scene(p, Color.BLACK));
            s.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    s.hide();
                    this.actionChoiceBox.getSelectionModel().selectFirst();
                }
            });
            s.showAndWait();
        } catch (IOException exp) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the ability to open this SpeciesBoard's ChoiceBox
     *
     * @param viewable whether or not it should be clickable
     */
    void setChoiceBoxViewable(boolean viewable) {
        this.actionChoiceBox.setDisable(!viewable);
    }

    /**
     * Changes the viewable options for this SpeciesBoard based on the given phase number
     *
     * @param phaseNum current phase
     */
    void setChoiceBoxPhase(int phaseNum) throws InvalidPlayerSelectException, IllegalSpeciesIndexException,
            InvalidWateringHoleCardCountException, FoodBankEmptyException {
        switch (phaseNum) {
            case 2:
                checkGameOver();
                this.actionChoiceBox.setItems(this.phase2Options);
                break;
            case 3:
                this.actionChoiceBox.setItems(this.phase3Options);
                break;
            case 4:
                for (ICard trait : traits) {
                    if (trait == null) {
                        continue;
                    }
                    if (trait.getName().equals("Carnivore")) {
                        carnivore = true;
                    }
                    if(trait.getName().equals("Fat Tissue"))
                        fatTissue = true;
                }
                if (carnivore) {
                    this.actionChoiceBox.setItems(this.phase4CarnivoreOptions);
                } else if(fatTissue){
                    this.actionChoiceBox.setItems(this.phase4FatTissue);
                } else {
                    this.actionChoiceBox.setItems(this.phase4Options);
                }
                this.gameController.staticElementsUpdate();
                ArrayList<ICard> traits = this.game.getTraits(this.playerIndex, this.speciesNum);
                setTraitLabel1(traits.size() >= 1 && traits.get(0) != null ? traits.get(0).getName() : "");
                setTraitLabel2(traits.size() >= 2 && traits.get(1) != null ? traits.get(1).getName() : "");
                setTraitLabel3(traits.size() >= 3 && traits.get(2) != null ? traits.get(2).getName() : "");
                break;
            default:
                ObservableList<String> options = FXCollections.observableArrayList();
                for (Actions a : Actions.values()) {
                    options.add(a.getName());
                }
                this.actionChoiceBox.setItems(options);
                break;
        }
        this.actionChoiceBox.getSelectionModel().selectFirst();
    }

    private void checkGameOver() {
        if (this.game.isOver()) {
//            if(!this.game.isOver() && this.game.getTurn() == this.playerIndex) {
            // TODO : use this ^^ for demo
            this.gameController.openWinScreen();
        }
    }

    /**
     * Sets the card that the user selects from the card popup
     *
     * @param c the card that was selected
     */
    void setSelectedCard(ICard c) {
        this.selectedCard = c;
    }

    void setSelectedSpeciesToAttack(int[] speciesToAttack) {
        this.selectedSpeciesToAttack = speciesToAttack;
    }

    /**
     * Sets the trait that the user selects from the card popup
     *
     * @param trait which trait to set
     */
    void setTraitSelection(int trait) {
        switch (trait) {
            case 1:
                System.out.println("added to trait 1");
                this.traits[0] = this.selectedCard;
                break;
            case 2:
                System.out.println("added to trait 2");
                this.traits[1] = this.selectedCard;
                break;
            case 3:
                System.out.println("added to trait 3");
                this.traits[2] = this.selectedCard;
                break;
        }
    }

    /**
     * Remove trait at index i
     *
     * @param i
     */
    public void removeTrait(int i) {
        switch (i) {
            case 1:
                System.out.println("removed trait 1");
                this.traits[0] = null;
                setTraitLabel1("");
                break;
            case 2:
                System.out.println("removed trait 2");
                this.traits[1] = null;
                setTraitLabel2("");
                break;
            case 3:
                System.out.println("removed trait 3");
                this.traits[2] = null;
                setTraitLabel3("");
                break;
        }
    }

    /**
     * Sets the value of the population size label for this species
     *
     * @param amount the current population size
     */
    void setPopulationSizeLabel(int amount) {
        this.populationSizeLabel.setText("Population: " + amount);
    }

    /**
     * Sets the values of the body size label for this species
     *
     * @param amount the current body size
     */
    void setBodySizeLabel(int amount) {
        this.bodySizeLabel.setText("Body Size: " + amount);
    }

    /**
     * Sets the value of the food label for this species
     *
     * @param amount amount of food on board
     */
    void setFoodLabel(int amount) {
        this.foodLabel.setText("Food: " + amount);
    }

    /**
     * Sets the first trait for this species
     *
     * @param trait the new trait to set for trait 1
     */
    private void setTraitLabel1(String trait) {
        // Set trait 1 for this species then update label
        this.traitLabel1.setText("Trait 1: " + trait);
    }

    /**
     * Sets the second trait for this species
     *
     * @param trait the new trait to set for trait 2
     */
    private void setTraitLabel2(String trait) {
        // Set trait 2 for this species then update label
        this.traitLabel2.setText("Trait 2: " + trait);
    }

    /**
     * Sets the third trait for this species
     *
     * @param trait the new trait to set for trait 3
     */
    private void setTraitLabel3(String trait) {
        // Set trait 3 for this species then update label
        this.traitLabel3.setText("Trait 3: " + trait);
    }

    /**
     * Gets this species current number
     *
     * @return species number
     */
    int getSpeciesNum() {
        return this.speciesNum;
    }

    /**
     * Sets this species current species number
     *
     * @param num the new number for this species
     */
    void setSpeciesNum(int num) {
        this.speciesNum = num;
    }

    /**
     * Returns the array of traits
     *
     * @return this.traits
     */
    public ICard[] getTraits() {
        return this.traits;
    }

    /**
     * Overwrites trait at index i
     */
    public void overwriteTrait(int i) throws IllegalSpeciesIndexException, InvalidPlayerSelectException,
            NullGameObjectException, SpeciesTraitNotFoundException {
        this.game.removeTraitFromSpecies(this.playerIndex, this.speciesNum, this.traits[i]);
    }
}
