
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
    
    private int speed;
    private int direction;
    private boolean targetPlayer;
    
    public Projectile(int projectileSpeed, int projectileDirection, boolean projectileTargetPlayer)
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
    
    public void setPosition(int chosenXPos, int chosenYPos)
    {
        xPos = chosenXPos;
        yPos = chosenYPos;
    }
    
    public int getSpeed()
    {
        return this.speed;
    }
}