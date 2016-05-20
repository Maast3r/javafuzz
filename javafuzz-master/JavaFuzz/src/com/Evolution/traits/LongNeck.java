package com.Evolution.traits;


import com.Evolution.abstracts.ATrait;
import com.Evolution.exceptions.*;
import com.Evolution.interfaces.ICard;
import com.Evolution.logic.Game;

import java.util.ArrayList;

/**
 * Created by goistjt on 4/27/2016.
 */
public class LongNeck extends ATrait {

    public LongNeck(Game game) {
        super(game);
    }

    /**
     * Makes the species executing this action feed a second time from the food bank
     *
     * @param playerIndex  [0]: Player applying action
     *                     [1]: Ignored
     * @param speciesIndex [0]: Species applying action
     *                     [1]: Ignored
     * @throws IllegalSpeciesIndexException propagated from {@link Game#feedPlayerSpeciesFromBank(int, int)}
     * @throws InvalidPlayerSelectException propagated from {@link Game#feedPlayerSpeciesFromBank(int, int)}
     * @throws SpeciesFullException         propagated from {@link Game#feedPlayerSpeciesFromBank(int, int)}
     * @throws WateringHoleEmptyException   propagated from {@link Game#feedPlayerSpeciesFromBank(int, int)}
     * @throws FoodBankEmptyException       propagated from {@link Game#feedPlayerSpeciesFromBank(int, int)}
     * @throws SpeciesPopulationException   propagated from {@link Game#feedPlayerSpecies(int, int)}
     */
    public void executeTrait(int[] playerIndex, int[] speciesIndex) throws IllegalSpeciesIndexException,
            InvalidPlayerSelectException, SpeciesFullException, WateringHoleEmptyException,
            FoodBankEmptyException, SpeciesPopulationException {
        this.game.feedPlayerSpeciesFromBank(playerIndex[0], speciesIndex[0]);
        ArrayList<ICard> cards = this.game.getPlayerObjects().get(playerIndex[0])
                .getSpecies().get(speciesIndex[0]).getTraits();
        for(ICard c : cards){
            if(c.getName().equals("Foraging")){
                Foraging f = new Foraging(this.game);
                f.executeTrait(playerIndex, speciesIndex);
            } else if(c.getName().equals("Cooperation")){
                Cooperation coop = new Cooperation(this.game);
                coop.executeTrait(playerIndex, speciesIndex);
            }
        }
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
