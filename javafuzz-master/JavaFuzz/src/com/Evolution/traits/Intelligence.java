package com.Evolution.traits;

import com.Evolution.abstracts.CTrait;
import com.Evolution.exceptions.*;
import com.Evolution.interfaces.ICard;
import com.Evolution.logic.Game;


public class Intelligence extends CTrait {
    public Intelligence(Game g) {
        super(g);
    }

    /**
     * Discard Intelligence to eat twice from the food bank.
     *
     * @param playerIndex  [0]: Player applying action
     *                     [1]: Player being affected
     * @param speciesIndex [0]: Species applying action
     *                     [1]: Species being affected
     * @throws IllegalSpeciesIndexException propagated from {@link Game#feedPlayerSpecies(int, int)}
     * @throws InvalidPlayerSelectException propagated from {@link Game#feedPlayerSpecies(int, int)}
     * @throws SpeciesFullException         propagated from {@link Game#feedPlayerSpecies(int, int)}
     * @throws NullGameObjectException      propagated from {@link Game#discardFromPlayer(int, ICard)}
     * @throws WateringHoleEmptyException   propagated from {@link Game#feedPlayerSpecies(int, int)}
     * @throws SpeciesPopulationException   propagated from {@link Game#feedPlayerSpecies(int, int)}
     * @throws IllegalCardDiscardException      propagated from {@link Game#discardFromPlayer(int, ICard)}
     * @throws IllegalCardRemovalException      propagated from {@link Game#discardFromPlayer(int, ICard)}
     * @throws FoodBankEmptyException       propagated from {@link Game#feedPlayerSpecies(int, int)}
     */
    @Override
    public void executeTrait(int[] playerIndex, int[] speciesIndex, ICard c)
            throws IllegalSpeciesIndexException, InvalidPlayerSelectException,
            SpeciesFullException, WateringHoleEmptyException,
            NullGameObjectException, IllegalCardFoodException,
            IllegalCardDiscardException, IllegalCardRemovalException,
            FoodBankEmptyException {

        this.game.discardFromPlayer(playerIndex[0], c);
        this.game.feedPlayerSpeciesFromBank(playerIndex[0],
                speciesIndex[0]);
        this.game.feedPlayerSpeciesFromBank(playerIndex[0],
                speciesIndex[0]);
    }
}
