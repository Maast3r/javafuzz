package com.Evolution.exceptions;

/**
 * Exception thrown if an illegal food number is given for a card
 */
public class IllegalCardFoodException extends Exception {
    public IllegalCardFoodException(String msg){
        super(msg);
    }
}
