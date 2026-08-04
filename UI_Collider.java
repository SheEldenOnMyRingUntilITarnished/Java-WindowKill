
/**
 * Write a description of class UI_Collider here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UI_Collider extends UI_Element
{
    private int xSize;
    private int ySize;
    
    public UI_Collider(int width, int height)
    {
        this.xSize = width;
        this.ySize = height;
    }
    
    public void collison(Object collidedObject)
    {
        //Epic code to cause EPIC EXPLOSION!!!!!
    }
    
    public void setSize(int chosenXSize, int chosenYSize)
    {
        this.xSize = chosenXSize;
        this.ySize = chosenYSize;
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
