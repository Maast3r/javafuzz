package com.Evolution.logic;

import com.Evolution.exceptions.IllegalCardDirectionException;
import com.Evolution.exceptions.IllegalCardFoodException;
import com.Evolution.exceptions.NullGameObjectException;
import com.Evolution.interfaces.ICard;

/**
 * Class for handling gameplay cards
 * Created by burchtm on 3/21/2016.
 */
public class Card implements ICard {

    private String name;
    private String desc;
    private String imgPath;
    private int food;
    private int direction;

    /**
     * Constructs a new card with the given attributes
     *
     * @param name      of card
     * @param desc      of trait
     * @param imgPath   file name of image including '.png'
     * @param food      amount of food this card will represent
     * @param direction of influence. Used for determining how it interacts with nearby
     *                  {@link com.Evolution.interfaces.ISpecies} when it is attached to a
     *                  {@link com.Evolution.interfaces.ISpecies}
     * @throws IllegalCardDirectionException when the direction is not within the integer interval [0, 2]
     * @throws IllegalCardFoodException      when the input food value does not fall between -3 and 9
     * @throws NullGameObjectException       if name == null || desc == null
     */
    public Card(String name, String desc, String imgPath, int food, int direction) throws
            IllegalCardDirectionException, IllegalCardFoodException, NullGameObjectException {
        if (name == null || desc == null) {
            throw new NullGameObjectException("The Card name & description must not be NULL");
        }
        if (desc == null) {
            throw new NullGameObjectException("The Card description must not be NULL");
        }
        if(imgPath == null) {
            throw new NullGameObjectException("The Card imgPath must not be NULL");
        }
        if (direction != 0 && direction != 1 && direction != 2) {
            throw new IllegalCardDirectionException("The direction is not 0, 1, or 2.\n");
        }
        if (food < -3 || food > 9) {
            throw new IllegalCardFoodException("Tried adding an invalid food value. Must be between -3 and 9.");
        }
        this.name = name;
        this.desc = desc;
        this.imgPath = imgPath;
        this.food = food;
        this.direction = direction;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public String getDesc() {
        return desc;
    }


    @Override
    public String getImgPath() {
        return imgPath;
    }

    @Override
    public int getFood() {
        return food;
    }

    @Override
    public int getDirection() {
        return direction;
    }
}
