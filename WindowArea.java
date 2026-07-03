// WARNING: This file is auto-generated and any changes to it will be overwritten
import lang.stride.*;
import javax.swing.JFrame;

/**
 * Write a description of class JavaRogue here.
 * @author (your name) @version (a version number or a date)
 */
public class WindowArea
{
    private JFrame window = null;
    private WindowPanel windowPanel = null;

    public WindowArea(GameSystem chosenGameSystem, int xSize, int ySize)
    {
        this.window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("");
        this.windowPanel = new WindowPanel(window, chosenGameSystem, xSize, ySize);
        window.add(windowPanel);
        window.pack();
        window.setAlwaysOnTop(true);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
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
