import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class GameTest {
    public Game game;
    public Card[] pack;


    @Before
    public void setUp() throws IOException {
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
    }

    @Test
    public void TestGetDone(){
        boolean value = game.getDone();
        assertEquals(value, false);

    }

    @Test
    public void testGetPlayerArray() throws IOException {
        Player[] allPlayers = game.getPlayerArray();
        int[] allPlayerNumbers = new int[4];
        for (int i = 0; i < 4; i++) {
            allPlayerNumbers[i] = allPlayers[i].getPlayerNumber();
        }
        int[] expectedPlayerNumbers = {1, 2, 3, 4};
        assertArrayEquals(expectedPlayerNumbers, allPlayerNumbers);
    }
    @Test
    public void TestNotifyEnd() throws IOException {
        Player[] playerArray = new Player[4];
        for (int i = 0; i < 4; i++) {
            playerArray[i] = new Player(i, 4, game);
        }
        game.notifyEnd(1);
        boolean gameValue =game.getDone();
        assertEquals(gameValue, true);
    }
    @Test
    public void TestHandOutCards() throws IOException {
        Player[] playerArray = new Player[4];
        Deck[] decks = new Deck[4];
        Card[] allCards = pack;
        for (int i = 0; i < 4; i++) {
            playerArray[i] = new Player(i, 4, game);
            decks[i] = new Deck(i);
        }
        int count = 0;
        int tally = 0;
        // Distributing to player's hands
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
        Card[] playerHands = playerArray[0].getPlayerHandArray();
        int playerDeck = decks[0].getDeck().size();
        assertEquals(playerHands.length, 4);
        assertEquals(playerDeck, 4);
    }
}
