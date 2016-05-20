package com.Evolution.interfaces;

import com.Evolution.exceptions.DeckEmptyException;
import com.Evolution.exceptions.NullGameObjectException;

/**
 * Interface for decks
 * Created by burchtm on 3/23/2016.
 */
public interface IDeck<T> {
    /**
     * Gets the amount of cards in the deck
     *
     * @return The number of cards in the deck
     */
    int getSize();

    /**
     * Draws from the deck
     *
     * @return The instance of the object stored in the deck
     * @throws DeckEmptyException when a draw is attempted without any cards remaining in the Deck
     */
    T draw() throws DeckEmptyException;

    /**
     * Discards an object onto the deck
     *
     * @param object The object to discard onto the deck
     * @throws NullGameObjectException if a null object is passed to be discarded
     */
    void discard(T object) throws NullGameObjectException;

    /**
     * Shuffles the contents of the deck into a new order
     */
    void shuffle();
}
