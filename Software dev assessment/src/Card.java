/**
 * Card Class
 * Instances of this class represents the cards in the game
 * Attributes in the class allows all cards to have a card number
 * and also we can track how long the cards have been held in a player's
 * hand for and whether the card should be discarded or not.
 *
 */
public class Card {
    private final int indexValue;
    //Attribute stores the amount of rounds a card has been held for
    private int timeStep;
    //Boolean attribute stores whether the card should be discarded or not
    private boolean discard;

    /**
     * Constructor
     * This creates a card instance
     * @param indexValue        this is the card number of the card
     */
    public Card(int indexValue){
        this.indexValue = indexValue;
        this.timeStep = 0;
        this.discard = false;
    }

    /**
     * Get Method
     * Allows us to access the card number of the card
     * @return              Returns the card value of the Card
     */
    public int getIndexValue(){ return indexValue; }

    /**
     * Get Method
     * Allows us to access the time step of a card
     * @return              Returns the amount of turns a card has been held in the hand of a player
     */
    public int getTimeStep(){
        //Allows us to access the time step value of a card
        return timeStep;  }

    /**
     * Get Method
     * @return Returns when the card should be discarded or not
     */
    public boolean getDiscard() {
        //Allows us to access whether the card should be discarded or not
        return discard;
    }

    /**
     * Set Method
     * This will run when a card has been held in a players hand for another turn
     */
    public void setTimeStep(){timeStep +=1; }

    /**
     * Set Method
     * Changes the boolean attribute to true so this means the card will be
     * considered for discard
     */
    public void setToDiscard() {
        //Card could be possibly discarded
        discard = true;
    }

    /**
     * Set Method
     * Changes the boolean attribute to false so this means the card will not
     * be discarded
     */
    public void setToKeep() {
        //Card will not be discarded
        discard = false;
    }

    /**
     * When a card leaves the hand of a player some of the data of the card
     * has to be reset
     */
    public void resetCard() {
        timeStep = 0;
        discard = false;
    }

}