
/**
 * Write a description of class InputManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class InputManager
{
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();
    
    public void updateInput()
    {
        //Movement
        if(keyH.upPressed == true)
        {
            System.out.println("UP");
        }
        if(keyH.downPressed == true)
        {
            System.out.println("DOWN");
        }
        if(keyH.leftPressed == true)
        {
            System.out.println("LEFT");
        }
        if(keyH.rightPressed == true)
        {
            System.out.println("RIGHT");
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