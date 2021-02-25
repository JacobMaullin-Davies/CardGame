import java.io.*;
import java.util.Queue;
import java.util.Random;
import java.io.File;  // Import the File class

/**
 * Pack Class
 * This handles the main interactions between the program and the text files.
 * One of the attributes in this class holds every single possible card instance
 * that will be used in the game, ready to be distributed to all the hands and
 * decks of the player. The class also has methods which will information about
 * winning players and losing players to a text file and will also read a text
 * file to generate every single possible card instance.
 *
 */
public class Pack {
    private static Card[] GameDeck;
    public String packName;
    public Integer amountOfPlayers;


    /**
     * Constructor
     * @param filename: Name of the text file that will be accessed to create the
     * card instances
     * @param playerNumber  This integer variable represents the number of
     * players in the game
     */
    public Pack(String filename, int playerNumber) {
        this.packName = filename;
        this.amountOfPlayers = playerNumber;
        GameDeck = new Card[amountOfPlayers*8];
    }


    /**
     * This method allows us to write what a player did in one of their turns.
     * So after a player has had their turn, the number of the player will be
     * written to the file along with the number of the card they picked up,
     * the number of the player they picked up from, the number of the card
     * they discarded and the number of the player they discarded to.
     * @param playerNumber       Number of the Player whose turn it was (Player A)
     * @param pickedCard         Number of Card Player A picked up
     * @param playerPickUp       Number of Player the the Player A picked up from
     * @param discardCard        Number of Card Player A Discarded
     * @param playerDiscardDeck  Number of Player, Player A discarded to
     * @throws IOException       Input exception
     */
    public static void writePlayers(int playerNumber, int pickedCard, int playerPickUp, int discardCard, int playerDiscardDeck) throws IOException {
        String filename = "player" + playerNumber + "output.txt";
        File playerFile = new File(filename);
        if (playerFile.exists()) {
            FileWriter playerFileWriter = new FileWriter(playerFile, true);
            BufferedWriter playerBuffer = new BufferedWriter(playerFileWriter);
            playerBuffer.write("Player " + playerNumber + " picked up " + pickedCard
                    + " from player " + playerPickUp + " and discarded " + discardCard
                    + " to player " + playerDiscardDeck);
            playerBuffer.newLine();
            playerBuffer.close();
            playerFileWriter.close();
        } else {
            try {
                FileWriter writePack = new FileWriter(filename);
                BufferedWriter playerBuffer = new BufferedWriter(writePack);
                playerBuffer.write("Player " + playerNumber + " picked up " + pickedCard
                        + " from player " + playerPickUp + " and discarded " + discardCard
                        + " to player " + playerDiscardDeck);
                playerBuffer.newLine();
                playerBuffer.close();
                writePack.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * This method checks if a file exists about the player in question. If
     * the file does exist the contents of the file is cleared. Otherwise a
     * new file for the player is created.
     * @param playerNum         Number of player the file will be about
     * @throws IOException      Input exception
     */
    public static void playerFileCheck(int playerNum) throws IOException {
        String filename = "player" + playerNum + "output.txt";
        File playerFile = new File(filename);
        if (playerFile.exists()) {
            PrintWriter writer = new PrintWriter(playerFile);
            writer.print("");
            writer.close();
        } else {
            playerFile.createNewFile();
        }
    }


    /**
     * This method writes information about the players that has won to a text
     * file. Firstly filePlayerCheck() is used to access the file that will be written
     * to. Then the hand of the player that has won is accessed using playerHandArray
     * and the card numbers of the object in their final hand is written to a text
     * file.
     * @param playerNumber       Number of Player that has won
     * @param playerHandArray    This is so information about the player has won
     * can be accessed
     */
    public static void winnerWrite(int playerNumber, Card[] playerHandArray) {
        String fileName = "player" + playerNumber + "output.txt";
        File playerFile = new File(fileName);
        try {
            FileWriter playerWriter = new FileWriter(playerFile, true);
            BufferedWriter br = new BufferedWriter(playerWriter);
            br.write("player " + playerNumber + " final hand: " );
            for (Card card : playerHandArray) {
                br.write(card.getIndexValue() + " ");
            }
            br.newLine();
            br.close();
            playerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method writes information about a player that has lost to a text file.
     * Firstly fileCheck() is used to access a file that will be written to about
     * the player that has lost. Then the fact that the player that has won and
     * has informed the losing player about this, is written to the text file.
     * Then the final hand of the player that has lost will be accessed from the
     * playerHandArray and written to the text file.
     * @param playerNumber       Player the text file will be about
     * @param winningPlayer      Number of the Player that has won
     * @param playerHandArray    Used so the necessary information can be accessed
     */
    public static void writeLoser(int playerNumber, int winningPlayer, Card[] playerHandArray) {
        String filename = "player" + playerNumber + "output.txt";
        File myObj = new File(filename);
        try {
            FileWriter fr = new FileWriter(myObj, true);
            BufferedWriter playerBuffer = new BufferedWriter(fr);
            playerBuffer.write("player " + winningPlayer + " has informed player "
                    + playerNumber + " they have won." +
                    " Players final hand: " );
            for (Card card : playerHandArray) {
                playerBuffer.write(card.getIndexValue() + " ");
            }
            playerBuffer.newLine();
            playerBuffer.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function passes the number of a player as an argument, and then
     * the deck card numbers of the deck of that player will be written to
     * a text file. Firstly the directory will be searched for a text file
     * about this deck. If this file exists the, deck of the player will be
     * written to the text file. On the other hand, if the file does not exist
     * a file is created so the deck can be written to it.
     * @param playerNumber       Number of player that you want to write the deck about
     * @param deck               The deck of the player that will be written to the text file
     * @throws IOException       Input exception
     */
    public static void deckWrite(int playerNumber, Queue<Card> deck) throws IOException {
        String fileName = "Deck_" + playerNumber + ".txt";
        File fileLocation = new File(fileName);
        if (fileLocation.exists()){
            FileWriter fileDrawer = new FileWriter(fileLocation);
            BufferedWriter myWriter = new BufferedWriter(fileDrawer);
            myWriter.write("Player  " + playerNumber + " final Deck: ");
            for(Card s : deck) {
                myWriter.write(s.getIndexValue() + " ");
            }
            myWriter.close();
            fileDrawer.close();
        }else {
            fileLocation.createNewFile();
            FileWriter fileDrawer = new FileWriter(fileLocation);
            BufferedWriter myWriter = new BufferedWriter(fileDrawer);
            myWriter.write("Player:  " + playerNumber + "Final Deck: ");
            for(Card s : deck) {
                myWriter.write(s.getIndexValue() + " ");
            }
            myWriter.close();
            fileDrawer.close();
        }
    }

    /**
     * This method takes a string argument and sees if a file exists with this
     * filename. If the file does exist the readPack() method is ran on the
     * file. However, if the file does not exist the writePack() method is
     * used to create a text file.
     * @param fileName          String which will be altered slightly so it can be
     *                          searched for as a text file in the directory
     * @param playerCount       The amount of players in the game
     */
    public void fileCheck(String fileName, int playerCount) throws InvalidPackLoad {
        File myObj = new File(fileName + ".txt");
        if (myObj.exists()) {
            System.out.println("File found");
            readPack(fileName);
        } else {
            System.out.println("File created: " + myObj.getName());
            writePack(playerCount);
            readPack(fileName);
        }
    }


    /**
     * This method takes an array of card objects as an argument. It then swaps
     * each card object with another random card object in the array. Consequently
     * the array will be considered as 'shuffled'.
     * @param packArray          Array that needs to be shuffled
     * @return                   Returns the shuffled array
     */
    public Card[] shuffleArray(Card[] packArray) {
        Random randomData = new Random();
        for (int i = 0; i < packArray.length; i++) {
            int randomIndexToSwap = randomData.nextInt(packArray.length);
            Card temp = packArray[randomIndexToSwap];
            packArray[randomIndexToSwap] = packArray[i];
            packArray[i] = temp;
        }
        return packArray;
    }


    /**
     * This method runs a loop that will create every single possible card
     * instance. Then the indexValue of each card instance is written onto
     * a separate row onto a text file.
     * @param n              Amount of Players in the Game
     */
    public void writePack(int n) {
        int cardNum = 8 * n;
        int deckNum = 2 * n;
        Card[] packArray = new Card[cardNum];
        int arraySection = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < deckNum + 1; j++) {
                packArray[arraySection] = new Card(j);
                arraySection++;
            }
        }
        try {
            FileWriter writePack = new FileWriter(packName + ".txt");
            BufferedWriter playerBuffer = new BufferedWriter(writePack);
            Card[] pack = shuffleArray(packArray);
            for (int i = 0; i < pack.length; i++) {
                int a = pack[i].getIndexValue();
                playerBuffer.write(String.valueOf(a));
                if(i != pack.length-1){
                    playerBuffer.newLine();
                }
            }

            playerBuffer.close();
            writePack.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    /**
     * This method takes a string argument uses it to find a text file
     * in the directory. Each row of the text file will be an integer.
     * Consequently each row of the text file will be used to create a card
     * instance which will be stored as an element in the GameDeck attribute.
     * Then the array is shuffled using shuffleArray().
     * @param filenameRead          name of the file to read
     */
    public void readPack(String filenameRead) throws InvalidPackLoad {
        try {
            File packFile = new File(filenameRead + ".txt");
            BufferedReader playerBuffer = new BufferedReader(new FileReader(packFile));
            String st;

            for (int j = 0; j < amountOfPlayers*8; j++) {
                st = playerBuffer.readLine();
                GameDeck[j] = new Card(Integer.parseInt(st));
            }
            playerBuffer.close();
        } catch (IOException e) {
            throw new InvalidPackLoad("Pack has invalid card number(s) in file for game");
        }

        for (int i = 0; i < GameDeck.length; i++) {
            if (GameDeck[i] == null ) {
                throw new InvalidPackLoad("Invalid pack load, too few cards in file for game");
            }else if(GameDeck[i].getIndexValue() < 1){
                throw new InvalidPackLoad("Pack has invalid card number(s) in file for game");
            }
        }
        if(GameDeck.length < amountOfPlayers*8){
            throw new InvalidPackLoad("Invalid pack load, too few cards in file for game");
        }else if (GameDeck.length > amountOfPlayers*8) {
            throw new InvalidPackLoad("Invalid pack load, too many cards in file for game");
        }
        Card[] Pack = shuffleArray(GameDeck);
        setGameDeck(Pack);
    }
    /**
     * Get Method
     * Returns the array of card instances stored in the GameDeck attribute
     * @return          returns the game deck
     */
    public static Card[] getGameDeck() {
        return GameDeck;
    }

    /**
     * This sets the GameDeck attribute to a new array of card objects
     * @param newCardDeck       New array of card objects
     */
    public static void setGameDeck(Card[] newCardDeck) {
        GameDeck = newCardDeck;
    }
}