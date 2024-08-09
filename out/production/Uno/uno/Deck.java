package uno;

import uno.card.Card;
import uno.card.Colour;
import uno.card.impl.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck {
    private final Stack<Card> cards = new Stack<>();

    public Deck() {
        initializeDeck();
        shuffle();
    }

    //Function is called at the start of the game to create deck with all 102 cards
    public void initializeDeck() {
        for (Colour colour : Colour.values()) {  //loop through all uno.card colour types
            if (colour == Colour.WILD) continue;

            cards.add(new NumberCard(colour, 0)); //Add one 0 card for each colour
            for (int i = 1; i <= 9; i++) { //Loop to add all number cards, two of each colour and number
                cards.add(new NumberCard(colour, i));
                cards.add(new NumberCard(colour, i));
            }

            //Manually adding two of each special uno.card, two for each colour
            cards.add(new SkipCard(colour, -1));
            cards.add(new SkipCard(colour, -1));
            cards.add(new ReverseCard(colour, -1));
            cards.add(new ReverseCard(colour, -1));
            cards.add(new DrawTwoCard(colour, -1));
            cards.add(new DrawTwoCard(colour, -1));

        }
        for (int i = 0; i < 4; i++) { //Add all wild cards
            cards.add(new WildCard(Colour.WILD, -1));
            cards.add(new WildDrawCard(Colour.WILD, -1));
        }
    }

    //Function called at the start of the game to deal initial cards,
    //Gives entered player 7 inital cards
    public void dealCards(Player player) {
        ArrayList<Card> playerHand = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            playerHand.add(drawCard());
        }
        player.setHand(playerHand);
    }

    //Shuffles deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    //Draws card from initial deck and removes from stack
    public Card drawCard() {
        return cards.pop();
    }

    public Stack<Card> getCards() {
        return cards;
    }
}
