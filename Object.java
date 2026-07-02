
/**
 * The Object class is a reusable class that contains positional data,
 * this class alows diffrent objects to compared easily.
 *
 * @author Zachary Quinn
 * @version 7/02/2026
 */
public class Object
{
    private int xPos;
    private int yPos;
    
    private int xSize;
    private int ySize;
    
    public Object(int chosenXPos, int chosenYPos, int chosenXSize, int chosenYSize)
    {
        this.xPos = chosenXPos;
        this.yPos = chosenYPos;
        this.xSize = chosenXSize;
        this.ySize = chosenYSize;
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
    
    public int getXPosition()
    {
        return this.xPos;
    }
    
    public int getYPosition()
    {
        return this.yPos;
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
