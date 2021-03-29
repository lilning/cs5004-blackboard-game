import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * This class simulates a Blackjack game with two players: a dealer and a player.
 */
public class Game {
    static final int BLACKJACK = 21; //Blackjack limit for determining if game is over
    private static final int MIN_BET = 2; //Minimum bet amount allowed for the player
    private static final int DEALER_TURN_LIMIT = 17; //Once dealer reaches 17, his/her turn stops
    private Deck deck = new Deck(); //New deck of cards
    private final Hand dealer = new Hand(); //Dealer's hand
    private final Hand player = new Hand(); //Player's hand
    private final Scanner input = new Scanner(System.in); //Scanner object for processing input
    private boolean askPlayer; //Determines if we need to ask player for user input on hit/stand
    private float bankRoll; //Amount of money that can be used to bet in game
    private final float initialBankRoll; //Initial amount of money set aside to play the game
    private float bet; //Amount of bet money for each round

    /**
     * Initializes a new blackjack game with a user-specified value of bankRoll in float type
     * We also set the initialBankRoll value equal to bankRoll, so this value can be leveraged
     * in later part for calculating total earnings or losses at the end of game.
     *
     * @throws IllegalArgumentException if bankRoll is less than the value of MIN_BET
     */
    public Game() throws IllegalArgumentException{
        setBankRoll();
        this.initialBankRoll = this.bankRoll;
    }

