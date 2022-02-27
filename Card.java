/**
 * A playing card in a 52-card deck.
 * 
 * @author Tiffany Chang
 * @version May 2020
 */
public class Card 
{
    private final int value;
    private final int suit;

    /**
     * Constructs a card of a certain value and suit.
     * 
     * @param value the value of the card
     * @param suit the suit of the card
     */
    public Card(int value, int suit) 
    {
        this.value = value;
        this.suit = suit;
    }

    /**
     * Retrieves the value of the card.
     * 
     * @return the value of the card
     */
    public int getValue() 
    {
        return this.value;
    }

    /**
     * Retrieves the suit of the card.
     * 
     * @return the suit of the card.
     */
    public int getSuit() 
    {
        return this.suit;
    }
}