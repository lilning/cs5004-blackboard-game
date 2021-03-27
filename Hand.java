package MyBlackJack;

import java.util.ArrayList;

public class Hand extends Deck {
    final int BLACKJACK = 21;
    private final ArrayList<Card> hand;
    private int totalHandValue;

    public Hand() {
        this.hand = new ArrayList<>();
    }

    @Override
    public ArrayList<Card> getDeck() {
        return hand;
    }

    public void dealCards(Deck deck, int numberOfCards){
        for(int i = 0; i < numberOfCards; i++){
            this.addCard(deck.removeCard());
            this.calculateHandValue();
        }
    }

    public int getTotalHandValue() {
        return this.totalHandValue;
    }


    public void calculateHandValue() {
        this.totalHandValue = 0; //reset hand value before each calculation.
        int aceCount = 0;

        for (int i = 0; i < this.getDeck().size(); i++) {
            if(this.getDeck().get(i).getValue() == 11){
                aceCount++;
            }
            totalHandValue += this.getDeck().get(i).getValue();
        }

        while(this.totalHandValue > BLACKJACK && aceCount > 0){
            this.totalHandValue -= 10;
            aceCount--;
        }
    }

    boolean isBust(int totalValue){
        return totalValue > BLACKJACK;
    }

    void returnCard(Deck deck, int numberOfCards)
            throws IllegalArgumentException, IllegalStateException{
        if(numberOfCards < 1){
            throw new IllegalArgumentException("Must return at least " +
                    "one card.");
        }

        if(this.getDeck().isEmpty() || this.getDeck() == null){
            throw new IllegalArgumentException("The hand is either null or " +
                    "empty.");
        }
        for(int i = 0; i < numberOfCards; i++){
            deck.addCard(this.getDeck().get(0));
            //this will remove the card at position 0
            this.removeCard();
        }
    }

    public static void main (String[]args){

        //Create the deck and teh hands
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



