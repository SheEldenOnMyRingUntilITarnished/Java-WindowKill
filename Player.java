/**
 * This script holds the players data such as the player position.
 * it also holds the list of player projectiles to seperate them from the enemy projectiles and give them priority.
 *
 * @author Zachary Quinn
 * @version 05/18/2026
 */

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JFrame;

public class Player extends RidgedBody2D
{
    GameSystem gameSystem = null;
    InputManager inputManager = new InputManager(this);
    PlayerStats playerStats = new PlayerStats();
    ArrayList<Projectile> playerProjectileList = new ArrayList<Projectile>();
    
    private int health = playerStats.playerMaxHealth;
    
    private double playerAccelerationX = 0;
    private double playerAccelerationY = 0;
    
    private Timer shootTimer = new Timer();
    private Timer invinceTimer = new Timer();
    
    public Player(GameSystem chosenGameSystem)
    {
        super(40, 40);
        gameSystem = chosenGameSystem;
    }
    
    @Override
    public void update()
    {
        int temp = 0;
        
        double friction = 0.35;
        
        if(this.playerAccelerationX > 0)
        {
            this.playerAccelerationX -= friction;
        }
        else if(this.playerAccelerationX < 0)
        {
            this.playerAccelerationX += friction;
        }
        
        if(this.playerAccelerationY > 0)
        {
            this.playerAccelerationY -= friction;
        }
        else if(this.playerAccelerationY < 0)
        {
            this.playerAccelerationY += friction;
        }
        
        double speedCap = playerStats.playerSpeedCap;
        
        if(this.playerAccelerationY > speedCap) this.playerAccelerationY = speedCap;
        if(this.playerAccelerationY < -speedCap) this.playerAccelerationY = -speedCap;
        if(this.playerAccelerationX > speedCap) this.playerAccelerationX = speedCap;
        if(this.playerAccelerationX < -speedCap) this.playerAccelerationX = -speedCap;
        
        //PLayer Movement Slippery
        this.updatePosition((int) Math.round(this.playerAccelerationX), (int) Math.round(this.playerAccelerationY));
    }
    
    /**
     * This method will check if the object is 
     * a enemy(the player if targetPlayer is true)
     * and if so deal damage to the target.
    **/
    @Override
    public void collison(Object collidedObject)
    {
        if(collidedObject instanceof Enemy || collidedObject instanceof Projectile)
        {
            // Take Damage
            this.health--;
            this.invinceTimer.setTimer(1.0);
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
        /*int windowX = getWindowX(collidedWindow);
        int windowY = getWindowY(collidedWindow);
        int windowWidth = getWindowWidth(collidedWindow);
        int windowHeight = getWindowHeight(collidedWindow);
        if(this.getXPosition() >= windowX + windowWidth)
        {
            //Right wall
            
        }
        else if(this.getXPosition() <= windowX + windowWidth)
        {
            //Left wall
            
        }
        
        if(this.getYPosition() >= windowY + windowHeight)
        {
            //Top wall
            
        }
        else if(this.getYPosition() <= windowY + windowHeight)
        {
            //Bottom wall
            
            
            java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2, 
        java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2,
        }*/
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
    
    public void attemptShoot()
    {
        //restartShootTimer();
        //Projectile newPlayerProjectile = new Projectile(playerStats.playerProjectileSpeed, gameSystem.calculateTheAngleBetweenTwoPoints(getXPosition(), getYPosition(), inputManager.MouseInfo.getPointerInfo().getLocation().getX(), inputManager.currentMouse.getLocation().getY()) * Math.PI/180,false);
        //newPlayerProjectile.setPosition(getXPosition(),getYPosition());
        //System.out.println("Bullet pointing this dir: " + newPlayerProjectile.getDirection());
        //gameSystem.activeObjects.add(newPlayerProjectile);
    }
    
    //Acceleration
    public void acceleratePlayerX(double accelerationAmount)
    {
        playerAccelerationX += accelerationAmount;
    }
    
    public void acceleratePlayerY(double accelerationAmount)
    {
        playerAccelerationY += accelerationAmount;
    }
}