package MyBlackJack;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deck = new ArrayList<Card>();

    public Deck() {
        for (Suit suit : Suit.values()) {
            for (BlackJackValues value: BlackJackValues.values()) {
                deck.add(new Card(value, suit));
            }
        }
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    void printDeck() {
        if(getDeck() == null || getDeck().isEmpty()){
            System.out.println("Cannot print -- Cards array is empty or null.");
        }
        for (Card card :getDeck()) {
            System.out.println(card.toString());
        }
    }

    //Look into collections.shuffle
    void shuffleDeck() {
        Collections.shuffle(getDeck());
    }

    void sortDeck() {
        getDeck().sort(new CardSortingComparator());
    }

    Card removeCard() throws IllegalStateException {
        if (getDeck().isEmpty()) {
            throw new IllegalStateException("The Deck empty or null -- Can't " +
                    "remove card.");
        }
        Card card = getDeck().get(0);
        getDeck().remove(0);
        return card;
    }

    void addCard(Card card) throws IllegalStateException {
        if (getDeck().contains(card)) {
            throw new IllegalStateException("Card is already in the deck.");
        }
        getDeck().add(card);
    }

    public static void main(String[] args) {
        ArrayList<Card> cardDeck = new ArrayList<>();
        Deck deck = new Deck();
        int count = 0;

        Card card = new Card(BlackJackValues.ACE,
                Suit.DIAMOND);

        for (Card cards : deck.getDeck()) {
            if(cards.getValue() == 11) {
                count++;
            }
        }
        System.out.println(count);
    }
}


