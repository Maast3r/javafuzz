package com.Evolution.logic;

import com.Evolution.exceptions.DeckEmptyException;
import com.Evolution.exceptions.NullGameObjectException;
import com.Evolution.interfaces.IDeck;

import java.util.Collections;
import java.util.Stack;

/**
 * Deck for dealing cards
 * Created by burchtm on 3/21/2016.
 */
public class Deck<T> extends Stack<T> implements IDeck<T> {

    public Deck() {
        super();
    }

    @Override
    public int getSize() {
        return this.size();
    }

    @Override
    public T draw() throws DeckEmptyException {
        if (getSize() == 0) {
            throw new DeckEmptyException("The deck is empty.");
        }
        return this.pop();
    }

    @Override
    public void discard(T object) throws NullGameObjectException {
        if(object == null) {
            throw new NullGameObjectException("Unable to discard a NULL object to Deck");
        }
        this.push(object);
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this);
    }
}
