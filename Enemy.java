
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
    
    private int targetX;
    private int targetY;
    
    private double accelerationSpeed = 1;
    
    private double accelerationX = 0;
    private double accelerationY = 0;
    
    private double accelerationCap = 6;
    private double friction = 0.1;
    
    public Enemy(EnemyTypes chosenType, EnemyStats chosenStats, EnemyAI chosenAI)
    {
        super(40, 40);
        
        this.type = chosenType;
        this.stats = chosenStats;
        this.ai = chosenAI;
    }
    
    @Override
    public void update()
    {
        
        double speed = stats.getSpeed();
        double angle = Math.atan2(this.targetY - getYPosition(), this.targetX - getXPosition());
        
        accelerationX += Math.round(accelerationSpeed * Math.cos(angle));
        accelerationY += Math.round(accelerationSpeed * Math.sin(angle));
        
        if(accelerationX > 0) accelerationX -= friction;
        else if(accelerationX < 0) accelerationX += friction;
        else if(accelerationY > 0) accelerationY -= friction;
        else if(accelerationY < 0) accelerationY += friction;
        
        if(accelerationX > accelerationCap) accelerationX = accelerationCap;
        else if(accelerationX < -accelerationCap) accelerationX = -accelerationCap;
        else if(accelerationY > accelerationCap) accelerationY = accelerationCap;
        else if(accelerationY < -accelerationCap) accelerationY = -accelerationCap;
        
        updatePosition(Math.round(accelerationX * Math.cos(angle)),Math.round(accelerationY * Math.sin(angle)));
    }
    
    @Override
    public void paint(Graphics2D g2, int windowX, int windowY)
    {
        g2.setColor(Color.GREEN);
        g2.drawRect(getXPosition() - windowX, getYPosition() - windowY, getXSize(), getYSize());
    }
    
    public void updateTargetPosition(int chosenTargetX, int chosenTargetY)
    {
        targetX = chosenTargetX;
        targetY = chosenTargetY;
    }
}