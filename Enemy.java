
/**
 * Write a description of class Enemy here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends RidgedBody2D
{    
    private EnemyTypes type;
    private EnemyStats stats;
    private EnemyAI ai;
    
    public Enemy(EnemyTypes chosenType, EnemyStats chosenStats, EnemyAI chosenAI)
    {
        super(10, 10);
        
        this.type = chosenType;
        this.stats = chosenStats;
        this.ai = chosenAI;
    }
    
    @Override
    public void update()
    {
        
        //int targetX = 
        //int targetY = 
        
        double speed = stats.getSpeed();
        //double angle = Math.atan2(targetY - getYPosition(), targetX - getXPosition());
        
        //this.updatePosition((int)Math.round(speed * Math.cos(angle)), (int)Math.round(speed * Math.sin(angle)));
    }
    
    @Override
    public void paint(Graphics2D g2, int windowX, int windowY)
    {
        g2.setColor(Color.GREEN);
        g2.drawRect(getXPosition() - windowX, getYPosition() - windowY, getXSize(), getYSize());
    }
    
    public void updateEnemy(int targetX, int targetY)
    {
        double speed = stats.getSpeed();
        double angle = Math.atan2(targetY - getYPosition(), targetX - getXPosition());
        
        this.updatePosition((int)Math.round(speed * Math.cos(angle)), (int)Math.round(speed * Math.sin(angle)));
    }
}