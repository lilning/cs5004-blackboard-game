import java.util.ArrayList;

/**
 * This class represents a poker hand of cards. For our use case, we view a hand as
 * a specialized version of a deck, therefore this class derives from {@link Deck}.
 */
public class Hand extends Deck {
    private final ArrayList<Card> hand;
    private int totalHandValue;

    /**
     * Constructor to initiate the Hand object with an empty arraylist
     */
    public Hand() {
        this.hand = new ArrayList<>();
        this.totalHandValue = 0;
    }

    /**
     * Getter method to return the hand arraylist
     *
     * @return hand    arraylist of Card objects
     */
    @Override
    public ArrayList<Card> getDeck() {
        return this.hand;
    }

    /**
     * Moves a specified number of cards from the deck to this hand
     *
     * @param deck  specified deck to deal cards from (Deck object)
     * @param numberOfCards specified number of cards to deal (int type)
     * @throws IllegalArgumentException if {@param numberOfCards} is less than 0
     */
    void dealCards(Deck deck, int numberOfCards) throws IllegalArgumentException{
        if (numberOfCards < 0){
            throw new IllegalArgumentException("Cannot deal negative number of cards.");
        }
        for(int i = 0; i < numberOfCards; i++){
            this.addCard(deck.removeCard());
            this.calculateHandValue();
        }
    }

    /**
     * Calculates total hand value based on the card value of each card within the hand arraylist
     *
     * Note that the default card value of Ace is assigned as 11, but if the hand contains at
     * least one Ace and the total hand value exceeds Blackjack, then Ace can be counted as 1
     * instead of 11. Please refer to in-line comments for further details.
     */
    void calculateHandValue() {
        this.totalHandValue = 0; //reset hand value before each calculation.
        int aceCount = 0;

        for (int i = 0; i < getDeck().size(); i++) {
            if(getDeck().get(i).getCardValue() == 11){
                aceCount++; //count number of Ace cards in hand
            }
            totalHandValue += getDeck().get(i).getCardValue();
        }

        /* If totalHandValue exceeds 21 and Ace is present in the hand, we can deduct 10 from
        totalHandValue for one Ace at a time (if aceCount > 1), until totalHandValue is
        less than or equal to 21. */
        while(this.totalHandValue > Game.BLACKJACK && aceCount > 0){
            this.totalHandValue -= 10;
            aceCount--;
        }
    }

    /**
     * Returns the total hand value of this hand
     *
     * @return  totalHandValue in int type
     */
    public int getTotalHandValue() {
        return this.totalHandValue;
    }

    /**
     * Returns boolean to determine if hand is bust (if total hand value exceeds 21)
     *
     * @return boolean value for whether totalHandValue of the hand is over 21
     */
    boolean isBust(){
        return this.getTotalHandValue() > Game.BLACKJACK;
    }

    /**
     * Returns dealt cards from the hand back to the deck
     *
     * @param deck  specified deck to return dealt cards to (Deck object)
     * @param numberOfCards specified number of cards to return to deck
     * @throws IllegalArgumentException if {@param numberOfCards} is less than 1
     * @throws IllegalStateException    if deck arraylist is null or empty
     */
    void returnCard(Deck deck, int numberOfCards)
            throws IllegalArgumentException, IllegalStateException{
        if (numberOfCards < 1){
            throw new IllegalArgumentException("Must return at least one card.");
        }

        if(getDeck().isEmpty() || getDeck() == null){
            throw new IllegalStateException("The hand is either null or empty.");
        }
        for(int i = 0; i < numberOfCards; i++){
            deck.addCard(getDeck().get(0));
            //this will remove the card at position 0
            this.removeCard();
        }
    }

    /**
     * Main method to test the creation and functionalities of sample Deck and Hand objects
     */
    public static void main (String[]args){

        //Create the deck and two hands
        Deck deck = new Deck();
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();

        //Shuffle the deck
        System.out.println("Before shuffle");
        System.out.println("Number of cards = " + deck.getDeck().size());
        deck.printDeck();
        System.out.println();

        deck.shuffleDeck();
        System.out.println("After shuffle");
        System.out.println("Number of cards = " + deck.getDeck().size());
        deck.printDeck();

        //Deal the cards to each of the two hands
        hand1.dealCards(deck, 5);
        hand2.dealCards(deck, 5);

        System.out.println();
        System.out.println("Print Hand 1 Cards:");
        System.out.println("\t\tBEFORE SORT");
        hand1.printDeck();
        hand1.sortDeck();
        System.out.println("\t\tAFTER SORT");
        hand1.printDeck();

        System.out.println("\nPrint Hand 2 Cards:");
        System.out.println("\t\tBEFORE SORT");
        hand2.printDeck();
        hand2.sortDeck();
        System.out.println("\t\tAFTER SORT");
        hand2.printDeck();

        //Show number of cards after dealing
        System.out.println("\nNumber of cards in the Deck after dealing out " +
                "10 cards: " + deck.getDeck().size());
        System.out.println();
        //Return the cards in the hand to the deck
        hand1.returnCard(deck,5);
        hand2.returnCard(deck,5);

        //Print to ensure cards have been properly returned
        System.out.println("--Print hands and deck after cards are returned to deck--");
        hand1.printDeck();
        hand2.printDeck();
        System.out.println();
        deck.printDeck();
        System.out.println("Size of deck is: " + deck.getDeck().size());
        }
}



