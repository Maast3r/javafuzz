package com.Evolution.logic;

import com.Evolution.exceptions.DeckEmptyException;
import com.Evolution.exceptions.IllegalCardDirectionException;
import com.Evolution.exceptions.InvalidPlayerSelectException;
import com.Evolution.exceptions.NullGameObjectException;
import com.Evolution.interfaces.IPhases;
import com.Evolution.interfaces.IPlayer;

/**
 * Logic for the fourth phase of the game
 */
public class PhaseFour implements IPhases {
    private Game game;

    public PhaseFour(Game g) {
        this.game = g;
    }

    @Override
    public void execute() throws IllegalCardDirectionException, DeckEmptyException, InvalidPlayerSelectException, NullGameObjectException {
        if (this.game.allFull()) {
            this.game.increaseRound();
            while (this.game.getTurn() != this.game.getFirstPlayer()) {
                this.game.nextTurn();
            }
            this.game.getPlayerObjects().forEach(IPlayer::moveFoodToFoodBag);
            this.game.setPhase(new PhaseOne(this.game));
        } else {
            this.game.nextTurn();
        }
    }

    @Override
    public String getName() {
        return "Feeding";
    }

    @Override
    public int getNumber() {
        return 4;
    }
}
