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
        if(playerAccelerationX > 0){
            playerAccelerationX -= friction;
        }else if(playerAccelerationX < 0)
        {
            playerAccelerationX += friction;
        }
        
        if(playerAccelerationY > 0){
            playerAccelerationY -= friction;
        }else if(playerAccelerationY < 0)
        {
            playerAccelerationY += friction;
        }
        
        double speedCap = playerStats.playerSpeedCap;
        
        if(playerAccelerationY > speedCap) playerAccelerationY = speedCap;
        if(playerAccelerationY < -speedCap) playerAccelerationY = -speedCap;
        if(playerAccelerationX > speedCap) playerAccelerationX = speedCap;
        if(playerAccelerationX < -speedCap) playerAccelerationX = -speedCap;
        
        //PLayer Movement Slippery
        playerX += Math.round(playerAccelerationX);
        playerY += Math.round(playerAccelerationY);
    }
    
    public int getPlayerX()
    {
        return playerX;
    }
    
    public int getPlayerY()
    {
        return playerY;
    }
    
    //Position
    public void setPlayerX(int chosenX)
    {
        playerX = chosenX;
    }
    
    public void setPlayerY(int chosenY)
    {
        playerY = chosenY;
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