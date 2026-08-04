/**
 * Write a description of class Button here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.PointerInfo;
import java.awt.Graphics2D;
import java.awt.Color;

public class Button extends UI_Collider
{
    
    public Button()
    {
        super(0,0);
    }
    
    public void paint(Graphics2D g2, int windowX, int windowY)
    {
        g2.setColor(Color.GREEN);
        g2.drawRect(getXPosition() - windowX, getYPosition() - windowY, getXSize(), getYSize());
    }
    
    @Override
    public void mouseChecks(PointerInfo currentMouse)
    {
        //check mouse hover
        
        //check mouse click on button
        
        //check if mouse stopped hovering
    }
    
    @Override
    public void collison(Object collidedObject)
    {
        //Collide with other elements cause why not
    }
}
