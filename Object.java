
/**
 * Write a description of class Object here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Object
{
    private int xPos;
    private int yPos;
    
    private int xSize;
    private int ySize;
    
    public Object(int chosenXPos, int chosenYPos)
    {
        this.xPos = chosenXPos;
        this.yPos = chosenYPos;
    }
    
    public void checkForCollision()
    {
        
    }
    
    public void setPosition(int chosenXPos, int chosenYPos)
    {
        this.xPos = chosenXPos;
        this.yPos = chosenYPos;
    }
    
    public void updatePosition(int chosenXPos, int chosenYPos)
    {
        this.xPos += chosenXPos;
        this.yPos += chosenYPos;
    }
    
    public int getXPosition()
    {
        return this.xPos;
    }
    
    public int getYPosition()
    {
        return this.yPos;
    }
}
