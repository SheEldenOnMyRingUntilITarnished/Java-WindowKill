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

    GameState gameState = GameState.MAIN_MENU; //GAME

    ArrayList<Object> activeObjects = new ArrayList<Object>();
    ArrayList<Object> deactiveObjects = new ArrayList<Object>();

    ArrayList<WindowArea> activeWindows = new ArrayList<WindowArea>();
    ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
    ArrayList<Enemy> enemyList = new ArrayList<Enemy>();

    EnemyManager enemyManger = new EnemyManager();
    Player player = new Player(this);
    InputManager inputManager = player.inputManager;

    WindowPanel window = null;

    private int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

    public void startGameThread()
    {
        gameThread = new Thread(this);
        activeObjects.add(player.getObject());
        setupWindow("W", 240, 240);
        setupWindow("I", 240, 240);
        setupWindow("N", 240, 240);
        setupWindow("D", 240, 240);
        setupWindow("O", 240, 240);
        setupWindow("W", 240, 240);
        setupWindow("G", 240, 240);
        setupWindow("O", 240, 240);
        setupWindow("O", 240, 240);
        setupWindow("N", 240, 240);
        setupWindow("Settings", 720, 1080);
        setupWindow("Start", 720, 1080);
        setupWindow("CharacterSelect", 720, 1080);
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

        while (gameThread != null)
        {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            //Updates everything on a frame
            if(delta >= 1)
            {
                inputManager.updateInput();
                activeObjectsCollisionCheck();
                player.updatePlayer();
                updateProjectiles();
                updateEnemys();
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

    public void updateProjectiles()
    {
        synchronized(projectileList) 
        {
            for(int i = projectileList.size() - 1; i >= 0; i--)
            {
                Projectile projectile = projectileList.get(i);
                //System.out.println(projectileList.get(i));
                projectile.updatePosition(projectile.getSpeed() * Math.cos(projectile.getDirection()), projectile.getSpeed() * Math.sin(projectile.getDirection()));
            }
        }
    }

    public void updateEnemys()
    {
        synchronized(enemyList)
        {
            for(int i = enemyList.size() - 1; i >= 0; i--)
            {
                Enemy enemy = enemyList.get(i);
                
                enemy.updateEnemy(player.getPlayerX(), player.getPlayerY());
            }
        }
    }

    public void activeObjectsCollisionCheck()
    {
        if(activeObjects.size() > 1)
        {
            for(int i = activeObjects.size() - 1; i >= 0; i--)
            {
                Object a = activeObjects.get(i);
                for(int j = i + 1; j < activeObjects.size(); j++)
                {
                    Object b = activeObjects.get(j);
                    if(collisionCheck(a, b))
                    {
                        //epic stuff in here when colliding with other objects
                    }
                }                
            }  
        }
    }

    public boolean collisionCheck(Object a, Object b)
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
            int overlapX = Math.min(aX + aWidth, bX + bWidth) - Math.max(aX, bX);
            int overlapY = Math.min(aY + aHeight, bY + bHeight) - Math.max(aY, bY);
            
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

    public int getWindowX(JFrame chosenWindow) {
        return (int) chosenWindow.getLocationOnScreen().getX();
    }

    public int getWindowY(JFrame chosenWindow) {
        return chosenWindow.getY();
    }

    public int getWindowWidth(JFrame chosenWindow){
        return chosenWindow.getWidth();
    }

    public int getWindowHeight(JFrame chosenWindow){
        return chosenWindow.getHeight();
    }

    public void setWindowSize(int Width, int Height)
    {
        window.setSize(Width,Height);
    }
}