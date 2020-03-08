import java.util.ArrayList;
import java.util.Collections;

//Represents 52 card standard deck of cards
//Constructor creates new deck of 52 cards sorted in random order
//2nd method newDeck() clears all cards and creates new deck of 52 randomly sorted cards
public class Deck extends ArrayList<Card> {

    private char[] suits = {'C', 'D', 'S', 'H'};

    //Creates a new random deck of 52 cards and shuffles it
    Deck() {
        generateDeck();
        Collections.shuffle(this);
    }

    //Essentially replaces deck with a new 52 card shuffled deck
    void newDeck(){
        this.clear();
        generateDeck();
        Collections.shuffle(this);
    }

    //This method can be used twice, to generate the initial deck and the new deck for newDeck()
    void generateDeck(){
        Card current;

        for(int i = 0; i < suits.length; i++){
            for(int j = 2; j <= 14; j++){
                current = new Card(suits[i], j);
                this.add(current);
            }
        }
    }

    //Testing method to check contents of the deck
    void printDeck(){
        for(Card c : this){
            System.out.println(c.getSuit() + " " + c.getValue());
        }
    }
}
