import java.util.LinkedList;
import java.util.Queue;

/**
 * Deck Class
 * This class creates the players deck in use in the game
 *
 */
public class Deck {
    private int playerDeckNumber;
    private Queue<Card> playerDeckArray;

    /**
     * Constructor
     * This creates a deck object, for the number of the player passed in
     * as an argument
     * @param playerNumber      Number of the player to create the deck
     */
    public Deck(int playerNumber){
        this.playerDeckNumber = playerNumber;
        this.playerDeckArray = new LinkedList();
    }

    /**
     * Get Method
     * Used throughout the program to access the card instances stored in the
     * deck of the player
     * @return                  This returns deck of a given player
     */
    public Queue<Card> getDeck(){return playerDeckArray; }

    /**
     * Allows us to add a card to the deck. This method will be often used
     * when a player discards a card as a card would have to be added to a deck.
     * @param newCard           This is the card object that will be added to the deck.
     */
    public void add(Card newCard){playerDeckArray.add(newCard); }

    /**
     * This method will be executed whenever a player needs to pick a card
     * up in their turn. The card chosen to be picked up will always be the
     * head of the queue, and this will subsequently be removed from the arrayList.
     * @return                  Returns the card that will has been picked up
     */
    public Card pickup(){
        Card pickedCard = playerDeckArray.remove();
        return pickedCard;
    }

    /**
     * This method will be executed whenever a player needs to discard a card in
     * their turn. The card object passed as an argument will be added to the
     * deck in this method.
     * @param discardCard       Card that has been discarded from a player's hand
     */
    public void discard(Card discardCard) { playerDeckArray.add(discardCard); }
}