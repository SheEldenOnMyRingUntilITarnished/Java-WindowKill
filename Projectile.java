
/**
 * Write a description of class Projectile here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JFrame;

public class Projectile extends RidgedBody2D
{
    private double speed;
    private double direction;
    private boolean targetPlayer;
    
    public Projectile(double projectileSpeed, double projectileDirection, boolean projectileTargetPlayer)
    {
        super(10, 10); 
        
        this.speed = projectileSpeed;
        this.direction = projectileDirection;
        this.targetPlayer = projectileTargetPlayer;
    }
    
    @Override
    public void update()
    {
        updatePosition(this.speed * Math.cos((int)this.direction),this.speed * Math.sin(this.direction));
    }
    
    /**
     * This method will check if the object is 
     * a enemy(the player if targetPlayer is true)
     * and if so deal damage to the target.
    **/
    @Override
    public void collison(Object collidedObject)
    {
        if(collidedObject instanceof Enemy && !targetPlayer)
        {
            //do nothin for now
        }
        
        if(collidedObject instanceof Player && targetPlayer)
        {
            //do nothin for now
        }
    }
    
    /**
     * This method will destroy the bullet on collision with 
     * the edge of the window and expand the window on 
     * the side the projectile hit.
    **/
    @Override
    public void windowEdge(JFrame collidedWindow)
    {
        int windowX = getWindowX(collidedWindow);
        int windowY = getWindowY(collidedWindow);
        int windowWidth = getWindowWidth(collidedWindow);
        int windowHeight = getWindowHeight(collidedWindow);
        if(this.getXPosition() >= windowX + windowWidth)
        {
            //Right wall
            System.out.println("Right");
        }
        else if(this.getXPosition() <= windowX + windowWidth)
        {
            //Left wall
            System.out.println("Left");
        }
        else if(this.getYPosition() >= windowY + windowHeight)
        {
            //Top wall
            System.out.println("Top");
        }
        else if(this.getYPosition() <= windowY + windowHeight)
        {
            //Bottom wall
            System.out.println("Bottom");
        }
    }
    
    /**
     * This method will also destroy the bullet on collision but 
     * instead with the edge of the moniter.
    **/
    @Override
    public void screenEdge()
    {
        //DEATH TO THE SCREEN!!!!!
    }
    
    @Override
    public void paint(Graphics2D g2, int windowX, int windowY)
    {
        g2.setColor(Color.RED);
        g2.drawRect(getXPosition() - windowX, getYPosition() - windowY, getXSize(), getYSize());
    }
    
    public double getSpeed()
    {
        return this.speed;
    }
    
    public double getDirection()
    {
        return this.direction;
    }
    
    public boolean getTargetPlayer()
    {
        return this.targetPlayer;
    }
    
    private int getWindowX(JFrame chosenWindow) {
        return (int) chosenWindow.getLocationOnScreen().getX();
    }

    private int getWindowY(JFrame chosenWindow) {
        return chosenWindow.getY();
    }

    private int getWindowWidth(JFrame chosenWindow){
        return chosenWindow.getWidth();
    }

    private int getWindowHeight(JFrame chosenWindow){
        return chosenWindow.getHeight();
    }

    private void setWindowSize(JFrame chosenWindow, int Width, int Height)
    {
        chosenWindow.setSize(Width,Height);
    }
}