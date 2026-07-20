
import java.awt.MouseInfo;
import java.awt.PointerInfo;

/**
 * Write a description of class InputManager here.
 *
 * @author (your name)
 * @version 05/18/2026
 */
public class InputManager extends Object
{
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();
    private Player player = null;
    
    public InputManager(Player player)
    {
        super(0,0);
        this.player = player;
    }
    
    @Override
    public void update()
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
                this.player.attemptShoot(currentMouse);
            }
            
            //Shop
            if(keyH.quickShopPressed == true)
            {
                System.out.println("SHOP");
                EnemyManager enemyManger = player.gameSystem.enemyManger;
                player.gameSystem.objects.add(enemyManger.SpawnEnemy());
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