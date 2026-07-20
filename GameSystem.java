/**
 * Write a description of class GamePanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.ArrayList;
import javax.swing.JFrame;

public class GameSystem implements Runnable
{   
    Thread gameThread;

    GameState gameState = GameState.GAME; //GAME

    ArrayList<Object> objects = new ArrayList<Object>();

    ArrayList<WindowArea> activeWindows = new ArrayList<WindowArea>();

    EnemyManager enemyManger = new EnemyManager();
    Player player = new Player(this);
    InputManager inputManager = player.inputManager;

    WindowPanel window = null;

    private int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

    public void startGameThread()
    {
        gameThread = new Thread(this);
        objects.add(player);
        //setupWindow("W", 240, 240);
        //setupWindow("I", 240, 240);
        //setupWindow("N", 240, 240);
        //setupWindow("D", 240, 240);
        //setupWindow("O", 240, 240);
        //setupWindow("W", 240, 240);
        //setupWindow("G", 240, 240);
        //setupWindow("O", 240, 240);
        //setupWindow("O", 240, 240);
        //setupWindow("N", 240, 240);
        //setupWindow("Settings", 720, 1080);
        //setupWindow("Start", 720, 1080);
        setupWindow("GAME", 1080, 720);
        gameThread.start();
    }

    public void setupWindow(String chosenWindowType, int chosenWidth, int chosenHeight)
    {
        WindowArea windowArea = new WindowArea(this, chosenWidth, chosenHeight);
        activeWindows.add(windowArea);
        window = windowArea.getGamePanel();
        window.setWindowType(chosenWindowType);
    }

    /**
     * 
     * Gets ran when we start the application.
     * Sets up the game.
     * 
     **/
    public void run()
    {
        double drawInterval = 1000000000/60; //60FPS
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        
        awakeObjects();
        startObjects();
        
        while (gameThread != null)
        {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            //Updates everything on a frame
            if(delta >= 1)
            {
                objectsCollisionCheck();
                updateObjects();
                for(int i = 0; i < activeWindows.size(); i++)
                {
                    activeWindows.get(i).getGamePanel().repaint();
                }
                if(gameState == GameState.MAIN_MENU)
                {
                    updateWindowCollisions();
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
        double angle = Math.atan2(Y2 - Y1, X2 - X1) * 180 / Math.PI;;
        System.out.println("--- " + angle);
        return angle;
    }
    
    public void awakeObjects()
    {
        for(int i = objects.size() - 1; i >= 0; i--)
        {
            Object obj = objects.get(i);
            
            obj.awake(); 
        }
    }
    
    public void startObjects()
    {
        for(int i = objects.size() - 1; i >= 0; i--)
        {
            Object obj = objects.get(i);
            
            obj.start(); 
        }
    }
    
    public void updateObjects()
    {
        for(int i = objects.size() - 1; i >= 0; i--)
        {
            Object obj = objects.get(i);
            
            obj.update(); 
        }
    }
    
    public void objectsCollisionCheck()
    {
        if(objects.size() > 1)
        {
            for(int i = objects.size() - 1; i >= 0; i--)
            {
                if(objects.get(i) instanceof RidgedBody2D)
                {
                    RidgedBody2D a = (RidgedBody2D) objects.get(i);
                    
                    if(collisionWithScreenEdgeCheck(a)) 
                    {
                        a.screenEdge();
                        //activeObjects.remove(i);
                        continue;
                    }
                    
                    JFrame comparedWindow = activeWindows.get(0).getWindow();
                    
                    if(collisionWithWindowCheck(a, comparedWindow))
                    {
                        System.out.println("WOW");
                        a.windowEdge(comparedWindow);
                        //activeObjects.remove(i);
                        continue;
                    }
                    
                    for(int j = i + 1; j < objects.size(); j++)
                    {
                        if(objects.get(i) instanceof RidgedBody2D)
                        {
                            RidgedBody2D b = (RidgedBody2D) objects.get(i);
                            if(collisionCheck(a, b))
                            {
                                a.collison(b);
                                b.collison(a);
                            }
                        }
                    }
                }
            }  
        }
    }
    
    public boolean collisionWithWindowCheck(RidgedBody2D a, JFrame comparedWindow)
    {
        int aX = a.getXPosition();
        int aY = a.getYPosition();
        int aWidth = a.getXSize();
        int aHeight = a.getYSize();

        int windowX = getWindowX(comparedWindow);
        int windowY = getWindowY(comparedWindow);
        int windowWidth = getWindowWidth(comparedWindow);
        int windowHeight = getWindowHeight(comparedWindow);
        
        if(aX > windowX + windowWidth || aX < windowX || //X Checks
        aY > windowY + windowHeight || aY < windowY)//Y Checks
        {
            //int overlapX = Math.min(aX + aWidth, windowX + windowWidth) - Math.max(aX, windowX);
            //int overlapY = Math.min(aY + aHeight, windowY + windowHeight) - Math.max(aY, windowY);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * This method is usedd to check if an object collides with the screen edges of the moniter.
     * 
     * This method takes an object to compare with the window bounds and returns true or false.
    **/
    public boolean collisionWithScreenEdgeCheck(RidgedBody2D a)
    {
        int aX = a.getXPosition();
        int aY = a.getYPosition();
        int aWidth = a.getXSize();
        int aHeight = a.getYSize();
    
        if (aX < 0 || aX + aWidth > screenWidth || aY < 0 || aY + aHeight > screenHeight) 
        {
            return true; 
        }
        
        return false;
    }
    
    public boolean collisionCheck(RidgedBody2D a, RidgedBody2D b)
    {
        int aX = a.getXPosition();
        int aY = a.getYPosition();
        int aWidth = a.getXSize();
        int aHeight = a.getYSize();

        int bX = b.getXPosition();
        int bY = b.getYPosition();
        int bWidth = b.getXSize();
        int bHeight = b.getYSize();

        if(aX < bX + bWidth && aX + aWidth > bX && //X Checks
        aY < bY + bHeight && aY + aHeight > bY)//Y Checks
        {
            //int overlapX = Math.min(aX + aWidth, bX + bWidth) - Math.max(aX, bX);
            //int overlapY = Math.min(aY + aHeight, bY + bHeight) - Math.max(aY, bY);

            return true;
        }
        else
        {
            return false;
        }
    }

    //Window Methods
    public void updateWindowCollisions()
    {
        for(int i = 0; i < activeWindows.size(); i++)
        {
            JFrame currentWindow = activeWindows.get(i).getWindow();
            int currentWindowX = getWindowX(currentWindow);
            int currentWindowY = getWindowY(currentWindow);
            int currentWindowWidth = getWindowWidth(currentWindow);
            int currentWindowHeight = getWindowHeight(currentWindow);

            //Checks for collisions with other windows
            for(int j = i + 1; j < activeWindows.size(); j++)
            {
                JFrame comparedWindow = activeWindows.get(j).getWindow();
                int comparedWindowX = getWindowX(comparedWindow);
                int comparedWindowY = getWindowY(comparedWindow);
                int comparedWindowWidth = getWindowWidth(comparedWindow);
                int comparedWindowHeight = getWindowHeight(comparedWindow);

                if(currentWindowX < comparedWindowX + comparedWindowWidth && currentWindowX + currentWindowWidth > comparedWindowX && //X Checks
                currentWindowY < comparedWindowY + comparedWindowHeight && currentWindowY + currentWindowHeight > comparedWindowY)//Y Checks
                {
                    int overlapX = Math.min(currentWindowX + currentWindowWidth, comparedWindowX + comparedWindowWidth) - Math.max(currentWindowX, comparedWindowX);
                    int overlapY = Math.min(currentWindowY + currentWindowHeight, comparedWindowY + comparedWindowHeight) - Math.max(currentWindowY, comparedWindowY);
                    int pushAmount = 0;

                    if(overlapX < overlapY)
                    {
                        pushAmount = overlapX/2;

                        if((currentWindowX + currentWindowWidth)/2 < (comparedWindowX + comparedWindowWidth)/2)
                        {
                            currentWindow.setLocation(currentWindowX - pushAmount, currentWindowY);
                            comparedWindow.setLocation(comparedWindowX + pushAmount, comparedWindowY);
                        }
                        else
                        {
                            currentWindow.setLocation(currentWindowX + pushAmount, currentWindowY);
                            comparedWindow.setLocation(comparedWindowX - pushAmount, comparedWindowY);
                        }
                    }
                    else if(overlapX > overlapY)
                    {
                        pushAmount = overlapY/2;

                        if((currentWindowY + currentWindowHeight)/2 > (comparedWindowY + comparedWindowHeight)/2)
                        {
                            currentWindow.setLocation(currentWindowX, currentWindowY + pushAmount);
                            comparedWindow.setLocation(comparedWindowX, comparedWindowY - pushAmount);
                        }
                        else
                        {
                            currentWindow.setLocation(currentWindowX, currentWindowY - pushAmount);
                            comparedWindow.setLocation(comparedWindowX, comparedWindowY + pushAmount);
                        }
                    }
                }
            }

            if(currentWindowX + currentWindowWidth > screenWidth)
            {
                currentWindow.setLocation(screenWidth - currentWindowWidth, currentWindowY);
            }
            if(currentWindowX < 0)
            {
                currentWindow.setLocation(0, currentWindowY);
            }

            if(currentWindowY + currentWindowHeight > screenHeight)
            {
                currentWindow.setLocation(currentWindowX, screenHeight - currentWindowHeight);
            }
            if(currentWindowY < 0)
            {
                currentWindow.setLocation(currentWindowX, 0);
            }
        }
    }

    private int getWindowX(JFrame chosenWindow) {
        return (int) chosenWindow.getLocationOnScreen().getX();
    }

    private int getWindowY(JFrame chosenWindow) {
        return chosenWindow.getY();
    }

    private int getWindowWidth(JFrame chosenWindow){
        return chosenWindow.getWidth();
    }

    private int getWindowHeight(JFrame chosenWindow){
        return chosenWindow.getHeight();
    }

    private void setWindowSize(int Width, int Height)
    {
        window.setSize(Width,Height);
    }
}