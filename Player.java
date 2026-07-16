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

public class Player extends Object
{
    GameSystem gameSystem = null;
    InputManager inputManager = new InputManager(this);
    PlayerStats playerStats = new PlayerStats();
    ArrayList<Projectile> playerProjectileList = new ArrayList<Projectile>();
    
    private double playerAccelerationX = 0;
    private double playerAccelerationY = 0;
    
    private double shootTimer = playerStats.playerFirerate;
    
    public Player(GameSystem chosenGameSystem)
    {
        super(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2, 
        java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2, 
        40, 
        40);
        gameSystem = chosenGameSystem;
    }
    
    @Override
    public void update()
    {
        int temp = 0;
        
        double friction = 0.35;
        
        if(shootTimer > 0)
        {
            shootTimer--;
        }
        
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
    public void paint(Graphics2D g2, int windowX, int windowY)
    {
        g2.setColor(Color.RED);
        g2.drawRect(getXPosition() - windowX, getYPosition() - windowY, getXSize(), getYSize());
    }
    
    public boolean canShoot()
    {
        return this.shootTimer < 1;
    }
    
    public void restartShootTimer()
    {
        this.shootTimer = playerStats.playerFirerate;
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