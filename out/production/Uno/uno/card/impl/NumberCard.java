package uno.card.impl;
import uno.GameLogic;
import uno.card.Card;
import uno.card.Colour;

public class NumberCard extends Card {
    public NumberCard(Colour color, int number) {
        super(color, number);
    }

    @Override
    public void onPlay(GameLogic game) {
    }

    @Override
    public String cardString() {
        return colour.getColourCode() + getNumber()+" - "+getColour();
    }
}
