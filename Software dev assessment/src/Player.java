import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * Player Class
 * This class will create threads for each player and has methods that consist
 * of what happens when a player takes a turn. There are also methods to check
 * if a player has won, selecting which card to discard and finding cards that
 * are equal to the player number.
 *
 */
public class Player implements Runnable{
    private Thread playerThread;
    private final String threadName;
    private final int playerNumber;
    private final Card[] playerHandArray;
    private Deck pickDeck;
    private Deck discardDeck;
    private final int playerAmount;
    private final Game game;
    private boolean hasWon;


    /**
     * Constructor
     * This creates a player object which will represent a player in the game.
     * You have to pass three arguments to create a player instance.
     * @param PlayerNumber      The number that the player will have
     * @param playerAmount      The amount of players the game will have
     * @param game              Creates a game instance
     * @throws IOException      Input exception
     */
    public Player(int PlayerNumber, int playerAmount, Game game) throws IOException { //parse the deck num through
        this.game = game;
        this.playerNumber = PlayerNumber;
        this.playerHandArray = new Card[4]; //0 to 4 where 4 is the add/remove hand
        this.playerAmount = playerAmount;
        this.hasWon = false;
        this.threadName = String.valueOf(playerNumber);
        fileCheckPlayer();
    }

    /**
     * This method takes a playerNumber and checks if a file exists for the player.
     * If the file exists for the player, then the file is read but if the file
     * does not exist a file is created for the player.
     * @throws IOException          input exception
     */
    public void fileCheckPlayer() throws IOException {Pack.playerFileCheck(playerNumber);}

    /**
     * This method passes a new array of card objects and stores this
     * under the pickDeck attribute for the player.
     * @param deck            Array of card objects that will be the new pickDeck
     */
    public void setPickDeckForPlayer(Deck deck){ this.pickDeck = deck; }

    /**
     * This method passes a new array of card objects and stores this
     * under the discardDeck attribute for the player.
     * @param deck           Array of card objects that will be the new discardDeck
     */
    public void setDiscardDeckForPlayer(Deck deck){ this.discardDeck = deck; }


    /**
     * Get Method
     * This method returns the number of the player. This is often used
     * when trying to access a player object with a certain player number.
     * @return              Returns the number of the player
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    public Card[] changeHandCard(int index, Card newCard) {
        if (index <= 3) {
            playerHandArray[index] = newCard;
        }
        return playerHandArray;
    }

    /**
     * Get Method
     * This method returns the hand array of the player. This is often used
     * when we are accessing the the cards in a players hand.
     * @return                  returns the players hand
     */
    public Card[] getPlayerHandArray() {
        return playerHandArray;
    }


    /**
     * Goes through the hand array of the player and sets the discard value
     * of each card to false.
     */
    public void resetHand() {
        for (int i = 0; i < playerHandArray.length; i++) {
            playerHandArray[i].setToKeep();
        }
    }

    /**
     * This method stops the thread from the player from running. Subsequently
     * it states the player number of the player that is leaving.
     */
    public void stopThread() {
        this.hasWon = true;
        System.out.println("Player " + playerNumber + " exits game...") ;
    }

    /**
     * This method starts a thread for the player.
     */
    public void start() {
        if(playerThread == null){
            playerThread = new Thread(this,threadName);
        }
        playerThread.start();
    }


