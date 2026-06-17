
/**
 * Write a description of class Projectile here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Projectile
{
    private int xPos;
    private int yPos;
    
    private double speed;
    private double direction;
    private boolean targetPlayer;
    
    public Projectile(double projectileSpeed, double projectileDirection, boolean projectileTargetPlayer)
    {
        this.speed = projectileSpeed;
        this.direction = projectileDirection;
        this.targetPlayer = projectileTargetPlayer;
    }
    
    public int getXPosition()
    {
        return xPos;
    }
    
    public int getYPosition()
    {
        return yPos;
    }
    
    public double getSpeed()
    {
        return this.speed;
    }
    
    public double getDirection()
    {
        return this.direction;
    }
    
    public void setPosition(int chosenXPos, int chosenYPos)
    {
        xPos = chosenXPos;
        yPos = chosenYPos;
    }
    
    public void updatePosition(double chosenXPos, double chosenYPos)
    {
        xPos += chosenXPos;
        yPos += chosenYPos;
    }
}