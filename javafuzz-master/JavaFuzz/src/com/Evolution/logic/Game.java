package com.Evolution.logic;

import com.Evolution.abstracts.ATrait;
import com.Evolution.abstracts.CTrait;
import com.Evolution.exceptions.*;
import com.Evolution.interfaces.*;
import com.Evolution.traits.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Game logic controller class. Handles most interaction between related game objects.
 */
public class Game {
    private int round = 1;
    private int turn = 1;
    private ArrayList<IPlayer> players;
    private IDeck<ICard> drawPile;
    private IDeck<ICard> discardPile;
    private IWateringHole wateringHole;
    private int foodBank = 240;
    private IPhases currentPhase = new PhaseOne(this);
    private HashMap<String, ATrait> feedTraitActions = new HashMap<>();
    private HashMap<String, ATrait> defendTraitActions = new HashMap<>();
    private boolean over;

    //TODO: Add Null check for every single method that takes in an Object

    /**
     * Evolution Game constructor which contains main logic to interact with players, species, and cards
     *
     * @param players      playing game
     * @param wateringHole food available to species
     * @param drawPile     cards available to draw from
     * @param discardPile  cards that have been discarded
     * @throws IllegalNumberOfPlayers  when an ArrayList is passed in with too many or too few player objects
     * @throws NullGameObjectException if any parameters are null
     */
    public Game(ArrayList<IPlayer> players, IWateringHole wateringHole, IDeck<ICard> drawPile, IDeck<ICard> discardPile)
            throws IllegalNumberOfPlayers, NullGameObjectException {
        if (players == null || wateringHole == null || drawPile == null || discardPile == null) {
            throw new NullGameObjectException("Unable to initialize the game with NULL objects");
        }
        if (players.size() < 3 || players.size() > 5) {
            throw new IllegalNumberOfPlayers("You must have between 3-5 players.\n");
        }
        this.players = players;
        this.wateringHole = wateringHole;

        this.drawPile = drawPile;
        this.discardPile = discardPile;
        this.feedTraitActions.put("Cooperation", new Cooperation(this));
        this.feedTraitActions.put("Foraging", new Foraging(this));

        this.defendTraitActions.put("Warning Call", new WarningCall(this));
        this.defendTraitActions.put("Symbiosis", new Symbiosis(this));
    }

    /**
     * Returns the current round of the Game
     *
     * @return round
     */
    public int getRound() {
        return this.round;
    }

    /**
     * Increases the current round by one
     */
    public void increaseRound() {
        this.round++;
    }

    /**
     * Returns the list of players in the Game
     *
     * @return players
     */
    public ArrayList<IPlayer> getPlayerObjects() {
        return this.players;
    }

    /**
     * Returns the Deck of Cards corresponding to the draw pile
     *
     * @return drawPile
     */
    public IDeck<ICard> getDrawPile() {
        return this.drawPile;
    }

    /**
     * Returns the Deck of Cards corresponding to the discard pile
     *
     * @return discardPile
     */
    public IDeck<ICard> getDiscardPile() {
        return this.discardPile;
    }

    /**
     * Starts the game with Phase 1.
     * Calls currentPhase.execute()
     *
     * @throws IllegalCardDirectionException         propagated from {@link IPhases#execute()}
     * @throws DeckEmptyException                    propagated from {@link IPhases#execute()}
     * @throws InvalidPlayerSelectException          propagated from {@link IPhases#execute()}
     * @throws NullGameObjectException               propagated from {@link IPhases#execute()}
     * @throws InvalidWateringHoleCardCountException propagated from {@link IPhases#execute()}
     * @throws SpeciesPopulationException            propagated from {@link IPhases#execute()}
     * @throws IllegalSpeciesIndexException          propagated from {@link IPhases#execute()}
     * @throws WateringHoleEmptyException            propagated from {@link IPhases#execute()}
     * @throws SpeciesFullException                  propagated from {@link IPhases#execute()}
     * @throws FoodBankEmptyException                propagated from {@link IPhases#execute()}
     */
    public void startGame() throws IllegalCardDirectionException,
            DeckEmptyException, InvalidPlayerSelectException,
            NullGameObjectException, InvalidWateringHoleCardCountException,
            SpeciesPopulationException, IllegalSpeciesIndexException,
            WateringHoleEmptyException, SpeciesFullException, FoodBankEmptyException {
        this.currentPhase.execute();
    }

    /**
     * The turn that the game is currently on
     *
     * @return The number of the player whose turn it is
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * Increments which player number's turn it is
     */
    public void nextTurn() {
        if (this.turn == this.players.size()) {
            this.turn = 1;
        } else {
            this.turn++;
        }
    }

