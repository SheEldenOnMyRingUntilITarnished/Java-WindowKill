import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Write a description of class SpriteRenderer here.
 *
 * @author Zachary Quinn
 * @version (a version number or a date)
 */
public class SpriteRenderer extends Object
{
    private int xSpriteSize;
    private int ySpriteSize;
    
    public SpriteRenderer()
    {
        super(0,0);
    }
    
    public void paint(Graphics2D g2, int windowX, int windowY)
    {
        g2.setColor(Color.GRAY);
        g2.drawRect(this.getXPosition() - windowX, this.getYPosition() - windowY, this.xSpriteSize, this.ySpriteSize);
    }
}