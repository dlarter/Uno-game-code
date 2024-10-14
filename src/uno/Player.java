package uno;

import uno.card.Card;
import uno.card.Colour;

import java.util.ArrayList;
public class Player {
    private ArrayList<Card> hand;
    private String name;
    private boolean isUno;

    final String BOLD = "\033[0m" + "\033[1m";


    public Player(String name){
        this.name=name;
        this.hand = new ArrayList<>();
        this.isUno = false;
    }
    //test
    //Prints all players cards
    public void printHand(){
        System.out.println(BOLD + Colour.YELLOW.getColourCode() + name + "'s CARDS");
        System.out.println("====================" + BOLD);
        System.out.println("0: " + Colour.YELLOW.getColourCode() +"DRAW CARD !" + BOLD);
        for(int i=0; i<hand.size(); i++){
            Card currentCard = hand.get(i);
            System.out.println((i+1)+": " + currentCard.cardString() + BOLD);
        }
        System.out.println(Colour.YELLOW.getColourCode() + "====================" + BOLD);
    }

    public ArrayList<Card> getHand(){
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public boolean isUno() {
        return isUno;
    }

    public void setUno(boolean uno) {
        isUno = uno;
    }
}
