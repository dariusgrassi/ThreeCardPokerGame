import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class ThreeCardTest {
	Player player1;
	Player player2;
	Dealer darius;

	@BeforeEach
	void init(){
		player1 = new Player();
		player2 = new Player();
		darius = new Dealer();

		player1.hand = darius.dealHand();
		player2.hand = darius.dealHand();
		darius.dealersHand = darius.dealHand();
	}

	@Test
	void checkCards(){
		assertNotEquals(player1.hand, player2.hand);
	}

	@Test
	void checkHighCard(){
		Card first = new Card('C', 5);
		Card second = new Card('D', 6);
		Card third = new Card('D', 14);

		ArrayList<Card> hand = new ArrayList<Card>(){
			{
				add(first);
				add(second);
				add(third);
			}
		};

		assertEquals(0, ThreeCardLogic.evalHand(hand),  "Should be nothing");
	}

	@Test
	void checkPair(){
		Card first = new Card('C', 5);
		Card second = new Card('D', 5);
		Card third = new Card('D', 14);

		ArrayList<Card> hand = new ArrayList<Card>(){
			{
				add(first);
				add(second);
				add(third);
			}
		};

		assertEquals(5, ThreeCardLogic.evalHand(hand),  "Should be a pair of 5's");
	}

	@Test
	void checkFlush(){
		Card first = new Card('D', 5);
		Card second = new Card('D', 2);
		Card third = new Card('D', 14);

		ArrayList<Card> hand = new ArrayList<Card>(){
			{
				add(first);
				add(second);
				add(third);
			}
		};

		assertEquals(4, ThreeCardLogic.evalHand(hand),  "Should be a flush");
	}

	@Test
	void checkStraight(){
		Card first = new Card('H', 10);
		Card second = new Card('D', 8);
		Card third = new Card('D', 9);

		ArrayList<Card> hand = new ArrayList<Card>(){
			{
				add(first);
				add(second);
				add(third);
			}
		};

		assertEquals(3, ThreeCardLogic.evalHand(hand),  "Should be a straight");
	}

	@Test
	void checkThreeOfAKind(){
		Card first = new Card('D', 5);
		Card second = new Card('C', 5);
		Card third = new Card('S', 5);

		ArrayList<Card> hand = new ArrayList<Card>(){
			{
				add(first);
				add(second);
				add(third);
			}
		};

		assertEquals(2, ThreeCardLogic.evalHand(hand),  "Should be a three-of-a-kind");
	}

	@Test
	void checkStraightFlush(){
		Card first = new Card('H', 4);
		Card second = new Card('H', 6);
		Card third = new Card('H', 5);

		ArrayList<Card> hand = new ArrayList<Card>(){
			{
				add(first);
				add(second);
				add(third);
			}
		};

		assertEquals(1, ThreeCardLogic.evalHand(hand),  "Should be a straight flush");
	}

	@Test
	void checkWinningsHighCard(){
		Card first = new Card('C', 5);
		Card second = new Card('D', 6);
		Card third = new Card('D', 14);

		ArrayList<Card> hand = new ArrayList<Card>(){
			{
				add(first);
				add(second);
				add(third);
			}
		};

		assertEquals(0, ThreeCardLogic.evalPPWinnings(hand, 25),  "Should lose bet");
	}

	@Test
	void checkWinningsPair(){
		Card first = new Card('C', 5);
		Card second = new Card('D', 5);
		Card third = new Card('D', 14);

		ArrayList<Card> hand = new ArrayList<Card>(){
			{
				add(first);
				add(second);
				add(third);
			}
		};

		assertEquals(25, ThreeCardLogic.evalPPWinnings(hand, 25),  "Should get bet back");
	}

	@Test
	void checkWinningsFlush(){
		Card first = new Card('D', 5);
		Card second = new Card('D', 2);
		Card third = new Card('D', 14);

		ArrayList<Card> hand = new ArrayList<Card>(){
			{
				add(first);
				add(second);
				add(third);
			}
		};

		assertEquals(75, ThreeCardLogic.evalPPWinnings(hand, 25),  "Should triple winnings");
	}

	@Test
	void checkWinningsStraight(){
		Card first = new Card('H', 10);
		Card second = new Card('D', 8);
		Card third = new Card('D', 9);

		ArrayList<Card> hand = new ArrayList<Card>(){
			{
				add(first);
				add(second);
				add(third);
			}
		};

		assertEquals(150, ThreeCardLogic.evalPPWinnings(hand, 25),  "Should be bet * 6");
	}

	@Test
	void checkWinningsThreeOfAKind(){
		Card first = new Card('D', 5);
		Card second = new Card('C', 5);
		Card third = new Card('S', 5);

		ArrayList<Card> hand = new ArrayList<Card>(){
			{
				add(first);
				add(second);
				add(third);
			}
		};

		assertEquals(750, ThreeCardLogic.evalPPWinnings(hand, 25),  "Should be bet * 30");
	}

	@Test
	void checkWinningsStraightFlush(){
		Card first = new Card('H', 4);
		Card second = new Card('H', 6);
		Card third = new Card('H', 5);

		ArrayList<Card> hand = new ArrayList<Card>(){
			{
				add(first);
				add(second);
				add(third);
			}
		};

		assertEquals(1000, ThreeCardLogic.evalPPWinnings(hand, 25),  "Should be bet * 40");
	}

	@Test
	void compareHandsDealerNoQueen(){
		Card firstP = new Card('H', 4);
		Card secondP = new Card('S', 6);
		Card thirdP = new Card('D', 4);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('C', 10);
		Card secondD = new Card('H', 6);
		Card thirdD = new Card('H', 5);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(0, ThreeCardLogic.compareHands(dealer, player), "Dealer should not be accepted");
	}

	@Test
	void checkWinningDealerEval(){
		Card firstP = new Card('H', 4);
		Card secondP = new Card('D', 4);
		Card thirdP = new Card('H', 5);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('S', 12);
		Card secondD = new Card('D', 12);
		Card thirdD = new Card('H', 12);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "Dealer should win with three of a kind");
	}

	@Test
	void checkWinningPlayerEval(){
		Card firstP = new Card('S', 4);
		Card secondP = new Card('D', 4);
		Card thirdP = new Card('H', 4);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('S', 5);
		Card secondD = new Card('D', 5);
		Card thirdD = new Card('H', 12);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "Player should win with three of a kind");
	}

	@Test
	void checkWinningPlayerHighCard(){
		Card firstP = new Card('S', 13);
		Card secondP = new Card('D', 4);
		Card thirdP = new Card('H', 6);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('S', 4);
		Card secondD = new Card('D', 5);
		Card thirdD = new Card('H', 12);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "Player should win with higher card");
	}

	@Test
	void checkWinningDealerHighCard(){
		Card firstP = new Card('S', 12);
		Card secondP = new Card('D', 4);
		Card thirdP = new Card('H', 6);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('S', 4);
		Card secondD = new Card('D', 5);
		Card thirdD = new Card('H', 13);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "Dealer should win with higher card");
	}

	@Test
	void checkWinningDealerHigherPair(){
		Card firstP = new Card('S', 4);
		Card secondP = new Card('D', 4);
		Card thirdP = new Card('H', 14);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('S', 5);
		Card secondD = new Card('D', 5);
		Card thirdD = new Card('H', 12);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "Dealer should win with higher pair");
	}

	@Test
	void checkWinningPlayerHigherPair(){
		Card firstP = new Card('S', 6);
		Card secondP = new Card('D', 6);
		Card thirdP = new Card('H', 14);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('S', 5);
		Card secondD = new Card('D', 5);
		Card thirdD = new Card('H', 12);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "Player should win with higher pair");
	}

	@Test
	void checkWinningPlayerMatchingPairsHighCard(){
		Card firstP = new Card('S', 12);
		Card secondP = new Card('D', 12);
		Card thirdP = new Card('H', 10);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('H', 12);
		Card secondD = new Card('C', 12);
		Card thirdD = new Card('H', 8);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "Player should win with higher card");
	}

	@Test
	void checkWinningDealerMatchingPairsHighCard(){
		Card firstP = new Card('S', 12);
		Card secondP = new Card('D', 12);
		Card thirdP = new Card('H', 8);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('H', 12);
		Card secondD = new Card('C', 12);
		Card thirdD = new Card('H', 14);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(1, ThreeCardLogic.compareHands(dealer, player), "Dealer should win with higher card");
	}

	@Test
	void checkTieOutOfOrder(){
		Card firstP = new Card('S', 11);
		Card secondP = new Card('D', 5);
		Card thirdP = new Card('H', 13);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('C', 13);
		Card secondD = new Card('D', 11);
		Card thirdD = new Card('H', 5);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(0, ThreeCardLogic.compareHands(dealer, player), "Hands should tie");
	}

	@Test
	void checkWinningPlayerHigherThreeOfAKind(){
		Card firstP = new Card('S', 14);
		Card secondP = new Card('D', 14);
		Card thirdP = new Card('H', 14);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('C', 12);
		Card secondD = new Card('D', 12);
		Card thirdD = new Card('H', 12);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "Player should win with higher three-of-a-kind");
	}

	@Test
	void checkWinningPlayerHigherFlush(){
		Card firstP = new Card('H', 11);
		Card secondP = new Card('H', 6);
		Card thirdP = new Card('H', 13);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('C', 13);
		Card secondD = new Card('C', 11);
		Card thirdD = new Card('C', 5);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "Player should have higher flush");
	}

	@Test
	void checkWinningPlayerHigherStraight(){
		Card firstP = new Card('S', 11);
		Card secondP = new Card('D', 12);
		Card thirdP = new Card('H', 13);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('C', 12);
		Card secondD = new Card('D', 11);
		Card thirdD = new Card('H', 10);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "Player should have a higher straight");
	}

	@Test
	void checkWinningPlayerHigherStraightFlush(){
		Card firstP = new Card('S', 12);
		Card secondP = new Card('S', 13);
		Card thirdP = new Card('S', 14);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('C', 12);
		Card secondD = new Card('C', 11);
		Card thirdD = new Card('C', 10);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "Player should have a higher straight flush");
	}

	@Test
	void checkFailingGUIHands(){
		Card firstP = new Card('C', 5);
		Card secondP = new Card('H', 11);
		Card thirdP = new Card('S', 11);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('H', 14);
		Card secondD = new Card('C', 9);
		Card thirdD = new Card('D', 8);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "Player should win with pair");
	}

	@Test
	void checkFailingGUIHandsFlush(){
		Card firstP = new Card('C', 2);
		Card secondP = new Card('C', 12);
		Card thirdP = new Card('C', 10);

		ArrayList<Card> player = new ArrayList<Card>(){
			{
				add(firstP);
				add(secondP);
				add(thirdP);
			}
		};

		Card firstD = new Card('H', 13);
		Card secondD = new Card('H', 14);
		Card thirdD = new Card('S', 3);

		ArrayList<Card> dealer = new ArrayList<Card>(){
			{
				add(firstD);
				add(secondD);
				add(thirdD);
			}
		};

		assertEquals(2, ThreeCardLogic.compareHands(dealer, player), "Player should win with flush");
	}
}
