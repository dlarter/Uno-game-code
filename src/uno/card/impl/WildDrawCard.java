package uno.card.impl;

import uno.GameLogic;
import uno.Player;
import uno.card.Card;
import uno.card.Colour;

import java.util.Random;
import java.util.Scanner;

public class WildDrawCard extends Card {
    public WildDrawCard(Colour colour, int number) {
        super(colour, number);
    }

    @Override
    public void onPlay(GameLogic game) {
        String BOLD = "\033[0m" + "\033[1m";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wild card played, pick a colour");
        System.out.println(Colour.RED.getColourCode() + "1: Red");
        System.out.println(Colour.YELLOW.getColourCode() +"2: Yellow");
        System.out.println(Colour.GREEN.getColourCode() + "3: Green");
        System.out.println(Colour.BLUE.getColourCode() + "4: Blue");

        int colourChoice = 0;
        while (colourChoice < 1 || colourChoice > 4) {
            System.out.println(BOLD + "Enter the number corresponding to pick your colour:");
            if (scanner.hasNextInt()) {
                colourChoice = scanner.nextInt();
                if (colourChoice < 1 || colourChoice > 4) {
                    System.out.println(BOLD + "Invalid choice. Please choose a number between 1 and 4.");
                }
            } else {
                System.out.println(BOLD + "Invalid input. Please enter a number between 1 and 4.");
                scanner.next();
            }
        }
        //Sets colour code for the card
        setColour(colourChoice);

        System.out.println("You picked: " + this.getColour());


        //Section below draws the four cards for the next player
        int currentPlayerIndex = game.nextPlayerIndex(game.getCurrentPlayerIndex());
        Player player = game.getPlayers()[currentPlayerIndex];
        player.getHand().add(game.getDeck().drawCard());
        player.getHand().add(game.getDeck().drawCard());
        player.getHand().add(game.getDeck().drawCard());
        player.getHand().add(game.getDeck().drawCard());
        System.out.println(player.getName() + ": Draws four cards");
    }

    @Override
    public String cardString() {
        if (this.getColour() == Colour.WILD){
            return "Wild Card - Pick up 4";
        } else {
            return colour.getColourCode() + this.getColour() + ": Wild Card - Pick up 4";
        }
    }

    public void setRandomColour(){
        Random rand = new Random();
        int randomInt = rand.nextInt(4)+1;
        setColour(randomInt);
    }

    private void setColour(int colourChoice){
        switch (colourChoice) {
            case 1:
                this.setColour(Colour.RED);
                break;
            case 2:
                this.setColour(Colour.YELLOW);
                break;
            case 3:
                this.setColour(Colour.GREEN);
                break;
            case 4:
                this.setColour(Colour.BLUE);
                break;
        }
    }
}
