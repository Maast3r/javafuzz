package com.Evolution.abstracts;

import com.Evolution.exceptions.*;
import com.Evolution.interfaces.ICard;
import com.Evolution.logic.Game;

/**
 * Method signatures for methods common amongst clickable  traits
 */
public abstract class CTrait {
    protected final Game game;

    /**
     * Constructs a new Trait which will perform actions when executed
     * @param game
     */
    public CTrait(Game game) {
        this.game = game;
    }

    public abstract void executeTrait(int[] playerIndex, int[] speciesIndex, ICard c) throws
            IllegalSpeciesIndexException,
            InvalidPlayerSelectException, SpeciesFullException, WateringHoleEmptyException, NullGameObjectException, IllegalCardFoodException, IllegalCardDirectionException, IllegalCardDiscardException, IllegalCardRemovalException, SpeciesPopulationException, FoodBankEmptyException, TempFoodMaxException;

    /**
     * Returns the Game currently associated with this Trait executor
     * @return Game this.game
     */
    public final Game getGame() {
        return this.game;
    }
}
