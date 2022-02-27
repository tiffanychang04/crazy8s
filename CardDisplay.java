import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Displays the cards for crazy eights game.
 * 
 * @author Tiffany Chang
 * @version May 2020
 */
public class CardDisplay extends JComponent implements MouseListener
{
    private static final Color BACKGROUND = new Color(0, 100, 25);
    private static final Color BORDER = Color.BLACK;
    private static final Color CARDCOLOR = Color.WHITE;

    private static int cardWidth = 49;
    private static int cardHeight = 70;
    private static int rows = 9;
    private static int columns = 21;
    private static int width = 1029;
    private static int height = 630;

    private Card[][] board; 
    private JFrame frame; 
    private ClickListener listener;
    
    private boolean humanTurn = true;
    private boolean gameOver = false;

    /**
     * Constructs a new display for displaying the given board.
     */
    public CardDisplay()
    {
        board = new Card[rows][columns];

        /* 
         * Schedules a job for the event-dispatching thread, which
         * creates and shows this application's GUI.
         */
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    createAndShowGUI();
                }
            });

        /*
         * Waits until display has been drawn.
         */
        try
        {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI()
    {
        //Create and set up the window.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.addMouseListener(this);
        
        //Display the window.
        this.setPreferredSize(new Dimension(
                cardWidth * columns,
                cardHeight * rows));

        frame.pack();
        frame.setVisible(true);
    }

    /**
     *  Redraws the board to include the pieces and border colors
     *  Uses fillRect instead of drawing JButton as earlier to 
     *  render each block cleanly and quickly.
     *  @param g the graphics object that lets you repaint the screen 
     */
    public void paintComponent(Graphics g)
    {
        g.setColor(BACKGROUND); 
        g.fillRect(0, 0, width, height);
                   
        for (int row = 0; row < rows; row++)
        {
            int cardCount = 0;
            for (int col = 0; col < columns; col++)
            {
                Card curCard = board[row][col];
                if (curCard != null)
                {
                    cardCount++;
                    g.setColor(BORDER);
                    g.drawRoundRect(col*cardWidth, row*cardHeight, cardWidth, cardHeight, 5, 5);
                    g.setColor(CARDCOLOR);
                    g.fillRoundRect(col*cardWidth + 2, row*cardHeight + 2, cardWidth - 4, 
                        cardHeight - 4, 5, 5);
                    g.setColor(Color.BLACK);
                    if (row > 3)
                    {
                        int number = curCard.getValue();
                        String rank;
                        if (number > 1 && number < 11)
                            rank = Integer.toString(number);
                        else if (number == 1)
                            rank = "A";
                        else if (number == 11)
                            rank = "J";
                        else if (number == 12)
                            rank = "Q";
                        else
                            rank = "K";
                            
                        int suitNum = curCard.getSuit();
                        String suit;
                        if (suitNum == 0) // clubs
                            suit = "\u2663";
                        else if (suitNum == 1) // diamonds
                            suit = "\u2666";
                        else if (suitNum == 2) // hearts
                            suit = "\u2764";
                        else // spades
                            suit = "\u2660";
                         
                        if(suitNum == 1 || suitNum == 2)
                        {
                            g.setColor(new Color(160, 0, 0));
                        }
                        if(!rank.equals("10"))
                        {
                            g.setFont(new Font("Courier", Font.BOLD,24));
                            g.drawString(rank, col*cardWidth + 17, row*cardHeight + 30);
                        }
                        else
                        {
                            g.setFont(new Font("Courier", Font.BOLD,24));
                            g.drawString(rank, col*cardWidth + 10, row*cardHeight + 30);
                        }
                        
                        g.setFont(new Font("Monospaced", Font.BOLD,24));
                        g.drawString(suit, col*cardWidth + 17, row*cardHeight + 55);
                        
                    }
                }
            }
            if(cardCount == 0 && row == 1)
            {   
                g.setColor(Color.BLACK);
                g.setFont(new Font("SansSerif", Font.BOLD,60));
                g.drawString("YOU LOST", 365, 123);
                gameOver = true;
            }
            if(cardCount == 0 && row == 7)
            {   
                g.setColor(Color.BLACK);
                g.setFont(new Font("SansSerif", Font.BOLD,60));
                g.drawString("YOU WON", 370, 547);
                gameOver = true;
            }
            if(cardCount >= 21 && row == 7)
            {
                g.setColor(Color.BLACK);
                g.setFont(new Font("SansSerif", Font.BOLD,60));
                g.drawString("YOU LOST", 365, 335);
                gameOver = true;
            }
            if(cardCount >= 21 && row == 1)
            {
                g.setColor(Color.BLACK);
                g.setFont(new Font("SansSerif", Font.BOLD,60));
                g.drawString("YOU WON", 370, 335);
                gameOver = true;
            }
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier", Font.BOLD,16));
        g.drawString("Draw", 495, 240);
        g.drawString("Pile", 495, 260);
        g.setColor(Color.YELLOW);
    }
    
    /**
     * Retrieves card's width.
     * 
     * @return width of card 
     */
    public int getCardWidth()
    {
        return cardWidth;
    }
    
    /**
     * Retrieves card's height.
     * 
     * @return height of card
     */
    public int getCardHeight()
    {
        return cardHeight;
    }

    /**
     * Sets the title of the window.
     * 
     * @param title  the information to be placed at the
     *               top of the window
     */
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    /**
     * Redraws board.
     * 
     * @param board1 the board
     */
    public void updateBoard(Card[][] board1)
    {
        this.board = board1;
        repaint();
    }
    
    /**
     * Gets location of mouse click and determines further action.
     * 
     * @param e any mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(listener == null)
            return;
        if (e.getButton() == MouseEvent.BUTTON1 && humanTurn && !gameOver) 
        {
            
            if (e.getX() > 10*cardWidth && 
                e.getX() < 11*cardWidth && 
                e.getY() > 236 && 
                e.getY() < 300) 
            {
                listener.drawPressed();
                this.getGraphics().setColor(Color.YELLOW);
                this.getGraphics().drawRoundRect(488, 208, cardWidth+4, cardHeight+4, 5, 5);
                try
                {
                    Thread.sleep(200);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                this.getGraphics().setColor(BACKGROUND);
            }
            //System.out.println(e.getX());
            //System.out.println(e.getY());
            int cardCounter = 0;
            for (int col = 0; col < columns; col++)
            {
                Card curCard = board[7][col];
                if (curCard != null)
                {
                    if (e.getX() > col*cardWidth && 
                        e.getX() < (col+1)*cardWidth && 
                        e.getY() > 515 && 
                        e.getY() < 580)
                    {
                        listener.cardPressed(cardCounter);
                        
                        this.getGraphics().setColor(Color.YELLOW);
                        this.getGraphics().drawRoundRect(col*cardWidth-2, 488, cardWidth+4, 
                            cardHeight+4, 5, 5);
                        
                        try
                        {
                            Thread.sleep(500);
                        }
                        catch(InterruptedException ex)
                        {
                            Thread.currentThread().interrupt();
                        }
                    }
                    cardCounter++;
                }
            }
        }
    }
    
    /**
     * No action necessary when mouse exited.
     * 
     * @param e any mouse event
     */
    @Override
    public void mouseExited(MouseEvent e)
    {
    }
    
    /**
     * No action necessary when mouse exited.
     * 
     * @param e any mouse event
     */
    @Override
    public void mouseEntered(MouseEvent e)
    {
    }
    
    /**
     * No action necessary when mouse exited.
     * 
     * @param e any mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e)
    { 
    }
    
    /**
     * No action necessary when mouse exited.
     * 
     * @param e any mouse event
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
    }
    
    /**
     * Sets click listener.
     * 
     * @param listenerA the click listener
     */
    public void setClickListener(ClickListener listenerA)
    {
        this.listener = listenerA;
    }
    
    /**
     * Switches player turns.
     */
    public void switchTurn()
    {
        humanTurn = !humanTurn;
    }
    
}