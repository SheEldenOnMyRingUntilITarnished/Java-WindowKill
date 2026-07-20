import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Write a description of class RidgedBody2D here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RidgedBody2D extends Object
{
    private int xSize;
    private int ySize;
    
    public RidgedBody2D(int chosenXSize, int chosenYSize)
    {
        super(0,0);
    }
    
    @Override
    public void update()
    {
        
    }
    
    /**
     * This method will contain the code that will be ran on collision
     * 
     * The method takes the collided object.
    **/
    public void collison(Object collidedObject)
    {
        //Epic code to cause EPIC EXPLOSION!!!!!
    }
    
    /**
     * This method will contain the code that will be ran
     * on collision with the edges of the window
    **/
    public void windowEdge(JFrame collidedWindow)
    {
        //DEATH TO THE WINDOW!!!!!
    }
    
    /**
     * This method will contain the code that will be ran
     * on collision with the edges of the screen
    **/
    public void screenEdge()
    {
        //DEATH TO THE SCREEN!!!!!
    }
    
    public void paint(Graphics2D g2, int windowX, int windowY)
    {
        g2.setColor(Color.GREEN);
        g2.drawRect(getXPosition() - windowX, getYPosition() - windowY, getXSize(), getYSize());
    }
    
    public void setSize(int chosenXSize, int chosenYSize)
    {
        this.xSize = chosenXSize;
        this.ySize = chosenYSize;
    }
    
    public void updateSize(int chosenXSize, int chosenYSize)
    {
        this.xSize += chosenXSize;
        this.ySize += chosenYSize;
    }
    
    public int getXSize()
    {
        return this.xSize;
    }
    
    public int getYSize()
    {
        return this.ySize;
    }
}