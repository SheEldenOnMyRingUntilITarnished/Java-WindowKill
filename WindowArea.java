import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Insets;

/**
 * Write a description of class JavaRogue here.
 * @author (your name) @version (a version number or a date)
 */
public class WindowArea extends Object
{
    private JFrame window = null;
    private WindowPanel windowPanel = null;
    
    private Dimension windowDimension = new Dimension(500,500);
    private int width;
    private int height;
    
    private float widthF;
    private float heightF;
    
    private int titleBarHeight;
    private int leftBorderWidth;
    
    public WindowArea(GameSystem chosenGameSystem, int xSize, int ySize)
    {
        super(0,0);
        this.width = xSize;
        this.height = ySize;
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
        window.setAlwaysOnTop(true);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    
    @Override
    public void awake()
    {
        this.widthF = this.width;
        this.heightF = this.height;
        
        this.setPosition(
        (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - this.width)/ 2, //X
        (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - this.height)/ 2 //Y
        );
        
        this.windowPanel.setSize(this.width, this.height);
    }
    
    @Override
    public void update()
    {
        //this.widthF = this.widthF - 0.5f;
        //this.heightF = this.heightF - 0.5f;
        
        int roundedWidthF = (int)Math.round(this.widthF);
        System.out.println("roundedWidthF: " + roundedWidthF);
        int roundedHeightF = (int)Math.round(this.heightF);
        System.out.println("roundedHeightF: " + roundedHeightF);
        //setWindowSize(roundedWidthF, roundedHeightF);
    }
    
    public void setWindowSize(int xSize, int ySize)
    {
        windowDimension = new Dimension(xSize,ySize);
        this.width = xSize;
        this.height = ySize;
        this.windowPanel.setPreferredSize(windowDimension);
        window.pack();
    }
    
    public int getWindowWidth()
    {
        return this.width;
    }
    
    public int getWindowHeight()
    {
        return this.height;
    }
    
    public int getWindowXPositionAccountingForTopBar()
    {
        return this.getXPosition() - leftBorderWidth;
    }
    
    public int getWindowYPositionAccountingForTopBar()
    {
        return this.getYPosition() - titleBarHeight;
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
