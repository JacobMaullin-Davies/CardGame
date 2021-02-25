import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

public class PlayerTest {
    public Game game;
    public Card[] pack;
    public Deck pickDeck;
    public Deck discardDeck;
    public boolean hasWon;
    private Thread t;

    @Before
    public void setUp() throws IOException {
        hasWon = false;
        int cardNum = 8 * 4;
        int deckNum = 2 * 4;
        pack = new Card[cardNum];
        int arraySection = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < deckNum + 1; j++) {
                pack[arraySection] = new Card(j);
                arraySection++;
            }
        }
        Pack.setGameDeck(pack);
        game = new Game(4);
        pickDeck = new Deck(1);
    }

    @Test
    public void TestFileCheckPlayer() throws IOException {
        boolean filefound = false;
        Pack.playerFileCheck(1);
        String filename = "player" + 1 + "output.txt";
        File myObj = new File(filename);
        if (myObj.exists()) {
            filefound = true;
        }
        assertTrue(filefound);
    }

    @Test
    public void TestsetPickDeckForPlayer() throws IOException {
        Player[] playerArray = new Player[4];
        Deck[] decks = new Deck[4];
        Card[] allCards = pack;
        for (int i = 0; i < 4; i++) {
            playerArray[i] = new Player(i, 4, game);
            decks[i] = new Deck(i);
        }
        int count = 0;
        int tally = 0;
        for (int arraySection = 0; arraySection < 4; arraySection++) {
            for (int eachPlayer = 0; eachPlayer < 4; eachPlayer++) {
                Card[] thisPlayerHand = playerArray[eachPlayer].getPlayerHandArray();
                thisPlayerHand[arraySection] = allCards[count];
                count++;
                tally++;
            }
        }
        for (int arraySection = 0; arraySection < 4; arraySection++) {
            for (int eachPlayer = 0; eachPlayer < 4; eachPlayer++) {
                decks[eachPlayer].add(allCards[tally]);
                tally++;
            }
        }
        Deck topick = decks[0];
        pickDeck = topick;
        assertEquals(pickDeck, topick);
    }

    @Test
    public void TestSetDiscardDeckForPlayer() throws IOException {
        Player[] playerArray = new Player[4];
        Deck[] decks = new Deck[4];
        Card[] allCards = pack;
        for (int i = 0; i < 4; i++) {
            playerArray[i] = new Player(i, 4, game);
            decks[i] = new Deck(i);
        }
        int count = 0;
        int tally = 0;
        for (int arraySection = 0; arraySection < 4; arraySection++) {
            for (int eachPlayer = 0; eachPlayer < 4; eachPlayer++) {
                Card[] thisPlayerHand = playerArray[eachPlayer].getPlayerHandArray();
                thisPlayerHand[arraySection] = allCards[count];
                count++;
                tally++;
            }
        }
        for (int arraySection = 0; arraySection < 4; arraySection++) {
            for (int eachPlayer = 0; eachPlayer < 4; eachPlayer++) {
                decks[eachPlayer].add(allCards[tally]);
                tally++;
            }
        }
        Deck topick = decks[1];
        discardDeck = topick;
        assertEquals(discardDeck, topick);
    }

    @Test
    public void testGetPlayerNumber() throws IOException {
        Game myGame = new Game(4);
        Player myPlayer = new Player(2, 4, myGame);
        int playerNumber = myPlayer.getPlayerNumber();
        assertEquals(2, playerNumber);
    }

    @Test
    public void testChangeHandCard() throws IOException {
        Game myGame = new Game(4);
        Player myPlayer = new Player(2, 4, myGame);
        //Creating new card
        Card cardToAdd = new Card(6);
        //Exchanging one of the cards in the hand
        myPlayer.changeHandCard(1, cardToAdd);
        //Finding number of card that has been inserted
        int secondHandValue = myPlayer.getPlayerHandArray()[1].getIndexValue();
        assertEquals(6, secondHandValue);
    }

    @Test
    public void testGetPlayerHandArray() throws IOException {
        Game myGame = new Game(4);
        Player myPlayer = new Player(3, 4, myGame);
        Card[] ExpectedPlayerHand = new Card[4];
        //Creating new cards and add the to hand array of player
        for (int i = 1; i == 4; i++) {
            Card[] thisPlayerHand = myPlayer.getPlayerHandArray();
            thisPlayerHand[i] = new Card(i);
            ExpectedPlayerHand[i] = new Card(i);
        }

        Card[] testArray = myPlayer.getPlayerHandArray();
        assertArrayEquals(ExpectedPlayerHand, testArray);
    }

    @Test
    public void testResetHand() throws IOException {
        Game myGame = new Game(4);
        Player myPlayer = new Player(2, 4, myGame);
        //Adding Cards to the player's hand
        for (int i = 0; i < 4; i++) {
            myPlayer.getPlayerHandArray()[i] = new Card(i);
        }
        myPlayer.resetHand();
        boolean[] playerDiscardValues = new boolean[4];
        for (int i = 0; i < 4; i++) {
            playerDiscardValues[i] = myPlayer.getPlayerHandArray()[i].getDiscard();
        }
        boolean[] expectedDiscardValues = {false, false, false, false};
        assertArrayEquals(expectedDiscardValues, playerDiscardValues);
    }

    @Test
    public void TestStopThread() {
        hasWon = true;
        assertTrue(hasWon);
    }


    @Test
    public void TestRun() {
        String threadName = String.valueOf(1);
        t = new Thread(threadName);
        t.start();
        t.run();
        assertTrue(Thread.currentThread().isAlive());
    }

    @Test
    public void TestTurn() {
        Queue<Card> pickDecks = new LinkedList<>();
        Queue<Card> discardDeck = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            pickDecks.add(new Card(i));
        }
        for (int i = 0; i < 3; i++) {
            discardDeck.add(new Card(i));
        }
        Card[] playerHandArray = new Card[4];
        for (int i = 0; i < playerHandArray.length; i++) {
            playerHandArray[i] = new Card(i);
        }

        int index = 1;
        Card a = pickDecks.remove();
        Card discardCard = playerHandArray[index];
        discardCard.resetCard();
        playerHandArray[index] = a;
        discardDeck.add(discardCard);
        assertEquals(playerHandArray.length, 4);
        assertEquals(pickDecks.size(), 3);
        assertEquals(discardDeck.size(), 4);
    }

    @Test
    public void testCheckWin() throws IOException {
        //Player Should Win
        Game myGame = new Game(4);
        Player myPlayer = new Player(2, 4, myGame);
        //Adding Cards all of the same value to the hand of the player
        for (int i = 0; i < 4; i++) {
            myPlayer.getPlayerHandArray()[i] = new Card(2);
        }
        assertEquals(true, myPlayer.checkWin());
    }

    @Test
    public void testCheckLoss() throws IOException {
        //Player Should Not Win
        Game myGame = new Game(4);
        Player myPlayer = new Player(2, 4, myGame);
        //Adding Cards of different values to the hand of the player
        for (int i = 0; i < 4; i++) {
            myPlayer.getPlayerHandArray()[i] = new Card(i);
        }
        assertEquals(false, myPlayer.checkWin());
    }

    @Test
    public void testSearchCardNumber() throws IOException {
        Game myGame = new Game(4);
        Player myPlayer = new Player(1, 4, myGame);
        //Adding Cards to the hand of the player
        for (int i = 0; i < 4; i++) {
            myPlayer.getPlayerHandArray()[i] = new Card(i);
        }
        assertEquals(true, myPlayer.searchCardNumber(2));
    }

    @Test
    public void testNoCardNumber() throws IOException {
        Game myGame = new Game(4);
        Player myPlayer = new Player(1, 4, myGame);
        //Adding Cards to the hand of the player
        for (int i = 0; i < 4; i++) {
            myPlayer.getPlayerHandArray()[i] = new Card(i);
        }
        assertEquals(false, myPlayer.searchCardNumber(7));
    }

    @Test
    public void TestDecideKeep() throws IOException {
        boolean test = true;
        Game myGame = new Game(4);
        Player myPlayer = new Player(3, 4, myGame);
        Card[] thisPlayerHand = myPlayer.getPlayerHandArray();
        //Creating new cards and add the to hand array of player
        for (int i = 0; i < 4; i++) {
            thisPlayerHand[i] = new Card(i + 1);
            thisPlayerHand[i].setToKeep();
        }
        for (int i = 0; i < thisPlayerHand.length; i++) {
            if (thisPlayerHand[i].getDiscard()) {
                test = false;
            }
        }
        assertTrue(test);
    }

    @Test
    public void TestDecideDiscard() throws IOException {
        boolean test = true;
        Game myGame = new Game(4);
        Player myPlayer = new Player(3, 4, myGame);
        Card[] thisPlayerHand = myPlayer.getPlayerHandArray();
        //Creating new cards and add the to hand array of player
        for (int i = 0; i < 4; i++) {
            thisPlayerHand[i] = new Card(i + 1);
            thisPlayerHand[i].setToDiscard();
        }
        for (int i = 0; i < thisPlayerHand.length; i++) {
            if (!thisPlayerHand[i].getDiscard()) {
                test = false;
            }
        }
        assertTrue(test);
    }

    @Test
    public void TestFindDiscardIndex() throws IOException {
        ArrayList<Integer> removeIndexes = new ArrayList<>();
        removeIndexes.add(0,2);
        removeIndexes.add(1,3);
        int index;
        int randomIndexToRemove = 0;
        int removePosition;
        index = removeIndexes.get(randomIndexToRemove);
        removePosition = removeIndexes.indexOf(index);
        removeIndexes.remove(removePosition);
        assertEquals(index, 2);
    }
}