    /**
     * Gets the IWateringHole associated with this Game
     *
     * @return IWateringHole
     */
    public IWateringHole getWateringHole() {
        return this.wateringHole;
    }

    /**
     * Returns the amount of food currently available in the bank
     *
     * @return foodBank
     */
    public int getFoodBankCount() {
        return this.foodBank;
    }

    /**
     * Decrements the food bank by one
     *
     * @throws FoodBankEmptyException if the food bank is empty and attempting to decrement
     */
    public void decrementFoodBank() throws FoodBankEmptyException {
        if (this.foodBank == 0) {
            throw new FoodBankEmptyException("The food bank is empty");
        }
        this.foodBank--;
    }

    /**
     * Decrements the food bank by i
     *
     * @param i food
     * @throws FoodBankEmptyException if attempting to remove a negative amount or more food than is in the
     *                                food bank
     */
    public void decrementFoodBank(int i) throws FoodBankEmptyException {
        if (i > this.foodBank || i < 0) {
            throw new FoodBankEmptyException("The argument is larger than the current food bank.");
        }
        this.foodBank -= i;
    }

    /**
     * Decrements the food bank by i and increments the wateringHole food by i
     *
     * @param i food
     * @throws FoodBankEmptyException propagated from {@link #decrementFoodBank(int)}
     */
    public void moveFoodFromBankToHole(int i) throws FoodBankEmptyException {
        decrementFoodBank(i);
        this.wateringHole.addFood(i);
    }

    /**
     * Deal a card from the draw pile to a player
     *
     * @param i the index of the player
     * @throws DeckEmptyException           propagated from {@link Deck#draw()}
     * @throws InvalidPlayerSelectException if the index provided is outside of [0, size) of {@link #getPlayerObjects()}
     * @throws NullGameObjectException      propagated from {@link Player#addCardToHand(ICard)}
     */
    public void dealToPlayer(int i) throws DeckEmptyException, InvalidPlayerSelectException, NullGameObjectException {
        if (i >= this.players.size() || i < 0) {
            throw new InvalidPlayerSelectException("You selected an invalid player to deal to.");
        }
        ICard card = this.drawPile.draw();
        this.players.get(i).addCardToHand(card);
        if (this.drawPile.getSize() == 0) {
            this.over = true;
            this.discardPile.shuffle();
            this.drawPile = this.discardPile;
            this.discardPile = new Deck<>();
        }
    }

    /**
     * Changes the current phase to phase;
     *
     * @param phase The phase being set
     * @throws NullGameObjectException if the provided phase is null
     */
    public void setPhase(IPhases phase) throws NullGameObjectException {
        if (phase == null) {
            throw new NullGameObjectException("Cannot set the Game to a null Phase");
        }
        this.currentPhase = phase;
    }

    /**
     * Draws the appropriate amount of cards for each player.
     * Appropriate amount = # of species + 3
     *
     * @throws DeckEmptyException           propagated from {@link #dealToPlayer(int)}
     * @throws InvalidPlayerSelectException propagated from {@link #dealToPlayer(int)}
     * @throws NullGameObjectException      propagated from {@link #dealToPlayer(int)}
     */
    void drawForPlayers() throws DeckEmptyException, InvalidPlayerSelectException, NullGameObjectException {
        for (int i = 0; i < this.players.size(); i++) {
            for (int j = 0; j < this.players.get(i).getSpecies().size() + 3; j++) {
                dealToPlayer(i);
            }
        }
    }

    /**
     * Removes the provided card object from the hand of the player specified by i
     *
     * @param i    player index
     * @param card to remove
     * @throws IllegalCardDiscardException  thrown when the given card is not in the specified
     *                                      player's hand
     * @throws InvalidPlayerSelectException thrown when the given player index is greater than the number of players
     * @throws IllegalCardRemovalException  propagated from {@link IPlayer#removeCardFromHand(ICard)}
     * @throws NullGameObjectException      propagated from {@link Deck#discard(Object)}
     */
    public void discardFromPlayer(int i, ICard card) throws InvalidPlayerSelectException,
            IllegalCardDiscardException, NullGameObjectException, IllegalCardRemovalException {
        if (i > this.players.size() - 1) {
            throw new InvalidPlayerSelectException("The given player index is greater than the number of players.");
        }
        if (!this.players.get(i).getCards().contains(card)) {
            throw new IllegalCardDiscardException("Selected card is not in this players hand.");
        }
        this.players.get(i).removeCardFromHand(card);
        this.discardPile.discard(card);
    }

