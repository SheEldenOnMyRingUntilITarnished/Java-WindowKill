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
    
    private int playerX = 0;
    private int playerY = 0;
    

    public Player(GameSystem chosenGameSystem)
    {
        gameSystem = chosenGameSystem;
    }

    public void updatePlayer()
    {
        int temp = 0;
        
        double friction = 0.35;
        if(this.playerAccelerationX > 0){
            this.playerAccelerationX -= friction;
        }else if(this.playerAccelerationX < 0)
        {
            this.playerAccelerationX += friction;
        }
        
        if(this.playerAccelerationY > 0){
            this.playerAccelerationY -= friction;
        }else if(this.playerAccelerationY < 0)
        {
            this.playerAccelerationY += friction;
        }
        
        double speedCap = playerStats.playerSpeedCap;
        
        if(this.playerAccelerationY > speedCap) this.playerAccelerationY = speedCap;
        if(this.playerAccelerationY < -speedCap) this.playerAccelerationY = -speedCap;
        if(this.playerAccelerationX > speedCap) this.playerAccelerationX = speedCap;
        if(this.playerAccelerationX < -speedCap) this.playerAccelerationX = -speedCap;
        
        //PLayer Movement Slippery
        this.playerX += Math.round(this.playerAccelerationX);
        this.playerY += Math.round(this.playerAccelerationY);
    }
    
    public int getPlayerX()
    {
        return this.playerX;
    }
    
    public int getPlayerY()
    {
        return this.playerY;
    }
    
    //Position
    public void setPlayerX(int chosenX)
    {
        this.playerX = chosenX;
    }
    
    public void setPlayerY(int chosenY)
    {
        this.playerY = chosenY;
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