
import java.awt.MouseInfo;
import java.awt.PointerInfo;

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
        PointerInfo currentMouse = MouseInfo.getPointerInfo();
        
        if(player.gameSystem.gameState == GameState.GAME){
            
            //Movement
            if(keyH.upPressed == true)
            {
                this.player.acceleratePlayerY(-this.player.playerStats.playerAcceleration);
            }
            if(keyH.downPressed == true)
            {
                this.player.acceleratePlayerY(this.player.playerStats.playerAcceleration);
            }
            if(keyH.leftPressed == true)
            {
                this.player.acceleratePlayerX(-this.player.playerStats.playerAcceleration);
            }
            if(keyH.rightPressed == true)
            {
                this.player.acceleratePlayerX(this.player.playerStats.playerAcceleration);
            }
            
            //Shooting
            if(mouseH.shootPressed == true)
            {
                //System.out.println("Mouse Pressed");
                //System.out.println("X: " + MouseInfo.getPointerInfo().getLocation().getX());
                //System.out.println("Y: " + MouseInfo.getPointerInfo().getLocation().getY());
                if(player.canShoot())//Temp we need to replace for checking if player can shoot
                {
                    player.restartShootTimer();
                    Projectile newPlayerProjectile = new Projectile(this.player.playerStats.playerProjectileSpeed, player.gameSystem.calculateTheAngleBetweenTwoPoints(this.player.getXPosition(), this.player.getYPosition(), MouseInfo.getPointerInfo().getLocation().getX(),currentMouse.getLocation().getY()) * Math.PI/180,false);
                    newPlayerProjectile.setPosition(this.player.getXPosition(),this.player.getYPosition());
                    //System.out.println("Bullet pointing this dir: " + newPlayerProjectile.getDirection());
                    player.gameSystem.activeObjects.add(newPlayerProjectile);
                    //System.out.println("activeObjects: " + player.gameSystem.activeObjects);
                }
            }
            
            //Shop
            if(keyH.quickShopPressed == true)
            {
                System.out.println("SHOP");
                EnemyManager enemyManger = player.gameSystem.enemyManger;
                player.gameSystem.activeObjects.add(enemyManger.SpawnEnemy());
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