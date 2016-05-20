package com.Evolution.interfaces;

import com.Evolution.exceptions.InvalidAddToWateringHoleException;
import com.Evolution.exceptions.InvalidWateringHoleCardCountException;
import com.Evolution.exceptions.WateringHoleEmptyException;

import java.util.ArrayList;

/**
 * Interface for the WateringHole
 * Created by goistjt on 3/23/2016.
 */
public interface IWateringHole {

    /**
     * Returns the current amount of food available in the
     * watering hole
     *
     * @return size
     */
    int getFoodCount();

    /**
     * Adds a single piece of food to the watering hole
     */
    void addFood();

    /**
     * Adds i pieces of food to the watering hole
     *
     * @param i amount of food to add
     */
    void addFood(int i);

    /**
     * Removes a single piece of food from the watering hole
     *
     * @throws WateringHoleEmptyException when more food is removed from the {@link IWateringHole} than is available
     */
    void removeFood() throws WateringHoleEmptyException;

    /**
     * Removes i pieces of food from the watering hole
     *
     * @throws WateringHoleEmptyException when more food is removed from the {@link IWateringHole} than is available
     */
    void removeFood(int i) throws WateringHoleEmptyException;

    /**
     * Returns the current cards in the watering hole
     * @return cards
     */
    ArrayList<ICard> getCards();

    /**
     * Adds the given card to the watering hole
     * @param card the card to add
     * @throws InvalidAddToWateringHoleException  when a null card is added to the {@link IWateringHole}
     */
    void addCard(ICard card) throws InvalidAddToWateringHoleException;

    /**
     * Removes all cards from the watering hole
     */
    void removeCards();

    /**
     * Adds total Card food count to watering hole
     * @throws InvalidAddToWateringHoleException  propagated from {@link IWateringHole#getCardFoodCount}
     */
    void addTotalCardFood() throws InvalidWateringHoleCardCountException;

    /**
     * Counts all the total food on the cards in the
     * watering hole
     * @return total food of cards
     * @throws InvalidAddToWateringHoleException  when an invalid amount of food is counted from the cards
     * {@link IWateringHole}
     */
    int getCardFoodCount() throws InvalidWateringHoleCardCountException;
}
