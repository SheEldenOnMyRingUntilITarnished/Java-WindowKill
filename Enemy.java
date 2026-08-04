
/**
 * Write a description of class Enemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Enemy extends RidgedBody2D
{    
    private EnemyTypes type;
    private EnemyStats stats;
    private EnemyAI ai;
    private GameSystem gameSystem;
    
    private int targetX;
    private int targetY;
    
    private double accelerationX = 0;
    private double accelerationY = 0;
    
    private double accelerationCap = 6;
    private double friction = 0.15;
    
    private double health;
    private double maxHealth;
    
    public Enemy(EnemyTypes chosenType, EnemyStats chosenStats, EnemyAI chosenAI, GameSystem chosenGameSystem)
    {
        super(30, 30);
        
        this.type = chosenType;
        this.stats = chosenStats;
        this.ai = chosenAI;
        this.gameSystem = chosenGameSystem;
        this.health = chosenStats.getHealth();
        this.maxHealth = chosenStats.getHealth();
        
        if (chosenType == EnemyTypes.SQUARE) {
            setSize(32, 32);
            accelerationCap = 6.0;
            friction = 0.12;
        } else if (chosenType == EnemyTypes.TRIANGLE) {
            setSize(34, 34);
            accelerationCap = 4.5;
            friction = 0.15;
        } else if (chosenType == EnemyTypes.CIRCLE) {
            setSize(24, 24);
            accelerationCap = 8.5;
            friction = 0.30;
        }
    }
    
    @Override
    public void update()
    {
        if (ai != null) {
            ai.updateAI(this, gameSystem);
        }
        
        // Friction
        if (accelerationX > 0) accelerationX = Math.max(0, accelerationX - friction);
        else if (accelerationX < 0) accelerationX = Math.min(0, accelerationX + friction);
        
        if (accelerationY > 0) accelerationY = Math.max(0, accelerationY - friction);
        else if (accelerationY < 0) accelerationY = Math.min(0, accelerationY + friction);
        
        // Cap max speed
        if (accelerationX > accelerationCap) accelerationX = accelerationCap;
        if (accelerationX < -accelerationCap) accelerationX = -accelerationCap;
        if (accelerationY > accelerationCap) accelerationY = accelerationCap;
        if (accelerationY < -accelerationCap) accelerationY = -accelerationCap;
        
        updatePosition(accelerationX, accelerationY);
    }
    
    public void accelerate(double accX, double accY)
    {
        this.accelerationX += accX;
        this.accelerationY += accY;
    }
    
    public void takeDamage(double damageAmount)
    {
        this.health -= damageAmount;
        if (this.health <= 0)
        {
            if (gameSystem != null)
            {
                if (type == EnemyTypes.SQUARE) gameSystem.addScore(10);
                else if (type == EnemyTypes.TRIANGLE) gameSystem.addScore(15);
                else if (type == EnemyTypes.CIRCLE) gameSystem.addScore(25);
            }
            this.destroy();
        }
    }
    
    @Override
    public void paint(Graphics2D g2, int windowX, int windowY)
    {
        int drawX = getXPosition() - windowX;
        int drawY = getYPosition() - windowY;
        int sizeX = getXSize();
        int sizeY = getYSize();
        
        if (type == EnemyTypes.SQUARE)
        {
            g2.setColor(Color.GREEN);
            g2.drawRect(drawX - sizeX / 2, drawY - sizeY / 2, sizeX, sizeY);
        }
        else if (type == EnemyTypes.TRIANGLE)
        {
            g2.setColor(Color.YELLOW);
            double angle = Math.atan2(targetY - getYPosition(), targetX - getXPosition());
            int p1x = drawX + (int)(Math.cos(angle) * (sizeX / 2));
            int p1y = drawY + (int)(Math.sin(angle) * (sizeY / 2));
            int p2x = drawX + (int)(Math.cos(angle + 2.4) * (sizeX / 2));
            int p2y = drawY + (int)(Math.sin(angle + 2.4) * (sizeY / 2));
            int p3x = drawX + (int)(Math.cos(angle - 2.4) * (sizeX / 2));
            int p3y = drawY + (int)(Math.sin(angle - 2.4) * (sizeY / 2));
            
            Polygon trianglePoly = new Polygon(new int[]{p1x, p2x, p3x}, new int[]{p1y, p2y, p3y}, 3);
            g2.draw(trianglePoly);
        }
        else if (type == EnemyTypes.CIRCLE)
        {
            g2.setColor(Color.BLUE);
            g2.drawOval(drawX - sizeX / 2, drawY - sizeY / 2, sizeX, sizeY);
        }
    }
    
    public void updateTargetPosition(int chosenTargetX, int chosenTargetY)
    {
        targetX = chosenTargetX;
        targetY = chosenTargetY;
    }
    
    public EnemyTypes getType() { return type; }
    public EnemyStats getStats() { return stats; }
    public int getTargetX() { return targetX; }
    public int getTargetY() { return targetY; }
}