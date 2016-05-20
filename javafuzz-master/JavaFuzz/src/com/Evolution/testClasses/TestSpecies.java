package com.Evolution.testClasses;

import com.Evolution.interfaces.ICard;
import com.Evolution.interfaces.ISpecies;

import java.util.ArrayList;

/**
 * Test version of Species that is used for testing
 * when a full impolementation of Species is not needed
 * Created by goistjt on 3/22/2016.
 */
public class TestSpecies implements ISpecies {

    @Override
    public int getBodySize() {
        return 0;
    }

    @Override
    public int getPopulation() {
        return 0;
    }

    @Override
    public void increasePopulation() {

    }

    @Override
    public void increaseBodySize() {

    }

    @Override
    public void decreasePopulation() {

    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void addTrait(ICard c) {

    }

    @Override
    public ArrayList<ICard> getTraits() {
        return null;
    }

    @Override
    public ICard removeTrait(ICard c) {
        return null;
    }

    @Override
    public void eat() {

    }

    @Override
    public void resetEatenFood() {

    }

    @Override
    public int getEatenFood() {
        return 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public int getTempFood() {
        return 0;
    }

    @Override
    public void eatTemp() {

    }

    @Override
    public void resetEatenTempFood() {

    }
}
