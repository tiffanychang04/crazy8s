/**
 * ClickListener establishes the name of the methods that
 * respond to a mouse click.
 * 
 * @author  Tiffany Chang
 * @version May 13, 2015
 */
public interface ClickListener
{
    /**
     * Responses to a mouse click within the draw card.
     */
    void drawPressed();
    
    /**
     * Responses to a mouse click within one of the human player's cards.
     * 
     * @param cardNum the position of the card in the player's hand
     */
    void cardPressed(int cardNum);
}
