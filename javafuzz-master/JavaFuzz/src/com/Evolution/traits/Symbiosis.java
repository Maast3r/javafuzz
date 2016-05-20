package com.Evolution.traits;


import com.Evolution.abstracts.ATrait;
import com.Evolution.exceptions.*;
import com.Evolution.logic.Game;

public class Symbiosis extends ATrait{

    public Symbiosis(Game g){
        super(g);
    }

    /**
     * Empty method.
     *
     * @param playerIndex
     * @param speciesIndex
     * @throws IllegalSpeciesIndexException
     * @throws InvalidPlayerSelectException
     * @throws SpeciesFullException
     * @throws WateringHoleEmptyException
     * @throws SpeciesPopulationException
     * @throws FoodBankEmptyException
     */
    @Override
    public void executeTrait(int[] playerIndex, int[] speciesIndex) throws IllegalSpeciesIndexException,
            InvalidPlayerSelectException, SpeciesFullException, WateringHoleEmptyException, SpeciesPopulationException,
            FoodBankEmptyException {

    }

    /**
     * If the species on the right has a bigger body size than itself, the species cannot be attacked.
     *
     * @param playerIndex  [0]: Player applying action
     *                     [1]: Player being affected
     * @param speciesIndex [0]: Species applying action
     *                     [1]: Species being affected
     * @return true/false (attackable/not attackable)
     */
    @Override
    public boolean canBeAttacked(int[] playerIndex, int[] speciesIndex) {
        if(speciesIndex[0]+1 >= 0 &&
                speciesIndex[0]+1 < this.game.getPlayerObjects().get(playerIndex[0]).getSpecies().size())
            return this.game.getPlayerObjects().get(playerIndex[0])
                    .getSpecies().get(speciesIndex[0] + 1).getBodySize() <=
                    this.game.getPlayerObjects().get(playerIndex[0])
                            .getSpecies().get(speciesIndex[0]).getBodySize();

        return true;
    }
}
