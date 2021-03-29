import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;


/**
 * This class tests the constructor and methods within the Card class.
 */
public class CardTest {
	Card card1;
	Card card2;
	Card card3;

	@Before
	public void setUp() throws Exception {
		card1 = new Card(CardName.ACE, Suit.SPADE);
		card2 = new Card(CardName.FOUR, Suit.HEART);
		card3 = new Card(CardName.KING, Suit.CLUB);
	}

	@Test (expected = IllegalStateException.class)
	public void testSetUpWithNullSuit() {
		card1 = new Card(CardName.ACE, null);
	}

	@Test (expected = IllegalStateException.class)
	public void testSetUpWithNullCardName() {
		card1 = new Card(null, Suit.DIAMOND);
	}

	@Test
	public void getSuit() {
		Assert.assertEquals(Suit.SPADE, card1.getSuit());
		Assert.assertEquals(Suit.HEART, card2.getSuit());
		Assert.assertEquals(Suit.CLUB, card3.getSuit());
	}

	@Test
	public void getCardName() {
		Assert.assertEquals(CardName.ACE, card1.getCardName());
		Assert.assertEquals(CardName.FOUR, card2.getCardName());
		Assert.assertEquals(CardName.KING, card3.getCardName());
	}

	@Test
	public void getCardValue() {
		Assert.assertEquals(11, card1.getCardValue());
		Assert.assertEquals(4, card2.getCardValue());
		Assert.assertEquals(10, card3.getCardValue());
	}

	@Test
	public void testToString() {
		Assert.assertEquals("Card{name=ACE, suit=SPADE}", card1.toString());
		Assert.assertEquals("Card{name=FOUR, suit=HEART}", card2.toString());
		Assert.assertEquals("Card{name=KING, suit=CLUB}", card3.toString());
	}

	@Test
	public void compareTo() {
		//For this test, we are checking if compareTo return value is a negative integer, 0, or
		// positive integer based on each comparison
		Assert.assertTrue(card1.compareTo(card2) < 0); //card1 < card2
		Assert.assertTrue(card1.compareTo(card1) == 0); //card1 == card1
		Assert.assertTrue(card2.compareTo(card1) > 0); //card2 > card1
		Assert.assertTrue(card2.compareTo(card3) < 0); //card2 < card3
		Assert.assertTrue(card3.compareTo(card1) > 0); //card3 > card1
	}
}