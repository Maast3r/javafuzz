package com.Evolution.testClasses;

import com.Evolution.interfaces.ICard;
import com.Evolution.interfaces.IPlayer;
import com.Evolution.interfaces.ISpecies;

import java.util.ArrayList;

/**
 * Test class for use in unit tests that
 * do not require full player implementations
 * Created by goistjt on 3/22/2016.
 */
public class TestPlayer implements IPlayer {
    private ArrayList<ISpecies> species;

    public TestPlayer(ISpecies species) {
        this.species = new ArrayList<>();
        this.species.add(species);
    }

    @Override
    public void addSpeciesRight(ISpecies species) {

    }

    @Override
    public void addSpeciesLeft(ISpecies species) {

    }

    @Override
    public ArrayList<ISpecies> getSpecies() {
        return null;
    }

    @Override
    public void removeSpecies(int i) {

    }

    @Override
    public ArrayList<ICard> getCards() {
        return null;
    }

    @Override
    public void addCardToHand(ICard card) {}

    @Override
    public void removeCardFromHand(ICard card) {
    }

    @Override
    public void moveFoodToFoodBag() {

    }

    @Override
    public int getFoodBag() {
        return 0;
    }

    @Override
    public boolean allSpeciesFull() {
        return false;
    }
}
