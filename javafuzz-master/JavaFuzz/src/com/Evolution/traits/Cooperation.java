package com.Evolution.traits;

import com.Evolution.abstracts.ATrait;
import com.Evolution.exceptions.*;
import com.Evolution.logic.Game;

public class Cooperation extends ATrait {
    public Cooperation(Game g) {
        super(g);
    }

    /**
     * Feeds the species to the right of this species
     *
     * @param playerIndex  [0]: Player applying action
     *                     [1]: Player being affected
     * @param speciesIndex [0]: Species applying action
     *                     [1]: Species being affected
     * @throws IllegalSpeciesIndexException propagated from {@link Game#feedPlayerSpecies(int, int)}
     * @throws InvalidPlayerSelectException propagated from {@link Game#feedPlayerSpecies(int, int)}
     * @throws SpeciesFullException         propagated from {@link Game#feedPlayerSpecies(int, int)}
     * @throws WateringHoleEmptyException   propagated from {@link Game#feedPlayerSpecies(int, int)}
     * @throws SpeciesPopulationException   propagated from {@link Game#feedPlayerSpecies(int, int)}
     * @throws FoodBankEmptyException       propagated from {@link Game#feedPlayerSpecies(int, int)}
     */
    @Override
    public void executeTrait(int[] playerIndex, int[] speciesIndex)
            throws IllegalSpeciesIndexException, InvalidPlayerSelectException,
            SpeciesFullException, WateringHoleEmptyException, FoodBankEmptyException,
            SpeciesPopulationException {

        this.game.feedPlayerSpeciesFromBank(playerIndex[0], speciesIndex[0] + 1);
    }

    /**
     * Empty method
     * @param ints
     * @param ints1
     * @return
     */
    @Override
    public boolean canBeAttacked(int[] ints, int[] ints1) {
        return false;
    }
}
