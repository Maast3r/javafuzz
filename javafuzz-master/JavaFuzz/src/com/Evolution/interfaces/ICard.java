package com.Evolution.interfaces;

/**
 * Interface for Cards
 * Created by burchtm on 3/23/2016.
 */
public interface ICard {

    /**
     * Returns the card's name
     * @return The card's name
     */
    String getName();

    /**
     * Returns the card's description
     * @return The card's description
     */
    String getDesc();

    /**
     * Returns the card's image path
     * @return The card's image path
     */
    String getImgPath();

    /**
     * Returns the amount of food the card is worth
     * @return The card's worth
     */
    int getFood();

    /**
     * Returns the direction on the board that the card effects
     * 0 - The card effects the species that the card is played on
     * 1 - The card effects the species that are left and right of the species the card is played on
     * 2 - The card effects the species that is right of the species the card is played on
     * Others - Invalid
     * @return The direction on the board that the card effects
     */
    int getDirection();
}
