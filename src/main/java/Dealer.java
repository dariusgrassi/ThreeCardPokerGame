import java.util.ArrayList;
import java.util.Random;

public class Dealer {
    Deck theDeck;
    ArrayList<Card> dealersHand;

    Dealer(){
        this.theDeck = new Deck();
    }

    //Returns arrayList of 3 random cards removed from the deck
    public ArrayList<Card> dealHand(){
        Random r = new Random();
        ArrayList<Card> hand = new ArrayList<>();

        //If deck size is below 34, generate a brand new deck
        if(theDeck.size() < 34){
            theDeck.newDeck();
        }

        //Find first card for hand
        int firstCardLocation = r.nextInt(theDeck.size());
        Card firstCard = theDeck.get(firstCardLocation);
        theDeck.remove(firstCardLocation);


        //Find second card for hand from remaining deck
        int secondCardLocation = r.nextInt(theDeck.size());
        Card secondCard = theDeck.get(secondCardLocation);
        theDeck.remove(secondCardLocation);


        //Find third card for hand from remaining deck
        int thirdCardLocation = r.nextInt(theDeck.size());
        Card thirdCard = theDeck.get(thirdCardLocation);
        theDeck.remove(thirdCardLocation);

        //Store in the cards
        hand.add(firstCard);
        hand.add(secondCard);
        hand.add(thirdCard);

        return hand;
    }

    //Testing method to print the dealer's hand
    //Also used for GUI hand display
    public String showHand(){
        String display = " ";
        String niceSuit = " ";

        for(Card c : dealersHand){
            if(c.getSuit() == 'C'){
                niceSuit = "Clubs";
            }
            else if(c.getSuit() == 'D'){
                niceSuit = "Diamonds";
            }
            else if(c.getSuit() == 'S'){
                niceSuit = "Spades";
            }
            else {
                niceSuit = "Hearts";
            }

            //Gives a nice list of the cards in the dealer's hand
            if(c != dealersHand.get(2))
                display += c.getValue() + " of " + niceSuit + ", ";
            else
                display += c.getValue() + " of " + niceSuit;
        }

        return display;
    }
}
