package com.Evolution.logic;

import com.Evolution.exceptions.IllegalCardDirectionException;
import com.Evolution.exceptions.IllegalCardFoodException;
import com.Evolution.exceptions.NullGameObjectException;
import com.Evolution.exceptions.WrongFileException;
import com.Evolution.interfaces.ICard;
import com.Evolution.interfaces.IDeck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Contains methods to generate an empty Deck or a filled Deck given an InputStream
 */
public class DeckFactory {

    /**
     * Parses a string and converts it into a new Card
     *
     * @param input string containing card information
     * @return Card
     * @throws IllegalCardDirectionException propagated from {@link Card#Card(String, String, String, int, int)}
     * @throws WrongFileException            thrown when string format does not match the specified pattern
     * @throws IllegalCardFoodException      propagated from {@link Card#Card(String, String, String, int, int)}
     * @throws NullGameObjectException       propagated from {@link Card#Card(String, String, String, int, int)} ||
     *                                       when the provided input string is null
     */
    public ICard readLineToCard(String input) throws IllegalCardDirectionException, WrongFileException,
            IllegalCardFoodException, NullGameObjectException {
        if (input == null) {
            throw new NullGameObjectException("The input must not be null");
        }
        String pattern = "^.{0,150};.{0,175};.{0,150}png;-?[0-9]+;[0-9]+$";
        if (!input.matches(pattern)) {
            throw new WrongFileException("You are reading from a bad file.");
        }
        String[] cardParams = input.split(";");
        return new Card(cardParams[0], cardParams[1], cardParams[2], Integer.parseInt(cardParams[3]),
                Integer.parseInt(cardParams[4]));
    }

    /**
     * Read an InputStream and convert it to an ArrayList of ICard line by line
     *
     * @param input input stream containing strings of card info
     * @return ArrayList<ICard>
     * @throws IllegalCardDirectionException propagated from {@link #readLineToCard(String)}
     * @throws IOException                   thrown if the BufferedReader is unable to read a line from the InputStream
     * @throws WrongFileException            propagated from {@link #readLineToCard(String)}
     * @throws NullGameObjectException       propagated from {@link #readLineToCard(String)} || when the provided
     *                                       InputStream is null
     * @throws IllegalCardFoodException      propagated from {@link #readLineToCard(String)}
     */
    public ArrayList<ICard> readFile(InputStream input) throws IllegalCardDirectionException, IOException,
            WrongFileException, IllegalCardFoodException, NullGameObjectException {
        if (input == null) {
            throw new NullGameObjectException("The provided Input Stream must not be null");
        }
        ArrayList<ICard> cards = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(input));

        String strLine;

        while ((strLine = br.readLine()) != null) {
            cards.add(readLineToCard(strLine));
        }

        return cards;
    }

    /**
     * Constructs a Deck filled with cards to be used during gameplay
     *
     * @param s input stream containing strings of card info
     * @return IDeck<ICard>
     * @throws IOException                   propagated from {@link #readFile(InputStream)}
     * @throws IllegalCardDirectionException propagated from {@link Card#Card(String, String, String, int, int)}
     * @throws WrongFileException            propagated from {@link #readFile(InputStream)}
     * @throws NullGameObjectException       propagated from {@link #readFile(InputStream)} || when the InputStream
     *                                       is null
     * @throws IllegalCardFoodException      propagated from {@link #readFile(InputStream)}
     */
    public IDeck<ICard> generateDrawPile(InputStream s) throws IOException, IllegalCardDirectionException,
            WrongFileException, IllegalCardFoodException, NullGameObjectException {
        if (s == null) {
            throw new NullGameObjectException("The provided Input Stream must not be null");
        }
        Deck<ICard> drawPile = new Deck<>();
        drawPile.addAll(readFile(s));
        return drawPile;
    }

    /**
     * Constructs an empty Deck to be used as a discard pile during gameplay
     *
     * @return empty IDeck<ICard>
     */
    public IDeck<ICard> generateDiscardPile() {
        return new Deck<>();
    }
}
