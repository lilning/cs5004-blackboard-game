package MyBlackJack;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Game {
    private Deck deck = new Deck();
    private Hand dealer = new Hand();
    private Hand player = new Hand();
    Scanner input = new Scanner(System.in);
    boolean askPlayer;
    private float bankRoll;
    private float bet;

    public Game(float bankRoll) throws IllegalArgumentException{
        if(bankRoll < 2){
            throw new IllegalArgumentException("Bankroll must be >= $2.00 to ");
        }
        this.bankRoll = bankRoll;
    }

    void dealCards(){
        //dealCards handles null and empty deck exception
        deck.shuffleDeck();
        for (int i = 0; i < 2; i++) {
            player.dealCards(deck, 1);
            dealer.dealCards(deck, 1);
        }
    }

    public float getBankRoll() {
        return bankRoll;
    }

    void showHandsForBlackJack(boolean isDealerTurn) throws
            IllegalStateException{
        if(dealer.getDeck().size() < 2 || player.getDeck().size() < 2){
            throw new IllegalStateException("showHandsForBlackJack works with" +
                    " 2 or more cards.");
        }
        System.out.println("Your current hand: ");
        player.printDeck();
        System.out.println("Total hand value: " + player.getTotalHandValue());

        if(!isDealerTurn){
            System.out.println("*********************");
            System.out.println("The dealer is showing: ");
            System.out.println(dealer.getDeck().get(0));
            System.out.println("Dealer showing total: " +
                    dealer.getDeck().get(0).getValue());
            System.out.println("*********************");
        } else{
            System.out.println("*********************");
            System.out.println("The dealer has: ");
            dealer.printDeck();
            System.out.println(dealer.getTotalHandValue());
            System.out.println("*********************");
        }
        System.out.println();
    }

    //ADDED this as a separate method. Used in Players turn
    //Allows user to Hit or stay and ask again if not a legal choice.
    void isHitOrStay(String userInput) throws InterruptedException {
        if(userInput.equals("HIT")){
            player.dealCards(deck,1);
        } else if(userInput.equals("STAY")){
            System.out.println("You chose to stay");
            System.out.println("Dealer's turn.\n");
            TimeUnit.SECONDS.sleep(2);
            askPlayer = false;
        } else {
            System.out.println();
            System.out.println("Not a legal choice. Please Try " +
                    "again in 5 seconds!\n");
            TimeUnit.SECONDS.sleep(5);
            askPlayer = true;
        }
    }

    void playerTurn() throws InterruptedException {
        String userInput;
        askPlayer = true;
        do{
            showHandsForBlackJack(false);
            System.out.println("\nWould like another card?");
            System.out.print("Type 'HIT' or 'STAY': ");
            userInput = input.nextLine();
            userInput = userInput.toUpperCase();
            System.out.println();
            isHitOrStay(userInput);
        } while(!player.isBust(player.getTotalHandValue())
                && askPlayer);
    }

    void dealerTurn() throws InterruptedException {
        showHandsForBlackJack(true);
        while(!player.isBust(player.getTotalHandValue()) &&
                dealer.getTotalHandValue() < 17){
            System.out.println("Dealer Takes a Card!\n");
            TimeUnit.SECONDS.sleep(2);
            dealer.dealCards(deck, 1);
            showHandsForBlackJack(true);
            TimeUnit.SECONDS.sleep(2);
        }
    }

    void askToMakeBet() throws  IllegalStateException{
        if(getBankRoll() < 2){
            throw new IllegalStateException("Can't play with less than $2.00");
        }

        System.out.println("What would you like to wager?");
        System.out.printf("Available Funds = $%.2f\n", getBankRoll());
        bet = input.nextFloat();
        input.nextLine();
        while(bet < 2 || bet > getBankRoll()){
            System.out.printf("Can't bet < $2 or more than $%.2f\n",
                    getBankRoll());
            System.out.println("Make legal wager: ");
            bet = input.nextFloat();
            input.nextLine();
        }
        bankRoll -= bet;
    }

    void endOfRoundBankRoll() throws InterruptedException {
        System.out.println();
        TimeUnit.SECONDS.sleep(1);
        if((!player.isBust(player.getTotalHandValue()) &&
                player.getTotalHandValue() > dealer.getTotalHandValue()) ||
                (dealer.isBust(dealer.getTotalHandValue())
                        && !player.isBust(player.getTotalHandValue()))){
            bankRoll += bet * 2;
            System.out.printf("YOU W0N $%.2f!!!!\n", bet);
        } else if (dealer.getTotalHandValue() == 21 && player.getTotalHandValue() == 21){
            System.out.println("PUSH - Dealer and Player both have Black Jack" +
                    " - the your bet is returned to your bankroll.");
            bankRoll += bet;
        } else {
            if(player.isBust(player.getTotalHandValue())){
                System.out.println("\t\t\tYou went over 21 -- Player Bust");
                player.printDeck();
                System.out.println(player.getTotalHandValue());
            }
            System.out.println("\t\t\tDEALER WINS!");
            System.out.printf("You lost $%.2f\n", bet);
        }
        System.out.printf("You know have: $%.2f available",
                getBankRoll());
    }

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

    void resetGame(){
        player.getDeck().clear();
        dealer.getDeck().clear();
        deck = new Deck();

    }

    public static void main(String[] args) throws InterruptedException {
       float bankroll = 10;
       Game BlackJack = new Game(bankroll);
       boolean keepPlaying;

       //Do while loop
        do{
            BlackJack.resetGame();
            BlackJack.askToMakeBet();
            System.out.println();
            BlackJack.dealCards();
            BlackJack.playerTurn();
            BlackJack.dealerTurn();
            BlackJack.endOfRoundBankRoll();
            if(BlackJack.getBankRoll() < 2){
                keepPlaying = false;
            } else {
                keepPlaying = BlackJack.playAnotherRound();
            }
        }
       while(keepPlaying);

       float totalEarnings = BlackJack.getBankRoll() - bankroll;

       System.out.println("\n\t\tGAME IS OVER!!!");
        if(totalEarnings < 0){
           System.out.printf("Total Lost: $%.2f", Math.abs(totalEarnings));
       } else if(BlackJack.getBankRoll() - bankroll > 0){
           System.out.printf("Total Winnings:  $%.2f",
                   BlackJack.getBankRoll() - bankroll);
       }
    }
}
