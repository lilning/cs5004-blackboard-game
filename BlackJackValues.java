package MyBlackJack;

public enum BlackJackValues {

    ACE(11), KING(10), QUEEN(10), JACK(10),
    TWO(2), THREE(3),   FOUR(4), FIVE(5), SIX(6),
    SEVEN(7), EIGHT(8), NINE(9), TEN(10);

    private int value;


    private BlackJackValues(int value){
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }

    public static void main(String[] args) {

    }
}

