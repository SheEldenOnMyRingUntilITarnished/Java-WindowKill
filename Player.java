import java.util.List;
import java.util.ArrayList;

/**
 * This script holds the players data such as the player position.
 * it also holds the list of player projectiles to seperate them from the enemy projectiles and give them priority.
 *
 * @author Zachary Quinn
 * @version 05/18/2026
 */
public class Player
{
    GameSystem gameSystem = null;
    InputManager inputManager = new InputManager(this);
    PlayerStats playerStats = new PlayerStats();
    ArrayList<Projectile> playerProjectileList = new ArrayList<Projectile>();
    
    private double playerAccelerationX = 0;
    private double playerAccelerationY = 0;
    
    private double shootTimer = playerStats.playerFirerate;
    
    //Places the player in the center of the screen at the start
    Object object = new Object(
    java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 2, 
    java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 2, 
    playerStats.playerSize, 
    playerStats.playerSize);
    public Player(GameSystem chosenGameSystem)
    {
        gameSystem = chosenGameSystem;
    }

    public void updatePlayer()
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
        this.object.updatePosition((int) Math.round(this.playerAccelerationX), (int) Math.round(this.playerAccelerationY));
    }
    
    public boolean canShoot()
    {
        return this.shootTimer < 1;
    }
    
    public void restartShootTimer()
    {
        this.shootTimer = playerStats.playerFirerate;
    }
    
    public Object getObject()
    {
        return this.object;
    }
    
    public int getPlayerX()
    {
        return this.object.getXPosition();
    }
    
    public int getPlayerY()
    {
        return this.object.getYPosition();
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