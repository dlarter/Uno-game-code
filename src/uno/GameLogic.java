package uno;

import uno.card.Card;
import uno.card.Colour;
import uno.card.impl.NumberCard;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Stack;
import java.util.Scanner;

public class GameLogic {
    private Deck deck;
    private ArrayList<Player> players;

    private Card currentCard;
    private Stack<Card> discardPile;
    private int currentPlayerIndex;
    private boolean directionClockwise;

    //ANSI colour codes
    final String BOLD =  Colour.BOLDRESET.getColourCode();

    public GameLogic() {
        this.players = new ArrayList<>();
        this.discardPile = new Stack<>();
        this.deck = new Deck(); //Generates deck and shuffles cards
        this.currentPlayerIndex = 0;
        this.directionClockwise = true;
        this.currentCard = deck.drawCard();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in); // Scanner to read user inputs

        //While statement gets the amount of players and creates them
        while (true) {
            System.out.println(BOLD + "How many players (2-4)");
            try {
                int playerCount = scanner.nextInt();
                if (playerCount < 2 || playerCount > 4) {
                    System.out.println("Needs to be 2-4 players.");
                } else {
                    for (int i = 0; i < playerCount; i++) {
                        players.add(new Player("Player " + (i + 1))); // Creates all players
                    }
                    break; // Exits the loop once valid answer is chosen and players created
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

            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println();
            System.out.println(BOLD + "Current card: " + currentCard.cardString()); //Prints current playing uno.card
            currentPlayer.printHand();
            System.out.println("Pick your card by typing the number or choose 0 to draw");


            int pickedCardNumber = validPickCheck(scanner, currentPlayer); //returns value of input, will loop till a valid response is given

            // if 0 -> Draw uno.card else checks if uno.card is valid and either plays it or loops back till a valid uno.card is given
            if (pickedCardNumber == 0) {
                currentPlayer.getHand().add(deck.drawCard());
                System.out.println("Drawn uno.card: " + currentPlayer.getHand().get(currentPlayer.getHand().size() - 1));
                continue; //loops to while loop so the same person plays again
            } else {
                Card pickedCard = currentPlayer.getHand().get(pickedCardNumber - 1);
                if (isValidCard(currentCard, pickedCard)) { //checks if uno.card can be played
                    discardPile.add(currentCard);
                    pickedCard.onPlay(this); // if special uno.card will apply actions
                    currentCard = pickedCard;
                    currentPlayer.getHand().remove(pickedCardNumber-1);
                } else {
                    System.out.println("Card cannot be played");
                    continue;
                }
            }

            //Checks if a player has won if not moves index to the next player
            if (currentPlayer.getHand().isEmpty()) {
                System.out.println(currentPlayer.getName() + " WON!!!");
                break;
            } else { //Else statements sets index of next player
               currentPlayerIndex = nextPlayerIndex(currentPlayerIndex);
            }

        }
    }

    // Function that takes in the top uno.card and picked uno.card and checks if the picked uno.card is valid
    public boolean isValidCard(Card currentCard, Card pickedCard) {
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
    public int validPickCheck(Scanner scanner, Player player) {
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
                scanner.next(); // Clears scanner
            }
        }
    }
    //Grabs next player, it takes into account the current direction
    public int nextPlayerIndex(int currentPlayerIndex){
        if (directionClockwise) {
            if (currentPlayerIndex >= players.size() - 1) {
                return 0;
            } else {
                return currentPlayerIndex+1;
            }
        } else { //Else statement reached if the rotation is going backwards after the use of a reverse uno.card
            if (currentPlayerIndex <= 0) {
                return players.size() - 1;
            } else {
                return currentPlayerIndex-1;
            }
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
}


