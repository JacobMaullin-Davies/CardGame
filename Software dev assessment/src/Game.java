import java.io.IOException;
/**
 * Game Class
 * This class handles the interactions between the players. It stores every player
 * instance in the game and will call methods so that players can pick and discard
 * cards to each other in the right order that the specification states.
 *
 */
public class Game extends Thread{
    private final int playerAmount;
    private final Player[] playerArray;
    private final Deck [] deckArray;
    private boolean done;

    /**
     * Constructor
     * This constructor sets up an array of player objects and then a
     * deck object for each player. Then all the card instances in the
     * main deck is distributed to each hand and each deck of the player.
     * Then an array of card objects is set for pickDeck and discardDeck.
     * @param playerAmount          amount of players
     * @throws IOException          Player input validate
     */
    public Game(int playerAmount) throws IOException {
        this.playerAmount = playerAmount;
        this.playerArray = new Player[playerAmount];
        this.deckArray = new Deck[playerAmount];
        this.done = false;
        int count = 1;

        //Sets up an array of player objects
        //Sets up a deck object for each player object
        for (int i = 0; i < playerAmount; i++){
            playerArray[i] = new Player(count, playerAmount,this);
            deckArray[i] = new Deck(count);
            count++;
        }
        //Distributes cards to hands and decks of each player
        handOutCards();

        for (int i = 0; i < playerAmount; i++) {
            int pickDeckNum;
            int discardDeckNum;
            pickDeckNum = i - 1;
            discardDeckNum = i + 1;
            if (pickDeckNum < 0) {
                pickDeckNum = playerArray.length - 1;
            }
            if (discardDeckNum > playerArray.length - 1) {
                discardDeckNum = 0;
            }
            Deck toPickDeck = deckArray[pickDeckNum];
            Deck toDiscardDeck = deckArray[discardDeckNum];
            playerArray[i].setPickDeckForPlayer(toPickDeck);
            playerArray[i].setDiscardDeckForPlayer(toDiscardDeck);
        }
    }

    /**
     * Get Method
     * This accesses the boolean data stored in the done attribute.
     * @return          returns done value
     */
    public boolean getDone(){
        return done;
    }

    /**
     * Get Method
     * Method is used whenever information about a player is required, as
     * the array consists of all the player instances in the game.
     * @return                      returns an array of all the player instances in the game
     */
    public Player[] getPlayerArray() {
        return playerArray;
    }


    /**
     * This method is called if a thread needs to be started for a player.
     * If this method is executed a thread is started for a player which will
     * execute a turn for the player and check if the player has won.
     */
    public void play() {
        //Runs a turn for a player
        System.out.println("Starting Game:: ");
        for (Player player : playerArray) {
            synchronized (this) {
                player.start();
            }
        }
    }


    /**
     * This method is executed when a player has won. When a player has won,
     * all the threads of all the other players are notified to stop. Then
     * information about all the losing players is written to text files. 
     * @param winningPlayer          Player number of the winning player
     * @throws IOException            Player input validate
     */
    public void notifyEnd(int winningPlayer) throws IOException {
        //Notifies all threads to stop
        synchronized (this) {
            done = true;
            for (int i = 0; i < playerArray.length; i++) {
                playerArray[i].stopThread();
                if(playerArray[i].getPlayerNumber() != winningPlayer){
                    //Prints data about losers to a text file
                    playerArray[i].writeLosers(winningPlayer);
                }
                Pack.deckWrite(playerArray[i].getPlayerNumber(), deckArray[i].getDeck());
            }
        }
    }


    /**
     * This method firstly distributes a card at a time to a player's hand each
     * time. Then the method distributes a card at a time to a players deck each
     * time.
     */
    public void handOutCards() {
        /*Distributes all cards from the main deck to the hands and decks
         * to each player
         */
        Card[] allCards = Pack.getGameDeck();
        Player[] allPlayers = getPlayerArray();
        int count = 0;
        int tally = 0;
        // Distributing to player's hands
        for (int arraySection = 0; arraySection < 4; arraySection++) {
            for (int eachPlayer = 0; eachPlayer < playerAmount; eachPlayer++) {
                Card[] thisPlayerHand = allPlayers[eachPlayer].getPlayerHandArray();
                thisPlayerHand[arraySection] = allCards[count];
                count++;
                tally++;
            }
        }
        //Distributing to player's decks in the deckArray
        for (int arraySection = 0; arraySection < 4; arraySection++) {
            for (int eachPlayer = 0; eachPlayer < playerAmount; eachPlayer++) {
                deckArray[eachPlayer].add(allCards[tally]);
                tally++;
            }
        }
    }
}