
/**
 * The Object class is a reusable class that contains positional data,
 * this class alows diffrent objects to compared easily.
 *
 * @author Zachary Quinn
 * @version 7/02/2026
 */

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JFrame;

public class Object
{
    private int xPos;
    private int yPos;
    private boolean isDestroyed = false;
    
    public Object(int chosenXPos, int chosenYPos)
    {
        this.xPos = chosenXPos;
        this.yPos = chosenYPos;
    }
    
    public void awake()
    {
        
    }
    
    public void start()
    {
        
    }
    
    public void update()
    {
        
    }
    
    public void setPosition(int chosenXPos, int chosenYPos)
    {
        this.xPos = chosenXPos;
        this.yPos = chosenYPos;
    }
    
    public void updatePosition(double chosenXPos, double chosenYPos)
    {
        this.xPos += (int)chosenXPos;
        this.yPos += (int)chosenYPos;
    }
    
    public int getXPosition()
    {
        return this.xPos;
    }
    
    public int getYPosition()
    {
        return this.yPos;
    }
    
    public boolean isDestroyed()
    {
        return this.isDestroyed;
    }
    
    public void destroy()
    {
        this.isDestroyed = true;
    }
}
