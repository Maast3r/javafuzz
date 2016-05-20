package com.Evolution.logic;

import com.Evolution.abstracts.ATrait;
import com.Evolution.exceptions.*;
import com.Evolution.interfaces.ICard;
import com.Evolution.interfaces.IPhases;
import com.Evolution.interfaces.ISpecies;
import com.Evolution.traits.FatTissue;
import com.Evolution.traits.Fertile;
import com.Evolution.traits.LongNeck;

import java.util.HashMap;

/**
 * Logic class for all logic for the third phase of the game
 */
public class PhaseThree implements IPhases{
    private Game game;
    private HashMap<String, ATrait> feedTraitActions = new HashMap<>();


    public PhaseThree(Game g){
        this.game = g;
        this.feedTraitActions.put("Long Neck", new LongNeck(this.game));
        this.feedTraitActions.put("Fertile", new Fertile(this.game));
    }

    @Override
    public void execute() throws IllegalCardDirectionException, DeckEmptyException,
            InvalidPlayerSelectException, NullGameObjectException,
            InvalidWateringHoleCardCountException, IllegalSpeciesIndexException,
            WateringHoleEmptyException, SpeciesFullException, FoodBankEmptyException,
            SpeciesPopulationException {
        this.game.nextTurn();
        if(this.game.getTurn() == this.game.getFirstPlayer()){
            this.game.moveFoodFromBankToHole(this.game.getWateringHole().getCardFoodCount());
            for(ICard c : this.game.getWateringHole().getCards()) {
                this.game.getDiscardPile().discard(c);
            }
            this.game.getWateringHole().removeCards();
            for(int i=0; i<this.game.getPlayerObjects().size(); i++){
                for(int j=0; j<this.game.getPlayerObjects().get(i).getSpecies().size(); j++){
                    for (ICard c : this.game.getPlayerObjects().get(i).getSpecies().get(j)
                            .getTraits()) {
                        String name = c.getName();
                        if (feedTraitActions.containsKey(name)) {
                            feedTraitActions.get(name).executeTrait(new int[]{i, 0}, new int[]{j, 0});
                        }
                    }
                    if(this.game.getPlayerObjects().get(i).getSpecies().get(j).getTempFood() > 0){
                        int tempFood = this.game.getPlayerObjects().get(i).getSpecies().get(j).getTempFood();
                        this.game.getPlayerObjects().get(i).getSpecies().get(j).resetEatenTempFood();
                        for(int f = 0; f < tempFood; f++){
                            this.game.getPlayerObjects().get(i).getSpecies().get(j).eat();
                        }
                    }
                }
            }
            this.game.setPhase(new PhaseFour(this.game));
        }
    }

    @Override
    public String getName() {
        return "Play Cards";
    }

    @Override
    public int getNumber() {
        return 3;
    }
}
