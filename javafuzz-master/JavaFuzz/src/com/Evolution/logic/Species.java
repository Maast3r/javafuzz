package com.Evolution.logic;

import com.Evolution.exceptions.*;
import com.Evolution.interfaces.ICard;
import com.Evolution.interfaces.ISpecies;

import java.util.ArrayList;

/**
 * Logic class for handling the logic behind Species during gameplay
 * Created by goistjt on 3/21/2016.
 */
public class Species implements ISpecies {
    private int bodySize;
    private int population;
    private ArrayList<ICard> traits = new ArrayList<>();
    private int eatenFood;
    private int tempFood;

    /**
     * Creates a species defaulted with body size and population equal to 0.
     */
    public Species() {
        this.bodySize = 1;
        this.population = 1;
        this.eatenFood = 0;

    }

    @Override
    public int getBodySize() {
        return isFull() && this.traits.parallelStream().anyMatch(c -> c.getName().equals("Burrowing"))?
                this.bodySize + 12 : this.traits.parallelStream().anyMatch(c -> c.getName().equals("Hard Shell")) ?
                this.bodySize + 4 : this.bodySize;
    }

    @Override
    public int getPopulation() {
        return this.population;
    }

    @Override
    public void increasePopulation() throws SpeciesPopulationException {
        if (this.population == 6) {
            throw new SpeciesPopulationException("Your species population is 6.\n");
        }
        this.population++;
    }

    @Override
    public void decreasePopulation() throws SpeciesPopulationException {
        if (this.population == 0) {
            throw new SpeciesPopulationException("Your species population is 0.\n");
        }
        this.population--;
    }

    @Override
    public void increaseBodySize() throws SpeciesBodySizeException {
        if (this.bodySize == 6) {
            throw new SpeciesBodySizeException("Your species body size is 6.\n");
        }
        this.bodySize++;
    }

    @Override
    public boolean isDead() {
        return this.population == 0;
    }

    @Override
    public void addTrait(ICard c) throws SpeciesNumberTraitsException, SpeciesDuplicateTraitException, NullGameObjectException {
        if (c == null) {
            throw new NullGameObjectException("The provided ICard must not be null");
        }
        if (this.getTraits().size() == 3) {
            throw new SpeciesNumberTraitsException("Too many traits");
        } else if (this.getTraits().stream().filter(t -> t.getName().equals(c.getName())).count() > 0) {
            throw new SpeciesDuplicateTraitException("Duplicate trait tried to be added");
        }
        this.traits.add(c);
    }

    @Override
    public ICard removeTrait(ICard c) throws SpeciesTraitNotFoundException, NullGameObjectException {
        if (c == null) {
            throw new NullGameObjectException("Cannot remove a null Card from a species");
        }
        if (!(this.getTraits().stream().filter(t -> t.getName().equals(c.getName())).count() > 0)) {
            throw new SpeciesTraitNotFoundException("The trait can't be removed as it is not a trait of the species");
        }
        ICard cardToRemove = this.traits.stream().filter(t -> t.getName().equals(c.getName())).iterator().next();
        this.traits.remove(cardToRemove);
        return cardToRemove;
    }

    @Override
    public void eat() throws SpeciesFullException {
        if (this.isFull()) {
            throw new SpeciesFullException("This species' population has already been fed.");
        }
        this.eatenFood++;
    }

    @Override
    public void resetEatenFood() {
        this.eatenFood = 0;
    }

    @Override
    public int getEatenFood() {
        return this.eatenFood;
    }

    @Override
    public boolean isFull() {
        return (this.eatenFood == this.population);
    }

    @Override
    public ArrayList<ICard> getTraits() {
        return this.traits;
    }

    @Override
    public int getTempFood() {
        return this.tempFood;
    }

    @Override
    public void eatTemp() {
        this.tempFood = this.bodySize;
    }

    @Override
    public void resetEatenTempFood() {
        this.tempFood = 0;
    }
}
