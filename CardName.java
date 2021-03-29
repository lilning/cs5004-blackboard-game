/**
 * This enum class represents the card names contained in a deck of cards,
 * as well as their corresponding card values (int type) in a Blackjack game.
 */

enum CardName {
    ACE(11), TWO(2), THREE(3), FOUR(4), FIVE(5),
    SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    JACK(10), QUEEN(10), KING(10);

    // Corresponding card values will be used for blackjack calculations
    private final int cardValue;

    /**
     * This constructor initializes each card name with corresponding
     * card value in integer type
     *
     * @param cardValue card value in integer type
     */
    CardName(int cardValue){
        this.cardValue = cardValue;
    }

    /**
     * Getter method to return card value (int type) for given card name
     *
     * @return  card value in integer type
     */
    public int getCardValue() {
        return this.cardValue;
    }
}