    /**
     * Returns the game's current phase
     *
     * @return phase
     */
    public IPhases getPhase() {
        return this.currentPhase;
    }

    /**
     * Discards the given card from the player at the given index's hand
     *
     * @param index The index of the player
     * @param card  The card to discard
     * @throws InvalidAddToWateringHoleException     propagated from {@link IWateringHole#addCard(ICard)}
     * @throws InvalidDiscardToWateringHoleException trying to discard to watering hole when it already has the
     *                                               maximum number of cards
     * @throws IllegalCardDiscardException           thrown when the given card is not in the specified
     *                                               player's hand
     * @throws InvalidPlayerSelectException          thrown when the given player index is greater than the number of players
     * @throws IllegalCardRemovalException           propagated from {@link IPlayer#removeCardFromHand(ICard)}
     * @throws NullGameObjectException               propagated from {@link IPlayer#removeCardFromHand(ICard)}
     */
    public void discardToWateringHole(int index, ICard card) throws InvalidDiscardToWateringHoleException,
            InvalidAddToWateringHoleException, InvalidPlayerSelectException, IllegalCardDiscardException,
            IllegalCardRemovalException, NullGameObjectException {
        if (this.wateringHole.getCards().size() == this.players.size()) {
            throw new InvalidDiscardToWateringHoleException("You can not discard more cards to the watering hole " +
                    "than the number of players.");
        }
        if (index > this.players.size() - 1) {
            throw new InvalidPlayerSelectException("The given player index is greater than the number of players.");
        }
        if (!this.players.get(index).getCards().contains(card)) {
            throw new IllegalCardDiscardException("Selected card is not in this players hand.");
        }
        this.wateringHole.addCard(card);
        this.players.get(index).removeCardFromHand(card);
    }

    /**
     * Increases the population of the species with the given index
     * for the player with the given index
     *
     * @param playerIndex  index of the player
     * @param speciesIndex index of the species
     * @param card         the card to remove from the player's hand
     * @throws SpeciesPopulationException   propagated from {@link Species#increasePopulation()}
     * @throws IllegalCardDiscardException  thrown when the given card is not in the specified
     *                                      player's hand
     * @throws InvalidPlayerSelectException thrown when the given player index is greater than the number of players
     * @throws IllegalSpeciesIndexException thrown when the given species index is greater than the number of species
     *                                      for the given player
     * @throws IllegalCardRemovalException  propagated from {@link IPlayer#removeCardFromHand(ICard)}
     * @throws NullGameObjectException      propagated from {@link IPlayer#removeCardFromHand(ICard)}
     */
    public void increasePopulation(int playerIndex, int speciesIndex, ICard card) throws SpeciesPopulationException,
            IllegalCardDiscardException, InvalidPlayerSelectException,
            IllegalSpeciesIndexException, IllegalCardRemovalException, NullGameObjectException {
        if (playerIndex > this.players.size() - 1) {
            throw new InvalidPlayerSelectException("The given player index is greater than the number of players.");
        }
        if (!this.players.get(playerIndex).getCards().contains(card)) {
            throw new IllegalCardDiscardException("Selected card is not in this player's hand.");
        }
        if (speciesIndex > this.players.get(playerIndex).getSpecies().size() - 1) {
            throw new IllegalSpeciesIndexException("The given species index is greater than the number of species for" +
                    " player " + playerIndex + 1);
        }
        this.players.get(playerIndex).getSpecies().get(speciesIndex).increasePopulation();
        this.discardFromPlayer(playerIndex, card);
    }

    /**
     * Increases the body size of the species with the given index
     * for the player with the given index
     *
     * @param playerIndex  index of the player
     * @param speciesIndex index of the species
     * @param card         the card to remove from the player's hand
     * @throws SpeciesBodySizeException     propagated from {@link Species#increaseBodySize()}
     * @throws IllegalCardDiscardException  thrown when the given card is not in the specified
     *                                      player's hand
     * @throws InvalidPlayerSelectException thrown when the given player index is greater than the number of players
     * @throws IllegalSpeciesIndexException thrown when the given species index is greater than the number of species
     *                                      for the given player
     * @throws NullGameObjectException      propagated from {@link #discardPile}
     * @throws IllegalCardRemovalException  propagated from {@link #discardFromPlayer(int, ICard)}
     */
    public void increaseBodySize(int playerIndex, int speciesIndex, ICard card) throws SpeciesBodySizeException,
            InvalidPlayerSelectException, IllegalCardDiscardException, IllegalSpeciesIndexException, NullGameObjectException, IllegalCardRemovalException {
        if (playerIndex > this.players.size() - 1) {
            throw new InvalidPlayerSelectException("The given player index is greater than the number of players.");
        }
        if (!this.players.get(playerIndex).getCards().contains(card)) {
            throw new IllegalCardDiscardException("Selected card is not in this players hand.");
        }
        if (speciesIndex > this.players.get(playerIndex).getSpecies().size() - 1) {
            throw new IllegalSpeciesIndexException("The given species index is greater than the number of species for" +
                    " player " + playerIndex + 1);
        }
        this.discardFromPlayer(playerIndex, card);
        this.players.get(playerIndex).getSpecies().get(speciesIndex).increaseBodySize();
    }

