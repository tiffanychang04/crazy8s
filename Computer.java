/**
 * A computer in a game of crazy eights.
 * 
 * @author Tiffany Chang
 * @version May 2020
 */
public class Computer extends Player 
{
    
    /**
     * Constructs a computer.
     */
    public Computer() 
    {
        super();
    }

    /**
     * Removes and returns a card from the computer's hand.
     * 
     * @param eights the current crazy eights game
     * @param prev the card on top of discard pile
     * 
     * @return the card that the computer places
     */
    @Override
    public Card play(CrazyEights eights, Card prev) 
    {
        Card card = searchForMatch(prev);
        if (card == null) 
        {
            card = drawForMatch(eights, prev);
        }
        return card;
    }

    /**
     * Searches the player's hand for a matching card.
     * 
     * @param prev the card on top of the discard pile
     * 
     * @return a card from the computer's hand that can be played
     */
    public Card searchForMatch(Card prev) 
    {
        Hand hand = getHand(); // this may not work
        for (int i = 0; i < hand.getSize(); i++) 
        {
            Card card = hand.getCard(i);
            if (cardMatches(card, prev)) 
            {
                return hand.removeCard(i);
            }
        }
        return null;
    }

    /**
     * Adds card from draw pile until a card is valid.
     * 
     * @param eights the current crazy eights game
     * @param prev the card on top of discard pile
     * 
     * @return the card drawn that is valid
     */
    public Card drawForMatch(CrazyEights eights, Card prev) 
    {
        Hand hand = getHand();
        while (true) 
        {
            Card card = eights.draw();
            hand.addCard(card);
            eights.update();
            try
            {
                Thread.sleep(500);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }

            if (cardMatches(card, prev)) 
            {
                hand.removeLast();
                eights.update();
                try
                {
                    Thread.sleep(200);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                return card;
            }
        }
    }

    
}