import java.util.ArrayList;
import java.util.Collections;

public class ThreeCardLogic {

    //Returns value based on hand. If nothing is found, returns 0.
    public static int evalHand(ArrayList<Card> hand){

        //Create an ordered ArrayList of the values in the hand
        ArrayList<Integer> values = getValues(hand);

        //Performs checks for certain situations here.
        boolean isSequential = checkSequentialOrder(values);
        boolean isSameSuit = checkSameSuit(hand);
        boolean isSameValue = checkSameValue(values);
        boolean isPair = checkPair(hand);

        //Check for straight flush
        if(isSameSuit && isSequential){
            return 1;
        }

        //Check for three of a kind
        if(isSameValue){
            return 2;
        }

        //Check for a straight
        if(isSequential){
            return 3;
        }

        //Check for a flush
        if(isSameSuit){
            return 4;
        }

        //Check for a pair
        if(isPair){
            return 5;
        }

        //Player only had a high card
        return 0;
    }


    //Evaluates winnings from pair plus bet if hand is found
    public static int evalPPWinnings(ArrayList<Card> hand, int bet){
        int handResult = evalHand(hand);    //Evaluates integer value of hand

        //Straight flush: bet * 40
        if(handResult == 1)
            return bet * 40;

        //3 of a kind: bet * 30
        else if(handResult == 2)
            return bet * 30;

        //Straight: bet * 6
        else if(handResult == 3)
            return bet * 6;

        //Flush: bet * 3
        else if(handResult == 4)
            return bet * 3;

        //Pair: bet * 1
        else if(handResult == 5)
            return bet;

        //High card: lose PairPlus bet
        else
            return 0;
    }

    //Compare dealer and player hands and return integer based on who won
    //Returns 0 on a tie, 1 if dealer wins, 2 if player wins
    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player){

        ArrayList<Integer> dealerVals = getValues(dealer);
        ArrayList<Integer> playerVals = getValues(player);

        //Return 0 if dealer didnt have at least a queen
        if(!dealerHasQueen(dealer)){
            return 0;
        }

        //Get values for player and dealer hands
        int dealerEval = evalHand(dealer);
        int playerEval = evalHand(player);

        //Compare values of cards to handle cases of ties
        //Returns whoever has better hand. If hands are equal, returns a 0
        int tieResult = findHighestCard(dealerVals, playerVals);

        //Return 1 if dealer hand won (if dealer isnt 0)
        if(dealerEval < playerEval && dealerEval != 0)
            return 1;

        //If dealer has high card and player has anything better
        if(dealerEval == 0 && playerEval > 0){
            return 2;
        }

        //If player has high card and dealer has anything better
        if(playerEval == 0 && dealerEval > 0){
            return 1;
        }

        //Return 2 if player hand won (if player isn't 0)
        if(playerEval < dealerEval && playerEval != 0)
            return 2;


        //If tie
        else{
            //If both high card
            if(playerEval == 0){
                //Higher card wins, loop until higher card is found
                return tieResult;
            }

            //If both pair
            else if(playerEval == 5){

                //Highest pair wins
                //If player pair higher than dealer pair, player wins
                if(getPair(playerVals) > getPair(dealerVals)){
                    return 2;
                }

                //If dealer pair higher than player pair, dealer wins
                if(getPair(dealerVals) > getPair(playerVals)){
                    return 1;
                }

                //If both same pair, loop until higher card is found
                if(getPair(dealerVals) == getPair(playerVals)){
                    return tieResult;
                }
            }

            //If both flush, straight, three of a kind, or straight flush, return highest card
            return tieResult;
        }
    }

    //Checks if the values of a hand are in sequential order
    public static boolean checkSequentialOrder(ArrayList<Integer> values){

        //Start one index ahead of start of ArrayList, and compare current value to previous one
        for(int i = 1; i < values.size(); i++){
            if(values.get(i) != (values.get(i-1) + 1)){
                return false;
            }
        }

        return true;
    }

    //Checks if all suits are equivalent in a hand
    public static boolean checkSameSuit(ArrayList<Card> hand){

        //If the current suit doesn't match the previous one, return false
        for(int i = 1; i < hand.size(); i++){
            if(hand.get(i).getSuit() != (hand.get(i-1).getSuit())){
                return false;
            }
        }

        return true;
    }

    //Checks if all card values are equivalent in a hand
    public static boolean checkSameValue(ArrayList<Integer> values){

        //If they are, then return true
        if(values.get(0) == values.get(1) && values.get(0) == values.get(2)){
            return true;
        }

        return false;
    }

    //Returns if a pair exists in a hand. We don't care about what the pair is for this method.
    public static boolean checkPair(ArrayList<Card> hand){
        //Grab integer value of each card in the hand
        int suitOne = hand.get(0).getValue();
        int suitTwo = hand.get(1).getValue();
        int suitThree = hand.get(2).getValue();

        //And compare them here
        if(suitOne == suitTwo || suitOne == suitThree || suitTwo == suitThree){
            return true;
        }

        return false;
    }

    //Returns the value of a pair found in a hand
    public static int getPair(ArrayList<Integer> values){
        int valOne = values.get(0);
        int valTwo = values.get(1);
        int valThree = values.get(2);

        //If any value matches another, you have a pair
        if(valOne == valTwo || valOne == valThree)
            return valOne;

        if(valTwo == valThree)
            return valTwo;

        return -999;
    }

    //Checks if dealer has at least a queen high. If not, returns false.
    public static boolean dealerHasQueen(ArrayList<Card> dealer){
        for(Card c : dealer){
            //Queen high is denoted by being a value of 12 or above
            if(c.getValue() >= 12){
                return true;
            }
        }

        return false;
    }

    //Stores values of a hand into an ArrayList and sorts them in ascending order
    public static ArrayList<Integer> getValues(ArrayList<Card> hand){
        ArrayList<Integer> values = new ArrayList<>();

        Card first = hand.get(0);
        Card second = hand.get(1);
        Card third = hand.get(2);

        values.add(first.getValue());
        values.add(second.getValue());
        values.add(third.getValue());

        Collections.sort(values);

        return values;
    }

    //Loops through each hand, compares the highest card. If highest are equal, compares the next highest
    public static int findHighestCard(ArrayList<Integer> dealer, ArrayList<Integer> player){

        //Size of every hand will be 3
        for(int i = 2; i >= 0; i--){

            //Returns 1 if dealer has highest card
            if(dealer.get(i) > player.get(i)){
                return 1;
            }

            //Returns 2 if player has highest card
            else if(player.get(i) > dealer.get(i)){
                return 2;
            }
        }

        //Returns 0 if every card ties, thus hands are even
        return 0;
    }
}
