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
    
    private int playerX = 0;
    private int playerY = 0;

    public Player(GameSystem chosenGameSystem)
    {
        gameSystem = chosenGameSystem;
    }
    
    public void updatePlayer()
    {
        int temp = 0;
        
        int friction = 1;
        
        //PLayer Movement Slippery
    }
    
    public int getPlayerX()
    {
        return playerX;
    }
    
    public int getPlayerY()
    {
        return playerY;
    }
    
    public void setPlayerX(int chosenX)
    {
        playerX = chosenX;
    }
    
    public void setPlayerY(int chosenY)
    {
        playerY = chosenY;
    }
}