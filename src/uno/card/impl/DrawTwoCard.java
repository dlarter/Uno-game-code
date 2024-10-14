package uno.card.impl;

import uno.GameLogic;
import uno.Player;
import uno.card.Card;
import uno.card.Colour;

public class DrawTwoCard extends Card {
    public DrawTwoCard(Colour colour, int number) {
        super(colour, number);
    }

    @Override
    public void onPlay(GameLogic game) { //Draws two cards for next player
        int currentPlayerIndex = game.nextPlayerIndex(game.getCurrentPlayerIndex());
        Player nextPlayer = game.getPlayers()[currentPlayerIndex];
        nextPlayer.getHand().add(game.getDeck().drawCard());
        nextPlayer.getHand().add(game.getDeck().drawCard());
        System.out.println(nextPlayer.getName() + ": Draws two cards");

    }

    @Override
    public String cardString() {
        return colour.getColourCode() + "Draw Two - " + getColour();
    }
}
