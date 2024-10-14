package uno.card.impl;

import uno.GameLogic;
import uno.card.Card;
import uno.card.Colour;

public class ReverseCard extends Card {
    public ReverseCard(Colour colour, int number) {
        super(colour, number);
    }

    @Override
    public void onPlay(GameLogic game) { //swaps whatever the current game direction is to the opposite
        if (game.getPlayers().length == 2) {
            int nextPlayer = game.nextPlayerIndex(game.getCurrentPlayerIndex());
            System.out.println(game.getPlayers()[nextPlayer].getName()+" skips turn");
            game.setCurrentPlayerIndex(nextPlayer);
        } else {
            if (game.isDirectionClockwise()) {
                game.setDirectionClockwise(false);
            } else {
                game.setDirectionClockwise(true);
            }
        }

    }

    @Override
    public String cardString() {
        return colour.getColourCode() + "Reverse card - " + getColour();

    }
}
