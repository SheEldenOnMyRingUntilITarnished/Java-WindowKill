
import java.awt.MouseInfo;

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
        if(player.gameSystem.gameState == GameState.GAME){
            //Movement
            if(keyH.upPressed == true)
            {
                this.player.setPlayerY(this.player.getPlayerY() - this.player.playerStats.playerSpeed);
            }
            if(keyH.downPressed == true)
            {
                this.player.setPlayerY(this.player.getPlayerY() + this.player.playerStats.playerSpeed);
            }
            if(keyH.leftPressed == true)
            {
                this.player.setPlayerX(this.player.getPlayerX() - this.player.playerStats.playerSpeed);
            }
            if(keyH.rightPressed == true)
            {
                this.player.setPlayerX(this.player.getPlayerX() + this.player.playerStats.playerSpeed);
            }
            
            //Shooting
            if(mouseH.shootPressed == true)
            {
                if(true)//Temp we need to replace for checking if player can shoot
                {
                    Projectile newPlayerProjectile = new Projectile(this.player.playerStats.playerProjectileSpeed, player.gameSystem.calculateTheAngleBetweenTwoPoints(this.player.getPlayerX(), this.player.getPlayerY(), MouseInfo.getPointerInfo().getLocation().getX(), MouseInfo.getPointerInfo().getLocation().getY()) * Math.PI/180,false);
                    newPlayerProjectile.setPosition(this.player.getPlayerX(),this.player.getPlayerY());
                    player.playerProjectileList.add(newPlayerProjectile);
                }
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
        }else
        {
            if(keyH.pausePressed == true)
            {
                System.out.println("WOW PAUSING THE MAIN MENU????");
            }
        }
    }
}