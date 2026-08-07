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

    GameState gameState = GameState.MAIN_MENU;

    ArrayList<Object> objects = new ArrayList<Object>();

    ArrayList<WindowArea> activeWindows = new ArrayList<WindowArea>();
    Player player = new Player(this);
    EnemyManager enemyManger = new EnemyManager(player);
    InputManager inputManager = player.inputManager;

    WindowPanel window = null;
    
    private boolean wrapping = false;
    
    private int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    
    private int gameTimeFrames = 0;

    public void startGameThread()
    {
        gameThread = new Thread(this);
        loadMainMenu();
        gameThread.start();
    }
    public void loadSettingsMenu()
    {
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;
        
        WindowArea settingsHolderWin = setupWindow("SettingsHolder", 240, 240);
        settingsHolderWin.setWindowPosition(centerX - 180, centerY - 150);
        Button enableWrappingBtn = new Button("ENABLE WRAPPING", 180, 46, (btn) -> {
            toggleWrapping(true);
        });
        enableWrappingBtn.attachToWindow(settingsHolderWin, 120, 80);
        objects.add(enableWrappingBtn);
        
        Button disableWrappingBtn = new Button("DISABLE WRAPPING", 180, 46, (btn) -> {
            toggleWrapping(false);
        });
        disableWrappingBtn.attachToWindow(settingsHolderWin, 120, 160);
        objects.add(disableWrappingBtn);
    }
    public boolean toggleWrapping(boolean enable)
    {
        wrapping = enable;
        return wrapping;
    }
    public boolean getCanWrap()
    {
        return this.wrapping;
    }
    public void loadMainMenu()
    {
        gameState = GameState.MAIN_MENU;
        
        for (int i = 0; i < activeWindows.size(); i++) {
            if (activeWindows.get(i).getWindow() != null) {
                activeWindows.get(i).getWindow().dispose();
            }
        }
        activeWindows.clear();
        objects.clear();
        
        objects.add(inputManager);
        
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;
        WindowArea titleWin = setupWindow("TITLE", 360, 130);
        titleWin.setWindowPosition(centerX - 180, centerY - 150);
        
        WindowArea startWin = setupWindow("START", 240, 130);
        startWin.setWindowPosition(centerX - 250, centerY + 20);
        
        WindowArea settingsWin = setupWindow("SETTINGS", 240, 130);
        settingsWin.setWindowPosition(centerX + 10, centerY + 20);
 
        
        Button startBtn = new Button("START GAME", 180, 46, (btn) -> {
            startGame();
        });
        startBtn.attachToWindow(startWin, 120, 65);
        
        Button settingsBtn = new Button("OPEN SETTINGS", 180, 46, (btn) -> {
            System.out.println("The Tijmen Angers");
            loadSettingsMenu();
        });
        settingsBtn.attachToWindow(settingsWin, 120, 65);
        
        objects.add(startBtn);
        objects.add(settingsBtn);
    }

    private int score = 0;

    public int getScore() {
        return this.score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void resetScore() {
        this.score = 0;
    }

    public void startGame()
    {
        gameState = GameState.GAME;
        resetScore();
        gameTimeFrames = 0;
        
        // Deletes existing menu windows
        for (int i = 0; i < activeWindows.size(); i++) {
            if (activeWindows.get(i).getWindow() != null) {
                activeWindows.get(i).getWindow().dispose();
            }
        }
        activeWindows.clear();
        objects.clear();
        
        objects.add(inputManager);
        objects.add(player);
        player.awake();
        player.start();
        
        enemyManger.setGameSystem(this);
        objects.add(enemyManger);
        
        setupWindow("GAME", 640, 640);
        
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;
        player.setPosition(centerX, centerY);
    }

    public void triggerGameOver()
    {
        gameState = GameState.GAME_OVER;
        
        // Deletes existing gameplay windows
        for (int i = 0; i < activeWindows.size(); i++) {
            if (activeWindows.get(i).getWindow() != null) {
                activeWindows.get(i).getWindow().dispose();
            }
        }
        activeWindows.clear();
        objects.clear();
        
        objects.add(inputManager);
        
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;
        WindowArea gameOverWin = setupWindow("GAME_OVER", 340, 220);
        
        Button restartBtn = new Button("RESTART", 180, 46, (btn) -> {
            startGame();
        });
        restartBtn.attachToWindow(gameOverWin, 170, 105);
        
        Button menuBtn = new Button("MAIN MENU", 180, 46, (btn) -> {
            loadMainMenu();
        });
        menuBtn.attachToWindow(gameOverWin, 170, 162);
        
        objects.add(restartBtn);
        objects.add(menuBtn);
    }

    public WindowArea setupWindow(String chosenWindowType, int chosenWidth, int chosenHeight)
    {
        WindowArea windowArea = new WindowArea(this, chosenWidth, chosenHeight);
        window = windowArea.getGamePanel();
        window.setWindowType(chosenWindowType);
        objects.add(windowArea);
        activeWindows.add(windowArea);
        
        return windowArea;
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
                if (gameState == GameState.GAME)
                {
                    gameTimeFrames++;
                    if (gameTimeFrames % 60 == 0)
                    {
                        score++;
                    }
                }
                
                objectsCollisionCheck();
                updateObjects();
                for(int i = 0; i < activeWindows.size(); i++)
                {
                    activeWindows.get(i).getGamePanel().repaint();
                }
                if(activeWindows.size() > 1)
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
        double angle = Math.atan2(Y2 - Y1, X2 - X1) * 180 / Math.PI;
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
            if (obj.isDestroyed())
            {
                objects.remove(i);
            }
            else
            {
                obj.update(); 
            }
        }
    }
    
    public void objectsCollisionCheck()
    {
        if(objects.size() > 1)
        {
            for(int i = objects.size() - 1; i >= 0; i--)
            {
                if(i < objects.size() && objects.get(i) instanceof RidgedBody2D)
                {
                    RidgedBody2D a = (RidgedBody2D) objects.get(i);
                    
                    if(collisionWithScreenEdgeCheck(a)) 
                    {
                        if(a.screenEdge())
                        {
                            objects.remove(i);
                        }
                        continue;
                    }
                    
                    boolean removed = false;
                    for(int w = 0; w < activeWindows.size(); w++)
                    {
                        WindowArea comparedWindow = activeWindows.get(w);
                        if(collisionWithWindowCheck(a, comparedWindow.getWindow()))
                        {
                            if(a.windowEdge(comparedWindow))
                            {
                                objects.remove(i);
                                removed = true;
                                break;
                            }
                        }
                    }
                    if(removed) continue;
                    
                    for(int j = objects.size() - 1; j >= 0; j--)
                    {
                        if(i != j && i < objects.size() && j < objects.size())
                        {
                            Object b = objects.get(j);
                            if (b instanceof RidgedBody2D || b instanceof UI_Collider)
                            {
                                if(collisionCheck(a, b))
                                {
                                    a.collison(b);
                                    if (b instanceof RidgedBody2D) {
                                        ((RidgedBody2D) b).collison(a);
                                    } else if (b instanceof UI_Collider) {
                                        ((UI_Collider) b).collison(a);
                                    }
                                }
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
        
        if(aX > windowX + windowWidth || aX < windowX ||
        aY > windowY + windowHeight || aY < windowY)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
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
    
    public boolean collisionCheck(Object a, Object b)
    {
        int aX = a.getXPosition();
        int aY = a.getYPosition();
        int aWidth = getObjectWidth(a);
        int aHeight = getObjectHeight(a);

        int bX = b.getXPosition();
        int bY = b.getYPosition();
        int bWidth = getObjectWidth(b);
        int bHeight = getObjectHeight(b);

        if(aX - aWidth/2 < bX + bWidth/2 && aX + aWidth/2 > bX - bWidth/2 &&
        aY - aHeight/2 < bY + bHeight/2 && aY + aHeight/2 > bY - bHeight/2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private int getObjectWidth(Object obj) {
        if (obj instanceof RidgedBody2D) return ((RidgedBody2D) obj).getXSize();
        if (obj instanceof UI_Collider) return ((UI_Collider) obj).getXSize();
        return 0;
    }

    private int getObjectHeight(Object obj) {
        if (obj instanceof RidgedBody2D) return ((RidgedBody2D) obj).getYSize();
        if (obj instanceof UI_Collider) return ((UI_Collider) obj).getYSize();
        return 0;
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
    
    public int getGameTimeFrames()
    {
        return this.gameTimeFrames;
    }
}