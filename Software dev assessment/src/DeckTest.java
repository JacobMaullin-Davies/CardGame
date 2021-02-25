import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;


public class DeckTest {
    @Test
    public void testGetDeck(){
        Queue<Card> playerDeckArray = new LinkedList();
        Queue<Integer> expectedDeckList = new LinkedList<>();
        Queue<Integer> actualDeckList = new LinkedList<>();
        Card[] cards = new Card[4];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card(i);
            playerDeckArray.add(cards[i]);
            expectedDeckList.add(i);
        }
        for(Card s : playerDeckArray) {
            actualDeckList.add(s.getIndexValue());
        }
        
        assertEquals(actualDeckList, expectedDeckList);
    }
    @Test
    public void TestAdd(){
        Queue<Card> playerDeckArray = new LinkedList();
        Queue<Integer> expectedDeckList = new LinkedList<>();
        Card[] card = new Card[1];
        card[0] = new Card(1);
        playerDeckArray.add(card[0]);
        expectedDeckList.add(1);
        int deckSize = playerDeckArray.size();
        int expectedDecks = expectedDeckList.size();
        assertEquals(deckSize, expectedDecks);
    }
    @Test
    public void TestPickup() {
        Queue<Card> playerDeckArray = new LinkedList();
        Card[] cards = new Card[4];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card(i);
            playerDeckArray.add(cards[i]);
        }
        Card a = playerDeckArray.remove();
        Card b = cards[0];
        assertEquals(a,b);
    }
    @Test
    public void TestDiscard() {
        Queue<Card> playerDeckArray = new LinkedList();
        Card[] cards = new Card[4];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card(i);
        }
        for (int i = 0; i < cards.length - 1; i++) {
            playerDeckArray.add(cards[i]);
        }
        playerDeckArray.add(cards[3]);
        int size = playerDeckArray.size();
        assertEquals(4, size);
    }

}