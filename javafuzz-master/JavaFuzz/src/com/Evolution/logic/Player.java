package com.Evolution.logic;

import com.Evolution.exceptions.IllegalCardRemovalException;
import com.Evolution.exceptions.InvalidPlayerSpeciesRemovalException;
import com.Evolution.exceptions.NullGameObjectException;
import com.Evolution.interfaces.ICard;
import com.Evolution.interfaces.IPlayer;
import com.Evolution.interfaces.ISpecies;

import java.util.ArrayList;

/**
 * Logic class handling all logic for Players of the game
 * Created by goistjt on 3/22/2016.
 */
public class Player implements IPlayer {
    private ArrayList<ISpecies> speciesList = new ArrayList<>();
    private ArrayList<ICard> cardList = new ArrayList<>();
    private int foodBag = 0;

    /**
     * Creates a new player object with an initial {@link ISpecies}
     *
     * @param species initial ISpecies a Player starts with
     * @throws NullGameObjectException if the ISpecies is null
     */
    public Player(ISpecies species) throws NullGameObjectException {
        if (species == null) {
            throw new NullGameObjectException("A player cannot be initialized with a null species");
        }
        speciesList.add(species);
    }

    public void addSpeciesRight(ISpecies species) throws NullGameObjectException {
        if (species == null) {
            throw new NullGameObjectException("Cannot add a null species to Player");
        }
        this.speciesList.add(species);
    }

    @Override
    public void addSpeciesLeft(ISpecies species) throws NullGameObjectException {
        if (species == null) {
            throw new NullGameObjectException("Cannot add a null species to Player");
        }
        this.speciesList.add(0, species);
    }

    public ArrayList<ISpecies> getSpecies() {
        return this.speciesList;
    }

    public void removeSpecies(int i) throws InvalidPlayerSpeciesRemovalException {
        if (speciesList.size() == 0 ||
                i < 0 || i >= speciesList.size()) {
            throw new InvalidPlayerSpeciesRemovalException("Removal Species index is wrong.\n");
        } else {
            this.speciesList.remove(i);
        }
    }

    public ArrayList<ICard> getCards() {
        return this.cardList;
    }

    @Override
    public void addCardToHand(ICard card) throws NullGameObjectException {
        if (card == null) {
            throw new NullGameObjectException("Unable to add a null Card to player hand");
        }
        this.cardList.add(card);
    }

    @Override
    public void removeCardFromHand(ICard card) throws IllegalCardRemovalException, NullGameObjectException {
        if (card == null) {
            throw new NullGameObjectException("Unable to remove a null Card from Player");
        }
        if (!this.cardList.remove(card)) {
            throw new IllegalCardRemovalException("Card not contained in hand!");
        }
    }

    @Override
    public void moveFoodToFoodBag() {
        for (ISpecies s : this.speciesList) {
            this.foodBag += s.getEatenFood();
            s.resetEatenFood();
        }
    }

    @Override
    public int getFoodBag() {
        return this.foodBag;
    }

    @Override
    public boolean allSpeciesFull() {
        for(ISpecies s : this.speciesList) {
            if(!s.isFull()) {
                return false;
            }
        }
        return true;
    }
}
