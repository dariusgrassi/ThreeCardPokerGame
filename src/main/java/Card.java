/*
Represents a card in a deck of 52. Suit will be either 'C', 'D', 'S' or 'H'
Value will be integer value b/w 2 and 14 (ace high)
 */

public class Card {
    private char suit;
    private int value;

    //Creates card object with data fields suit and value
    Card(char suit, int value){
        this.suit = suit;
        this.value = value;
    }

    //Getters for encapsulation

    char getSuit(){
        return suit;
    }

    int getValue(){
        return value;
    }
}
