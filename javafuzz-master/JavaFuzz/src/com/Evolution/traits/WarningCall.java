package com.Evolution.traits;

import com.Evolution.abstracts.ATrait;
import com.Evolution.exceptions.*;
import com.Evolution.interfaces.ICard;
import com.Evolution.logic.Game;

import java.util.ArrayList;


public class WarningCall extends ATrait {
    public WarningCall(Game g) {
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
     * Checks to see if the given player species can be attacked.
     * If the attacker doesn't have Ambush, this species is safe.
     *
     * @param playerIndex  [0]: Player applying action
     *                     [1]: Player being affected
     * @param speciesIndex [0]: Species applying action
     *                     [1]: Species being affected
     * @return true/false (attackable/not attackable)
     */
    @Override
    public boolean canBeAttacked(int[] playerIndex, int[] speciesIndex) {
        ArrayList<ICard> attackerTraits = this.game.getPlayerObjects().get(playerIndex[1]).getSpecies()
                .get(speciesIndex[1]).getTraits();

        for(ICard c : attackerTraits) {
            if(c.getName().equals("Ambush")) return true;
        }
        return false;
    }
}
