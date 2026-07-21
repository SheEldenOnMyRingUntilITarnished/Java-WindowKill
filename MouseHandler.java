/**
 * Write a description of class MouseHandler here.
 *
 * @author Zachary Quinn
 * @version 05/11/2026
 */

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.MouseInfo;

public class MouseHandler implements MouseInputListener
{
    public boolean shootPressed;
    private double mouseX;
    private double mouseY;
    
    
    @Override
    public void mousePressed(MouseEvent e)
    {
        shootPressed = true;
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        shootPressed = false;
    }
    
    @Override
    public void mouseEntered(MouseEvent e)
    {
       
    }
    
    @Override
     public void mouseExited(MouseEvent e) 
    {
       
    }
    
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        
    }
    
    @Override
    public void mouseMoved(MouseEvent e) 
    {
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = MouseInfo.getPointerInfo().getLocation().getY();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) 
    {
        
    }
    
    public double getMouseX()
    {
        return this.mouseX;
    }
    
    public double getMouseY()
    {
        return this.mouseY;
    }
    
}