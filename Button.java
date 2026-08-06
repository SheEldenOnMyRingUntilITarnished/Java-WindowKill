import java.awt.PointerInfo;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class Button extends UI_Collider
{
    private String label;
    private Runnable action;
    private boolean isHovered = false;
    private Color normalColor = Color.GREEN;
    private Color hoverColor = Color.CYAN;
    private Color textColor = Color.WHITE;
    
    private WindowArea parentWindow = null;
    private int relativeX = 0;
    private int relativeY = 0;
    
    public Button(String label, int width, int height, Runnable onClickAction)
    {
        super(width, height);
        this.label = label;
        this.action = onClickAction;
    }
    
    public void attachToWindow(WindowArea window, int relX, int relY)
    {
        this.parentWindow = window;
        this.relativeX = relX;
        this.relativeY = relY;
        updateRelativePosition();
    }
    
    public void updateRelativePosition()
    {
        if (parentWindow != null)
        {
            setPosition(parentWindow.getWindowXPosition() + relativeX, parentWindow.getWindowYPosition() + relativeY);
        }
    }
    
    public void setColors(Color normal, Color hover, Color text)
    {
        this.normalColor = normal;
        this.hoverColor = hover;
        this.textColor = text;
    }
    
    @Override
    public void update()
    {
        updateRelativePosition();
    }
    
    @Override
    public void mouseChecks(PointerInfo currentMouse)
    {
        updateRelativePosition();
        if (currentMouse == null || currentMouse.getLocation() == null) return;
        
        int mouseX = (int) currentMouse.getLocation().getX();
        int mouseY = (int) currentMouse.getLocation().getY();
        
        int btnX = getXPosition();
        int btnY = getYPosition();
        int halfW = getXSize() / 2;
        int halfH = getYSize() / 2;
        
        if (mouseX >= btnX - halfW && mouseX <= btnX + halfW &&
            mouseY >= btnY - halfH && mouseY <= btnY + halfH)
        {
            isHovered = true;
        }
        else
        {
            isHovered = false;
        }
    }
    
    public boolean isHovered()
    {
        return this.isHovered;
    }
    
    public void triggerClick()
    {
        if (action != null)
        {
            action.run();
        }
    }
    
    public void UpdateLabel(String newLabel)
    {
        this.label = newLabel;
    }
    
    @Override
    public void collison(Object collidedObject)
    {
        if (collidedObject instanceof Projectile)
        {
            Projectile p = (Projectile) collidedObject;
            if (p.getFromPlayer())
            {
                p.destroy();
                triggerClick();
            }
        }
    }
    
    @Override
    public void paint(Graphics2D g2, int windowX, int windowY)
    {
        int drawX = getXPosition() - windowX;
        int drawY = getYPosition() - windowY;
        int width = getXSize();
        int height = getYSize();
        int left = drawX - (width / 2);
        int top = drawY - (height / 2);
        
        g2.setColor(isHovered ? hoverColor : normalColor);
        g2.drawRect(left, top, width, height);
        
        // Semi-transparent fill on hovr
        if (isHovered)
        {
            g2.setColor(new Color(hoverColor.getRed(), hoverColor.getGreen(), hoverColor.getBlue(), 60));
            g2.fillRect(left, top, width, height);
        }
        
        // Render Centered Label Text
        if (label != null && !label.isEmpty())
        {
            g2.setColor(textColor);
            g2.setFont(new Font("SansSerif", Font.BOLD, 16));
            FontMetrics metrics = g2.getFontMetrics();
            int textX = drawX - (metrics.stringWidth(label) / 2);
            int textY = drawY + (metrics.getAscent() / 2) - 2;
            g2.drawString(label, textX, textY);
        }
    }
}