    /**
     * Adds a given species to the left of a player's Species
     *
     * @param playerIndex position in player list of player to add to
     * @param card        Card from player's hand that is being discarded
     * @param species     Species being added to player
     * @throws InvalidPlayerSelectException thrown when the given player index is greater than the number of players
     * @throws IllegalCardDiscardException  thrown when the given card is not in the specified
     *                                      player's hand
     * @throws NullGameObjectException      propagated from {@link Deck#discard(Object)}
     * @throws IllegalCardRemovalException  propagated from {@link #discardFromPlayer(int, ICard)}
     */
    public void discardForLeftSpecies(int playerIndex, ICard card, ISpecies species) throws
            InvalidPlayerSelectException, IllegalCardDiscardException, NullGameObjectException, IllegalCardRemovalException {
        if (card == null) {
            throw new NullGameObjectException("Unable to discard a null card");
        }
        if (species == null) {
            throw new NullGameObjectException("Unable to add a null species");
        }
        if (playerIndex > this.players.size() - 1) {
            throw new InvalidPlayerSelectException("The given player index is greater than the number of players.");
        }
        if (!this.players.get(playerIndex).getCards().contains(card)) {
            throw new IllegalCardDiscardException("Selected card is not in this players hand.");
        }
        discardFromPlayer(playerIndex, card);
        this.players.get(playerIndex).addSpeciesLeft(species);
    }

    /**
     * Adds a given species to the right of a player's Species
     *
     * @param playerIndex position in player list of player to add to
     * @param card        Card from player's hand that is being discarded
     * @param species     Species being added to player
     * @throws InvalidPlayerSelectException thrown when the given player index is greater than the number of players
     * @throws IllegalCardDiscardException  thrown when the given card is not in the specified
     *                                      player's hand
     * @throws NullGameObjectException      propagated from {@link Deck#discard(Object)}
     * @throws IllegalCardRemovalException  propagated from {@link #discardFromPlayer(int, ICard)}
     */
    public void discardForRightSpecies(int playerIndex, ICard card, ISpecies species) throws
            InvalidPlayerSelectException, IllegalCardDiscardException, NullGameObjectException,
            IllegalCardRemovalException {
        if (card == null) {
            throw new NullGameObjectException("Unable to discard a null card");
        }
        if (species == null) {
            throw new NullGameObjectException("Unable to add a null species");
        }
        if (playerIndex > this.players.size() - 1) {
            throw new InvalidPlayerSelectException("The given player index is greater than the number of players.");
        }
        if (!this.players.get(playerIndex).getCards().contains(card)) {
            throw new IllegalCardDiscardException("Selected card is not in this players hand.");
        }
        discardFromPlayer(playerIndex, card);
        this.players.get(playerIndex).addSpeciesRight(species);
    }

    /**
     * Adds the provided Card to a player's species
     *
     * @param playerIndex  position in player list of player to add to
     * @param speciesIndex position in species list of species to add to
     * @param card         Card being added to species
     * @throws SpeciesNumberTraitsException   propagated from {@link ISpecies#addTrait(ICard)}
     * @throws SpeciesDuplicateTraitException propagated from {@link ISpecies#addTrait(ICard)}
     * @throws InvalidPlayerSelectException   when the provided player index is not in [0, numPlayers)
     * @throws IllegalSpeciesIndexException   when the provided species index is not in [0, numSpecies)
     * @throws NullGameObjectException        when the provided Card is NULL
     * @throws IllegalCardRemovalException    propagated from {@link IPlayer#removeCardFromHand(ICard)}
     */
    public void addTraitToSpecies(int playerIndex, int speciesIndex, ICard card) throws SpeciesNumberTraitsException,
            SpeciesDuplicateTraitException, InvalidPlayerSelectException, IllegalSpeciesIndexException,
            NullGameObjectException, IllegalCardRemovalException {
        if (card == null) {
            throw new NullGameObjectException("The given Card object cannot be NULL");
        }
        if (playerIndex < 0 || playerIndex >= this.players.size()) {
            throw new InvalidPlayerSelectException("The given player index must be within [0,numPlayers)");
        }
        if (speciesIndex < 0 || speciesIndex >= this.players.get(playerIndex).getSpecies().size()) {
            throw new IllegalSpeciesIndexException("The given species index must be within [0, numSpecies)");
        }
        this.players.get(playerIndex).removeCardFromHand(card);
        this.players.get(playerIndex).getSpecies().get(speciesIndex).addTrait(card);

    }

