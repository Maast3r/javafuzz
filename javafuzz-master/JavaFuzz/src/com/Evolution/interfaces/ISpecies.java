package com.Evolution.interfaces;

import com.Evolution.exceptions.*;

import java.util.ArrayList;

/**
 * Interface for Species
 * Created by goistjt on 3/22/2016.
 */
public interface ISpecies {

    /**
     * Returns the current body size of the species
     *
     * @return bodySize
     */
    int getBodySize();

    /**
     * Returns the current population of the species
     *
     * @return population
     */
    int getPopulation();

    /**
     * Increases the population size of the species up to a maximum of 6
     *
     * @throws SpeciesPopulationException when the {@link ISpecies#getPopulation()} is already at 6
     */
    void increasePopulation() throws SpeciesPopulationException;

    /**
     * Increases the body size of the species up to a maximum of 6
     *
     * @throws SpeciesBodySizeException when the {@link ISpecies#getBodySize()} is already at 6
     */
    void increaseBodySize() throws SpeciesBodySizeException;

    /**
     * Decreases the population size of the species down to a minimum of 0
     *
     * @throws SpeciesPopulationException when the {@link ISpecies#getPopulation()} is already at 0
     */
    void decreasePopulation() throws SpeciesPopulationException;

    /**
     * Returns whether or not this species has at least 1 population
     *
     * @return isDead
     */
    boolean isDead();

    /**
     * Adds the given card as a trait on the species
     *
     * @param c The card which to add as a trait
     * @throws SpeciesNumberTraitsException   when the {@link ISpecies#getTraits()} size is already at 3
     * @throws SpeciesDuplicateTraitException when the {@link ISpecies#getTraits()} already contains the trait
     * @throws NullGameObjectException        when the provided ICard is null
     */
    void addTrait(ICard c) throws SpeciesNumberTraitsException, SpeciesDuplicateTraitException, NullGameObjectException;

    /**
     * Gets the list of all traits on the species
     *
     * @return The list of traits
     */
    ArrayList<ICard> getTraits();

    /**
     * Removes the given trait from the species
     *
     * @param c the trait to remove
     * @throws SpeciesTraitNotFoundException when the {@link ISpecies#getTraits()} does not contain the trait
     * @throws NullGameObjectException       when the provided ICard is null
     */
    ICard removeTrait(ICard c) throws SpeciesTraitNotFoundException, NullGameObjectException;

    /**
     * This species eats one food, increasing its eatenFood count
     * @throws SpeciesFullException when the player tries to eat more food when the population is already
     *  fed (eatenFood = population)
     */
    void eat() throws SpeciesFullException;

    /**
     * resets the eatenFood variable to 0
     */
    void resetEatenFood();

    /**
     * returns the amount of food this species has eaten this round
     * @return eatenFood
     */
    int getEatenFood();

    /**
     * Returns whether or not the species entire population is fed
     * @return if the species is full
     */
    boolean isFull();

    /**
     * Returns the number of temp food
     * @return tempFood
     */
    int getTempFood();

    /**
     * Increment tempFood by 1
     */
    void eatTemp();

    /**
     * Resets tempFood to 0
     */
    void resetEatenTempFood();
}
