/**
 * Write a description of class GamePanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public class GameSystem implements Runnable
{   
    
    Thread gameThread;
    
    ArrayList<WindowArea> activeWindows = new ArrayList<WindowArea>();
    
    
    WindowPanel window = null;
    
    public void startGameThread()
    {
        gameThread = new Thread(this);
        for(int i = 0; i < 3; i++)
        {
            WindowArea windowArea = new WindowArea(this);
            activeWindows.add(windowArea);
            window = windowArea.getGamePanel();
        }
        
        gameThread.start();
    }
    
    public void run()
    {
        double drawInterval = 1000000000/60;//60FPS
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        
        while (gameThread != null)
        {
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            //Updates everything on a frame
            if(delta >= 1)
            {
                for(int i = 0; i < activeWindows.size(); i++)
                {
                    activeWindows.get(i).getGamePanel().repaint();
                }
                delta--;
            }
            
            
            if(timer >= 1000000000)
            {
                timer = 0;
            }
            
        }
    }
    
    public double calculateTheAngleBetweenTwoPoints(double X1, double Y1, double X2, double Y2)
    {
        double theta = Math.atan2(Y2 - Y1, X2 - X1);
        
        theta += Math.PI/2.0;
        
        double angle = Math.toDegrees(theta);
        
        if (angle < 0)
        {
            angle += 360;
        }
        
        return angle;
    }
    
    //Window Methods
    public int getWindowX() {
        return (int) window.getLocationOnScreen().getX();
    }
    public int getWindowY() {
        return window.getY();
    }
    public void setWindowPosition(int X, int Y)
    {
        window.setLocation(X,Y);
    }
    public void setWindowSize(int Width, int Height)
    {
        window.setSize(Width,Height);
    }
}