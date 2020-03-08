import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/* Creates the GUI layout for the game Three Card Poker. Defines a console where game information is output,
 * Two players each with their own fields to place bets, and make decisions for certain turns. Also creates
 * dealer in center of GUI, and shows winnings and control buttons of to the right.
 */
public class ThreeCardPokerGame extends Application {

	//Create players for game
	Player playerOne;
	Player playerTwo;
	Dealer theDealer;

	private boolean isPlayingP1, isPlayingP2, isFoldingP1, isFoldingP2;
	private int totalBetP1, totalBetP2, continueBtnCounter, winnerA, winnerB, p1PPWinnings, p2PPWinnings;
	private Button anteBtnP1, anteBtnP2, ppBtnP1, ppBtnP2, playBtnP1, playBtnP2, foldBtnP1, foldBtnP2, dealBtnP1, dealBtnP2, continueBtn;
	private TextField p1AnteText, p2AnteText, p1PairPlusText, p2PairPlusText;
	private HBox p1BetsTexts, p2BetsTexts, p1Buttons, p2Buttons, p1BetButtons, p1PlayButtons, p2BetButtons, p2PlayButtons;
	private VBox player1V, player2V, playerBalances, player1Winnings, player2Winnings, dealerBox, rightSide;
	private BorderPane pane;
	private MenuBar menu;
	private Scene betScene;
	private VBox gameOutput;
	private HBox players;
	private ListView<String> console;
	private ObservableList<String> dialogue;
	private Label p1Label, p2Label, dealerLabel, winningsLabel, p1Balance, p2Balance, p1Cards, p2Cards, dealerCards;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Set window title
		primaryStage.setTitle("Let's Play Three Card Poker!!!!");

		/* Initialize all variables for later here */
		continueBtnCounter = 0;

		isPlayingP1 = false;
		isPlayingP2 = false;
		isFoldingP1 = false;
		isFoldingP2 = false;

		playerOne = new Player();
		playerTwo = new Player();
		theDealer = new Dealer();

		pane = new BorderPane();

		Image orig = new Image("mac.png");
		BackgroundSize bSize2 = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		pane.setBackground(new Background(new BackgroundImage(orig, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, bSize2)));

		continueBtn = new Button("Continue");
		continueBtn.setDisable(true);

		anteBtnP1 = new Button("Ante");
		anteBtnP2 = new Button("Ante");

		ppBtnP1 = new Button("Pair Plus Bet");
		ppBtnP2 = new Button("Pair Plus Bet");

		dealBtnP1 = new Button("Deal");
		dealBtnP2 = new Button("Deal");
		dealBtnP1.setDisable(true);
		dealBtnP2.setDisable(true);

		playBtnP1 = new Button("Play");
		playBtnP2 = new Button("Play");
		playBtnP1.setDisable(true);
		playBtnP2.setDisable(true);

		foldBtnP1 = new Button("Fold");
		foldBtnP2 = new Button("Fold");
		foldBtnP1.setDisable(true);
		foldBtnP2.setDisable(true);

		p1AnteText = new TextField();
		p2AnteText = new TextField();

		p1PairPlusText = new TextField();
		p2PairPlusText = new TextField();

		console = new ListView<>();
		console.setPrefHeight(1200);
		dialogue = FXCollections.observableArrayList();
		console.setItems(dialogue);
		console.scrollTo(dialogue.size() - 1);

		p1Label = new Label("Player 1");
		p2Label = new Label("Player 2");
		dealerLabel = new Label("Dealer");

		p1Cards = new Label("");
		p2Cards = new Label("");
		p1Cards.setStyle("-fx-text-fill: Red");
		p2Cards.setStyle("-fx-text-fill: Red");

		dealerCards = new Label("");

		winningsLabel = new Label("Winnings: ");
		p1Balance = new Label("$" + playerOne.totalWinnings);
		p2Balance = new Label("$" + playerTwo.totalWinnings);

		p1BetsTexts = new HBox(p1AnteText, p1PairPlusText);
		p2BetsTexts = new HBox(p2AnteText, p2PairPlusText);

		p1BetButtons = new HBox(120, anteBtnP1, ppBtnP1);
		p1PlayButtons = new HBox(dealBtnP1, playBtnP1, foldBtnP1);
		p1Buttons = new HBox(65, p1BetButtons, p1PlayButtons);

