package com.Evolution.testClasses;

import com.Evolution.interfaces.IDeck;

/**
 * Class for testing things of the IDeck interface
 * Created by burchtm on 3/23/2016.
 */
public class TestDeck implements IDeck {
    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Object draw() {
        return null;
    }

    @Override
    public void discard(Object object) {}

    @Override
    public void shuffle() {}
}
