/**
 * Write a description of class GamePanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */


/**
 * Window realted imports
**/
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class WindowPanel extends JPanel
{
    
    JFrame window = null;
    private GameSystem gameSystem;
    private Player player = null;
    private String windowType = "null";
    
    public WindowPanel(JFrame window, GameSystem chosenGameSystem) 
    {
        this.setPreferredSize(new Dimension(1080,720));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.gameSystem = chosenGameSystem;
        this.player = chosenGameSystem.player;
        this.addKeyListener(chosenGameSystem.inputManager.keyH);
        this.addMouseListener(chosenGameSystem.inputManager.mouseH);
        this.setFocusable(true);
        this.window = window;
    }
    
    public void setWindowType(String chosenWindowType)
    {
        this.windowType = chosenWindowType;
    }
    
    /**
     * Runs when .repaint is ran in the gameSystem
     * repaints the windows screen with the new information.
    **/
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        if(this.windowType.equals("null"))
        {
            System.err.println("Window Missing WindowType");
            return;
        }
        
        if (gameSystem.gameState == GameState.MAIN_MENU) {
            switch(this.windowType)
            {
                case "CharacterSelect":
                    paintCharacterSelect(g2);
                    break;
                
                case "Settings":
                    paintSettings(g2);
                    break;
                
                case "Start":
                    paintStart(g2);
                    break;
                
            }
        } 
        else if (gameSystem.gameState == GameState.GAME) {
            paintPlayer(g2);
        }
        
        //Paint Projectiles
        for(int i = 0; i < gameSystem.projectileList.size(); i++)
        {
            Projectile projectile = gameSystem.projectileList.get(i);
            int projectileX = projectile.getXPosition();
            int projectileY = projectile.getYPosition();
            
            paintProjectile(g2, Color.RED,projectileX,projectileY);
        }
        
    }
    
    public void paintStart(Graphics2D g2)
    {
        g2.setColor(Color.GREEN);
        g2.drawRect(0,0,30,30);
    }
    
    public void paintCharacterSelect(Graphics2D g2)
    {
        g2.setColor(Color.BLUE);
        g2.drawRect(0,0,30,30);
    }
    
    public void paintSettings(Graphics2D g2)
    {
        g2.setColor(Color.WHITE);
        g2.drawRect(0,0,30,30);
    }
    
    public void paintPlayer(Graphics2D g2)
    {
        g2.setColor(Color.RED);
        g2.drawRect(player.getPlayerX(),player.getPlayerY(),10, 10);
    }
    
    public void paintEnemy(Graphics2D g2, Color color, int xPos, int yPos)
    {
        g2.setColor(color);
        g2.drawRect(xPos,yPos,5, 5);
    }
    
    public void paintProjectile(Graphics2D g2, Color color, int xPos, int yPos)
    {
        g2.setColor(color);
        g2.drawRect(xPos,yPos,5, 5);
    }
    
    //Window Methods
    public int getWindowX() {return (int) window.getLocationOnScreen().getX();}
    public int getWindowY() {return window.getY();}
    
    public void setWindowPosition(int X, int Y) {window.setLocation(X,Y);}
    public void setWindowSize(int Width, int Height){window.setSize(Width,Height);}
}