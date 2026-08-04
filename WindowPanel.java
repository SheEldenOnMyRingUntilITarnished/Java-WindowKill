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
import java.awt.BasicStroke;

/**
 * Graphics Related Imports
**/
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


public class WindowPanel extends JPanel
{
    
    JFrame window = null;
    private GameSystem gameSystem;
    private Player player = null;
    private String windowType = "null";
    
    private double width;
    private double height;
    
    public WindowPanel(JFrame window, GameSystem chosenGameSystem) 
    {
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.gameSystem = chosenGameSystem;
        this.player = chosenGameSystem.player;
        this.addKeyListener(chosenGameSystem.inputManager.keyH);
        this.addMouseListener(chosenGameSystem.inputManager.mouseH);
        this.addMouseMotionListener(chosenGameSystem.inputManager.mouseH);
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
        float strokeThickness = 5;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        g2.setStroke(new BasicStroke(strokeThickness));
        
        if(this.windowType.equals("null"))
        {
            System.err.println("Window Missing WindowType");
            return;
        }
        
        for(int i = 0; i < gameSystem.objects.size(); i++)
        {
            if(i < gameSystem.objects.size())
            {
                Object item = gameSystem.objects.get(i);
                if (item instanceof RidgedBody2D)
                {
                    RidgedBody2D obj = (RidgedBody2D) item;
                    Graphics2D g2d = (Graphics2D) g2.create();
                    obj.paint(g2d, getWindowX(), getWindowY());
                    g2d.dispose();
                }
                else if (item instanceof UI_Element)
                {
                    UI_Element ui = (UI_Element) item;
                    Graphics2D g2d = (Graphics2D) g2.create();
                    ui.paint(g2d, getWindowX(), getWindowY());
                    g2d.dispose();
                }
            }
        }
        
        // Render window headers based on windowType
        if (this.windowType.equals("TITLE"))
        {
            Graphics2D g2d = (Graphics2D) g2.create();
            g2d.setColor(Color.WHITE);
            g2d.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 32));
            java.awt.FontMetrics fm = g2d.getFontMetrics();
            String title = "WINDOWKILL";
            int titleX = (getWidth() - fm.stringWidth(title)) / 2;
            int titleY = (getHeight() + fm.getAscent()) / 2 - 6;
            g2d.drawString(title, titleX, titleY);
            g2d.dispose();
        }
        else if (this.windowType.equals("START"))
        {
            Graphics2D g2d = (Graphics2D) g2.create();
            g2d.setColor(Color.GREEN);
            g2d.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
            java.awt.FontMetrics fm = g2d.getFontMetrics();
            String header = "PLAY";
            int headerX = (getWidth() - fm.stringWidth(header)) / 2;
            g2d.drawString(header, headerX, 28);
            g2d.dispose();
        }
        else if (this.windowType.equals("SETTINGS"))
        {
            Graphics2D g2d = (Graphics2D) g2.create();
            g2d.setColor(Color.CYAN);
            g2d.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
            java.awt.FontMetrics fm = g2d.getFontMetrics();
            String header = "OPTIONS";
            int headerX = (getWidth() - fm.stringWidth(header)) / 2;
            g2d.drawString(header, headerX, 28);
            g2d.dispose();
        }
        else if (this.windowType.equals("GAME_OVER"))
        {
            Graphics2D g2d = (Graphics2D) g2.create();
            g2d.setColor(Color.RED);
            g2d.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 26));
            java.awt.FontMetrics fm = g2d.getFontMetrics();
            String header = "GAME OVER";
            int headerX = (getWidth() - fm.stringWidth(header)) / 2;
            g2d.drawString(header, headerX, 42);
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 14));
            fm = g2d.getFontMetrics();
            String scoreText = "FINAL SCORE: " + (gameSystem != null ? gameSystem.getScore() : 0);
            int scoreX = (getWidth() - fm.stringWidth(scoreText)) / 2;
            g2d.drawString(scoreText, scoreX, 68);
            g2d.dispose();
        }
        
        // Render Score
        if (gameSystem != null && gameSystem.gameState == GameState.GAME)
        {
            Graphics2D g2d = (Graphics2D) g2.create();
            g2d.setColor(Color.WHITE);
            g2d.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 15));
            g2d.drawString("SCORE: " + gameSystem.getScore(), 15, 25);
            g2d.dispose();
        }
    }
    
    //Window Methods
    public int getWindowX() {
        try {
            return (int) this.getLocationOnScreen().getX();
        } catch (Exception e) {
            return (int) window.getLocationOnScreen().getX();
        }
    }
    
    public int getWindowY() {
        try {
            return (int) this.getLocationOnScreen().getY();
        } catch (Exception e) {
            return window.getY();
        }
    }
    
    public void setWindowPosition(int X, int Y) 
    {
        window.setLocation(X,Y);
    }
}