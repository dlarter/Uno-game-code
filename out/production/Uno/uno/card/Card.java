package uno.card;


import uno.GameLogic;

public abstract class Card {

    protected Colour colour;
    private int number;

    public Card(Colour colour, int number) {
        this.colour = colour;
        this.number = number;
    }

    public abstract void onPlay(GameLogic game);

    public abstract String cardString();

    public Colour getColour() {
        return colour;
    }

    public int getNumber() {
        return number;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }
}
