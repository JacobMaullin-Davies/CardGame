import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void testGetIndexValue() {
        Card myCard = new Card(5);
        int cardValue = myCard.getIndexValue();
        assertEquals(5,cardValue);
    }
    @Test
    public void testGetTimeStep() {
        Card myCard = new Card(4);
        //Increments time step by 1
        myCard.setTimeStep();
        myCard.setTimeStep();
        int cardTimeStep = myCard.getTimeStep();
        assertEquals(2, cardTimeStep);
    }
    @Test
    public void testSetTimeStep() {
        Card myCard = new Card(6);
        myCard.setTimeStep();
        int cardTimeStep = myCard.getTimeStep();
        assertEquals(1, cardTimeStep);
    }
    @Test
    public void testGetDiscard() {
        Card myCard = new Card(1);
        boolean discardValue = myCard.getDiscard();
        assertFalse(discardValue);
    }
    @Test
    public void testSetToDiscard() {
        Card myCard = new Card(7);
        myCard.setToDiscard();
        boolean discardValue = myCard.getDiscard();
        assertTrue(discardValue);
    }
    @Test
    public void testSetToKeep() {
        Card myCard = new Card(2);
        myCard.setToKeep();
        boolean discardValue = myCard.getDiscard();
        assertEquals(false, discardValue);
    }
    @Test
    public void testResetCard(){
        Card myCard = new Card(2);
        myCard.setToKeep();
        myCard.setTimeStep();
        myCard.resetCard();
        boolean cardValue = myCard.getDiscard();
        int cardTimeStep = myCard.getTimeStep();
        assertEquals(0, cardTimeStep);
        assertEquals(false, cardValue);
    }
}