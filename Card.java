package MyBlackJack;

public class Card {
    private Suit suit;
    private BlackJackValues value;

    public Card(BlackJackValues value, Suit suit) {
        setSuit(suit);
        setValue(value);
    }

    private void setSuit(Suit suit) throws IllegalStateException {
        if(suit == null){
            throw new IllegalStateException("Exception -- MyBlackJack.Suit value cannot " +
                    "be null.");
        }
        this.suit = suit;
    }

    private void setValue(BlackJackValues value) throws IllegalStateException {
        if(suit == null){
            throw new IllegalStateException("Exception -- MyBlackJack.Suit value cannot " +
                    "be null.");
        }
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public Integer getValue() {
        return value.getValue();
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", suit=" + suit +
                '}';
    }
}
