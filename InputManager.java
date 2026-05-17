
/**
 * Write a description of class InputManager here.
 *
 * @author (your name)
 * @version 05/18/2026
 */
public class InputManager
{
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();
    private Player player = null;
    
    public InputManager(Player player)
    {
        this.player = player;
    }
    
    public void updateInput()
    {
        //Movement
        if(keyH.upPressed == true)
        {
            this.player.playerY -= this.player.playerStats.playerSpeed;
        }
        if(keyH.downPressed == true)
        {
            this.player.playerY += this.player.playerStats.playerSpeed;
        }
        if(keyH.leftPressed == true)
        {
            this.player.playerX -= this.player.playerStats.playerSpeed;
        }
        if(keyH.rightPressed == true)
        {
            this.player.playerX += this.player.playerStats.playerSpeed;
        }
        
        //Shooting
        if(mouseH.shootPressed == true)
        {
            System.out.println("SHOOT");
        }
        
        //Shop
        if(keyH.quickShopPressed == true)
        {
            System.out.println("SHOP");
        }
        else if(keyH.quickBossShopPressed == true)
        {
            System.out.println("BOSS_SHOP");
        }
        //Other
        else if(keyH.pausePressed == true)
        {
            System.out.println("PAUSE");
        }
    }
}