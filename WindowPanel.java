/**
 * Write a description of class GamePanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */


/**
 * Window realted imports
**/
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class WindowPanel extends JPanel
{
    
    JFrame window = null;
    private Player player = null;
    
    public WindowPanel(JFrame window, GameSystem chosenGameSystem) 
    {
        this.setPreferredSize(new Dimension(600,600));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.player = chosenGameSystem.player;
        this.addKeyListener(chosenGameSystem.inputManager.keyH);
        this.addMouseListener(chosenGameSystem.inputManager.mouseH);
        this.setFocusable(true);
        this.window = window;
    }
    
    /**
     * Runs when .repaint is ran in the gameSystem
     * repaints the windows screen with the new information.
    **/
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        paintPlayer(g2);
        
    }
    
    public void paintPlayer(Graphics2D g2)
    {
        g2.setColor(Color.RED);
        g2.drawRect(player.playerX,player.playerY,30, 30);
        System.out.println("playerX: " + player.playerX);
    }
    
    //Window Methods
    public int getWindowX() {return (int) window.getLocationOnScreen().getX();}
    public int getWindowY() {return window.getY();}
    
    public void setWindowPosition(int X, int Y) {window.setLocation(X,Y);}
    public void setWindowSize(int Width, int Height){window.setSize(Width,Height);}
}