    /**
     * Sets bankroll amount by asking for user input
     *
     * @throws InputMismatchException if user input for bankroll is not a valid float type
     * @throws IllegalArgumentException if bankRoll is less than the value of MIN_BET
     */
    void setBankRoll() throws InputMismatchException, IllegalArgumentException{
        boolean askBankRoll = true;
        while (askBankRoll) {
            try {
                System.out.println("""
                        Welcome to the Blackjack game!
                        Enter the amount of money you would like to set aside for playing the game
                        (Note: Initial bankroll must be > $2 for game to proceed) :""");
                bankRoll = input.nextFloat();
                input.nextLine();
                askBankRoll = false;
                if (bankRoll < 0) {
                    System.out.println("Bankroll amount cannot be a negative number. Please " +
                            "re-enter value:");
                    askBankRoll = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input type for bankroll -- please enter a float number:");
                input.nextLine();
                askBankRoll = true;
            }
        }
        validateBankRoll(this.bankRoll);
    }

    /**
     * Private validation method for bankRoll to ensure amount is greater than MIN_BET
     *
     * @param bankRoll  Amount of money set aside to play the game in float type
     * @return  bankroll amount in float type
     * @throws IllegalArgumentException if bankRoll is less than the value of MIN_BET
     */
    private float validateBankRoll(float bankRoll) throws IllegalArgumentException {
        try {
            if (bankRoll < MIN_BET) {
                System.out.printf("Available Funds = $%.2f\n", bankRoll);
                throw new IllegalArgumentException("Insufficient bankroll amount -- " +
                        "game cannot proceed. Please try again.");
            }
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return bankRoll;
    }

    /**
     * Getter method for bankRoll amount
     *
     * @return  bankRoll in float type
     */
    public float getBankRoll() {
        return this.bankRoll;
    }

    /**
     * Asks user for user input on bet amount in float type
     *
     * @throws InputMismatchException if user input is not a float type
     * @throws IllegalArgumentException if bankRoll is less than the value of MIN_BET
     */
    void askToMakeBet() throws  InputMismatchException, IllegalArgumentException{
        validateBankRoll(getBankRoll());
        boolean askBet = true;
        while (askBet) {
            try {
                //Asks for user input for bet amount
                System.out.println("Enter the amount you would like to wager (e.g. 5):");
                System.out.printf("Available Funds = $%.2f\n", getBankRoll());
                bet = input.nextFloat();
                input.nextLine();
                //Checks if bet is less than MIN_BET or greater than bankRoll
                while (bet < MIN_BET || bet > getBankRoll()) {
                    if (bet < MIN_BET) {
                        System.out.println("Bet amount cannot be less than $2.00. " +
                                "Please re-enter value:");
                    } else if (bet > getBankRoll()) {
                        System.out.printf("Bet amount cannot be more than current bankroll value of"
                                + " $%.2f. Please re-enter value.\n", getBankRoll());
                    }
                    bet = input.nextFloat();
                    input.nextLine();
                }
                askBet = false;
                bankRoll -= bet;
            } catch (InputMismatchException e) {
                System.out.println("Wrong input type for bankroll -- please enter a float number:");
                input.nextLine();
                askBet = true;
            }
        }
    }

    /**
     * Shuffles the deck and deals cards for player and dealer hands
     *
     * @throws IllegalStateException if deck is empty or null
     */
    void dealCardsBeforePlayerTurns() throws IllegalStateException{
        deck.shuffleDeck();
        for (int i = 0; i < 2; i++) {
            player.dealCards(deck, 1);
            dealer.dealCards(deck, 1);
        }
    }

    /**
     * Displays print messages for both player and dealer's hands after each turn
     * Note that when it is not yet dealer's turn, one of dealer's cards should be hidden
     *
     * @param isDealerTurn boolean to determine if it's dealer's turn
     * @throws IllegalStateException if player or dealer's hand has less than 2 cards
     */
    void showHandsForBlackJack(boolean isDealerTurn) throws IllegalStateException{
        if(dealer.getDeck().size() < 2 || player.getDeck().size() < 2){
            throw new IllegalStateException("Error -- both player and dealer should have " +
                    "at least two cards in hand.");
        }

        //Player's hand and score
        System.out.println("Your current hand: ");
        player.printDeck();
        System.out.println("Total hand value: " + player.getTotalHandValue());

        //If not dealer's turn, dealer should hide one of the cards in his/her hand
        if(!isDealerTurn){
            System.out.println("*********************");
            System.out.println("The dealer is showing: ");
            System.out.println(dealer.getDeck().get(0));
            System.out.println("Dealer showing total: " +
                    dealer.getDeck().get(0).getCardValue());
            System.out.println("*********************");

        //If dealer's turn, dealer can display all of the cards in hand
        } else{
            System.out.println("*********************");
            System.out.println("The dealer has: ");
            dealer.printDeck();
            System.out.println(dealer.getTotalHandValue());
            System.out.println("*********************");
        }
        System.out.println();
    }

    /**
     * Asks user for hit or stand input in String type and allows user to retype choice if an
     * invalid input was entered
     *
     * @param userInput HIT or STAND response in String type
     * @throws InterruptedException when the method is waiting, sleeping, or otherwise occupied,
     * and the method is interrupted, either before or during the activity.
     */
    void isHitOrStand(String userInput) throws InterruptedException {
        if(userInput.equals("HIT")){
            player.dealCards(deck,1);
        } else if(userInput.equals("STAND")){
            System.out.println("You chose to stand");
            System.out.println("Dealer's turn.\n");
            TimeUnit.SECONDS.sleep(2);
            askPlayer = false;
        } else {
            System.out.println();
            System.out.println("Not a legal choice. Please Try " +
                    "again in 3 seconds!\n");
            TimeUnit.SECONDS.sleep(3);
            askPlayer = true;
        }
    }

    /**
     * Simulates player's turn of the game by taking in user input for HIT or STAND decisions on
     * next moves.
     *
     * @throws InterruptedException when the method is waiting, sleeping, or otherwise occupied,
     *      * and the method is interrupted, either before or during the activity.
     */
    void playerTurn() throws InterruptedException {
        String userInput;
        askPlayer = true;
        do{
            showHandsForBlackJack(false);
            System.out.println("\nWould like another card?");
            System.out.print("Type 'HIT' or 'STAND': ");
            userInput = input.nextLine();
            userInput = userInput.toUpperCase();
            System.out.println();
            isHitOrStand(userInput);
        } while(!player.isBust() && askPlayer);
    }

    /**
     * Simulates dealer's turn of the game: dealer will hit until his/her cards total 17 or higher
     *
     * @throws InterruptedException when the method is waiting, sleeping, or otherwise occupied,
     * and the method is interrupted, either before or during the activity.
     */
    void dealerTurn() throws InterruptedException {
        showHandsForBlackJack(true);
        while(!player.isBust() && dealer.getTotalHandValue() < DEALER_TURN_LIMIT){
            System.out.println("Dealer Takes a Card!\n");
            TimeUnit.SECONDS.sleep(2);
            dealer.dealCards(deck, 1);
            showHandsForBlackJack(true);
            TimeUnit.SECONDS.sleep(2);
        }
    }

    /**
     * Calculates game results and available bankroll after end of game
     *
     * @throws InterruptedException when the method is waiting, sleeping, or otherwise occupied,
     * and the method is interrupted, either before or during the activity.
     */
    void endOfRoundBankRoll() throws InterruptedException {
        System.out.println();
        TimeUnit.SECONDS.sleep(1);
        //If player wins the game
        if (!player.isBust() && player.getTotalHandValue() > dealer.getTotalHandValue() ||
                dealer.isBust() && !player.isBust()) {
            bankRoll += bet * 2;
            System.out.printf("YOU W0N $%.2f!!!!\n", bet);
        //If game ends with a draw
        } else if (!player.isBust() && dealer.getTotalHandValue() == player.getTotalHandValue()){
            System.out.println("PUSH - Dealer and Player have a draw" +
                    " - then your bet is returned to your bankroll.");
            bankRoll += bet;
        //If dealer wins the game
        } else {
            if(player.isBust()){
                System.out.println("\t\t\tYou went over 21 -- Player Bust");
                player.printDeck();
                System.out.println(player.getTotalHandValue());
            }
            System.out.println("\t\t\tDEALER WINS!");
            System.out.printf("You lost $%.2f\n", bet);
        }
        System.out.printf("You know have: $%.2f available\n",
                validateBankRoll(getBankRoll()));
    }

    /**
     * boolean to determine if user wants to play another round based on user input
     *
     * @return boolean for whether user input equals YES
     */
    boolean playAnotherRound(){
        String keepPlaying;
        System.out.println("\nWould you like to keep playing?");
        System.out.print("Type 'YES' or 'N0': ");
        keepPlaying = input.next().toUpperCase();
        while(!(keepPlaying.equals("NO") || keepPlaying.equals("YES"))){
            System.out.println("Not a legal choice");
            System.out.print("Type 'YES' or 'N0': ");
            keepPlaying = input.next().toUpperCase();
        }
        return keepPlaying.equals("YES");
    }

    /**
     * Resets the game by clearing player and dealer hands, and starting with a new deck of cards
     */
    void resetGame(){
        player.getDeck().clear();
        dealer.getDeck().clear();
        deck = new Deck();
    }

    /**
     * Main method to run the Blackjack game.
     *
     * @throws InterruptedException when the method is waiting, sleeping, or otherwise occupied,
     * and the method is interrupted, either before or during the activity.
     */
    public static void main(String[] args) throws InterruptedException {
        Game BlackJack = new Game(); //Creates a new Blackjack game
        do {
            BlackJack.resetGame();
            BlackJack.askToMakeBet();
            System.out.println();
            BlackJack.dealCardsBeforePlayerTurns();
            BlackJack.playerTurn();
            BlackJack.dealerTurn();
            BlackJack.endOfRoundBankRoll();
        } while (BlackJack.playAnotherRound());

        //calculates totalEarnings after game rounds and print out final earnings/loss
        float totalEarnings = BlackJack.getBankRoll() - BlackJack.initialBankRoll;
        System.out.println("\n\t\tGAME IS OVER!!!");
        if (totalEarnings < 0) {
            System.out.printf("Total Lost: $%.2f", Math.abs(totalEarnings));
        } else {
            System.out.printf("Total Winnings:  $%.2f", totalEarnings);
        }
    }
}
