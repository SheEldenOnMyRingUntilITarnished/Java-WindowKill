/**
 * Write a description of class KeyHandler here.
 *
 * @author (your name)
 * @version 05/11/2026
*/
 
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class KeyHandler implements KeyListener
{
    public boolean upPressed, downPressed, leftPressed, rightPressed, dashPressed, pausePressed, quickShopPressed, quickBossShopPressed;
    
    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_W){
            System.out.println("UP");
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_SHIFT){
            dashPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            System.out.println("PAUSE");
            pausePressed = true;
        }
        if(code == KeyEvent.VK_TAB){
            System.out.println("QUICK BOSS SHOP");
            quickBossShopPressed = true;
        }
        if(code == KeyEvent.VK_SPACE){
            System.out.println("QUICK SHOP");
            quickShopPressed = true;
        }
    }
    
    
    @Override
    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();
        
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_SHIFT){
            dashPressed = false;
        }
        if(code == KeyEvent.VK_ESCAPE){
            System.out.println("PAUSE");
            pausePressed = false;
        }
        if(code == KeyEvent.VK_TAB){
            System.out.println("QUICK BOSS SHOP");
            quickBossShopPressed = false;
        }
        if(code == KeyEvent.VK_SPACE){
            System.out.println("QUICK SHOP");
            quickShopPressed = false;
        }
    }
    
}
