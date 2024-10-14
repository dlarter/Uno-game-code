package uno.card.impl;

import uno.GameLogic;
import uno.card.Card;
import uno.card.Colour;

public class SkipCard extends Card {
    public SkipCard(Colour colour, int number) {
        super(colour, number);
    }

    @Override
    public void onPlay(GameLogic game) {
        int nextPlayer = game.nextPlayerIndex(game.getCurrentPlayerIndex());
        System.out.println(game.getPlayers()[(nextPlayer)].getName()+" skips turn");
        game.setCurrentPlayerIndex(nextPlayer);

    }

    @Override
    public String cardString() {
        return colour.getColourCode() + "Skip card - "+ getColour();
    }
}
