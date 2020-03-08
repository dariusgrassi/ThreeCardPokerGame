import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DealerTest {
    Dealer d;
    ArrayList<Card> hand;

    @BeforeEach
    void init(){
        d = new Dealer();
        hand = new ArrayList<>();
    }

    @Test
    void dealerExists(){
        assertNotNull(d, "Dealer object should be initialized");
    }

    @Test
    void dealerDeckExists(){
        assertNotNull(d.theDeck, "Dealer should have a deck as well");
    }

    @Test
    void checkDealHand(){
        hand = d.dealHand();

        assertNotNull(hand, "Dealer's hand should exist and hold three cards");
    }

    @Test
    void checkDealHandTwice(){
        hand = d.dealHand();

        ArrayList<Card> handTwo = d.dealHand();

        assertNotEquals(hand, handTwo, "dealHand() should return two different array lists of different cards");
    }

    @Test
    void checkDeckSize(){
        hand = d.dealHand();

        assertNotEquals(52, d.theDeck.size(), "Deck should no longer be size 52 if dealHand() called");
    }

    @Test
    void checkDeckSizeBefore34(){
        hand = d.dealHand();    //49
        hand = d.dealHand();    //46
        hand = d.dealHand();    //43
        hand = d.dealHand();    //40
        hand = d.dealHand();    //37
        hand = d.dealHand();    //34

        assertEquals(34, d.theDeck.size(), "Deck size should stay 34");
    }

    @Test
    void checkDeckAfter34(){
        hand = d.dealHand();    //49
        hand = d.dealHand();    //46
        hand = d.dealHand();    //43
        hand = d.dealHand();    //40
        hand = d.dealHand();    //37
        hand = d.dealHand();    //34
        hand = d.dealHand();    //31

        //Sees deck size is below 34, should now reset to 52, then remove 3
        hand = d.dealHand();

        assertEquals(49, d.theDeck.size(), "Deck size should reset to 52");
    }

}
