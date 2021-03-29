import java.util.Comparator;

/**
 * This class represents a single poker card.
 * Each card should contain a suit value (Suit type) and a card name (CardName type).
 */
public class Card implements Comparable<Card> {
    private Suit suit;
    private CardName name;

    /**
     * Initializes a card object with card name and suit value as parameters.
     *
     * @param name card name in CardName type (e.g. CardName.ACE)
     * @param suit suit value in Suit type (e.g. Suit.SPADE)
     */
    public Card(CardName name, Suit suit) {
        setSuit(suit);
        setCardName(name);
    }

    /**
     * Private setter method for suit value to validate the value cannot be null
     *
     * @param suit suit value in Suit type (e.g. Suit.SPADE)
     * @throws IllegalStateException if suit value is null
     */
    private void setSuit(Suit suit) throws IllegalStateException {
        if (suit == null) {
            throw new IllegalStateException("Exception -- Suit value cannot " +
                    "be null.");
        }
        this.suit = suit;
    }

    /**
     * Private setter method for card name to validate the value cannot be null
     *
     * @param name card name in cardName type (e.g. CardName.ACE)
     * @throws IllegalStateException if card name value is null
     */
    private void setCardName(CardName name) throws IllegalStateException {
        if (name == null) {
            throw new IllegalStateException("Exception -- Card name cannot " +
                    "be null.");
        }
        this.name = name;
    }

    /**
     * Getter method to obtain suit value for the Card object
     *
     * @return suit value in Suit type (e.g. Suit.SPADE)
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Getter method to obtain card name for the Card object
     *
     * @return card name in CardName type (e.g. CardName.ACE)
     */
    public CardName getCardName() {
        return this.name;
    }

    /**
     * Method to return card value in integer type for the corresponding card name
     * (e.g. return 2 for CardName.TWO)
     *
     * @return card value in integer type
     */
    public int getCardValue() {
        return name.getCardValue();
    }

    /**
     * ToString method to return Card object in String type
     *
     * @return a String message representing the Card Object
     */
    @Override
    public String toString() {
        return "Card{" +
                "name=" + name +
                ", suit=" + suit +
                '}';
    }

    /**
     * Compares this object with the specified object {@param other} for order
     * based on card name and suit value.
     *
     * @param other Card object used to compare with the specified card object
     * @return a negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Card other) {
        return Comparator.comparing(Card::getCardName)
                .thenComparing(Card::getSuit)
                .compare(this, other);
    }
}
