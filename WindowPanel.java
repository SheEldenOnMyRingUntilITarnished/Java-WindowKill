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
    private GameSystem gameSystem;
    private Player player = null;
    private String windowType = "null";
    
    private double width;
    private double height;
    
    public WindowPanel(JFrame window, GameSystem chosenGameSystem, int xSize, int ySize) 
    {
        this.setPreferredSize(new Dimension(xSize,ySize));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.gameSystem = chosenGameSystem;
        this.player = chosenGameSystem.player;
        this.addKeyListener(chosenGameSystem.inputManager.keyH);
        this.addMouseListener(chosenGameSystem.inputManager.mouseH);
        this.setFocusable(true);
        this.window = window;
    }
    
    public void setWindowType(String chosenWindowType)
    {
        this.windowType = chosenWindowType;
    }
    
    /**
     * Runs when .repaint is ran in the gameSystem
     * repaints the windows screen with the new information.
    **/
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        if(this.windowType.equals("null"))
        {
            System.err.println("Window Missing WindowType");
            return;
        }
        
        for(int i = 0; i < gameSystem.objects.size(); i++)
        {
            if(gameSystem.objects.get(i) instanceof RidgedBody2D)
            {
                RidgedBody2D obj = (RidgedBody2D) gameSystem.objects.get(i);
                
                obj.paint(g2, getWindowX(), getWindowY());
            }
        }      
    }
    //Window Methods
    public int getWindowX() {
        try {
            return (int) this.getLocationOnScreen().getX();
        } catch (Exception e) {
            return (int) window.getLocationOnScreen().getX();
        }
    }
    
    public int getWindowY() {
        try {
            return (int) this.getLocationOnScreen().getY();
        } catch (Exception e) {
            return window.getY();
        }
    }
    
    public int getWindowWidth(){
        return this.getWidth();
    }

    public int getWindowHeight(){
        return this.getHeight();
    }
    
    public void setWindowPosition(int X, int Y) 
    {
        window.setLocation(X,Y);
    }
    
    public void setWindowSize(double chosenWidth, double chosenHeight)
    {
        width = chosenWidth;
        height = chosenHeight;
        window.setSize((int)width,(int)height);
    }
}