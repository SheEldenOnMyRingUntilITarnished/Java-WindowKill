
/**
 * Write a description of class Projectile here.
 *
 * @author Zachary Quinn
 * @version (a version number or a date)
 */

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Projectile extends RidgedBody2D
{
    private double speed;
    private double direction;
    
    private boolean fromPlayer;
    private boolean canHurtPlayer;
    
    /**
     * This class takes:
     * 
     * (double projectileSpeed, double projectileDirection, boolean projectileFromPlayer)
    **/
    public Projectile(int xSize, int ySize, double projectileSpeed, double projectileDirection, boolean projectileFromPlayer, boolean projectileCanHurtPlayer)
    {
        super(xSize, ySize); 
        
        this.canHurtPlayer = projectileCanHurtPlayer;
        
        this.speed = projectileSpeed;
        this.direction = projectileDirection;
        this.fromPlayer = projectileFromPlayer;
    }
    
    /**
     * This method is ran inside of the GameSystem,
     * This method updates the projectiles position based off of its speed and direction
    **/
    @Override
    public void update()
    {
        updatePosition(this.speed * Math.cos(this.direction),this.speed * Math.sin(this.direction));
    }
    
    /**
     * This method will check if the object is 
     * a enemy(the player if targetPlayer is true)
     * and if so deal damage to the target.
     * 
     * This method takes:
     * 
     * (Object collidedObject)
    **/
    @Override
    public void collison(Object collidedObject)
    {
        if(collidedObject instanceof Enemy && fromPlayer)
        {
            //do nothin for now
        }
        
        if(collidedObject instanceof Player && canHurtPlayer)
        {
            //do nothin for now
        }
    }
    
    /**
     * This method will destroy the bullet on collision with 
     * the edge of the window and expand the window on 
     * the side the projectile hit.
     * 
     * if the method returns true it will destroy itself
     * 
     * This method takes:
     * 
     * (WindowArea collidedWindow)
    **/
    @Override
    public boolean windowEdge(WindowArea collidedWindow)
    {
        WindowPanel windowPanel = collidedWindow.getGamePanel();
        int windowX = collidedWindow.getWindowXPositionAccountingForTopBar();
        int windowY = collidedWindow.getWindowYPositionAccountingForTopBar();
        int windowWidth = collidedWindow.getWindowWidth();
        int windowHeight = collidedWindow.getWindowHeight();
        
        if(!fromPlayer) //May change if i give gimics to the enemy bullets
        {
            return false;
        }
        
        if(this.getXPosition() >= windowX + windowWidth)
        {
            //Right wall
            System.out.println("Right");
            collidedWindow.setWindowSize(windowWidth + 50,windowHeight);
            windowPanel.setWindowPosition(windowX,windowY);
            return true;
        }
        else if(this.getXPosition() <= windowX)
        {
            //Left wall
            System.out.println("Left");
            collidedWindow.setWindowSize(windowWidth + 50,windowHeight);
            windowPanel.setWindowPosition(windowX,windowY);
            return true;
        }
        else if(this.getYPosition() <= windowY)
        {
            //Top wall
            System.out.println("Top");
            collidedWindow.setWindowSize(windowWidth ,windowHeight + 50);
            windowPanel.setWindowPosition(windowX,windowY);
            return true;
        }
        else if(this.getYPosition() >= windowY + windowHeight)
        {
            //Bottom wall
            System.out.println("Bottom");
            collidedWindow.setWindowSize(windowWidth ,windowHeight + 50);
            windowPanel.setWindowPosition(windowX - 6,windowY - 29);
            return true;
        }
        return false;
    }
    
    /**
     * This method will also destroy the bullet on collision but 
     * instead with the edge of the moniter.
     * 
     * returns true by default to destroy the projectile 
    **/
    @Override
    public boolean screenEdge()
    {
        //DEATH TO THE SCREEN!!!!!
        return true;
    }
    
    /**
     * The paint method is ran inside of the Window panel
     * 
     * This method takes:
     * 
     * (Graphics2D g2, int windowX, int windowY)
    **/
    @Override
    public void paint(Graphics2D g2, int windowX, int windowY)
    {
        g2.setColor(Color.WHITE);
        int positionX = this.getXPosition();
        int positionY = this.getYPosition();
        
        int sizeX = this.getXSize();
        int sizeY = this.getYSize();
        
        Shape circle = new Ellipse2D.Double(positionX - windowX - (sizeX/2), positionY - windowY - (sizeY/2), sizeX, sizeY);
        g2.rotate(direction, positionX - windowX - (sizeX/2), positionY - windowY - (sizeY/2));
        g2.draw(circle);
        g2.fill(circle);
        g2.dispose();
    }
    
    /**
     * returns the speed of the projectile as a double
    **/
    public double getSpeed()
    {
        return this.speed;
    }
    
    /**
     * returns the direction of the projectile as a double
    **/
    public double getDirection()
    {
        return this.direction;
    }
    
    /**
     * Returns if the projectile is able to hurt the player as a boolean
    **/
    public boolean getCanHurtPlayer()
    {
        return this.canHurtPlayer;
    }
    
    /**
     * Returns if the projectile originated from the player as a boolean
    **/
    public boolean getFromPlayer()
    {
        return this.fromPlayer;
    }
}