		p2BetButtons = new HBox(120, anteBtnP2, ppBtnP2);
		p2PlayButtons = new HBox(dealBtnP2, playBtnP2, foldBtnP2);
		p2Buttons = new HBox(65, p2BetButtons, p2PlayButtons);

		player1V = new VBox(5, p1Cards, p1BetsTexts, p1Buttons);
		player2V = new VBox(5, p2Cards, p2BetsTexts, p2Buttons);

		player1Winnings = new VBox(p1Label, p1Balance);
		player2Winnings = new VBox(p2Label, p2Balance);
		playerBalances = new VBox(10, winningsLabel, player1Winnings, player2Winnings);

		rightSide = new VBox(300, playerBalances, continueBtn);

		players = new HBox(200, player1V, player2V);
		players.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.5));
		players.setAlignment(Pos.CENTER);

		menu = new MenuBar();
		Menu mOne = new Menu("Options");
		menu.getMenus().addAll(mOne);

		MenuItem iOne = new MenuItem("Exit");
		MenuItem iTwo = new MenuItem("Fresh Start");
		MenuItem iThree = new MenuItem("New Look");

		mOne.getItems().addAll(iOne, iTwo, iThree);

		gameOutput = new VBox(20, console);
		dealerBox = new VBox(dealerCards, dealerLabel);
		dealerBox.setAlignment(Pos.CENTER);

		gameOutput.setPrefWidth(275);

		pane.setLeft(gameOutput);
		pane.setTop(menu);
		pane.setBottom(players);
		pane.setCenter(dealerBox);
		pane.setRight(rightSide);

		betScene = new Scene(pane, 1600, 900);
		primaryStage.setScene(betScene);

		primaryStage.show();

		//Introduce player to game
		print("Welcome to Three Card Poker!");
		print("This is a two-player game where the");
		print("user plays as both players against");
		print("a dealer.");
		print(" ");
		print("Each player has $1000 to start.");
		print("Good luck!!!");
		/* End of initializing variables */

		//Exit option
		iOne.setOnAction(e-> Platform.exit());

		//Fresh Start menu option resets the whole game
		//Screen is refreshed, and player winnings are reset
		//Also clears game output console
		iTwo.setOnAction(
				e->{
					resetScreen();
					playerOne.totalWinnings = 1000;
					playerTwo.totalWinnings = 1000;
					p1Balance.setText("$" + playerOne.totalWinnings);
					p2Balance.setText("$" + playerTwo.totalWinnings);

					console.getItems().clear();
					print("New game has begun!");
				}
		);

		//New Look changes the background and some of the colors of the UI
		String originalMenu = menu.getStyle();
		String originalRight = rightSide.getStyle();
		Background originalBG = pane.getBackground();

		iThree.setOnAction(
				e->{
					//Set GUI to new look
					if(iThree.getText().equals("New Look")){
						iThree.setText("Old Look");

						//Set pane color
						menu.setStyle("-fx-background-color: PeachPuff");

						Image random = new Image("windows.jpeg");
						BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
						pane.setBackground(new Background(new BackgroundImage(random, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, bSize)));


						p1Cards.setStyle("-fx-text-fill: White");
						p2Cards.setStyle("-fx-text-fill: White");
						dealerCards.setStyle("-fx-text-fill: White");
						dealerLabel.setStyle("-fx-text-fill: White");
					}

					//Set GUI back to old look
					else {
						iThree.setText("New Look");

						menu.setStyle(originalMenu);
						rightSide.setStyle(originalRight);
						pane.setBackground(originalBG);

						p1Cards.setStyle("-fx-text-fill: Black");
						p2Cards.setStyle("-fx-text-fill: Black");
						dealerCards.setStyle("-fx-text-fill: Black");
						dealerLabel.setStyle("-fx-text-fill: Black");
					}
				}
		);

		//Ante button player1 event handler: add ante value to player object, print out ante bet placed
		anteBtnP1.setOnAction(
				e->{
					int anteBet = 0;

					try {
						anteBet = Integer.parseInt(p1AnteText.getText());
					} catch(NumberFormatException nfe){
						print("Error: Ante text box can't be empty.");
					}

					if(anteBet >= 5 && anteBet <= 25) {
						playerOne.anteBet = anteBet;
						anteBtnP1.setDisable(true);

						print("Ante of $" + p1AnteText.getText() + " placed by player 1.");
					}
					else {
						print("Ante must be between $5-25.");
					}

					//If ante has been pressed for both, activate deal
					if(anteBtnP1.isDisabled() && anteBtnP2.isDisabled()){
						dealBtnP1.setDisable(false);
						dealBtnP2.setDisable(false);
					}
				});

		//Ante button player2 event handler: add ante value to player object, print out ante bet placed
		anteBtnP2.setOnAction(
				e->{
					int anteBet = 0;

					try {
						anteBet = Integer.parseInt(p2AnteText.getText());
					} catch(NumberFormatException nfe){
						print("Error: Ante text box can't be empty.");
					}

					if(anteBet >= 5 && anteBet <= 25) {
						playerTwo.anteBet = anteBet;
						anteBtnP2.setDisable(true);

						print("Ante of $" + p2AnteText.getText() + " placed by player 2.");
					}
					else {
						print("Ante must be between $5-25.");
					}

					//If ante has been pressed for both, activate deal
					if(anteBtnP1.isDisabled() && anteBtnP2.isDisabled()){
						dealBtnP1.setDisable(false);
						dealBtnP2.setDisable(false);
					}
				});

		//Pair plus button player1 event handle: add pair plus value to player object, and print out ante bet placed
		ppBtnP1.setOnAction(
				e->{
					int ppBet = 0;

					try {
						ppBet = Integer.parseInt(p1PairPlusText.getText());
					}
					catch(NumberFormatException nfe){
						print("Error: Pair plus text box can't be empty.");
					}

					if(ppBet >= 5 && ppBet <= 25) {
						playerOne.pairPlusBet = ppBet;
						ppBtnP1.setDisable(true);

						print("Pair plus of $" + p1PairPlusText.getText() + " placed by player 1.");
					}
					else {
						print("Pair plus bet must be between $5-25");
					}
				});

		//Pair plus button player1 event handle: add pair plus value to player object, and print out ante bet placed
		ppBtnP2.setOnAction(
				e->{
					int ppBet = 0;

					try {
						ppBet = Integer.parseInt(p2PairPlusText.getText());
					}
					catch(NumberFormatException nfe){
						print("Error: Pair plus text box can't be empty.");
					}

					if(ppBet >= 5 && ppBet <= 25) {
						playerTwo.pairPlusBet = ppBet;
						ppBtnP2.setDisable(true);

						print("Pair plus of $" + p2PairPlusText.getText() + " placed by player 2.");
					}
					else {
						print("Pair plus bet must be between $5-25.");
					}
				});

		//Deal button player 1 event handle: Deal cards to player 1 and output them in player1 VBox label
		dealBtnP1.setOnAction(
				e->{
					if(playerOne.anteBet != 0) {
						print("Dealing cards to Player One...");
						playerOne.hand = theDealer.dealHand();
						dealBtnP1.setDisable(true);

						//Don't allow player to make any more bets once cards are dealt
						ppBtnP1.setDisable(true);

						p1Cards.setText(playerOne.showHand());
					} else {
						print("Ante bet must be placed first.");
					}

					//If both hands have been dealt, deal to dealer and show hand face down
					if(dealBtnP1.isDisabled() && dealBtnP2.isDisabled()){
						theDealer.dealersHand = theDealer.dealHand();

						dealerCards.setText("Card, Card, Card");

						playBtnP1.setDisable(false);
						playBtnP2.setDisable(false);
						foldBtnP1.setDisable(false);
						foldBtnP2.setDisable(false);

						print("Cards have been dealt.\nNow, decide to play or fold.");
					}
				}
		);

		//Deal button player 2 event handle: Deal cards to player 2 and output them in player2 VBox label
		dealBtnP2.setOnAction(
				e->{
					if(playerTwo.anteBet != 0) {
						print("Dealing cards to Player Two...");
						playerTwo.hand = theDealer.dealHand();
						dealBtnP2.setDisable(true);

						//Don't allow player to make any more bets once cards are dealt
						ppBtnP2.setDisable(true);

						p2Cards.setText(playerTwo.showHand());
					} else {
						print("Ante bet must be placed first");
					}

					if(dealBtnP1.isDisabled() && dealBtnP2.isDisabled()){
						theDealer.dealersHand = theDealer.dealHand();

						dealerCards.setText("Card, Card, Card");

						playBtnP1.setDisable(false);
						playBtnP2.setDisable(false);
						foldBtnP1.setDisable(false);
						foldBtnP2.setDisable(false);

						print("Cards have been dealt.\nNow, decide to play or fold.");
					}
				}
		);

		//Fold button player 1 event handle: Lose bets and clear hand of player, removing them from this round
		foldBtnP1.setOnAction(
				e->{
					//Lose ante bet and pair plus bet
					totalBetP1 = playerOne.anteBet + playerOne.pairPlusBet;
					isFoldingP1 = true;

					playerOne.totalWinnings -= totalBetP1;
					p1Balance.setText("$" + playerOne.totalWinnings);

					//Clear bet text fields
					p1AnteText.clear();
					p1PairPlusText.clear();
					p1Cards.setText("");

					//Disable play and fold buttons
					playBtnP1.setDisable(true);
					foldBtnP1.setDisable(true);

					//Print player one folded
					print("Player One has folded.\nPlayer One lost a bet of $" + totalBetP1);

					//If both players fold, start a new round but keep winnings the same
					if(isFoldingP1 && isFoldingP2) {
						print("Both players folded.\nStarting new round...");

						resetScreen();
					}

					//if player 1 is folding but player 2 is still in the game
					else if(isFoldingP1 && isPlayingP2){
							print("Player Two compared against dealer");
							continueBtn.setDisable(false);
					}
				}
		);

		//Fold button player 2 event handle: Lose bets and clear hand of player, removing them from this round
		foldBtnP2.setOnAction(
				e->{
					//Lose ante bet and pair plus bet
					totalBetP2 = playerTwo.anteBet + playerTwo.pairPlusBet;
					isFoldingP2 = true;

					playerTwo.totalWinnings -= totalBetP2;
					p2Balance.setText("$" + playerTwo.totalWinnings);

					//Clear bet text fields
					p2AnteText.clear();
					p2PairPlusText.clear();
					p2Cards.setText("");

					//Disable play and fold buttons
					playBtnP2.setDisable(true);
					foldBtnP2.setDisable(true);

					//Print player one folded
					print("Player Two has folded.\nPlayer Two lost a bet of $" + totalBetP2);

					//If both players fold, start a new round
					if(isFoldingP1 && isFoldingP2){
						print("Both players folded.\nStarting new round...");

						resetScreen();
					}

					//If player 2 folds but player 1 is still in the game
					else if(isFoldingP2 && isPlayingP1){
						print("Player One compared against dealer");
						continueBtn.setDisable(false);
					}
				}
		);


		playBtnP1.setOnAction(
				e->{
					//If player One decides to play, they must make a play wager equal to the amount of their ante bet
					totalBetP1 += playerOne.anteBet;
					playerOne.playBet = playerOne.anteBet;
					isPlayingP1 = true;

					//This decision is outputted to the game's console
					print("Player One has decided to play.\nPlayer One makes a play wager of $" + playerOne.playBet);

					//After making this decision, the options to play or fold are disabled
					playBtnP1.setDisable(true);
					foldBtnP1.setDisable(true);

					//If player 1 is playing and player 2 has decided to either fold or play
					if(isPlayingP1 && (isPlayingP2 || isFoldingP2)){
						continueBtn.setDisable(false);

						if(isPlayingP2) {
							print("Players One & Two vs dealer");
						}
						else {
							print("Player One compared against dealer");
						}
					}
				}
		);

		playBtnP2.setOnAction(
				e->{
					totalBetP2 += playerTwo.anteBet;
					playerTwo.playBet = playerTwo.anteBet;
					isPlayingP2 = true;

					print("Player Two has decided to play.\nPlayer Two makes a play wager of $" + playerTwo.playBet);

					playBtnP2.setDisable(true);
					foldBtnP2.setDisable(true);

					//If player 2 is playing and player 1 has decided to either fold or play
					if(isPlayingP2 && (isPlayingP1 || isFoldingP1)){
						continueBtn.setDisable(false);

						if(isPlayingP1) {
							print("Players One & Two vs Dealer");
						}
						else {
							print("Player Two compared against dealer");
						}
					}
				}
		);

		//Continue button event handler: controls flow of game once players are in play
		continueBtn.setOnAction(
				e->{
					if(continueBtnCounter == 0){
						continueBtnCounter++;

						//Show dealers hand
						dealerCards.setText(theDealer.showHand());
					}
					else if(continueBtnCounter == 1){
						continueBtnCounter++;

						// Output if dealer has a queen or not
						if(ThreeCardLogic.dealerHasQueen(theDealer.dealersHand)){
							print("Dealer qualifies with at least a queen.");
						} else {
							print("Dealer did not qualify.\nNeeds queen high or better.");

							//Take bets from players who did fold. Update total earnings and label
							print("Players who played keep bets.");
						}
					}
					else if(continueBtnCounter == 2){
						continueBtnCounter++;

						// If dealer has no queen, reset the screen
						if(!ThreeCardLogic.dealerHasQueen(theDealer.dealersHand)){

							//If player 1 or player 2 made pair plus bets, calculate their winnings
							//This is the same calculation as below, but in the case of both players folding
							if(playerOne.pairPlusBet > 0 || playerTwo.pairPlusBet > 0) {
								print("Calculating pair plus bets...");

								//If player one made a pair plus bet and played their hand
								if(playerOne.pairPlusBet > 0 && isPlayingP1) {

									//Calculate pair plus winnings from hand
									p1PPWinnings = ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.pairPlusBet);

									//If winnings > 0, player 1 won something. Otherwise, they lose their bet
									if(p1PPWinnings > 0) {
										print("Player 1 won $" + p1PPWinnings + "!");
										playerOne.totalWinnings += p1PPWinnings;
									} else {
										print("Player 1 lost pair plus bet (-$" + playerOne.pairPlusBet + ")");
										playerOne.totalWinnings -= playerOne.pairPlusBet;
									}

									//Displays new balance of player 1
									p1Balance.setText("$" + playerOne.totalWinnings);
								}

								//Same calculation for player 1 except for player 2
								if(playerTwo.pairPlusBet > 0 && isPlayingP2) {
									p2PPWinnings = ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.pairPlusBet);

									if(p2PPWinnings > 0) {
										print("Player 2 won $" + p2PPWinnings + "!");
										playerTwo.totalWinnings += p2PPWinnings;
									} else {
										print("Player 2 lost pair plus bet (-$" + playerTwo.pairPlusBet + ")");
										playerTwo.totalWinnings -= playerTwo.pairPlusBet;
									}

									p2Balance.setText("$" + playerTwo.totalWinnings);
								}
							}

							//Start new round
							print("Starting new round...");
							resetScreen();

							//Pushes bet to next hand
							p1AnteText.setText(String.valueOf(playerOne.anteBet));
							p2AnteText.setText(String.valueOf(playerTwo.anteBet));
							p1PairPlusText.setText(String.valueOf(playerOne.pairPlusBet));
							p2PairPlusText.setText(String.valueOf(playerTwo.pairPlusBet));
						}

						// If dealer has a queen, Evaluate dealer hand against each player's hand and output winner in console
						else {
							print("Evaluating winning hands...");

							//If player 1 is playing this round
							if(isPlayingP1){

								winnerA = ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand);

								if(winnerA == 2){
									print("Player 1 beat dealer!");
								}
								else if(winnerA == 1){
									print("Dealer beat Player 1.");
								}
								else {
									print("Player 1 and dealer tied.");
								}
							}

							if(isPlayingP2){

								winnerB = ThreeCardLogic.compareHands(theDealer.dealersHand, playerTwo.hand);

								if(winnerB == 2){
									print("Player 2 beat dealer!");
								}
								else if(winnerB == 1){
									print("Dealer beat Player 2.");
								}
								else {
									print("Player 2 beat Dealer!");
								}
							}
						}
					}

					else if(continueBtnCounter == 3){
						continueBtnCounter++;

						//Calculate winnings and output in console and labels
						print("Calculating winnings...");

						//Calculate non-pair plus winnings
						//Calculate for player 1 win against dealer
						if(winnerA == 2){
							playerOne.totalWinnings += (playerOne.anteBet + playerOne.playBet);
							print("Player 1 wins $" + (playerOne.anteBet + playerOne.playBet) + "!");
						}

						//Calculate for player 1 loss against dealer
						if(winnerA == 1){
							playerOne.totalWinnings -= (playerOne.anteBet + playerOne.playBet);
							print("Player 1 loses ante and play bet ($-" + (playerOne.anteBet + playerOne.playBet) + ")");
						}

						//Update player 1 balance
						p1Balance.setText("$" + playerOne.totalWinnings);

						//Calculate for player 2 win against dealer
						if(winnerB == 2){
							playerTwo.totalWinnings += (playerTwo.anteBet + playerTwo.playBet);

							print("Player 2 wins $" + (playerTwo.anteBet + playerTwo.playBet) + "!");

						}
						//Calculate for player 2 loss against dealer
						if(winnerB == 1){
							playerTwo.totalWinnings -= (playerTwo.anteBet + playerTwo.playBet);
							print("Player 2 loses ante bet and play bet ($-" + (playerTwo.anteBet + playerTwo.playBet) + ")");
						}

						p2Balance.setText("$" + playerTwo.totalWinnings);

						//If player 1 or player 2 made pair plus bets, calculate their winnings.
						//This is the same as the upper bet, but in the case of at least one of the players playing
						if(playerOne.pairPlusBet > 0 || playerTwo.pairPlusBet > 0) {
							print("Calculating pair plus bets...");

							if(playerOne.pairPlusBet > 0 && isPlayingP1) {
								p1PPWinnings = ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.pairPlusBet);

								if(p1PPWinnings > 0) {
									print("Player 1 won $" + p1PPWinnings + "!");
									playerOne.totalWinnings += p1PPWinnings;
								} else {
									print("Player 1 lost pair plus bet (-$" + playerTwo.pairPlusBet + ")");
									playerOne.totalWinnings -= playerOne.pairPlusBet;
								}

								p1Balance.setText("$" + playerOne.totalWinnings);
							}

							if(playerTwo.pairPlusBet > 0 && isPlayingP2) {
								p2PPWinnings = ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.pairPlusBet);

								if(p2PPWinnings > 0) {
									print("Player 2 won $" + p2PPWinnings + "!");
									playerTwo.totalWinnings += p2PPWinnings;
								} else {
									print("Player 2 lost pair plus bet (-$" + playerTwo.pairPlusBet + ")");
									playerTwo.totalWinnings -= playerTwo.pairPlusBet;
								}

								p2Balance.setText("$" + playerTwo.totalWinnings);
							}
						}
					}
					//For last press, complete the round and start a new one
					else if(continueBtnCounter == 4) {
						print("Starting new round...");

						resetScreen();

						p1AnteText.setText(String.valueOf(playerOne.anteBet));
						p2AnteText.setText(String.valueOf(playerTwo.anteBet));

						if(playerOne.pairPlusBet > 0)
							p1PairPlusText.setText(String.valueOf(playerOne.pairPlusBet));

						if(playerTwo.pairPlusBet > 0)
							p2PairPlusText.setText(String.valueOf(playerTwo.pairPlusBet));
					}
				}
		);
	}

	void resetScreen(){
		//Reset status values
		continueBtnCounter = 0;
		isPlayingP1 = false;
		isPlayingP2 = false;
		isFoldingP1 = false;
		isFoldingP2 = false;

		//Reset labels
		p1Cards.setText("");
		p2Cards.setText("");
		dealerCards.setText("");

		//Reset buttons
		anteBtnP1.setDisable(false);
		anteBtnP2.setDisable(false);
		ppBtnP1.setDisable(false);
		ppBtnP2.setDisable(false);
		continueBtn.setDisable(true);

		//Reset bet text fields
		p1AnteText.clear();
		p1PairPlusText.clear();
		p2AnteText.clear();
		p2PairPlusText.clear();

		//Reset player bet amounts (only pair plus since it's optional)
		playerOne.pairPlusBet = 0;
		playerTwo.pairPlusBet = 0;
	}

	//Outputs some String 's' to console
	void print(String s){
		dialogue.add(s);
		console.setItems(dialogue);
		console.scrollTo(dialogue.size() - 1);
	}
}
