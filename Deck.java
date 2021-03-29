import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents a deck of poker cards, represented as an arraylist with 52 Card objects
 */
public class Deck {
    private final ArrayList<Card> deck = new ArrayList<>();

    /**
     * Constructor to initialize the Deck object and fill the deck arraylist
     * with 52 Card objects
     */
    public Deck() {
        for (Suit suit : Suit.values()) {
            for (CardName name : CardName.values()) {
                deck.add(new Card(name, suit));
            }
        }
    }

    /**
     * Getter method to return the deck arraylist
     *
     * @return deck     arraylist of Card objects
     */
    ArrayList<Card> getDeck() {
        return this.deck;
    }

    /**
     * Prints all of the Card objects within deck arraylist
     * If the deck arraylist is null or empty, error message will be printed instead
     */
    void printDeck() {
        if (getDeck() == null || getDeck().isEmpty()) {
            System.out.println("Cannot print -- Cards array is empty or null.");
        }
        for (Card card : getDeck()) {
            System.out.println(card.toString());
        }
    }

    /**
     * Shuffles the card objects within deck arraylist
     */
    void shuffleDeck() {
        Collections.shuffle(getDeck());
    }

    /**
     * Sorts the card objects within deck arraylist
     * Refer to {@link Card#compareTo(Card)} for specified comparator used to determine
     * the order of Card objects.
     */
    void sortDeck() {
        Collections.sort(getDeck());
    }

    /**
     * Removes a card from the top of the deck arraylist
     *
     * @return  the card (Card type) to be removed from the deck
     * @throws IllegalStateException if the deck arraylist is null or empty
     */
    Card removeCard() throws IllegalStateException {
        if (getDeck() == null || getDeck().isEmpty()) {
            throw new IllegalStateException("The Deck is empty or null -- Can't remove card.");
        }
        Card card = getDeck().get(0);
        getDeck().remove(0);
        return card;
    }

    /**
     * Adds a card to the bottom of the deck arraylist
     *
     * @param card  card (Card type) to be added to the deck
     * @throws IllegalStateException if the card to be added already exists in the deck
     */
    void addCard(Card card) throws IllegalStateException {
        if (getDeck().contains(card)) {
            throw new IllegalStateException("Card is already in the deck.");
        }
        getDeck().add(card);
    }
}
