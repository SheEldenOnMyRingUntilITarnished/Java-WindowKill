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
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import java.awt.PointerInfo;

public class Player extends RidgedBody2D
{
    GameSystem gameSystem = null;
    InputManager inputManager = new InputManager(this);
    PlayerStats playerStats = new PlayerStats();
    
    private int health = playerStats.playerMaxHealth;
    
    private double playerAccelerationX = 0;
    private double playerAccelerationY = 0;
    
    private int playerSize = 0;
    
    //Timers
    private double shootRate;
    private Timer shootTimer = new Timer();
    
    private double invinceTime;
    private Timer invinceTimer = new Timer();
    
    public Player(GameSystem chosenGameSystem)
    {
        super(0,0);
        
        gameSystem = chosenGameSystem;
        
        //Add Systems to the update manager
        gameSystem.objects.add(inputManager);
        gameSystem.objects.add(shootTimer);
        gameSystem.objects.add(invinceTimer);
    }
    
    @Override
    public void awake()
    {
        playerSize = playerStats.playerSize;
        this.health = playerStats.playerMaxHealth;
        shootRate = playerStats.playerFirerate;
        invinceTime = playerStats.playerInvincibility;
    }
    
    @Override
    public void start()
    {
        this.health = playerStats.playerMaxHealth;
        this.playerAccelerationX = 0;
        this.playerAccelerationY = 0;
        
        this.setPosition(
        java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2, //X
        java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2 //Y
        );
        
        this.setSize(playerSize,playerSize);
    }

    @Override
    public void update()
    {
        shootTimer.update();
        invinceTimer.update();
        
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
    
    @Override
    public void collison(Object collidedObject)
    {
        if(invinceTimer.timerHasPassed() && (collidedObject instanceof Enemy || collidedObject instanceof Projectile))
        {
            if (collidedObject instanceof Projectile && ((Projectile) collidedObject).getFromPlayer()) {
                return;
            }
            
            this.health--;
            this.invinceTimer.setTimer(120);
            
            if (this.health <= 0 && gameSystem != null && gameSystem.gameState == GameState.GAME)
            {
                gameSystem.triggerGameOver();
            }
        }
    }
    
    /**
     * This method will destroy the bullet on collision with 
     * the edge of the window and expand the window on 
     * the side the projectile hit.
    **/
    @Override
    public boolean windowEdge(WindowArea collidedWindow)
    {
        WindowPanel windowPanel = collidedWindow.getGamePanel();
        int windowX = windowPanel.getWindowX();
        int windowY = windowPanel.getWindowY();
        int windowWidth = collidedWindow.getWindowWidth();
        int windowHeight = collidedWindow.getWindowHeight();
        
        int xPos = this.getXPosition();
        int yPos = this.getYPosition();
        
        int halfSize = playerSize / 2;
        int minX = windowX + halfSize;
        int maxX = windowX + windowWidth - halfSize;
        int minY = windowY + halfSize;
        int maxY = windowY + windowHeight - halfSize;
        
        int clampedX = Math.max(minX, Math.min(maxX, xPos));
        int clampedY = Math.max(minY, Math.min(maxY, yPos));
        
        if (clampedX != xPos) {
            playerAccelerationX = 0;
        }
        if (clampedY != yPos) {
            playerAccelerationY = 0;
        }
        
        this.setPosition(clampedX, clampedY);
        return false;
    }
    
    /**
     * This method will also destroy the bullet on collision but 
     * instead with the edge of the moniter.
    **/
    @Override
    public boolean screenEdge()
    {
        //DEATH TO THE SCREEN!!!!!
        return false;
    }
    
    @Override
    public void paint(Graphics2D g2, int windowX, int windowY)
    {
        boolean isInvincible = !invinceTimer.timerHasPassed();
        
        if (isInvincible)
        {
            int blinkPhase = (int) Math.abs(invinceTimer.getCurrentTime()) % 10;
            if (blinkPhase < 5)
            {
                // Skip rendering frame during invinces
                return;
            }
            // Dimmed white color during invulnerability
            g2.setColor(new Color(255, 255, 255, 110));
        }
        else
        {
            g2.setColor(Color.WHITE);
        }
        
        int playerX = this.getXPosition();
        int playerY = this.getYPosition();
        
        int playerSizeX = this.getXSize();
        int playerSizeY = this.getYSize();
        
        int drawX = playerX - windowX;
        int drawY = playerY - windowY;
        
        Shape circle = new Ellipse2D.Double(drawX - (playerSizeX/2), drawY - (playerSizeY/2), this.playerSize, this.playerSize);
        g2.draw(circle);
        
        //HP Points :3
        if (isInvincible && health > 0)
        {
            int dotSize = 6;
            int dotSpacing = 10;
            int totalWidth = (health - 1) * dotSpacing;
            int startX = drawX - (totalWidth / 2);
            int dotCenterY = drawY - (playerSizeY / 2) - 14;
            
            for (int i = 0; i < health; i++)
            {
                int dotX = startX + (i * dotSpacing) - (dotSize / 2);
                int dotY = dotCenterY - (dotSize / 2);
                g2.fill(new Ellipse2D.Double(dotX, dotY, dotSize, dotSize));
            }
        }
    }
    
    public void attemptShoot(PointerInfo currentMouse)
    {
        if(shootTimer.timerHasPassed())
        {
            shootTimer.setTimer(shootRate);
            
            Projectile newPlayerProjectile = new Projectile(playerStats.projectileWidth, playerStats.projectileHeight, playerStats.playerProjectileSpeed, gameSystem.calculateTheAngleBetweenTwoPoints(getXPosition(), getYPosition(), currentMouse.getLocation().getX(), currentMouse.getLocation().getY()) * Math.PI/180,true,false);
            newPlayerProjectile.setPosition(getXPosition(),getYPosition());
            gameSystem.objects.add(newPlayerProjectile);
        }
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