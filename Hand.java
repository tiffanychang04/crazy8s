import java.util.*;

/**
 * A player's hand of cards.
 * 
 * @author Tiffany Chang
 * @version May 19, 2020
 */
public class Hand 
{
    private ArrayList<Card> cards;

    /**
     * Creates an empty hand.
     */
    public Hand() 
    {
        this.cards = new ArrayList<Card>();
    }

    /**
     * Adds a given card to the hand.
     * 
     * @param card the card to be added
     */
    public void addCard(Card card) 
    {
        cards.add(card);
    }

    /**
     * Removes and returns the card at a certain index.
     * 
     * @param i the index to remove card from
     * 
     * @return the card at the index
     */
    public Card removeCard(int i) 
    {
        return cards.remove(i);
    }

    /**
     * Removes and returns the last card.
     * 
     * @return the card at the last index
     */
    public Card removeLast() 
    {
        int i = getSize() - 1;
        return removeCard(i);
    }

    /**
     * Returns the number of cards.
     * 
     * @return size of the hand
     */
    public int getSize() 
    {
        return cards.size();
    }

    /**
     * Returns whether the hand is empty.
     * 
     * @return true if the hand is empty; otherwise;
     *         false
     */
    public boolean isEmpty() 
    {
        return getSize() == 0;
    }

    /**
     * Returns the card at a certain index.
     * 
     * @param i the index of the card to return
     * 
     * @return the card at the index
     */
    public Card getCard(int i) 
    {
        return cards.get(i);
    }

    /**
     * Returns the last card.
     * 
     * @return the card at the last index
     */
    public Card getLast() 
    {
        int i = getSize() - 1;
        return cards.get(i);
    }
    
    /**
     * Moves a certain number of cards from this hand to another.
     * 
     * @param that the hand to move cards to
     * @param numCards number of cards to be moved
     */
    public void deal(Hand that, int numCards) 
    {
        for (int i = 0; i < numCards; i++) 
        {
            Card card = removeLast();
            that.addCard(card);
        }
    }

    /**
     * Moves all remaining cards to the given collection.
     * 
     * @param that the hand to move cards to
     */
    public void dealAll(Hand that) 
    {
        int n = getSize();
        deal(that, n);
    }

    /**
     * Shuffles cards.
     */
    public void shuffle() 
    {
        Random random = new Random();
        for (int i = getSize() - 1; i > 0; i--) 
        {
            int j = random.nextInt(i);
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

}