import java.util.*;

/**
 * The human player in the crazy eights game.
 * 
 * @author Tiffany Chang
 * @version May 2020
 */
public class HumanPlayer extends Player implements ClickListener
{
    private Card retCard;
    private Card prev;
    private boolean moveMade = false;
    private boolean cardDrawn = false;
    
    /**
     * Constructs a HumanPlayer object.
     */
    public HumanPlayer()
    {
        super();
    }
    
    /**
     * Takes input from click listener to make move.
     * 
     * @param cardNum the position of the card in the player's hand based on location of mouse click
     */
    @Override
    public void cardPressed(int cardNum)
    {
        if (cardMatches(getHand().getCard(cardNum), prev)) // selected card works :)
        {
            Card card = getHand().removeCard(cardNum);
            retCard = card;
            moveMade = true;
        }
    }
    
    /**
     * Adds card from draw pile to player's hand if location of click is within draw pile borders.
     */
    @Override
    public void drawPressed()
    {
        cardDrawn = true;
    }
   
    /**
     * Plays a human's turn.
     * 
     * @param eights the current crazy eights game
     * @param prev1 the top card of discard pile
     * 
     * @return card the card being played by the player
     */
    @Override
    public Card play(CrazyEights eights, Card prev1)
    {
        eights.getDisplay().setClickListener(this);
        this.prev = prev1;
        boolean validInput = false;
        int index = 0;
        retCard = null;
        while (!validInput)
        {
            eights.update();
            if(moveMade)
            {
                validInput = true;
                eights.update();
                moveMade = false;
                eights.update();
                return retCard;
            }
            if(cardDrawn)
            {
                Card newCard = eights.draw();
                getHand().addCard(newCard);
                eights.update();
                if(eights.getDrawPile().isEmpty())
                {
                    eights.reshuffle();
                    eights.update();
                    try
                    {
                        Thread.sleep(3000);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
                cardDrawn = false;
            }
            moveMade = false;
            cardDrawn = false;
            try
            {
                Thread.sleep(50);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
        return null;
    }
}
