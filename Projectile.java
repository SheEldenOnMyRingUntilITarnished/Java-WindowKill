
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
            Enemy enemy = (Enemy) collidedObject;
            enemy.takeDamage(1);
            this.destroy();
        }
        
        if(collidedObject instanceof Player && canHurtPlayer)
        {
            this.destroy();
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
        int windowX = collidedWindow.getWindowXPosition();
        int windowY = collidedWindow.getWindowYPosition();
        int windowWidth = collidedWindow.getWindowWidth();
        int windowHeight = collidedWindow.getWindowHeight();
        
        if(!fromPlayer)
        {
            return false;
        }
        
        int expandAmount = 50;
        
        if(this.getXPosition() >= windowX + windowWidth)
        {
            //Right wall
            collidedWindow.setWindowSize(windowWidth + expandAmount, windowHeight);
            collidedWindow.setWindowPosition(windowX, windowY);
            return true;
        }
        else if(this.getXPosition() <= windowX)
        {
            //Left wall
            collidedWindow.setWindowSize(windowWidth + expandAmount, windowHeight);
            collidedWindow.setWindowPosition(windowX - expandAmount, windowY);
            return true;
        }
        else if(this.getYPosition() <= windowY)
        {
            //Top wall
            collidedWindow.setWindowSize(windowWidth, windowHeight + expandAmount);
            collidedWindow.setWindowPosition(windowX, windowY - expandAmount);
            return true;
        }
        else if(this.getYPosition() >= windowY + windowHeight)
        {
            //Bottom wall
            collidedWindow.setWindowSize(windowWidth, windowHeight + expandAmount);
            collidedWindow.setWindowPosition(windowX, windowY);
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
        Graphics2D g2d = (Graphics2D) g2.create();
        g2d.setColor(Color.WHITE);
        
        int drawX = this.getXPosition() - windowX;
        int drawY = this.getYPosition() - windowY;
        int sizeX = this.getXSize();
        int sizeY = this.getYSize();
        
        // Rotate around center point of projectile cause thats good
        g2d.rotate(direction, drawX, drawY);
        Shape bulletShape = new Ellipse2D.Double(drawX - (sizeX / 2.0), drawY - (sizeY / 2.0), sizeX, sizeY);
        g2d.draw(bulletShape);
        g2d.fill(bulletShape);
        g2d.dispose();
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