    /**
     * Removes the given trait from the given species from the given player and puts the card on the discard pile
     *
     * @param playerIndex  Index of the player in the player list
     * @param speciesIndex Index of the speices for the player
     * @param traitCard    Card representing the trait to remove
     * @throws SpeciesTraitNotFoundException propagated from {@link ISpecies#removeTrait(ICard)}
     * @throws NullGameObjectException       if the trait card passed in is null
     * @throws InvalidPlayerSelectException  if the player index is not in the valid range
     * @throws IllegalSpeciesIndexException  if the species index is not in the valid range
     */
    public void removeTraitFromSpecies(int playerIndex, int speciesIndex, ICard traitCard) throws
            SpeciesTraitNotFoundException, InvalidPlayerSelectException, IllegalSpeciesIndexException,
            NullGameObjectException {
        if (traitCard == null) {
            throw new NullGameObjectException("Trait card is null!");
        } else if (this.players.size() <= playerIndex || playerIndex < 0) {
            throw new InvalidPlayerSelectException("Player index is out of range!");
        } else if (speciesIndex < 0 || speciesIndex >= this.players.get(playerIndex).getSpecies().size()) {
            throw new IllegalSpeciesIndexException("Species index is out of range!");
        }

        ICard removedCard = this.players.get(playerIndex).getSpecies().get(speciesIndex).removeTrait(traitCard);
        this.getDiscardPile().discard(removedCard);
    }

    /**
     * Returns a list of the traits for a given player's species
     *
     * @param playerIndex  the index of the player
     * @param speciesIndex the index of the species
     * @return an ArrayList of trait cards for this species
     * @throws InvalidPlayerSelectException if the player index is not in the valid range
     * @throws IllegalSpeciesIndexException if the species index is not in the valid range
     */
    public ArrayList<ICard> getTraits(int playerIndex, int speciesIndex) throws InvalidPlayerSelectException,
            IllegalSpeciesIndexException {
        if (this.players.size() <= playerIndex || playerIndex < 0) {
            throw new InvalidPlayerSelectException("Player index is out of range!");
        } else if (speciesIndex < 0 || speciesIndex >= this.players.get(playerIndex).getSpecies().size()) {
            throw new IllegalSpeciesIndexException("Species index is out of range!");
        }
        return this.players.get(playerIndex).getSpecies().get(speciesIndex).getTraits();
    }

    /**
     * Feeds the player's species
     *
     * @param playerIndex  Index of the player in the player list
     * @param speciesIndex Index of the species for the player
     * @throws InvalidPlayerSelectException if the player index is not in the valid range
     * @throws IllegalSpeciesIndexException if the species index is not in the valid range
     * @throws SpeciesFullException         if the species' eaten is equal to species' population
     * @throws WateringHoleEmptyException   if the food count in the watering hole is 0
     * @throws SpeciesPopulationException   propagates from {@link Fertile#executeTrait(int[], int[])}
     * @throws FoodBankEmptyException       propagates from {@link LongNeck#executeTrait(int[], int[])}
     */
    public void feedPlayerSpecies(int playerIndex, int speciesIndex) throws InvalidPlayerSelectException,
            IllegalSpeciesIndexException, WateringHoleEmptyException, SpeciesFullException, SpeciesPopulationException,
            FoodBankEmptyException {
        if (this.players.size() <= playerIndex || playerIndex < 0) {
            throw new InvalidPlayerSelectException("Player index is out of range!");
        } else if (speciesIndex < 0 || speciesIndex >= this.players.get(playerIndex).getSpecies().size()) {
            throw new IllegalSpeciesIndexException("Species index is out of range!");
        } else if (this.wateringHole.getFoodCount() == 0) {
            throw new WateringHoleEmptyException("Cannot feed. Watering hole is empty.");
        }

        this.wateringHole.removeFood();
        if (this.wateringHole.getFoodCount() == 0 && this.foodBank == 0) this.over = true;
        this.players.get(playerIndex).getSpecies().get(speciesIndex).eat();
        for (ICard c : this.players.get(playerIndex).getSpecies().get(speciesIndex).getTraits()) {
            String name = c.getName();
            if (feedTraitActions.containsKey(name)) {
                feedTraitActions.get(name).executeTrait(new int[]{playerIndex, 0}, new int[]{speciesIndex, 0});
            }
        }
    }

