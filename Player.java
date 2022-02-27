/**
 * A player in a game of crazy eights.
 * 
 * @author Tiffany Chang
 * @version May 2020
 */
public class Player 
{
    private Hand hand;

    /**
     * Constructs a player with an empty hand.
     */
    public Player() 
    {
        this.hand = new Hand();
    }

    /**
     * Retrieves the player's hand.
     * 
     * @return the player's hand.
     */
    public Hand getHand() 
    {
        return hand;
    }

    /**
     * Checks whether a card can be played based on the previous card.
     * 
     * @param cur the card that is being played
     * @param prev the previous card
     * 
     * @return whether the card can be played
     */
    public static boolean cardMatches(Card cur, Card prev) 
    {
        if (cur.getSuit() == prev.getSuit()) 
        {
            return true;
        }
        if (cur.getValue() == prev.getValue()) 
        {
            return true;
        }
        if (cur.getValue() == 8) 
        {
            return true;
        }
        return false;
    }

    /**
     * Plays one turn. Overriden in subclass.
     * 
     * @param eights the current crazy eights game
     * @param prev the card on top of draw pile
     * 
     * @return the Card that the player plays
     */
    public Card play(CrazyEights eights, Card prev) // to make compiler happy
    {
        return null;
    }
}