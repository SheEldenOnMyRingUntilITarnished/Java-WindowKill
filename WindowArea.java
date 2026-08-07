import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Insets;

/**
 * Write a description of class JavaRogue here.
 * @author (your name) @version (a version number or a date)
 */
public class WindowArea extends Object
{
    private GameSystem gameSystem;
    private JFrame window = null;
    private WindowPanel windowPanel = null;
    
    private Dimension windowDimension = new Dimension(500,500);
    private int width;
    private int height;
    
    private float xF;
    private float yF;
    
    private float widthF;
    private float heightF;
    
    private float shrinkSpeed = 0.25f;
    private int minWidth = 160;
    private int minHeight = 160;
    private int maxWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int maxHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    
    private int titleBarHeight;
    private int leftBorderWidth;
    
    public WindowArea(GameSystem chosenGameSystem, int xSize, int ySize)
    {
        super(0,0);
        this.gameSystem = chosenGameSystem;
        this.width = xSize;
        this.height = ySize;
        this.widthF = xSize;
        this.heightF = ySize;
        this.windowDimension = new Dimension(xSize, ySize);
        this.window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("");
        this.windowPanel = new WindowPanel(window, chosenGameSystem);
        window.add(windowPanel);
        windowPanel.setPreferredSize(windowDimension);
        window.pack();
        
        Insets insets = window.getInsets();
        titleBarHeight = insets.top;
        leftBorderWidth = insets.left;
        
        int startX = (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - this.width) / 2;
        int startY = (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - this.height) / 2;
        this.xF = startX;
        this.yF = startY;
        this.setPosition(startX, startY);
        
        window.setAlwaysOnTop(true);
        window.setLocation(getWindowXPositionAccountingForTopBar(), getWindowYPositionAccountingForTopBar());
        window.setVisible(true);
        this.windowPanel.setPreferredSize(windowDimension);
        this.windowPanel.setSize(this.width, this.height);
    }
    
    @Override
    public void awake()
    {
        
    }
    
    @Override
    public void update()
    {
        if (window != null && window.isShowing()) {
            try {
                int actualX = (int) window.getLocationOnScreen().getX() + leftBorderWidth;
                int actualY = (int) window.getLocationOnScreen().getY() + titleBarHeight;
                
                if (actualX != getXPosition() || actualY != getYPosition()) {
                    this.setPosition(actualX, actualY);
                    this.xF = actualX;
                    this.yF = actualY;
                }
            } catch (Exception e) {
                // Ignore if window is not yet fully visible on screen
            }
        }
        
        // Only shrink during active GAME state
        if (gameSystem != null && gameSystem.gameState == GameState.GAME)
        {
            float prevWidthF = widthF;
            float prevHeightF = heightF;
            
            if (widthF > minWidth) {
                widthF = Math.max(minWidth, widthF - shrinkSpeed);
            }
            if (heightF > minHeight) {
                heightF = Math.max(minHeight, heightF - shrinkSpeed);
            }
            
            float deltaW = prevWidthF - widthF;
            float deltaH = prevHeightF - heightF;
            
            xF += deltaW / 2.0f;
            yF += deltaH / 2.0f;
            
            int newWidth = Math.round(widthF);
            int newHeight = Math.round(heightF);
            int newX = Math.round(xF);
            int newY = Math.round(yF);
            
            if (newWidth != width || newHeight != height || newX != getXPosition() || newY != getYPosition()) {
                setWindowPosition(newX, newY);
                applyWindowSize(newWidth, newHeight);
            }
        }
    }
    
    private void applyWindowSize(int xSize, int ySize)
    {
        xSize = Math.max(minWidth, Math.min(maxWidth, xSize));
        ySize = Math.max(minHeight, Math.min(maxHeight, ySize));
        this.width = xSize;
        this.height = ySize;
        windowDimension = new Dimension(xSize, ySize);
        this.windowPanel.setPreferredSize(windowDimension);
        window.pack();
    }
    
    public void setWindowSize(int xSize, int ySize)
    {
        xSize = Math.max(minWidth, Math.min(maxWidth, xSize));
        ySize = Math.max(minHeight, Math.min(maxHeight, ySize));
        this.widthF = xSize;
        this.heightF = ySize;
        applyWindowSize(xSize, ySize);
    }
    
    public int getWindowWidth()
    {
        return this.width;
    }
    
    public int getWindowHeight()
    {
        return this.height;
    }
    
    public void setWindowPosition(int X, int Y)
    {
        this.setPosition(X,Y);
        this.xF = X;
        this.yF = Y;
        this.windowPanel.setWindowPosition(getWindowXPositionAccountingForTopBar(),getWindowYPositionAccountingForTopBar());
    }
    
    public int getWindowXPositionAccountingForTopBar()
    {
        return this.getXPosition() - leftBorderWidth;
    }
    
    public int getWindowYPositionAccountingForTopBar()
    {
        return this.getYPosition() - titleBarHeight;
    }
    
    public int getWindowXPosition()
    {
        return this.getXPosition();
    }
    
    public int getWindowYPosition()
    {
        return this.getYPosition();
    }
    
    public JFrame getWindow()
    {
        return this.window;
    }

    public WindowPanel getGamePanel()
    {
        return this.windowPanel;
    }
}
