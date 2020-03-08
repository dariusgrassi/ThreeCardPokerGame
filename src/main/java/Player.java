import java.util.ArrayList;

public class Player {
    ArrayList<Card> hand;
    int anteBet;
    int playBet;
    int pairPlusBet;
    int totalWinnings;

    //Creates player object with empty hand
    Player(){
        hand = new ArrayList<>();
        pairPlusBet = 0;
        totalWinnings = 1000;
    }

    //Testing method to check player's hand
    //Same idea as showHand() in Dealer class, but for Player objects
    public String showHand(){
        String display = " ";
        String niceSuit = " ";

        for(Card c : hand){
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

            if(c != hand.get(2))
                display += c.getValue() + " of " + niceSuit + ", ";
            else
                display += c.getValue() + " of " + niceSuit;
        }

        return display;
    }
}
