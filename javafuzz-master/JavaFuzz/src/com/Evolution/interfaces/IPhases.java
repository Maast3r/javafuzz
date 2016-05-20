package com.Evolution.interfaces;

import com.Evolution.exceptions.*;
import com.Evolution.logic.Player;
import com.Evolution.logic.Species;
import com.Evolution.logic.WateringHole;

public interface IPhases {
    /**
     * Executes a certain phase of the game.
     * Like a linked list, each phase knows which phase should happen next.
     * This method moves to the next phase of the game.
     *
     * @throws IllegalCardDirectionException propagated from
     *                                       {@link com.Evolution.logic.Card#Card(String, String, String, int, int)}
     * @throws DeckEmptyException            propagated from {@link IDeck#draw()}
     * @throws InvalidPlayerSelectException  propagated from {@link com.Evolution.logic.Game#dealToPlayer(int)}
     * @throws NullGameObjectException       propagated from {@link com.Evolution.logic.Game#dealToPlayer(int)}
     * @throws InvalidWateringHoleCardCountException propagated from {@link WateringHole#addTotalCardFood()}
     * @throws IllegalSpeciesIndexException  propagated from {@link Player#getSpecies()}
     * @throws WateringHoleEmptyException    propagated from {@link WateringHole#getCards()}
     * @throws SpeciesFullException          propagated from {@link com.Evolution.logic.Species#eat()}
     * @throws FoodBankEmptyException        propagated from {@link com.Evolution.logic.Species#eat()}
     * @throws SpeciesPopulationException    propagated from {@link Species#increasePopulation()}
     */
    void execute() throws IllegalCardDirectionException, DeckEmptyException,
            InvalidPlayerSelectException, NullGameObjectException,
            InvalidWateringHoleCardCountException, IllegalSpeciesIndexException,
            WateringHoleEmptyException, SpeciesFullException, FoodBankEmptyException,
            SpeciesPopulationException;

    /**
     * Returns the name of the current phase
     *
     * @return phase name
     */
    String getName();

    /**
     * Returns the number of the phase
     *
     * @return phase number
     */
    int getNumber();

}
