
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version 05/18/2026
 */
public class Player
{
    InputManager inputManager = new InputManager(this);
    PlayerStats playerStats = new PlayerStats();
    
    public int playerX = 0;
    public int playerY = 0;
    
    public Player()
    {
        
    }
    
    public void updatePlayer()
    {
        int temp = 0;
        
        int friction = 1;
        
        //PLayer Movement Slippery
    }
}