    /**
     * Returns the amount of food eaten by the given species of the given player during this round
     *
     * @param playerIndex  the player index
     * @param speciesIndex the species index
     * @return the amount of food eaten
     * @throws InvalidPlayerSelectException if the player index is not in the valid range
     * @throws IllegalSpeciesIndexException if the species index is not in the valid range
     */
    public int getSpeciesFood(int playerIndex, int speciesIndex) throws InvalidPlayerSelectException,
            IllegalSpeciesIndexException {
        if (this.players.size() <= playerIndex || playerIndex < 0) {
            throw new InvalidPlayerSelectException("Player index is out of range!");
        } else if (speciesIndex < 0 || speciesIndex >= this.players.get(playerIndex).getSpecies().size()) {
            throw new IllegalSpeciesIndexException("Species index is out of range!");
        }
        return this.players.get(playerIndex).getSpecies().get(speciesIndex).getEatenFood();
    }

    /**
     * Determines whether or not every species for each player has eaten enough food
     *
     * @return players.species.isFull() ? true : false
     */
    public boolean allFull() {
        for (IPlayer p : this.players) {
            if (!p.allSpeciesFull()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Increases a species population without discarding a card
     *
     * @param playerIndex  the player index
     * @param speciesIndex the species index
     * @throws SpeciesPopulationException   {@link Species#increasePopulation()}
     * @throws InvalidPlayerSelectException if the playerIndex is out of bounds
     * @throws IllegalSpeciesIndexException if the Species index is out of bounds
     */
    public void increasePopulation(int playerIndex, int speciesIndex)
            throws SpeciesPopulationException, InvalidPlayerSelectException, IllegalSpeciesIndexException {
        if (playerIndex >= this.players.size() || playerIndex < 0) {
            throw new InvalidPlayerSelectException("Player index is out of range!");
        }
        ArrayList<ISpecies> playerSpecies = this.players.get(playerIndex).getSpecies();
        if (speciesIndex >= playerSpecies.size() || speciesIndex < 0) {
            throw new IllegalSpeciesIndexException("Species index is out of range!");
        }
        playerSpecies.get(speciesIndex).increasePopulation();
    }

    /**
     * Feeds species from the food bank instead of the watering hold
     *
     * @param playerIndex  the player index
     * @param speciesIndex the species index
     * @throws SpeciesFullException         propagated from {@link Species#eat()}
     * @throws FoodBankEmptyException       if attempting to eat from an empty food bank
     * @throws InvalidPlayerSelectException if the playerIndex is out of bounds
     * @throws IllegalSpeciesIndexException if the speciesIndex is out of bounds
     */
    public void feedPlayerSpeciesFromBank(int playerIndex, int speciesIndex)
            throws SpeciesFullException, FoodBankEmptyException, InvalidPlayerSelectException, IllegalSpeciesIndexException {
        if (this.foodBank == 0) {
            throw new FoodBankEmptyException("The food bank hs been depleted!");
        }
        if (this.players.size() <= playerIndex || playerIndex < 0) {
            throw new InvalidPlayerSelectException("Player index is out of range!");
        }
        if (speciesIndex < 0 || speciesIndex >= this.players.get(playerIndex).getSpecies().size()) {
            throw new IllegalSpeciesIndexException("Species index is out of range!");
        }
        this.players.get(playerIndex).getSpecies().get(speciesIndex).eat();
        this.foodBank--;
    }

    /**
     * Computes and returns the player number for whoever the first player of a round is. One-indexed
     *
     * @return firstPlayer
     */
    public int getFirstPlayer() {
        int firstPlayer = this.round % this.players.size();
        return firstPlayer == 0 ? this.players.size() : firstPlayer;
    }

    /**
     * Attacks another species
     *
     * @param playerIndex1  The index of the player attacking
     * @param speciesIndex1 The index of the species attacking
     * @param playerIndex2  The index of the player under attack
     * @param speciesIndex2 The index of the species under attack
     * @throws NonCarnivoreAttacking        thrown if the species attacking is not a carnivore
     * @throws BodySizeIllegalAttack        thrown if the species attacking does not have a higher body size than the species under attack
     * @throws AttackingSelfException       thrown if the species attacking is attacking itself
     * @throws SpeciesPopulationException   propagated from {@link Species#decreasePopulation()}
     * @throws FoodBankEmptyException       propagated from {@link Game#feedPlayerSpeciesFromBank(int, int)}
     * @throws InvalidPlayerSelectException propagated from {@link Game#feedPlayerSpeciesFromBank(int, int)}
     * @throws SpeciesFullException         propagated from {@link Species#eat()}
     * @throws IllegalSpeciesIndexException propagated from {@link Game#feedPlayerSpeciesFromBank(int, int)}
     * @throws InvalidAttackException       thrown if the species attacking does not have climbing, but the species under attack does
     */
    public void attackSpecies(int playerIndex1, int speciesIndex1, int playerIndex2, int speciesIndex2)
            throws NonCarnivoreAttacking, BodySizeIllegalAttack, SpeciesPopulationException, FoodBankEmptyException,
            InvalidPlayerSelectException, SpeciesFullException, IllegalSpeciesIndexException, AttackingSelfException,
            InvalidAttackException {
        if (playerIndex1 == playerIndex2 && speciesIndex1 == speciesIndex2) {
            throw new AttackingSelfException("You can not attack yourself");
        } else if (this.getPlayerObjects().get(playerIndex1).getSpecies().get(speciesIndex1).getTraits().stream().filter(c -> c.getName().equals("Carnivore")).count() < 1) {
            throw new NonCarnivoreAttacking("You must be a carnivore to attack");
        } else if (this.getPlayerObjects().get(playerIndex1).getSpecies().get(speciesIndex1).getBodySize() <= this.getPlayerObjects().get(playerIndex2).getSpecies().get(speciesIndex2).getBodySize()) {
            throw new BodySizeIllegalAttack("You must have a higher body size than the species which you are attacking");
        } else if (!this.getPlayerObjects().get(playerIndex1).getSpecies().get(speciesIndex1).getTraits().stream().anyMatch(c -> c.getName().equals("Climbing"))
                && this.getPlayerObjects().get(playerIndex2).getSpecies().get(speciesIndex2).getTraits().stream().anyMatch(c -> c.getName().equals("Climbing"))) {
            throw new InvalidAttackException("You must have climbing to attack this species");
        }
        IPlayer player1 = this.getPlayerObjects().get(playerIndex1);
        IPlayer player2 = this.getPlayerObjects().get(playerIndex2);
        ISpecies species1 = player1.getSpecies().get(speciesIndex1);
        ISpecies species2 = player2.getSpecies().get(speciesIndex2);
        species2.decreasePopulation();
        for (int i = 0; i < species2.getBodySize(); i++) {
            if (species1.getEatenFood() < species1.getPopulation()) {
                this.feedPlayerSpeciesFromBank(playerIndex1, speciesIndex1);
            }
        }
        for (int i = 0; i < this.players.size(); i++) {
            for (int j = 0; j < this.players.get(i).getSpecies().size(); j++) {
                if (this.players.get(i).getSpecies().get(j).getTraits().parallelStream().anyMatch(c ->
                        c.getName().equals("Scavenger"))) {
                    feedPlayerSpeciesFromBank(i, j);
                }
            }
        }
    }

    /**
     * Returns the flag signaling whether or not the game is ready to end
     *
     * @return over flag
     */
    public boolean isOver() {
        return this.over;
    }

    /**
     * Retrieves indices of all species which can successfully be attacked by a Carnivore species.
     *
     * @param attackingPlayer  index of player that is searching for attackable species
     * @param attackingSpecies index of species that will be attacking others
     * @return list of attackable index pair
     */
    public ArrayList<int[]> getAttackableSpecies(int attackingPlayer, int attackingSpecies) {
        /*
        Can just return a null array rather than throw an error.
        -climbing
        -warning call -> ambush
        -symbiosis
        -hard shell
        -defensive herding
        TODO: Implement check for prohibitive traits.
            1. add a new method, 'canBeAttacked' to atraits.
            2. each species has a 'canbeattacked' temp bool assigned to it. put this temp just inside the j loop
            3. attackingSpecies body size > player i species j body size, then
                - loop through all traits and run 'canBeAttacked' to them.
                - if 'canBeAttacked' returns true, we add it to the list
         */
        ArrayList<int[]> attackable = new ArrayList<>();
        boolean hasCarnivore = false;
        int carnivoreAttackingBodySize = this.players.get(attackingPlayer).getSpecies().get(attackingSpecies)
                .getBodySize();
        ArrayList<ICard> traits = this.players.get(attackingPlayer).getSpecies().get(attackingSpecies).getTraits();
        for (ICard c : traits) {
            if (c.getName().equals("Carnivore")) hasCarnivore = true;
            if (c.getName().equals("Pack Hunting"))
                carnivoreAttackingBodySize += this.players.get(attackingPlayer).getSpecies().get(attackingSpecies)
                        .getPopulation();
        }
        if (hasCarnivore) {
            for (int i = 0; i < this.players.size(); i++) {
                for (int j = 0; j < this.players.get(i).getSpecies().size(); j++) {
                    HashSet<Boolean> canBeAttacked = new HashSet<>();
                    if (i != attackingPlayer || j != attackingSpecies) {
                        if (this.players.get(i).getSpecies().get(j).getBodySize() <
                                carnivoreAttackingBodySize
                                && !(!this.getPlayerObjects().get(attackingPlayer).getSpecies().get(attackingSpecies)
                                .getTraits().stream().anyMatch(c -> c.getName().equals("Climbing"))
                                && this.getPlayerObjects().get(i).getSpecies().get(j).getTraits().stream()
                                .anyMatch(c -> c.getName().equals("Climbing")))) {
                            ArrayList<ICard> attackeeTraits = this.players.get(i).getSpecies().get(j).getTraits();
                            if ((j - 1) >= 0 && (j - 1) < this.players.get(i).getSpecies().size()) {
                                ArrayList<ICard> attackeeTraitsL = this.players.get(i).getSpecies().get(j - 1).getTraits();
                                for (ICard c : attackeeTraitsL) {
                                    if (c.getName().equals("Warning Call")) {
                                        canBeAttacked.add(this.defendTraitActions.get(c.getName())
                                                .canBeAttacked(new int[]{i, attackingPlayer},
                                                        new int[]{j, attackingSpecies}));
                                    }
                                }
                            }
                            if ((j + 1) >= 0 && (j + 1) < this.players.get(i).getSpecies().size()) {
                                ArrayList<ICard> attackeeTraitsR = this.players.get(i).getSpecies().get(j + 1).getTraits();
                                for (ICard c : attackeeTraitsR) {
                                    if (c.getName().equals("Warning Call")) {
                                        canBeAttacked.add(this.defendTraitActions.get(c.getName())
                                                .canBeAttacked(new int[]{i, attackingPlayer},
                                                        new int[]{j, attackingSpecies}));
                                    }
                                }
                            }
                            for (ICard c : attackeeTraits) {
                                if (this.defendTraitActions.containsKey(c.getName())) {
                                    canBeAttacked.add(this.defendTraitActions.get(c.getName())
                                            .canBeAttacked(new int[]{i, attackingPlayer},
                                                    new int[]{j, attackingSpecies}));
                                }
                            }
                            if (!canBeAttacked.contains(false) || canBeAttacked.size() == 0) {
                                attackable.add(new int[]{i, j});
                            }

                        }
                    }
                }
            }
        }
        return attackable;
    }

    /**
     * @param playerIndex  The index of the player that is eating onto fat tissue
     * @param speciesIndex The index of the species that is eating onto fat tissue
     * @throws InvalidPlayerSelectException if the player index is out of range
     * @throws IllegalSpeciesIndexException if the species index is out of range
     * @throws WateringHoleEmptyException   propagated from {@link FatTissue#executeTrait(int[], int[], ICard)}
     * @throws SpeciesPopulationException   propagated from {@link FatTissue#executeTrait(int[], int[], ICard)}
     * @throws TempFoodMaxException         propagated from {@link FatTissue#executeTrait(int[], int[], ICard)}
     */
    public void fatTissueEat(int playerIndex, int speciesIndex) throws InvalidPlayerSelectException, IllegalSpeciesIndexException, WateringHoleEmptyException, SpeciesPopulationException, TempFoodMaxException {
        if (this.players.size() <= playerIndex || playerIndex < 0) {
            throw new InvalidPlayerSelectException("Player index is out of range!");
        } else if (speciesIndex < 0 || speciesIndex >= this.players.get(playerIndex).getSpecies().size()) {
            throw new IllegalSpeciesIndexException("Species index is out of range!");
        }
        FatTissue fatTissue = new FatTissue(this);
        fatTissue.executeTrait(new int[]{playerIndex, playerIndex}, new int[]{speciesIndex, speciesIndex}, null);
    }

    /**
     * Returns the first person who has the most food in their food bag.
     *
     * @return playerIndex
     */
    public int getWinner() {
        int winner = 0;
        for(int i=0; i<this.players.size(); i++){
            if(this.players.get(i).getFoodBag() > winner) {
                winner = i;
            }
        }
        return winner;
    }
}
