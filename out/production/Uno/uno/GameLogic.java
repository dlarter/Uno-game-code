package uno;

import uno.card.Card;
import uno.card.Colour;
import uno.card.impl.NumberCard;
import uno.card.impl.WildCard;
import uno.card.impl.WildDrawCard;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Stack;
import java.util.Scanner;

public class GameLogic {

    private Deck deck;
    private Player[] players;
    private Card currentCard;
    private Stack<Card> discardPile;
    private int currentPlayerIndex;
    private boolean directionClockwise;

    //ANSI colour codes
    final String BOLD = "\033[0m" + "\033[1m";

    public GameLogic() {
        this.discardPile = new Stack<>();
        this.currentPlayerIndex = 0;
        this.directionClockwise = true;
    }

    public static void main(String[] args) {
        GameLogic game = new GameLogic();
        game.start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in); // Scanner to read user inputs
        System.out.println(BOLD + "===============");
        System.out.println("||    UNO    ||");
        System.out.println("===============");
        System.out.println();
        //While statement gets the amount of players and creates them

        boolean game = true;

        //Loop is here in case the player wants to play another game once they finish the first game
        while (game) {

            this.deck = new Deck(); //Generates deck and shuffles cards.
            this.currentCard = deck.drawCard();

            //If statement selects a random colour if a wild card is drawn as the starting card

            if (currentCard instanceof WildCard) {
                ((WildCard) currentCard).setRandomColour();
            }else if(currentCard instanceof  WildDrawCard){
                ((WildDrawCard) currentCard).setRandomColour();
            }

            while (true) {
                System.out.println(BOLD + "Enter how many players are going to be playing  (Needs to be a number between 2 - 4 players)");
                try {
                    int playerCount = scanner.nextInt();
                    if (playerCount >= 2 && playerCount <= 4) {
                        this.players = new Player[playerCount];
                        createPlayers(playerCount);
                        break; // Exits the loop once valid answer is chosen and players created
                    } else {
                        System.out.println("Need to enter a number between 2-4");
                    }
                } catch (InputMismatchException e) { //Catches error if a number is not entered
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clears scanner
                }
            }


            //deals initial cards for all players
            for (Player player : players) {
                deck.dealCards(player);
            }

            //While statement acts as the main game loop, broken once a player wins
            while (true) {

                Player currentPlayer = players[currentPlayerIndex];
                System.out.println(BOLD + "Current card: " + currentCard.cardString()); //Prints current playing uno.card
                currentPlayer.printHand();
                System.out.println("Pick your card by typing the number or choose 0 to draw");


                int pickedCardNumber = validPickCheck(scanner, currentPlayer); //returns value of input, will loop till a valid response is given

                // if 0 -> Draw card else checks if uno.card is valid and either plays it or loops back till a valid uno.card is given
                if (pickedCardNumber == 0) {
                    if (deck.getCards().isEmpty()){
                        deck.setCards(discardPile);
                    }
                    currentPlayer.getHand().add(deck.drawCard());
                    System.out.println("Drawn card: " + currentPlayer.getHand().get(currentPlayer.getHand().size() - 1).cardString());
                } else {
                    Card pickedCard = currentPlayer.getHand().get(pickedCardNumber - 1);
                    if (isValidCard(currentCard, pickedCard)) { //checks if card can be played
                        discardPile.add(currentCard);
                        pickedCard.onPlay(this); // if special card will apply actions
                        currentCard = pickedCard;
                        currentPlayer.getHand().remove(pickedCardNumber - 1);
                    } else {
                        System.out.println("Card cannot be played");
                        continue;
                    }
                }

                //Checks if a player has won if not moves index to the next player
                if (currentPlayer.getHand().isEmpty()) {
                    System.out.println(BOLD + currentPlayer.getName() + " WON!!!");
                    System.out.println("Would you like to play another game ? (Type 'yes' or 'no')");
                    Scanner answer = new Scanner(System.in);
                    String userAnswer = answer.nextLine();
                    userAnswer = userAnswer.replace(" ","").toLowerCase();
                    if(userAnswer.equals("no") || userAnswer.equals("n")){
                        game = false; //this breaks the first while loop and ends the game
                    }
                    break;
                } else { //Else statements sets index of next player
                    currentPlayerIndex = nextPlayerIndex(currentPlayerIndex);
                }

            }
        }
    }

    // Function that takes in the top uno.card and picked uno.card and checks if the picked uno.card is valid
    private boolean isValidCard(Card currentCard, Card pickedCard) {
        if (pickedCard.getColour() == currentCard.getColour()) { //if colours match it is a valid uno.card
            return true;
        }
        if (pickedCard.getColour() == Colour.WILD) { //all wild cards are valid
            return true;
        }
        if (pickedCard.getClass() == currentCard.getClass()) {
            if (currentCard instanceof NumberCard) {
                return currentCard.getNumber() == pickedCard.getNumber(); //if numbers match it is valid
            }
            return true; //if types of cards are the same they are valid
        }
        return false; //if reach it is an invalid uno.card
    }

    //takes users input and checks if it is a valid answer. If in range it returns the number,
    // if not it will loop until a valid number is drawn
    private int validPickCheck(Scanner scanner, Player player) {
        while (true) {
            try {
                int pickedCardNumber = scanner.nextInt();

                if (pickedCardNumber >= 0 && pickedCardNumber < player.getHand().size() + 1) { //checking if in range of hand size
                    return pickedCardNumber;
                } else {
                    System.out.println("Invalid input, Pick a number from your deck or draw by typing '0'");
                }
            } catch (InputMismatchException e) { //Catches error if a number is not entered
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Makes scanner ready for next turn
            }
        }
    }

    //Grabs next player, it takes into account the current direction
    public int nextPlayerIndex(int currentPlayerIndex) {
        if (directionClockwise) {
            if (currentPlayerIndex >= players.length - 1) {
                return 0;
            } else {
                return currentPlayerIndex + 1;
            }
        } else { //Else statement reached if the rotation is going backwards after the use of a reverse uno.card
            if (currentPlayerIndex <= 0) {
                return players.length - 1;
            } else {
                return currentPlayerIndex - 1;
            }
        }
    }

    private void createPlayers(int numberOfPlayers) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter player " + (i + 1) + "'s name");
            players[i] = (new Player(scanner.nextLine()));
        }
    }

    public void setDirectionClockwise(boolean directionClockwise) {
        this.directionClockwise = directionClockwise;
    }

    public boolean isDirectionClockwise() {
        return directionClockwise;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

}