    /**
     * This method runs a thread that has been started for a player. The method
     * checks if the a winner has been found. If not, the hand of the player is
     * searched through for card objects where the indexValue is not equal to
     * the number of the player. When these cards have been found a method
     * is executed on these cards to decide which card will be discarded. When
     * the decision is made, the index of the array of the card that will be
     * discarded, is found. Using this index the player has a turn where they will
     * pick up a card and then discard a card. After the turn has been executed the
     * hand of the player is checked to see if the player has a winning hand. If
     * the player does having a winning hand, the number of the winning player is
     * displayed on the UI and states that the player has won. Subsequently
     * the winnerWrite method is executed on the player.
     */
    @Override
    public void run() {
        synchronized (this) {
            while (!hasWon) {
                if (checkWin()) {
                    System.out.println("Winner is: " + playerNumber);
                    try {
                        game.notifyEnd(playerNumber);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Pack.winnerWrite(playerNumber, playerHandArray);
                }
                if (game.getDone()) {
                    continue;
                } else {
                    Thread.interrupted();
                }
                boolean playerNumExist = searchCardNumber(playerNumber);
                if (playerNumExist) {
                    decideKeep();
                } else {
                    decideDiscard();
                }
                int indexHand = findDiscardIndex();
                try {
                    turn(indexHand);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (checkWin()) {
                    System.out.println("Winner is: " + playerNumber);
                    try {
                        game.notifyEnd(playerNumber);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Pack.winnerWrite(playerNumber, playerHandArray);
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * This executes a turn for the player. Firstly the method finds index
     * in the array of the player that their deck is going to be picked up from.
     * Then the method finds index of array of the player that their card is going
     * to be discarded to. Then a card is picked up and added to the players hand
     * and a card is discarded from the player. The details of this action are
     * displayed on the UI.
     * @param index                 index for the card to be removed from the hand
     * @throws IOException          input validation
     */
    public void turn(int index) throws IOException {


        int playerPickUp = playerNumber - 1;
        int playerDiscardDeck = playerNumber + 1;

        if(playerPickUp <= 0){
            playerPickUp = playerAmount;
        }


        if(playerDiscardDeck > playerAmount){
            playerDiscardDeck = 1;
        }

        //Makes necessary adjustments for the card to be picked up and discarded
        resetHand();
        Card a = pickDeck.pickup();
        Card discardCard = playerHandArray[index];
        discardCard.resetCard();
        playerHandArray[index] = a;
        discardDeck.discard(discardCard);
        Pack.writePlayers(playerNumber,
                a.getIndexValue(),
                playerPickUp,
                discardCard.getIndexValue(),
                playerDiscardDeck);

        System.out.println("Player " + playerNumber + " picked up " + a.getIndexValue()
                + " from player " + playerPickUp +" and discarded " + discardCard.getIndexValue()
                + " to player " + playerDiscardDeck);
    }

    /**
     * This method searches through the card objects stored in the hand array
     * of the player. It finds the card number of each object and sees if they
     * are all equal. If this is the case, True will be returned. If this is
     * not the case, False will be returned. The method will be executed
     * whenever a check needs to be made if a player has won.
     *
     * @return boolean value depending if items are equal
     */
    public boolean checkWin(){
        //Gathers together card values
        int[] cardNumbers = new int[4];
        for (int eachCard = 0; eachCard < playerHandArray.length; eachCard++) {
            cardNumbers[eachCard] = playerHandArray[eachCard].getIndexValue();
        }

        //Checking if the card values are the same
        int comparisonValue = cardNumbers[0];
        for (int eachValue = 1; eachValue < cardNumbers.length; eachValue++) {
            if (comparisonValue != cardNumbers[eachValue]) {
                return false;
            }
        }
        return true;
    }


    /**
     * Searches the hand array of the player and sees if one of their
     * cards has a card number equal to the player number, based on this
     * a boolean value is returned.
     * @param cardNumber    the players number to check
     * @return              returns boolean
     */
    public boolean searchCardNumber(int cardNumber) {
        for (int eachCard = 0; eachCard < playerHandArray.length; eachCard++) {
            int currentCardNum = playerHandArray[eachCard].getIndexValue();
            if (currentCardNum == cardNumber) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds card where the index is the player number and changes the cards attribute
     * If the card is not the players number its attribute is set to true, as this is
     * a card to be discarded
     */
    public void decideKeep() {
        if (searchCardNumber(playerNumber)) {
            for (int g = 0; g < playerHandArray.length; g++) {
                int currentCardNum = playerHandArray[g].getIndexValue();
                if (currentCardNum == playerNumber) {
                    playerHandArray[g].setToKeep();
                } else {
                    playerHandArray[g].setToDiscard();
                }
            }
        }
    }

    /**
     * Sorts hand into sorted arraylist, then from the sorted list finds the most common value in the hand
     * All other cards,  are set to true to be discarded
     * If all the cards in the players hand are different (cardNumAmount > 1) then they are all set to be discarded
     * This function runs if no cards are equal to the player number
     *
     */
    public void decideDiscard(){
        //Decides which carded should be discarded
        Card[] playerHandArray = getPlayerHandArray();
        int[] tempArray = new int[4];
        for (int i = 0; i < playerHandArray.length; i++) {
            tempArray[i] = playerHandArray[i].getIndexValue();
        }
        Arrays.sort(tempArray);
        int cardNumAmount = 1;
        int tempValue;
        int winingVal = tempArray[0];
        int count;
        for (int i = 0; i < (tempArray.length); i++) {
            count = 1;
            tempValue = tempArray[i];

            for (int j = i+1; j < tempArray.length; j++) {
                int againstVal = tempArray[j];
                if (tempValue == againstVal) {
                    count++;
                }
            }if(count > cardNumAmount) {
                winingVal = playerHandArray[i].getIndexValue();
                cardNumAmount = count;
            }
        }
        if(cardNumAmount > 1 ) {
            for (int i = 0; i < playerHandArray.length; i++) {
                if (winingVal == playerHandArray[i].getIndexValue()) {
                    playerHandArray[i].setToKeep();
                } else {
                    playerHandArray[i].setToDiscard();
                }
            }
        }
        else{
            for (int i = 0; i < playerHandArray.length; i++) {
                playerHandArray[i].setToDiscard();
            }
        }
    }


    /**
     * Returns the index to which card should be removed from the players hand
     * If the card has been in the hand for more than 3 turns then it will
     * get added to the priority index
     * This will not stagnate the game
     *
     * @return          the index for the players hand array
     */
    public int findDiscardIndex(){
        //Finds index  where the card to be discarded
        ArrayList<Integer> removeIndexes = new ArrayList<>();
        ArrayList<Integer> removePriorityIndex = new ArrayList<>();
        for (int i = 0; i < playerHandArray.length; i++) {
            if(playerHandArray[i].getDiscard()){
                if(playerHandArray[i].getTimeStep() > 3){
                    removePriorityIndex.add(i);
                }else {
                    removeIndexes.add(i);
                }
            }
        }
        Random rand = new Random();
        int index;
        if(removePriorityIndex.size() > 0){
            index = getIndex(removePriorityIndex, rand);
        }else {
            index = getIndex(removeIndexes, rand);
        }
        return index;
    }


    /**
     * Where the card attribute to discard is true in findDiscardIndex
     * The function gets a random index from the specified list and returns it
     * Any other card: its (timestep) is increased as it is not discarded this turn
     *
     * @param discardIndexList           list of indexes for potential discarded cards
     * @param rand                       Random variable to get index (if multiple)
     * @return                           returns the index for the discard card
     */
    private int getIndex(ArrayList<Integer> discardIndexList, Random rand) {
        int randomIndexToRemove;
        int index;
        int removePosition;
        randomIndexToRemove = rand.nextInt(discardIndexList.size());
        index = discardIndexList.get(randomIndexToRemove);
        removePosition = discardIndexList.indexOf(index);
        discardIndexList.remove(removePosition);
        for (Integer priorityIndex : discardIndexList) {
            for (int i = 0; i < playerHandArray.length; i++) {
                if (priorityIndex == i) {
                    playerHandArray[i].setTimeStep();
                }
            }
        }
        return index;
    }

    /**
     * If informed player has lost, will write to file as loser
     *
     * @param winningPlayer        the informant winning player
     */
    public void writeLosers(int winningPlayer) {
        Pack.writeLoser(playerNumber, winningPlayer, playerHandArray);
    }
}