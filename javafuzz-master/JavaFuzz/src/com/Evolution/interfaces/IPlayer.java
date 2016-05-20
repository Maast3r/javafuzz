package com.Evolution.interfaces;

import com.Evolution.exceptions.IllegalCardRemovalException;
import com.Evolution.exceptions.InvalidPlayerSpeciesRemovalException;
import com.Evolution.exceptions.NullGameObjectException;

import java.util.ArrayList;

/**
 * Interface for Players
 * Created by goistjt on 3/22/2016.
 */
public interface IPlayer {

    /**
     * Add the provided species to the right of the list of species
     * held by the player
     *
     * @param species to add
     * @throws NullGameObjectException if the ISpecies is null
     */
    void addSpeciesRight(ISpecies species) throws NullGameObjectException;

    /**
     * Add the provided species to the left of the list of species
     * held by the player
     *
     * @param species to add
     * @throws NullGameObjectException if the ISpecies is null
     */
    void addSpeciesLeft(ISpecies species) throws NullGameObjectException;

    /**
     * Returns the list of all species currently held by the player
     * in order left -> right
     *
     * @return speciesList
     */
    ArrayList<ISpecies> getSpecies();

    /**
     * Removes the species at the specified index from the player's
     * species list
     *
     * @param i index of species to remove
     * @throws InvalidPlayerSpeciesRemovalException if the index i is not within [0, size)
     *                                              of {@link IPlayer#getSpecies()}
     */
    void removeSpecies(int i) throws InvalidPlayerSpeciesRemovalException;

    /**
     * Returns the list of cards in the player's hand
     *
     * @return The list of cards in the player's hand
     */
    ArrayList<ICard> getCards();

    /**
     * Adds a card to a player's hand
     *
     * @param card The card to add
     * @throws NullGameObjectException if a null ICard is passed in
     */
    void addCardToHand(ICard card) throws NullGameObjectException;

    /**
     * Removes the given card from the players hand
     *
     * @param card the card to remove
     * @throws IllegalCardRemovalException if the Card is not contained in the Player's hand
     * @throws NullGameObjectException     if the Card is null
     */
    void removeCardFromHand(ICard card) throws IllegalCardRemovalException, NullGameObjectException;

    /**
     * moves the food from each of the player's species to their food bag
     */
    void moveFoodToFoodBag();

    /**
     * returns the amount of food in this player's food bag
     */
    int getFoodBag();

    /**
     * Returns is each species held by the player is full
     *
     * @return full ? true : false
     */
    boolean allSpeciesFull();
}
