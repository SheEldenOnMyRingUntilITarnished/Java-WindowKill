
/**
 * Write a description of class Projectile here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.Color;
import java.awt.Graphics2D;

public class Projectile extends Object
{
    private double speed;
    private double direction;
    private boolean targetPlayer;
    
    public Projectile(double projectileSpeed, double projectileDirection, boolean projectileTargetPlayer)
    {
        super(0, 0, 10, 10); 
        
        this.speed = projectileSpeed;
        this.direction = projectileDirection;
        this.targetPlayer = projectileTargetPlayer;
    }
    
    @Override
    public void update()
    {
        updatePosition(this.speed * Math.cos((int)this.direction),this.speed * Math.sin(this.direction));
    }
    
    @Override
    public void paint(Graphics2D g2, int windowX, int windowY)
    {
        g2.setColor(Color.RED);
        g2.drawRect(getXPosition() - windowX, getYPosition() - windowY, getXSize(), getYSize());
    }
    
    public double getSpeed()
    {
        return this.speed;
    }
    
    public double getDirection()
    {
        return this.direction;
    }
    
    public boolean getTargetPlayer()
    {
        return this.targetPlayer;
    }
}