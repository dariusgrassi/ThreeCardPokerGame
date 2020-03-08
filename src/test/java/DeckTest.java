import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DeckTest {

    Deck d;

    @BeforeEach
    void init(){
        d = new Deck();
    }

    @Test
    void deckExists() {
        assertNotNull(d);
    }

    @Test
    void deckHasCards() {
        assertEquals(Card.class, d.get(0).getClass(), "Should be class Card");
    }

    @Test
    void cardValues() {
        assertEquals((int)d.get(0).getValue(), d.get(0).getValue(), "Should be type integer");
    }

    @Test
    void newDeckExists() {
        d.newDeck();
        assertNotNull(d, "Deck should still be an object");
    }

    //Should usually pass, but can possibly fail
    @Test
    void newDeckIsShuffled() {
        Card firstVal = d.get(0);
        d.newDeck();

        Card secondVal = d.get(0);

        assertNotEquals(firstVal, secondVal, "First and second should almost never be equal");
    }

    @Test
    void newDeckCard() {
        Card firstVal;
        d.newDeck();
        firstVal = d.get(0);

        assertNotNull(firstVal.getSuit(), "Suit of a card in a shuffled deck with newDeck() should not be null");
    }

    @Test
    void newDeckSize(){
        d.remove(0);
        int initSize = d.size();
        d.newDeck();

        assertNotEquals(initSize, d.size(), "Size should be reset after newDeck()");
    }

    @Test
    void newDeckIsFull(){
        d.remove(0);
        d.remove(1);
        d.remove(2);

        d.newDeck();
        d.remove(0);

        assertEquals(51, d.size(), "Size should stay at 52 once newDeck() is called");
    }
}
