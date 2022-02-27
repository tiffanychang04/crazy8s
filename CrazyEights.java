import java.util.Scanner;

/**
 * A game of Crazy Eights.  
 * 
 * I give permission for the Harker Computer Science department to use this as a template.
 * 
 * @author Tiffany Chang
 * @version May 25, 2020
 */
public class CrazyEights 
{
    private Player comp;
    private Player user;
    private Hand drawPile;
    private Hand discardPile;
    private Scanner in;
    private CardDisplay display;
    private Card[][] board;
    private boolean isOver = false;
    
    /** 
     * Initial size of a hand.
     */
    public static final int HAND_SIZE = 7;
    
    // indices of where to play cards in grid based on how many cards are currently in hand
    private int[][] indices = { 
        {},
        {10}, 
        {9, 11}, 
        {8, 10, 12}, 
        {7, 9, 11, 13}, 
        {6, 8, 10, 12, 14}, 
        {5, 7, 9, 11, 13, 15}, 
        {4, 6, 8, 10, 12, 14, 16}, 
        {3, 5, 7, 9, 11, 13, 15, 17}, 
        {2, 4, 6, 8, 10, 12, 14, 16, 18}, 
        {1, 3, 5, 7, 9, 11, 13, 15, 17, 19}, 
        {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, 
        {4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, 
        {4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}, 
        {3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}, 
        {3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17}, 
        {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17}, 
        {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18}, 
        {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18}, 
        {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19}, 
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19}, 
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20} 
    };

    /**
     * Creates an instance of the Crazy 8s.
     */
    public CrazyEights() 
    {
        Hand deck = new Hand();
        for (int suit = 0; suit <= 3; suit++) 
        { 
            for (int value = 1; value <= 13; value++) 
            {
                deck.addCard(new Card(value, suit));
            }
        }
        deck.shuffle();

        comp = new Computer();
        deck.deal(comp.getHand(), HAND_SIZE);
        user = new HumanPlayer(); 
        deck.deal(user.getHand(), HAND_SIZE);

        discardPile = new Hand();
        deck.deal(discardPile, 1);
        drawPile = new Hand();
        deck.dealAll(drawPile);
        
        display = new CardDisplay();
        
        board = new Card[9][21];
        // index of draw pile    [3][10]
        // index of discard pile [5][10]
    }

    /**
     * Returns the card display.
     * 
     * @return the card display
     */
    public CardDisplay getDisplay()
    {
        return display;
    }
    
    /**
     * Returns if a player has zero cards or over 21 cards.
     * (Over 21 is an automatic loss)
     */
    public void gameOver() 
    {
        if(comp.getHand().isEmpty() || user.getHand().isEmpty() || comp.getHand().getSize()>=21 
            || user.getHand().getSize()>=21)
        {
            isOver = true;
        }
    }

    /**
     * Returns draw pile. 
     * 
     * @return the draw pile
     */
    public Hand getDrawPile()
    {
        return drawPile;
    }
    
    /**
     * Shuffles discard pile and moves cards to draw pile.
     */
    public void reshuffle() 
    {
        Card top = discardPile.removeLast(); // top of discard pile
        discardPile.dealAll(drawPile);
        discardPile.addCard(top);
        drawPile.shuffle(); // shuffle draw pile
        display.updateBoard(board);
    }

    /**
     * Returns the card a player draws.
     * 
     * @return the drawn card
     */
    public Card draw() 
    {
        if(drawPile.isEmpty()) 
        {
            reshuffle();
        }
        update();
        return drawPile.removeLast();
    }

    /**
     * Switches players.
     * 
     * @param current the current player
     * 
     * @return the new player
     */
    public Player switchPlayer(Player current) 
    {
        if (current == comp) 
        {
            display.switchTurn();
            return user;
        } 
        else 
        {
            display.switchTurn();
            return comp;
        }
    }

    /**
     * Plays the game.
     */
    public void playGame() 
    {
        Player player = user;

        // keep playing until there's a winner
        while (!isOver) 
        {
            update();
            if(player == comp)
            {
                try
                {
                    Thread.sleep(2500);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
            }
            display.updateBoard(board);
            
            gameOver();
            
            Card prev = discardPile.getLast(); // top of discard pile
            Card next = player.play(this, prev); // player's card
            update();
            discardPile.addCard(next); // add's player card to top of discard pile
            update();
            
            player = switchPlayer(player); // next player
            
            update();
            gameOver();
        }
        update();
    }
    
    /**
     * Updates the cardDisplay.
     */
    public void update()
    {
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[0].length; j++)
            {
                board[i][j] = null;
            }
        }
        if (!drawPile.isEmpty()) 
            board[3][10] = new Card(0, 0); // to display blank card
        else
            board[3][10] = null; // if empty draw pile, won't display a card but will display text
        board[5][10] = discardPile.getLast();
        
        int compSize = comp.getHand().getSize();
        int userSize = user.getHand().getSize();
        
        if(compSize >= 21) // board only has space to update 21 cards
        {
            for (int index = 0; index < 21; index++) 
            {
                board[1][indices[21][index]] = comp.getHand().getCard(index);
            }
            display.updateBoard(board);
            return;
        }
        else
        {
            for (int index = 0; index < indices[compSize].length; index++) 
            {
                board[1][indices[compSize][index]] = comp.getHand().getCard(index);
            }
        }
        
        if(userSize >= 21) // board only has space to update 21 cards
        {
            for (int index = 0; index < 21; index++) 
            {
                board[7][indices[21][index]] = user.getHand().getCard(index);
            }
            display.updateBoard(board);
            return;
        }
        else
        {
            for (int index = 0; index < indices[userSize].length; index++) 
            {
                board[7][indices[userSize][index]] = user.getHand().getCard(index);
            }
        }
        
        display.updateBoard(board);
    }

    
    /**
     * Creates the game and runs it.
     * 
     * @param args information from the command line
     */
    public static void main(String[] args) 
    {
        CrazyEights game = new CrazyEights();
        game.playGame();
    }

}